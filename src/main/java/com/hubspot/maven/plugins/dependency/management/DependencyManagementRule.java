package com.hubspot.maven.plugins.dependency.management;

import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;

public class DependencyManagementRule implements EnforcerRule {

  private RequireManagement requireManagement = new RequireManagement();

  @Override
  public void execute(EnforcerRuleHelper helper) throws EnforcerRuleException {
    final MavenProject project;
    try {
      project = (MavenProject) helper.evaluate("${project}");
    } catch (ExpressionEvaluationException e) {
      throw new EnforcerRuleException("Unable to resolve Maven Project", e);
    }

    boolean success = new DependencyManagementAnalyzer(project, requireManagement, helper.getLog()::warn).analyze();
    if (!success) {
      throw new EnforcerRuleException("Dependency management issues found");
    }
  }

  @Override
  public boolean isCacheable() {
    return false;
  }

  @Override
  public boolean isResultValid(EnforcerRule cachedRule) {
    return false;
  }

  @Override
  public String getCacheId() {
    return null;
  }
}
