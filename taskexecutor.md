## Spring Framework

### Scheduling ans Acync TaskExecutor

```xml
xmlns:task="http://www.springframework.org/schema/task
        http://www.springframework.org/schema/task
        http://www.springframework.org/schema/task/spring-task-3.0.xsd


<task:annotation-driven scheduler="myScheduler"  executor="taskExecutor"/>
    <task:scheduler id="myScheduler" pool-size="10"/>
    <task:executor id="taskExecutor"
                   pool-size="5-25"
                   queue-capacity="100"/>

``` 


ScheduledJob -> PrintWorker;

##### reference
* http://krams915.blogspot.kr/2011/01/spring-3-task-scheduling-via.html
* http://www.childc.co.kr/629
* https://blog.outsider.ne.kr/1066