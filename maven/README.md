# [Maven](https://github.com/qala-io/java-course/blob/master/docs/programme/maven.md)

##Step 1

* Dependencies and transitive dependencies [Introduction to the Dependency Mechanism](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html)
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
* Super and Effective POMs 
    * [Introduction to the POM](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html)
    * [The POM](https://books.sonatype.com/mvnref-book/reference/pom-relationships-sect-pom.html)
    * *Project Object Model* 0_o
    * *Super POM* like an Object class in java, each POM extend it
    * *Effective POM* - what you really have after all inheritance including Super POM
    * `mvn help:effective-pom` - or just use IDEA
    
* Packaging:
    * default packaging type is 'jar'
    * [Package-specific Lifecycles](https://books.sonatype.com/mvnref-book/reference/lifecycle-sect-package-specific.html)
    * declare what you gonna have and how (executed during lifecycle plugins)

***
Refs:
* [Java Professionals Course](https://github.com/qala-io/java-course)
* [Apache Maven Project](https://maven.apache.org/index.html)
* [Maven: The Complete Reference](https://books.sonatype.com/mvnref-book/reference/index.html)
 
    