### Communication contract
- How my microservice request data?
    - In this app, RabbitMQ is the data passing tool between microservices. 
    - So that we don't have to request data in an explicit way.
    - We both send data by one of our microservice(producer).
- How my microservice receive data?
    - My microservice(consumer) will subscribe Stephen's RabbitMQ, 
    - If there are new data passed by Stephen's microservice(his producer), my consumer will receive the data
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


