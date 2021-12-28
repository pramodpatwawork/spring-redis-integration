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

1. Make copy of redis.conf using command "cp redis.conf redis-slave.conf"
2. Find tag port and change port from 6379 to 6380.
3. Find tag replicaof, uncomment and put host port of master node (replicaof 127.0.0.1 6379).
4. Go to redis root directory and make copies (2 for our case) of file sentinel.conf, "cp sentinel.conf sentinel1.conf"
5. Open file sentinel1.conf.
6. Find tag sentinel-down-after-milliseconds, and change value from 30000 to 10000 (optional, after this time election of master node will happen)
7. Review setting "review monitor mymaster 127.0.0.1 6379 2", last parameter will decide quorum,miimum node value to elect master.
8. Find tag port and change value from 26379 to 26380 .
9. Repeat steps from 5 to 8 for sentinel2 (value of sentinel port will be 26381). 
10. Change configuration of sentinel.conf as well if needed (step no 6 atleast).
11. Start master server using command ./$REDIS_ROOT/src/redis-server $REDIS_ROOT/redis.conf
12. Start Slave server using command ./$REDIS_ROOT/src/redis-server $REDIS_ROOT/redis-slave1.conf
13. Start sentinel using command ./$REDIS_ROOT/src/redis-sentinel $REDIS_ROOT/sentinel.conf, repeat this step for sentinel1.conf and sentinel2.conf

