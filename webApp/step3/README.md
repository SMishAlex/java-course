## Load test result

I have the following PC specifications:

CPU: Intel® Core™ i5-7300U CPU @ 2.60GHz × 4 (2 physical)
RAM: 15,5 GB
JDK: 1.8.0_171-b11
OS: Ubuntu 19.10

#### single thread
```$xml
<Executor name="tomcatThreadPool" namePrefix="catalina-exec-"
          maxThreads="1" minSpareThreads="1"/>

<Connector executor="tomcatThreadPool"
                 port="8080" protocol="HTTP/1.1"
                 acceptCount="1"
                 connectionTimeout="20000"
                 redirectPort="8443"/>
```
totalCallsNumber	|clientNumber	|time per single request (ms)	
:---:|:---:|:---:
1000	|100	|203.399
1000	|200	|709.423
1000	|400	|380.107
1000	|800	|800.055
2000	|100	|160.5105
2000	|200	|308.224

I was not patient enough to wait for other results...

#### 10 threads
```$xml
<Executor name="tomcatThreadPool" namePrefix="catalina-exec-"
          maxThreads="10" minSpareThreads="1"/>

<Connector executor="tomcatThreadPool"
                 port="8080" protocol="HTTP/1.1"
                 acceptCount="1"
                 connectionTimeout="20000"
                 redirectPort="8443"/>
```
totalCallsNumber	|clientNumber	|time per single request (ms)	
:---:|:---:|:---:
1000	|100	|128.592
1000	|200	|197.632

#### 100 threads
```$xml
<Executor name="tomcatThreadPool" namePrefix="catalina-exec-"
          maxThreads="100" minSpareThreads="1"/>
```

totalCallsNumber	|clientNumber	|time per single request (ms)	
:---:|:---:|:---:
1000	|100	|5.089
1000	|200	|358.908
1000	|400	|553.992

something strange...

#### 100 threads, acceptCount=100
```$xml
<Executor name="tomcatThreadPool" namePrefix="catalina-exec-"
          maxThreads="100" minSpareThreads="1"/>
<Connector executor="tomcatThreadPool"
           port="8080" protocol="HTTP/1.1"
           acceptCount="100"
           connectionTimeout="20000"
           redirectPort="8443"/>
```
totalCallsNumber	|clientNumber	|time per single request (ms)	
:---:|:---:|:---:
1000	|100	|1.378
1000	|200	|2.57
1000	|400	|1.317
1000	|800	|0.603
2000	|100	|0.2765
2000	|200	|0.794
2000	|400	|0.755
2000	|800	|0.913
4000	|100	|0.14925
4000	|200	|0.34425
4000	|400	|0.36175
4000	|800	|0.382
8000	|100	|0.132125
8000	|200	|0.228375
8000	|400	|0.244
8000	|800	|0.427
16000	|100	|0.165375
16000	|200	|0.1750625
16000	|400	|0.21575
16000	|800	|0.285375

wow!


#### 100 threads, acceptCount=100, minSpareThreads=4
```$xml
<Executor name="tomcatThreadPool" namePrefix="catalina-exec-"
          maxThreads="100" minSpareThreads="4"/>
<Connector executor="tomcatThreadPool"
           port="8080" protocol="HTTP/1.1"
           acceptCount="100"
           connectionTimeout="20000"
           redirectPort="8443"/>
```

totalCallsNumber	|clientNumber	|time per single request (ms)	
:---:|:---:|:---:
1000	|100	|0.975
1000	|200	|2.347
1000	|400	|1.356
1000	|800	|0.996
2000	|100	|0.3075
2000	|200	|0.6975
2000	|400	|0.811
2000	|800	|0.789
4000	|100	|0.11375
4000	|200	|0.288
4000	|400	|0.78175
4000	|800	|0.688
8000	|100	|0.11175
8000	|200	|0.240125
8000	|400	|0.40025
8000	|800	|0.404
16000	|100	|0.121625
16000	|200	|0.1763125
16000	|400	|0.168
16000	|800	|0.46875

#### 40 threads, acceptCount=100, minSpareThreads=4
```$xml
<Executor name="tomcatThreadPool" namePrefix="catalina-exec-"
          maxThreads="40" minSpareThreads="4"/>
<Connector executor="tomcatThreadPool"
           port="8080" protocol="HTTP/1.1"
           acceptCount="100"
           connectionTimeout="20000"
           redirectPort="8443"/>
```
totalCallsNumber	|clientNumber	|time per single request (ms)	
:---:|:---:|:---:
1000	|100	|1.006
1000	|200	|1.684
1000	|400	|1.46
1000	|800	|0.642
2000	|100	|0.2
2000	|200	|0.7645
2000	|400	|0.7925
2000	|800	|1.133
4000	|100	|0.1355
4000	|200	|0.35625
4000	|400	|0.32775
4000	|800	|0.4025
8000	|100	|0.126
8000	|200	|0.153125
8000	|400	|0.199625
8000	|800	|0.238625
16000	|100	|0.149875
16000	|200	|0.27075
16000	|400	|0.207
16000	|800	|0.494125

#### 40 threads, acceptCount=800, minSpareThreads=4
```$xml
<Executor name="tomcatThreadPool" namePrefix="catalina-exec-"
          maxThreads="40" minSpareThreads="4"/>
<Connector executor="tomcatThreadPool"
           port="8080" protocol="HTTP/1.1"
           acceptCount="800"
           connectionTimeout="20000"
           redirectPort="8443"/>
```
totalCallsNumber	|clientNumber	|time per single request (ms)	
:---:|:---:|:---:
1000	|100	|0.887
1000	|200	|1.665
1000	|400	|1.293
1000	|800	|0.659
2000	|100	|0.177
2000	|200	|0.6575
2000	|400	|0.7005
2000	|800	|1.616
4000	|100	|0.1515
4000	|200	|0.37875
4000	|400	|0.31925
4000	|800	|0.41025
8000	|100	|0.10575
8000	|200	|0.21675
8000	|400	|0.196
8000	|800	|0.20375
16000	|100	|0.122625
16000	|200	|0.1555
16000	|400	|0.2059375
16000	|800	|0.2046875

#### 40 threads, acceptCount=default (100), minSpareThreads=4
```$xml
<Executor name="tomcatThreadPool" namePrefix="catalina-exec-"
          maxThreads="40" minSpareThreads="4"/>
<Connector executor="tomcatThreadPool"
           port="8080" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8443"/>
```

totalCallsNumber	|clientNumber	|time per single request (ms)	
:---:|:---:|:---:
1000	|100	|0.856
1000	|200	|1.753
1000	|400	|1.323
1000	|800	|0.566
2000	|100	|0.1775
2000	|200	|0.712
2000	|400	|0.686
2000	|800	|0.718
4000	|100	|0.11525
4000	|200	|0.37475
4000	|400	|0.368
4000	|800	|0.4355
8000	|100	|0.129625
8000	|200	|0.252375
8000	|400	|0.192125
8000	|800	|0.24825
16000	|100	|0.1066875
16000	|200	|0.1671875
16000	|400	|0.1999375
16000	|800	|0.31

## Let's play with really hard work =)
```$java
  @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(200);
        reallyHardWork();
        PrintWriter writer = resp.getWriter();
        writer.println("Hello servlet world!");
    }

    private void reallyHardWork() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
```

#### 40 threads, acceptCount=default (100), minSpareThreads=4

totalCallsNumber	|clientNumber	|time per single request (ms)	
:---:|:---:|:---:
1000	|100	|25.481
1000	|200	|34.272
1000	|400	|33.532
1000	|800	|125.434

#### 40 threads, acceptCount=800, minSpareThreads=4

totalCallsNumber	|clientNumber	|time per single request (ms)	
:---:|:---:|:---:
1000	|100	|26.995
1000	|200	|34.736
1000	|400	|57.548

#### 100 threads, acceptCount=800, minSpareThreads=4
totalCallsNumber	|clientNumber	|time per single request (ms)	
:---:|:---:|:---:
1000	|100	|10.798
1000	|200	|10.1
1000	|400	|11.768
1000	|800	|11.032
2000	|100	|10.0565

#### MAX_INT threads (2147483647), acceptCount=800, minSpareThreads=4

totalCallsNumber	|clientNumber	|time per single request (ms)	
:---:|:---:|:---:
1000	|100	|10.622
1000	|200	|5.304
1000	|400	|3.102
1000	|800	|2.049
2000	|100	|10.045
2000	|200	|5.0335
2000	|400	|2.606
2000	|800	|1.5585
4000	|100	|10.01825
4000	|200	|5.016
4000	|400	|2.55425
4000	|800	|1.31025
8000	|100	|10.008875
8000	|200	|5.01425
8000	|400	|2.542625
8000	|800	|1.309
16000	|100	|10.013125
16000	|200	|5.0769375
16000	|400	|2.522
16000	|800	|1.3068125

#### no thread pool, acceptCount=default (100)

totalCallsNumber	|clientNumber	|time per single request (ms)	
:---:|:---:|:---:
1000	|100	|10.707
1000	|200	|5.22
1000	|400	|6.063
1000	|800	|8.913
2000	|100	|10.077
2000	|200	|5.07
2000	|400	|7.6325
2000	|800	|7.8035
4000	|100	|10.01725
4000	|200	|5.03875
4000	|400	|10.03075
4000	|800	|14.40275
8000	|100	|10.00925
8000	|200	|5.0155
8000	|400	|6.990625
8000	|800	|13.5695
16000	|100	|10.00825
16000	|200	|5.012125
16000	|400	|6.866625
16000	|800	|8.576375






