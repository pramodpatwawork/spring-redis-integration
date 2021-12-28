# spring-redis-integration

## Node setup architecute on 3 machines 
 
* Redis ideal recommonds 1 machine for each node in production. 
* POC considers 3 machines.
* Cluster works only when atleast one node from each shard is functional. Example Master A, Slave B, Master C are working. 

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
  
  
