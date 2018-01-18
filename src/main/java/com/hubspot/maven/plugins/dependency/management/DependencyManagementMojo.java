package com.hubspot.maven.plugins.dependency.management;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

@Mojo(name = "analyze", requiresDependencyCollection = ResolutionScope.TEST, threadSafe = true)
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

    boolean success = new DependencyManagementAnalyzer(project, requireManagement, fail, getLog()).analyze();
    if (success) {
      getLog().info("No dependency management issues found");
    } else if (fail) {
      throw new MojoExecutionException("Dependency management issues found");
    }
  }
}
