# dependency-management-plugin

Maven Plugin Mojo for validating that the versions in dependency management match the resolved dependency versions

### Available parameters

* skip

    Skips plugin execution entirely. Defaults to false.

* fail

    Whether to fail the build if any mismatches are found.  Defaults to false.

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
