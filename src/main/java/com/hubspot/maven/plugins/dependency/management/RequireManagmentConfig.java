package com.hubspot.maven.plugins.dependency.management;

public interface RequireManagmentConfig {
  boolean requireDependencyManagement();
  boolean requirePluginManagement();
  boolean allowVersions();
  boolean allowExclusions();
  String unManagedDependencyMessage();
  String dependencyVersionMismatchMessage();
  String unManagedPluginMessage();
  String pluginVersionMismatchMessage();
  String dependencyExclusionsMessage();
  String dependencyVersionDisallowedMessage();
}
