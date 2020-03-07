To see content of requests to PG you should turn ssl off.

```shell script
#cd /etc/postgresql/10/main
#cat postgresql.conf
###here U need line 
# - Security and Authentication -
ssl = false				# (change requires restart)

#service postgres restart
```

### With prepared statement:   
    Frame 29764: 289 bytes on wire (2312 bits), 289 bytes captured (2312 bits) on interface 0
    Linux cooked capture
    Internet Protocol Version 4, Src: 127.0.0.1, Dst: 127.0.0.1
    Transmission Control Protocol, Src Port: 40638, Dst Port: 5432, Seq: 307, Ack: 461, Len: 221
    PostgreSQL
        Type: Parse
        Length: 117
        Statement: S_1
        Query: INSERT INTO DOGS (NAME, DATEOFBIRTH, HEIGHT, WEIGHT) VALUES ( $1, $2, $3, $4 )\nRETURNING *
        Parameters: 4
            Type OID: 1043
            Type OID: 0
            Type OID: 20
            Type OID: 20
    PostgreSQL
        Type: Describe
        Length: 9
        Statement: S_1
    PostgreSQL
        Type: Bind
        Length: 77
        Portal: 
        Statement: S_1
        Parameter formats: 4
            Format: Text (0)
            Format: Text (0)
            Format: Binary (1)
            Format: Binary (1)
        Parameter values: 4
            Column length: 8
            Data: 446f67314e616d65
            Column length: 14
            Data: 323032302d30332d3033202b3033
            Column length: 8
            Data: 0000000000000001
            Column length: 8
            Data: 0000000000000001
        Result formats: 0
    PostgreSQL
        Type: Execute
        Length: 9
        Portal: 
        Returns: all rows
    PostgreSQL
        Type: Sync
        Length: 4
        
Second call:

    Frame 30868: 171 bytes on wire (1368 bits), 171 bytes captured (1368 bits) on interface 0
    Linux cooked capture
    Internet Protocol Version 4, Src: 127.0.0.1, Dst: 127.0.0.1
    Transmission Control Protocol, Src Port: 40638, Dst Port: 5432, Seq: 533, Ack: 702, Len: 103
    PostgreSQL
        Type: Bind
        Length: 87
        Portal: 
        Statement: S_1
        Parameter formats: 4
            Format: Text (0)
            Format: Text (0)
            Format: Binary (1)
            Format: Binary (1)
        Parameter values: 4
            Column length: 8
            Data: 446f67314e616d65
            Column length: 14
            Data: 323032302d30332d3033202b3033
            Column length: 8
            Data: 0000000000000001
            Column length: 8
            Data: 0000000000000001
        Result formats: 5
            Format: Binary (1)
            Format: Text (0)
            Format: Binary (1)
            Format: Binary (1)
            Format: Binary (1)
    PostgreSQL
        Type: Execute
        Length: 9
        Portal: 
        Returns: all rows
    PostgreSQL
        Type: Sync
        Length: 4
        
Seems like magic is here:

```java
/**
 {@link org.postgresql.jdbc.PgPreparedStatement.PgPreparedStatement(org.postgresql.jdbc.PgConnection, java.lang.String, int, int, int)}
*/
 PgPreparedStatement(PgConnection connection, String sql, int rsType, int rsConcurrency,
      int rsHoldability) throws SQLException {
    this(connection, connection.borrowQuery(sql), rsType, rsConcurrency, rsHoldability);
  }
```
so if U close connection U lose your prepared statements as well.

### With common statement:
    
    Frame 9050: 220 bytes on wire (1760 bits), 220 bytes captured (1760 bits) on interface 0
    Ethernet II, Src: 00:00:00_00:00:00 (00:00:00:00:00:00), Dst: 00:00:00_00:00:00 (00:00:00:00:00:00)
    Internet Protocol Version 4, Src: 127.0.0.1, Dst: 127.0.0.1
    Transmission Control Protocol, Src Port: 50966, Dst Port: 5432, Seq: 425, Ack: 425, Len: 154
    PostgreSQL
        Type: Parse
        Length: 118
        Statement: 
        Query: INSERT INTO DOGS (NAME, DATEOFBIRTH, HEIGHT, WEIGHT) VALUES ( 'Dog1Name', '2020-02-29 +03', 1, 1 )\nRETURNING *
        Parameters: 0 
    PostgreSQL
        Type: Bind
        Length: 12
        Portal: 
        Statement: 
        Parameter formats: 0
        Parameter values: 0
        Result formats: 0
    PostgreSQL
        Type: Describe
        Length: 6
        Portal:  
    PostgreSQL
        Type: Execute
        Length: 9
        Portal: 
        Returns: all rows  
    PostgreSQL
        Type: Sync
        Length: 4
        
With JOOQ:
    Frame 26334: 319 bytes on wire (2552 bits), 319 bytes captured (2552 bits) on interface 0
    Linux cooked capture
    Internet Protocol Version 4, Src: 127.0.0.1, Dst: 127.0.0.1
    Transmission Control Protocol, Src Port: 60352, Dst Port: 5432, Seq: 307, Ack: 461, Len: 251
    PostgreSQL
        Type: Parse
        Length: 202
        Statement: S_1
        Query: select "public"."dogs"."id", "public"."dogs"."name", "public"."dogs"."dateofbirth", "public"."dogs"."height", "public"."dogs"."weight" from "public"."dogs" where "public"."dogs"."id" = $1
        Parameters: 1
            Type OID: 23
    PostgreSQL
        Type: Bind
        Length: 25
        Portal: 
        Statement: S_1
        Parameter formats: 1
            Format: Binary (1)
        Parameter values: 1
            Column length: 4
            Data: 00000001
        Result formats: 0
    PostgreSQL
        Type: Describe
        Length: 6
        Portal: 
    PostgreSQL
        Type: Execute
        Length: 9
        Portal: 
        Returns: all rows
    PostgreSQL
        Type: Sync
        Length: 4


