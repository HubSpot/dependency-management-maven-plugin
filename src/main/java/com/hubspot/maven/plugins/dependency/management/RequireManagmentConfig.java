package com.hubspot.maven.plugins.dependency.management;

public interface RequireManagmentConfig {
  boolean requireDependencyManagement();
  boolean requirePluginManagement();
  boolean allowVersions();
  boolean allowExclusions();
  String unmanagedDependencyMessage();
  String dependencyVersionMismatchMessage();
  String unmanagedPluginMessage();
  String pluginVersionMismatchMessage();
  String dependencyExclusionsMessage();
  String dependencyVersionDisallowedMessage();
}
