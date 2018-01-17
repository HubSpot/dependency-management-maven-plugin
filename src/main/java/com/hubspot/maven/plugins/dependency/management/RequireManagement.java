package com.hubspot.maven.plugins.dependency.management;

import java.util.ArrayList;
import java.util.List;

public class RequireManagement implements RequireManagmentConfig {
  private boolean dependencies = false;
  private boolean plugins = false;
  private boolean allowVersions = true;
  private boolean allowExclusions = true;
  private String unmanagedDependencyMessage = null;
  private String dependencyVersionMismatchMessage = null;
  private String unmanagedPluginMessage = null;
  private String pluginVersionMismatchMessage = null;
  private String dependencyExclusionsMessage = null;
  private String dependencyVersionDisallowedMessage = null;

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

  @Override
  public String unmanagedDependencyMessage() {
    return unmanagedDependencyMessage;
  }

  public void setUnmanagedDependencyMessage(String unmanagedDependencyMessage) {
    this.unmanagedDependencyMessage = unmanagedDependencyMessage;
  }

  @Override
  public String dependencyVersionMismatchMessage() {
    return dependencyVersionMismatchMessage;
  }

  public void setDependencyVersionMismatchMessage(String dependencyVersionMismatchMessage) {
    this.dependencyVersionMismatchMessage = dependencyVersionMismatchMessage;
  }

  @Override
  public String unmanagedPluginMessage() {
    return unmanagedPluginMessage;
  }

  public void setUnmanagedPluginMessage(String unmanagedPluginMessage) {
    this.unmanagedPluginMessage = unmanagedPluginMessage;
  }

  @Override
  public String pluginVersionMismatchMessage() {
    return pluginVersionMismatchMessage;
  }

  public void setPluginVersionMismatchMessage(String pluginVersionMismatchMessage) {
    this.pluginVersionMismatchMessage = pluginVersionMismatchMessage;
  }

  @Override
  public String dependencyExclusionsMessage() {
    return dependencyExclusionsMessage;
  }

  public void setDependencyExclusionsMessage(String dependencyExclusionsMessage) {
    this.dependencyExclusionsMessage = dependencyExclusionsMessage;
  }

  @Override
  public String dependencyVersionDisallowedMessage() {
    return dependencyVersionDisallowedMessage;
  }

  public void setdependencyVersionDisallowedMessage(String dependencyVersionDisallowedMessage) {
    this.dependencyVersionDisallowedMessage = dependencyVersionDisallowedMessage;
  }
}
