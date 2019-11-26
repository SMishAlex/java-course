# [Maven](https://github.com/qala-io/java-course/blob/master/docs/programme/maven.md)

##### Dependencies and transitive dependencies [Introduction to the Dependency Mechanism](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html)
* nearest definition by default
* `dependencyManagement` section to manage versions directly
* `scope` - where dependency is visible in terms of maven lifecycle:
    * `compile` - default, for classPath and depended projects;
    * `provided` - expect dependency from JDK or from container, not transitive
    * `runtime` - not required for compilation, contains in runtime classPath
    * `test` - test compilation and execution
    * `system` - take the flash drive and bring this jar artifact to me. (`<systemPath>${java.home}/lib/rt.jar</systemPath>`)
    * `import` - for 'pom' type only, when you already have parent but want someones dependency definitions
        
* `exclusion` - guess what :-)
* `optional` = excluded by default
* use something from transitive dependency in the project? - add as directly dependency!
* [`mvn dependency:tree`](https://maven.apache.org/plugins/maven-dependency-plugin/examples/resolving-conflicts-using-the-dependency-tree.html)
##### Super and Effective POMs 
* [Introduction to the POM](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html)
* [The POM](https://books.sonatype.com/mvnref-book/reference/pom-relationships-sect-pom.html)
* *Project Object Model* 0_o
* *Super POM* like an Object class in java, each POM extend it
* *Effective POM* - what you really have after all inheritance including Super POM
* `mvn help:effective-pom` - or just use IDEA
    
##### Packaging:
* default packaging type is 'jar'
* [Package-specific Lifecycles](https://books.sonatype.com/mvnref-book/reference/lifecycle-sect-package-specific.html)
* declare what you gonna have and how (executed during lifecycle plugins)
   
    
##### Plugins
* maven knows only how to read POM and download dependencies, all other work made by plugins.
* plugins has `goals` - kind of functions
* `goals` can be mapped to the lifecycle `phases`
* [Lifecycle phases](https://github.com/apache/maven/blob/master/maven-core/src/test/resources/META-INF/plexus/components.xml)
* [Default plugins binding](https://github.com/qala-io/java-course/blob/master/docs/programme/articles/maven-plugins.md)
* [It is possible to override plugin binding](https://stackoverflow.com/questions/7821152/disable-a-maven-plugin-defined-in-a-parent-pom)
* Effective POM contains lifecycle description as well
    
##### Debugging
* You [can](https://maven.apache.org/surefire/maven-surefire-plugin/examples/debugging.html) debug your tests with `maven-surefire-plugin`
* `mvnDebug` allows debug maven core or plugins
* (to see maven plugin code in idea you can open it's POM and export maven project %))

##### Inheritance and Aggregation
* [Multi-module vs. Inheritance](https://books.sonatype.com/mvnref-book/reference/pom-relationships-sect-pom-best-practice.html#pom-relationships-sect-multi-vs-inherit)
    * Inheritance - if you'd like to inherit some other POMs:
       * dependencies
       * developers and contributors
       * plugin lists (including reports)
       * plugin executions with matching ids
       * plugin configuration
       * resources
    * Multi-module - if you'd like to cascade execution of lifecycle, phase, or even goal on submodules recursively.
    
        > if a Maven command is invoked against the parent project, that Maven command will then be executed to the parent's modules as well. To do Project Aggregation, you must do the following:
        >     * Change the parent POMs packaging to the value "pom".
        >     * Specify in the parent POM the directories of its modules (children POMs).
    * By default, Maven looks for the parent POM first at project’s root, then the local repository, and lastly in the remote repository. If parent POM file is not located in any other place, then you can use code tag. This relative path shall be relative to project root.
    ```$xml
   <parent>
          <artifactId>step4</artifactId>
          <groupId>com.epam.java-cource.sid.maven</groupId>
          <version>1.0</version>
        <relativePath>../baseapp/pom.xml</relativePath>
    </parent>
    ```
* [Reactor](https://maven.apache.org/guides/mini/guide-multiple-modules.html)
    * Collects all the available modules to build
    * Sorts the projects into the correct build order (the reactor sorts all the projects in a way that guarantees any project is built before it is required.
      
      The following relationships are honoured when sorting projects:
                                                       
       * a project dependency on another module in the build
       * a plugin declaration where the plugin is another module in the build
       * a plugin dependency on another module in the build
       * a build extension declaration on another module in the build
       * the order declared in the <modules> element (if no other rule applies)
       * `dependencyManagement` and `pluginManagement` elements do not cause a change to the reactor sort order)
    * Builds the selected projects in order
    * Command Line Options
        * `--resume-from` - resumes a reactor the specified project (e.g. when it fails in the middle)
        * `--also-make` - build the specified projects, and any of their dependencies in the reactor
        * `--also-make`-dependents - build the specified projects, and any that depend on them
        * `--fail-fast` - the default behavior - whenever a module build fails, stop the overall build immediately
        * `--fail-at-end` - if a particular module build fails, continue the rest of the reactor and report all failed modules at the end instead
        * `--non-recursive` - do not use a reactor build, even if the current project declares modules and just build the project in the current directory
#### Dependencies

-| compile | provided | runtime | test
:---:|:---:|:---:|:---:|:---:
Available during compilation|V|V|-|-
Included into final binary|V|-|V|-
Available during test compilation and execution|V|V|V|V

lib|scope
:---:|:---:
JDBC Drivers| `runtime`
JUnit, TestNG, Mockito | `test`
slf4j-api| `compile`
slf4j-binding | [can be `runtime`](https://dzone.com/articles/adding-slf4j-your-maven)
lombok|[ should be `provided`](https://stackoverflow.com/questions/29385921/maven-scope-for-lombok-compile-vs-provided)

***
Refs:
* [Java Professionals Course](https://github.com/qala-io/java-course)
* [Apache Maven Project](https://maven.apache.org/index.html)
* [Maven: The Complete Reference](https://books.sonatype.com/mvnref-book/reference/index.html)
 
    