package com.hubspot.maven.plugins.dependency.management;

import java.util.ArrayList;
import java.util.List;

public class RequireManagement {
  private boolean dependencies = false;
  private boolean plugins = false;
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

  public List<String> getExceptions() {
    return exceptions;
  }

  public void setExceptions(List<String> exceptions) {
    this.exceptions = exceptions;
  }
}
