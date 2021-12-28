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

## Steps to create cluster using script

1. Visit to page https://redis.io/download
2. Download redis from browser or wget command.
3. Follow the instructions mentioned bellow on website, snip of step needs to be followed is:
```
wget https://download.redis.io/releases/redis-6.2.6.tar.gz
tar xzf redis-6.2.6.tar.gz
cd redis-6.2.6
make
make test (optional step)
```
