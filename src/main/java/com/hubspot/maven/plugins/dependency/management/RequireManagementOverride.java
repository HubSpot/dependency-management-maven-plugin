package com.hubspot.maven.plugins.dependency.management;

import java.util.ArrayList;
import java.util.List;

public class RequireManagementOverride {
  private List<String> exceptions = new ArrayList<>();
  private Boolean dependencies = null;
  private Boolean plugins = null;
  private Boolean allowVersions = null;
  private Boolean allowExclusions = null;

  public List<String> getExceptions() {
    return exceptions;
  }

  public void setExceptions(List<String> exceptions) {
    this.exceptions = exceptions;
  }

  public void setDependencies(boolean dependencies) {
    this.dependencies = dependencies;
  }

  public void setPlugins(boolean plugins) {
    this.plugins = plugins;
  }

  public void setAllowVersions(boolean allowVersions) {
    this.allowVersions = allowVersions;
  }

  public void setAllowExclusions(boolean allowExclusions) {
    this.allowExclusions = allowExclusions;
  }

  public RequireManagmentConfig toRequireManagementConfig(final RequireManagement requireManagement) {
    return new RequireManagmentConfig() {
      @Override
      public boolean requireDependencyManagement() {
        return fallbackIfNull(dependencies, requireManagement.requireDependencyManagement());
      }

      @Override
      public boolean requirePluginManagement() {
        return fallbackIfNull(plugins, requireManagement.requirePluginManagement());
      }

      @Override
      public boolean allowVersions() {
        return fallbackIfNull(allowVersions, requireManagement.allowVersions());
      }

      @Override
      public boolean allowExclusions() {
        return fallbackIfNull(allowExclusions, requireManagement.allowExclusions());
      }
    };
  }

  private static boolean fallbackIfNull(Boolean b1, boolean b2) {
    return b1 != null ? b1 : b2;
  }
}
