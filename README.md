# spring-redis-integration (high availability through sentinel)

## Overview

* High availability in small redis setup can be achived using sentinel.
* Sentinel monitors redis if master goes down then quorum of 2 sentinel promote one of slave as master.
* Spring boot client will connect to sentinel and internally library will establish connection with master node.
* Architecture of POC looks like 

```
                +----------+  
                | CLIENT   |
                |          |
                +----------+ 
		    |
		    |
  +----------+  +----------+  +----------+ 
  |Sentinel  |  |Sentinel  |  |Sentinel  |
  |    A     |  |    B     |  |    C     |
  +----------+  +----------+  +----------+
     |             |             |
     |-------------|-------------+ 
     |             |
  +----------+  +----------+   
  |  NODE    |  |  NODE    |
  |    A     |  |    B     |
  +----------+  +----------+

```

## Redis installation
