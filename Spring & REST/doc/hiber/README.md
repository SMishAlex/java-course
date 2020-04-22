## Hibernate [[0.33]](https://docs.jboss.org/hibernate/core/3.3/reference/en/html/objectstate.html#objectstate-overview) [[0.43]](https://docs.jboss.org/hibernate/core/4.3/manual/en-US/html/)
### Hibernate Proxy
Hibernate generates uninitialized proxy using CGlib. [[1]](https://www.baeldung.com/hibernate-proxy-load-method) 

 Something like that: 
```java
public class HibernateProxy extends MyEntity {
    private MyEntity target;
 
    public String getFirstName() {
        if (target == null) {
            target = readFromDatabase();
        }
        return target.getFirstName();
    }
}
```
`OneToMany` and `ManyToOne` will be loaded depending on the fetching strategy.
 
 [another good link](https://vladmihalcea.com/how-does-a-jpa-proxy-work-and-how-to-unproxy-it-with-hibernate/)
 
[entity lifecycle diagram](./entity-lifecycle.puml) [[2]](https://vladmihalcea.com/a-beginners-guide-to-jpa-hibernate-entity-state-transitions/) [[3]](https://dzone.com/articles/jpa-entity-lifecycle) [[3]](https://www.baeldung.com/hibernate-entity-lifecycle)