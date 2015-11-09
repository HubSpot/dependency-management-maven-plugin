package com.hubspot.maven.plugins.dependency.management;

import java.util.ArrayList;
import java.util.List;

public class RequireManagement {
  private boolean dependencies = false;
  private boolean plugins = false;
  private boolean allowVersions = true;
  private boolean allowExclusions = true;
  private List<String> exceptions = new ArrayList<>();

  public boolean requireDependencyManagement() {
    return dependencies;
  }

  public void setDependencies(boolean dependencies) {
    this.dependencies = dependencies;
  }

  public boolean requirePluginManagement() {
    return plugins;
  }

  public void setPlugins(boolean plugins) {
    this.plugins = plugins;
  }

  public boolean allowVersions() {
    return allowVersions;
  }

  public void setAllowVersions(boolean allowVersions) {
    this.allowVersions = allowVersions;
  }

  public boolean allowExclusions() {
    return allowExclusions;
  }

  public void setAllowExclusions(boolean allowExclusions) {
    this.allowExclusions = allowExclusions;
  }

  public List<String> getExceptions() {
    return exceptions;
  }

  public void setExceptions(List<String> exceptions) {
    this.exceptions = exceptions;
  }
}
