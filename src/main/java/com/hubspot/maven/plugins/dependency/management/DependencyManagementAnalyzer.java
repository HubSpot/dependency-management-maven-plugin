package com.hubspot.maven.plugins.dependency.management;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.SelectorUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependencyManagementAnalyzer {
  private final MavenProject project;
  private final RequireManagement requireManagement;
  private final Log log;

  public DependencyManagementAnalyzer(MavenProject project, RequireManagement requireManagement, Log log) {
    this.project = project;
    this.requireManagement = requireManagement;
    this.log = log;
  }

  public boolean analyze() {
    boolean success = checkDependencyManagement();
    // don't combine with previous line, we don't want short-circuit evaluation
    success &= checkPluginManagement();

    return success;
  }

  private boolean checkDependencyManagement() {
    Map<String, Dependency> managedDependencies = getManagedDependenciesAsMap();
    Map<String, Dependency> originalDependencies = getOriginalDependenciesAsMap();

    boolean success = true;
    for (Dependency projectDependency : project.getDependencies()) {
      String dependencyKey = projectDependency.getManagementKey();
      Dependency managedDependency = managedDependencies.get(dependencyKey);
      Dependency originalDependency = originalDependencies.get(dependencyKey);

      if (managedDependency != null) {
        String projectVersion = projectDependency.getVersion();
        String managedVersion = managedDependency.getVersion();

        if (!projectVersion.equals(managedVersion)) {
          String errorFormat = "Version mismatch for %s, managed version %s does not match project version %s";
          log.warn(String.format(errorFormat, dependencyKey, managedVersion, projectVersion));
          success = false;
        } else if (originalDependency != null) {
          if (!requireManagement.allowVersions() && originalDependency.getVersion() != null) {
            log.warn(String.format("Version tag must be removed for managed dependency %s", dependencyKey));
            success = false;
          }

          if (!requireManagement.allowExclusions() && !originalDependency.getExclusions().isEmpty()) {
            log.warn(String.format("Exclusions must be removed for managed dependency %s", dependencyKey));
            success = false;
          }
        }
      } else if (requireManagement.requireDependencyManagement() && !ignored(dependencyKey)) {
        log.warn(String.format("Dependency %s is not managed", dependencyKey));
        success = false;
      }
    }

    return success;
  }

  private boolean checkPluginManagement() {
    Map<String, Plugin> managedPlugins = project.getPluginManagement().getPluginsAsMap();

    boolean success = true;
    for (Plugin projectPlugin : project.getBuildPlugins()) {
      Plugin managedPlugin = managedPlugins.get(projectPlugin.getKey());

      if (managedPlugin != null) {
        String projectVersion = projectPlugin.getVersion();
        String managedVersion = managedPlugin.getVersion();

        if (!projectVersion.equals(managedVersion)) {
          String errorFormat = "Version mismatch for plugin %s, managed version %s does not match project version %s";
          log.warn(String.format(errorFormat, projectPlugin.getKey(), managedVersion, projectVersion));
          success = false;
        }
      } else if (requireManagement.requirePluginManagement() && !ignored(projectPlugin.getKey())) {
        log.warn(String.format("Plugin %s is not managed", projectPlugin.getKey()));
        success = false;
      }
    }

    return success;
  }

  private Map<String, Dependency> getManagedDependenciesAsMap() {
    if (project.getDependencyManagement() == null || project.getDependencyManagement().getDependencies() == null) {
      return Collections.emptyMap();
    } else {
      return asMap(project.getDependencyManagement().getDependencies());
    }
  }

  private Map<String, Dependency> getOriginalDependenciesAsMap() {
    if (project.getOriginalModel() == null || project.getOriginalModel().getDependencies() == null) {
      return Collections.emptyMap();
    } else {
      return asMap(project.getOriginalModel().getDependencies());
    }
  }

  private boolean ignored(String key) {
    if (key.indexOf(':') != key.lastIndexOf(':')) {
      key = key.substring(0, key.indexOf(':', key.indexOf(':') + 1));
    }

    for (String exception : requireManagement.getExceptions()) {
      if (SelectorUtils.match(exception, key)) {
        return true;
      }
    }

    return false;
  }

  private static Map<String, Dependency> asMap(List<Dependency> dependencies) {
    Map<String, Dependency> dependencyMap = new HashMap<>();
    for (Dependency dependency : dependencies) {
      dependencyMap.put(dependency.getManagementKey(), dependency);
    }

    return dependencyMap;
  }
}
