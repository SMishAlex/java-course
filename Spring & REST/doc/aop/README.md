## AOP

### JDK Dynamic proxy

U can create dynamic proxy for classes that implements interface (or multiple) with standard JDK proxy.

The main thing here is InvocationHandler interface that allow U to wrap invocation of any method of proxied object.

This is also Functional Interface so U can declare it as lambda.

The signature of invocation handler method: 

```java
/**
* See {@link java.lang.reflect.InvocationHandler}
*/
public Object invoke(Object proxy, Method method, Object[] args)
       throws Throwable;
```

### CGLib



### AspectJ

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
  
### Spring AOP:

The CGLib proxy for classes that has interface could be turned on with:
```xml
<aop:config proxy-target-class="true">
    ...
</aop:config>
```
By default for that classes JDK dynamic proxy will be used, which means that even public methods that doesn't overrides interface method will not be proxied.
(e.g. [question](https://stackoverflow.com/questions/51795511/when-will-is-cglib-proxy-used-by-spring-aop?rq=1))
