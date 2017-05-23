# dependency-management-plugin

Maven Plugin for validating that the versions in dependency management and plugin management match the resolved versions. Can be
run as a standalone plugin or as a rule with the maven-enforcer-plugin.

### Available parameters

* skip

    Skips plugin execution entirely. Defaults to false.

* fail

    Whether to fail the build if any errors are found.  Defaults to false.

* requireManagement

    Wrapper for configuration to make dependency and/or plugin management required. See [here](#full-usage-example) for an example

### Minimal usage example

```xml
<plugins>
  ...
  <plugin>
    <groupId>com.hubspot.maven.plugins</groupId>
    <artifactId>dependency-management-plugin</artifactId>
    <version>0.4</version>
    <executions>
      <execution>
        <goals>
          <goal>analyze</goal>
        </goals>
        <phase>validate</phase>
      </execution>
    </executions>
  </plugin>
  ...
</plugins>
```

### Full usage example

```xml
<plugins>
  ...
  <plugin>
    <groupId>com.hubspot.maven.plugins</groupId>
    <artifactId>dependency-management-plugin</artifactId>
    <version>0.4</version>
    <executions>
      <execution>
        <goals>
          <goal>analyze</goal>
        </goals>
        <phase>validate</phase>
        <configuration>
          <fail>true</fail>
          <requireManagement>
            <dependencies>true</dependencies>
            <plugins>true</plugins>
            <allowVersions>false</allowVersions>
            <allowExclusions>false</allowExclusions>
            <overrides>
              <override>
                <exceptions>
                  <exception>com.example:*</exception>
                </exceptions>
                <dependencies>false</dependencies>
              </override>
            </overrides>
          </requireManagement>
        </configuration>
      </execution>
    </executions>
  </plugin>
  ...
</plugins>
```

### Enforcer plugin example

```xml
<plugins>
  ...
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-enforcer-plugin</artifactId>
    <version>1.4</version>
    <dependencies>
      <dependency>
        <groupId>com.hubspot.maven.plugins</groupId>
        <artifactId>dependency-management-plugin</artifactId>
        <version>0.4</version>
      </dependency>
    </dependencies>
    <executions>
      <execution>
        <goals>
          <goal>enforce</goal>
        </goals>
        <configuration>
          <fail>true</fail>
          <rules>
            <dependencyManagementRule implementation="com.hubspot.maven.plugins.dependency.management.DependencyManagementRule">
              <requireManagement>
                <dependencies>true</dependencies>
                <plugins>true</plugins>
                <allowVersions>false</allowVersions>
                <allowExclusions>false</allowExclusions>                
                <exceptions>
                  <exception>com.example:*</exception>
                </exceptions>                
              </requireManagement>
            </dependencyManagementRule>
          </rules>
        </configuration>
      </execution>
    </executions>
  </plugin>
  ...
</plugins>
```
