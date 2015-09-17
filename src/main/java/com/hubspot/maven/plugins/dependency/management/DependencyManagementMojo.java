package com.hubspot.maven.plugins.dependency.management;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.SelectorUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mojo(name = "analyze", requiresDependencyResolution = ResolutionScope.TEST)
public class DependencyManagementMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project}", readonly = true, required = true)
  private MavenProject project;

  @Parameter
  private RequireManagement requireManagement = new RequireManagement();

  @Parameter(defaultValue = "false")
  private boolean fail;

  @Parameter(defaultValue = "false")
  private boolean skip;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (skip) {
      getLog().info("Skipping plugin execution");
      return;
    }

    boolean success = checkDependencyManagement();
    // don't combine with previous line, we don't want short-circuit evaluation
    success &= checkPluginManagement();

    if (success) {
      getLog().info("No dependency management issues found");
    } else if (fail) {
      throw new MojoExecutionException("Dependency management issues found");
    }
  }

  private boolean checkDependencyManagement() {
    Map<String, Dependency> managedDependencies = asMap(project.getDependencyManagement().getDependencies());

    boolean success = true;
    for (Dependency projectDependency : project.getDependencies()) {
      String dependencyKey = projectDependency.getManagementKey();
      Dependency managedDependency = managedDependencies.get(dependencyKey);

      if (managedDependency != null) {
        String projectVersion = projectDependency.getVersion();
        String managedVersion = managedDependency.getVersion();

        if (!projectVersion.equals(managedVersion)) {
          String errorFormat = "Version mismatch for %s, managed version %s does not match project version %s";
          getLog().warn(String.format(errorFormat, dependencyKey, managedVersion, projectVersion));
          success = false;
        }
      } else if (requireManagement.requireDependencyManagement() && !ignored(dependencyKey)) {
        getLog().warn(String.format("Dependency %s is not managed", dependencyKey));
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
          getLog().warn(String.format(errorFormat, projectPlugin.getKey(), managedVersion, projectVersion));
          success = false;
        }
      } else if (requireManagement.requirePluginManagement() && !ignored(projectPlugin.getKey())) {
        getLog().warn(String.format("Plugin %s is not managed", projectPlugin.getKey()));
        success = false;
      }
    }

    return success;
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
