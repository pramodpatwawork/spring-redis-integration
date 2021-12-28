# spring-redis-integration (Spring boot redis cluster application)

## Node setup architecute on 3 machines 
 
* Redis ideally recommonds 1 machine for each node in production. 
* POC considers 6 nodes on 3 machines.
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

## Steps to install redis

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
5. clone and install spring boot project (branch sentinel demo) and test with url http://localhost:8080/students/1, 
6. First time program will take time to return result second time cached response will be return so will be immediate, after 5 minutes cache will automatically will be destroyed

```

```
* To stop cluster use command "./create-cluster stop"
* To destroy cluster use command "./create-cluster clean"

```

## Create redis cluster manually

* Follow steps from site  https://iamvishalkhare.medium.com/create-a-redis-cluster-faa89c5a6bb4, snip of steps that I followed are 

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
Create cluster of mster nodes using redis cli

./$REDIS_ROOT/redis-cli --cluster create 127.0.0.1:30001 127.0.0.1:30002 127.0.0.1:30003 --cluster-replicas 0

Get clusters's id using command 

./$REDIS_ROOT/src/redis-cli -c -p 30001 cluster nodes

Command will show ** <cluster id> <ip>:port@pid <master/slave info> **
```

```
Add all slave nodes to specific master node (see architecture diagram above) using bellow command, specify master id based on output coming from above step 

./$REDIS_ROOT/redis-cli --cluster add-node 127.0.0.1:30004 127.0.0.1:30001 --cluster-slave --cluster-master-id 3c3a0c74aae0b56170ccb03a76b60cfe7dc1912e

Test cluster using cli, "./$REDIS_ROOT/src/redis-cli -c -p 30001", "set foo bar", "get foo"

You can now run application and can test with cluster.

```



