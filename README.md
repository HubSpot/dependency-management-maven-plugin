# dependency-management-plugin

Maven Plugin Mojo for validating that the versions in dependency management and plugin management match the resolved versions

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
    <version>0.1</version>
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
    <version>0.1</version>
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
            <exceptions>
              <exception>com.example:*</exception>
            </exceptions>
          </requireManagement>
        </configuration>
      </execution>
    </executions>
  </plugin>
  ...
</plugins>
```
