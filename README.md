### Communication contract
- How my microservice request data?
    - In our apps, RabbitMQ is the data passing tool between microservices. So that we don't have to request data in an explicit way.
    - We both send data by one of our microservice(producer).
- How my microservice receive data?
    - My microservice(consumer) will subscribe Stephen's RabbitMQ, 
    - If there are new data passed by Stephen's microservice(his producer), my consumer will receive the data
```
// Send message to or subscribe to my MQ by Java
ConnectionFactory factory = new ConnectionFactory();
factory.setUsername("hui");
factory.setPassword("HuiOSU");

// Send message to or subscribe to Stephens MQ by Java
ConnectionFactory factory = new ConnectionFactory();
factory.setUsername("stephen");
factory.setPassword("StephenOSU");

// amqps string for Java and JavaScript
amqps://stephen:StephenOSU@b-e01a602c-5662-4ffe-a635-733e9ba114fe.mq.us-west-2.amazonaws.com:5671
amqps://hui:HuiOSU@b-e01a602c-5662-4ffe-a635-733e9ba114fe.mq.us-west-2.amazonaws.com:5671

```
- The format of the data between microservices
```
{
   "username":"Hui Zhang",
   "email":"zhangh9@oregonstate.edu",
   "expenses":{
      "rent":"1000",
      "food":"200",
      "game":"80"
   }
}
```

### UML sequence diagram
![](https://github.com/HuiZhangOSU/super-blog/blob/master/UML%20sequence%20diagram.png)

