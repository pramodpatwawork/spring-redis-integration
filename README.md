# spring-redis-integration

## Node setup architecute on 3 machines 
 
* Redis ideal recommonds 1 machine for each node in production. 
* POC considers 3 machines.
* Cluster works only when atleast one node from each shard is functional. Example Master A, Slave B, Master C are working. 
```
   Machine A     Machine B     Machine C  
  +----------+  +----------+  +----------+ 
  |  Master  |  |  Master  |  |  Master  |
  |    A     |  |    B     |  |    C     |
  +----------+  +----------+  +----------+
       |             |             |
       |             |             |
  +----------+  +----------+  +----------+ 
  |  Slave   |  |  Slave   |  |  Slave   |
  |    B     |  |    C     |  |    A     |
  +----------+  +----------+  +----------+
  
```

## Steps to install cluster

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

## Steps to create cluster from script (30001 to 30006, for testing purpose)

* Step will allow to create 6 node cluster for testing purpose.
* Nodes port will be from 30001 to 30006
```
1. Go to directory $REDIS_ROOT/utils/create-cluster
2. execute script "./create-cluster start"
3. execute script "./create-cluster create" and type yes and enter
4. Test cluster using cli, "./$REDIS_ROOT/src/redis-cli -c -p 30001", "set foo bar", "get foo"
5. You can now run application and can test wit cluster.
```

```
* To stop cluster use command "./create-cluster stop"
* To destroy cluster use command "./create-cluster clean"

```

## Create redis cluster manually

1. Follow steps from site  https://iamvishalkhare.medium.com/create-a-redis-cluster-faa89c5a6bb4, snip of steps that I followed are 

```
Create 6 redis configurations with command "cp $REDIS_ROOT/redis.conf $REDIS_ROOT/redis-node1.conf" (were value node1 will be changed for each node) and change with bellow values

port 30001
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes
```
```
After change start 3 master servers with command "./$REDIS_ROOT/redis-server $REDIS_ROOT/redis-node1.conf" (from node1 to node3)

output for each server should have text *Running in cluster mode*
```

```
Create cluster using redis cli

./$REDIS_ROOT/redis-cli --cluster create 127.0.0.1:30001 127.0.0.1:30002 127.0.0.1:30003 --cluster-replicas 0

```
