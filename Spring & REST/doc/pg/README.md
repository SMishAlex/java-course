To see content of requests to PG you should turn ssl off.

```shell script
#cd /etc/postgresql/10/main
#cat postgresql.conf
###here U need line 
# - Security and Authentication -
ssl = false				# (change requires restart)

#service postgres restart
```
<details>
<summary>With prepared statement:</summary>

<details>
        <summary>Frame</summary>
        <blockquote>
     ~~~
    Frame 10: 278 bytes on wire (2224 bits), 278 bytes captured (2224 bits) on interface 0
     Ethernet II, Src: 00:00:00_00:00:00 (00:00:00:00:00:00), Dst: 00:00:00_00:00:00 (00:00:00:00:00:00)
     Internet Protocol Version 4, Src: 127.0.0.1, Dst: 127.0.0.1
     Transmission Control Protocol, Src Port: 50966, Dst Port: 5432, Seq: 1, Ack: 1, Len: 212
         Source Port: 50966
         Destination Port: 5432
         [Stream index: 1]
         [TCP Segment Len: 212]
         Sequence number: 1    (relative sequence number)
         [Next sequence number: 213    (relative sequence number)]
         Acknowledgment number: 1    (relative ack number)
         1000 .... = Header Length: 32 bytes (8)
         Flags: 0x018 (PSH, ACK)
         Window size value: 512
         [Calculated window size: 512]
         [Window size scaling factor: -1 (unknown)]
         Checksum: 0xfefc [unverified]
         [Checksum Status: Unverified]
         Urgent pointer: 0
         Options: (12 bytes), No-Operation (NOP), No-Operation (NOP), Timestamps
         [SEQ/ACK analysis]
         [Timestamps]
         TCP payload (212 bytes)
         [PDU Size: 115]
         [PDU Size: 75]
         [PDU Size: 7]
         [PDU Size: 10]
         [PDU Size: 5]
     ~~~
     </blockquote>
    </details>
    <details>
    <blockquote>
~~~
     PostgreSQL
         Type: Parse
         Length: 114
         Statement: 
         Query: INSERT INTO DOGS (NAME, DATEOFBIRTH, HEIGHT, WEIGHT) VALUES ( $1, $2, $3, $4 )\nRETURNING *
         Parameters: 4
             Type OID: 1043
             Type OID: 0
             Type OID: 20
             Type OID: 20
~~~
    </blockquote>
    </details>
    <details>
    <blockquote>
    ~~~
     PostgreSQL
         Type: Bind
         Length: 74
         Portal: 
         Statement: 
         Parameter formats: 4
             Format: Text (0)
             Format: Text (0)
             Format: Binary (1)
             Format: Binary (1)
         Parameter values: 4
             Column length: 8
             Data: 446f67314e616d65
             Column length: 14
             Data: 323032302d30322d3239202b3033
             Column length: 8
             Data: 0000000000000001
             Column length: 8
             Data: 0000000000000001
         Result formats: 0
    ~~~
    </blockquote>
    </details>
    <details>
    <blockquote>
    ~~~
     PostgreSQL
         Type: Describe
         Length: 6
         Portal: 
        ~~~
    </blockquote>
    </details>
    <details>
    <blockquote>
    ~~~
     PostgreSQL
         Type: Execute
         Length: 9
         Portal: 
         Returns: all rows
    ~~~
    </blockquote>
    </details>
    <details>
    <blockquote>
    ~~~
     PostgreSQL
         Type: Sync
         Length: 4
    ~~~
    </blockquote>
    </details>
</details>

<details>
    <summary>With common statement:</summary>
    <details>
    <blockquote>
    ~~~
    Frame 9050: 220 bytes on wire (1760 bits), 220 bytes captured (1760 bits) on interface 0
    Ethernet II, Src: 00:00:00_00:00:00 (00:00:00:00:00:00), Dst: 00:00:00_00:00:00 (00:00:00:00:00:00)
    Internet Protocol Version 4, Src: 127.0.0.1, Dst: 127.0.0.1
    Transmission Control Protocol, Src Port: 50966, Dst Port: 5432, Seq: 425, Ack: 425, Len: 154
    ~~~
    </blockquote>
    </details>
    <details>
    <blockquote>
    ~~~
    PostgreSQL
        Type: Parse
        Length: 118
        Statement: 
        Query: INSERT INTO DOGS (NAME, DATEOFBIRTH, HEIGHT, WEIGHT) VALUES ( 'Dog1Name', '2020-02-29 +03', 1, 1 )\nRETURNING *
        Parameters: 0
    ~~~
    </blockquote>
    </details>
    <details>
    <blockquote>
    ~~~
    PostgreSQL
        Type: Bind
        Length: 12
        Portal: 
        Statement: 
        Parameter formats: 0
        Parameter values: 0
        Result formats: 0
    ~~~
    </blockquote>
    </details>
    <details>
    <blockquote>
    ~~~
    PostgreSQL
        Type: Describe
        Length: 6
        Portal:  
    ~~~
    </blockquote>
    </details>
    <details>
    <blockquote>
    ~~~
    PostgreSQL
        Type: Execute
        Length: 9
        Portal: 
        Returns: all rows
    ~~~
    </blockquote>
    </details>
    <details>
    <blockquote>
    ~~~
    PostgreSQL
        Type: Sync
        Length: 4
    ~~~
    </blockquote>
    </details>
</details>


