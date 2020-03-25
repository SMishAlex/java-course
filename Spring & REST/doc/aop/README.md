## AOP

Aspect-orientated programming - programming approach with extracting common non-business functional (e.g. logging, auditing, security) to the aspects.

Terms:
* Pointcut - en expression with predicate `Join Point` -> is `Advice` should be applied.
* Join Point - a point during program execution, generally call of the method
* Advise - The logic that should be applied on particular `Join point`. Might have following types:
    * Before
    * After (like finally)
    * After throwing (like catch)
    * After returning
    * Around (like proxy)
* Aspect - Logic that applied across multiple classes
* Weaving - linking an `Aspect` with other application logic, might be at: 
    * compile time - in the time of building an artifact
    * post-compile - U also can weave an existing jars: 
    ```xml
    <configuration>
        <weaveDependencies>
            <weaveDependency>  
                <groupId>org.agroup</groupId>
                <artifactId>to-weave</artifactId>
            </weaveDependency>
            <weaveDependency>
                <groupId>org.anothergroup</groupId>
                <artifactId>gen</artifactId>
            </weaveDependency>
        </weaveDependencies>
    </configuration>
    ``` 
    * Load-time - aspects weaved in time of class loading, to allow it there should be weaving class loader. Can be provided by environment or as agent:
    ```
  -javaagent:"${settings.localRepository}"/org/aspectj/
              aspectjweaver/${aspectj.version}/
              aspectjweaver-${aspectj.version}.jar
    ```