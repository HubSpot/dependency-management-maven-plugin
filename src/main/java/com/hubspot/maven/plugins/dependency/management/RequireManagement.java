package com.hubspot.maven.plugins.dependency.management;

import java.util.ArrayList;
import java.util.List;

public class RequireManagement implements RequireManagmentConfig {
  private boolean dependencies = false;
  private boolean plugins = false;
  private boolean allowVersions = true;
  private boolean allowExclusions = true;
  private List<RequireManagementOverride> overrides = new ArrayList<>();

  @Override
  public boolean requireDependencyManagement() {
    return dependencies;
  }

  public void setDependencies(boolean dependencies) {
    this.dependencies = dependencies;
  }

  @Override
  public boolean requirePluginManagement() {
    return plugins;
  }

  public void setPlugins(boolean plugins) {
    this.plugins = plugins;
  }

  @Override
  public boolean allowVersions() {
    return allowVersions;
  }

  public void setAllowVersions(boolean allowVersions) {
    this.allowVersions = allowVersions;
  }

  @Override
  public boolean allowExclusions() {
    return allowExclusions;
  }

  public void setAllowExclusions(boolean allowExclusions) {
    this.allowExclusions = allowExclusions;
  }

  public List<RequireManagementOverride> getOverrides() {
    return overrides;
  }

  public void setOverrides(List<RequireManagementOverride> overrides) {
    this.overrides = overrides;
  }
}
