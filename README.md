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

### Steps to install redis

1. Install make using command on ubantu "sudo apt-get update" , "sudo apt-get install make". Please refer post to install make in case of any issue faced
2. Visit to page https://redis.io/download
3. Download redis from browser or wget command.
4. Follow the instructions mentioned bellow on website, snip of step needs to be followed is:
```
wget https://download.redis.io/releases/redis-6.2.6.tar.gz
tar xzf redis-6.2.6.tar.gz
cd redis-6.2.6
make
make test (optional step)
```

## Configuration

* Make copy of redis.conf using command "cp redis.conf redis-slave.conf"
* Find tag port and change port from 6379 to 6380.
* Find tag replicaof, uncomment and put host port of master node (replicaof 127.0.0.1 6379).
