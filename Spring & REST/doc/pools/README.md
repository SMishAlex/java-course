## Network firewall polices
 * `Deny` - (or `DROP`) drop the traffic without any response, client will be wait for connection timeout.
 If connection timeout is huge it can affect client. 
 * `Reject` - the message about connection rejection will be sent to the requester.
 
 This actually means that if U use `Reject` everyone can scan network and find U'r machine connection.
 Probably it's better to use it only for private network communications.  
 
 ---
 [[1]](https://kb.zyxel.com/KB/searchArticle!gwsViewDetail.action?articleOid=012926&lang=EN)
 
 ===
 [Tomcat database pool](https://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html) <p></p>
 [c3p0](https://www.mchange.com/projects/c3p0/)
 
 Pools configuration comparing:
 
 |c3p0 property| tomcat DB pool property|
 |:-----------:|:----------------------:|
 |driverClass                |driverClassName|
 |jdbcUrl                    |url|
 |user                       |user|
 |password                   |password|
 |maxPoolSize                |maxActive (maxIdle?)|
 |minPoolSize                |initialSize (minIdle?)|
 |maxStatements              |poolPreparedStatements + maxOpenPreparedStatements<sup>*</sup>|
 |maxStatementsPerConnection ||
 |testConnectionOnCheckout   |testOnBorrow (will retry), testOnConnect (will throw an SQLException)|
 |testConnectionOnCheckin    |testOnReturn|
 |idleConnectionTestPeriod   |testWhileIdle + timeBetweenEvictionRunsMillis|
 |checkoutTimeout            |maxWait|
 |unreturnedConnectionTimeout|removeAbandoned + removeAbandonedTimeout|
 |debugUnreturnedConnectionStackTraces|logAbandoned|
 
<sup>*</sup> - These properties not used according to documentation however there is org.apache.tomcat.jdbc.pool.interceptor.StatementCache with attribute `max`.
 