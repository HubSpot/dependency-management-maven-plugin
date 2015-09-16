package com.hubspot.maven.plugins.dependency.management;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mojo(name = "analyze", requiresDependencyResolution = ResolutionScope.TEST)
public class DependencyManagementMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project}", readonly = true, required = true)
  private MavenProject project;

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

    Map<String, Dependency> managedDependencies = asMap(project.getDependencyManagement().getDependencies());

    boolean mismatch = false;
    for (Dependency projectDependency : project.getDependencies()) {
      String dependencyKey = projectDependency.getManagementKey();
      Dependency managedDependency = managedDependencies.get(dependencyKey);

      if (managedDependency != null) {
        String projectVersion = projectDependency.getVersion();
        String managedVersion = managedDependency.getVersion();

        if (!projectVersion.equals(managedVersion)) {
          String errorFormat = "Version mismatch for %s, managed version %s does not match project version %s";
          getLog().warn(String.format(errorFormat, dependencyKey, managedVersion, projectVersion));
          mismatch = true;
        }
      }
    }

    if (mismatch) {
      if (fail) {
        throw new MojoExecutionException("Dependency management issues found");
      }
    } else {
      getLog().info("No dependency management issues found");
    }
  }

  private static Map<String, Dependency> asMap(List<Dependency> dependencies) {
    Map<String, Dependency> dependencyMap = new HashMap<>();
    for (Dependency dependency : dependencies) {
      dependencyMap.put(dependency.getManagementKey(), dependency);
    }

    return dependencyMap;
  }
}
