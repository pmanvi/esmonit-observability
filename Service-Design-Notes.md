#Design Notes
***
> Design is all about choices we make. It's contextual, there is no absolute rule.
##Package Design
**_Organization by Responsibility Vs Contexts_**
* Interfaces - How you can deliver your software.
Ex:REST, Swagger
* Application - orchestration and the transactional scopes Ex: <some>Manager
* Domain - bounded contexts, biz rules. Ex: Workflow, validations
* Infrastructure - supporting for other layers.
Ex: SQL, i18n, Bulk, search

##Configuring logs and capturing context 
**_It is important to capture context information while logging and the log level_**

##SOLID principle for class design
- 'S'ingle Responsibility - Have One reason for change
- 'O'pen/Close -  Open to add but Closed of changes
- 'L'iskov’s Substitution - Substitutes can always replace parents.
- I'nterface Segregation - Prefer granular interfaces
- 'D'ependency Inversion -  Make changing easier

##Designing Exceptions
* Checked exceptions reached language design dead-end. Spring.Hibernate, Kotlin, Scala all proves that point
* Exception handling in code is a smell. rely on return types internally. 
* Its top level framework, UI should handle the exceptions. 
* Exceptions should be limited to logic errors (client mistakes), system failures.

##Logging Exceptions
**_Use slf4j facade for logging_**
* Its as simple as including @Slf4j annotation with lombok.
* Use placeholders to avoid string concatenation and avoid printStackTrace statements, Pick correct log levels.
* Ex -
```Java
log.debug("message1 {} and {} message2 {} ", msg1, msg2);
```
and not
```Java
logger.debug("message1 "+msg1+"and message2 "+ msg2");
```
use
```Java
log.warn("ignoring irrelevant functional role: {} on order: {}", roleName, t.getOrder().getSalesOrderId());
```
and not
```Java
logger.warn("ignoring irrelevant functional role:" + roleName + " on order:" + t.getOrder().getSalesOrderId());
```
use
```Java
log.severe("Error occured while persisting ticketsearch cache for tenant: {} ", tenant, e);
```
and not
```Java
e.printStackTrace();
```

LOGGER.error("Error occured while persisting ticketsearch cache for tenant: " + tenant);
The logger reference is not a constant, but a final reference, and should NOT be in uppercase.  Use just 'log' for the variable name and be consistent everywhere.

Do not catch generic Exception OR worse allow leakage of exceptions. Use multi-catch to avoid verbosity. Avoid deep exception hierarchies
Ex:
```Java
catch (FileNotFoundException | IOException ex) {
log.warn("found error while getting Property Category Data for Production System", ex);
}
```
and not,eating up the exception and including it in log is the same crime as Nullpointer exception. Context is lost.
```Java
catch (Exception ex) {
        log.warn("found error while getting Property Category Data for Production System");
}
```
Do not log exceptions, errors if they are being thrown back unless there is some contextual information logged. It introduces lot of unnecessary noise in log search tools (Kibana, Splunk) and blows up the log size as stack traces generally are very large.
Do not log huge payload as part of the log, Use kafka if the huge payload is really required to be logged. Please note that gateway has mechanism to log the requests and responses (optionally). It will make ELK unstable.

Use guard to log the time for DB operations that are sensitive to data size and query type - Use StopWatch to collect and log as warn once it crosses the threshold (say 100 milliseconds)

##Managing fault tolerance, Resiliance, Alternate flows
- Circuit-breaker -> failed attempts crosses threshold, eagerly reject all subsequent ones.
- Bulkhead  -> limit the number of concurrent calls
- Retry -> auto-retry of failed call
- RateLimiter -> limiting access

##Guideline for logging levels
Info level will only output information that is important to know e.g. some service (not spring service) was started.
Debug level will output information that is needed to track the correct flow of code.
Warning to warn that something didn't happen as expected but the system can still operate.
The error will be used to indicate that the system can't work.
Pay close attention the log messages from kibana, messages should result in some actions OR provide some insights else they are not worth it.Reviewing the logs should lead to the cause of any errors that may have occurred. The logs should be easy to navigate without having to filter through irrelevant information adding logs for developer unit testing should be avoided. (loops, method entry/exit etc...)

Following examples are explained with rationale and taskmanager to help make informed decisions.
level (targeted audience)

log.debug (support, enable optionally from users for diagnosing operational issues)
Fine-grained information that is useful to programmer useful for investigation.
Ex:
```Java
log.debug("Ticket Search {} ", ticketSearch);
log.debug("Db trigger event {} ", dbEvent);
```
log.info (administrators for auditing)
Changes such as configuration settings, entry/exit points for importance processes like scheduled jobs running, services starting and stopping and changes to data.
Ex:
```Java
log.info("Broker cache initialized successfully");
log.info("Kafka broker {} was removed", broker.getId());
```
* log.warn (automated systems, operators, and administrators)
Any condition that, while not an error in itself, may indicate that the system is running sub-optimally. Warnings should highlight an abnormal or unexpected event in the application flow. This event does not cause the application execution to stop. invalid authentication, higher memory/disk usage etc...
Ex:
```Java
log.warn("Metric processing is taking {} , higher than threshold {} (millis)", processedTime, THRESHOLD_PREDEFINED_TIME );
```
  
* log.error (monitoring systems and devOps)
any message with condition that indicates something has gone wrong with the system. Generally, all exceptions should be logged with the error, that also says do not use exceptions for logic but in exceptional cases only.
Make sure to log the error to get a complete stack trace. These should indicate a failure in the current activity, not an application-wide failure. These will mainly be unhandled exceptions and recoverable failures.
Ex:
```Java
log.error("Unable to read Redis controller data", e);
```
* log.severe (EveryOne)
A critical issue that should describe an unrecoverable application or system crash, or a catastrophic failure that requires immediate attention.

* log.trace(No Audience)
Not sure of its relevant use cases, use debug where-ever you think trace is applicable.

###Javadoc
http://blog.joda.org/search?q=javadoc

###Guidelines for managing timezones in services and stores
**_In general business logic, data transfer, data storage, and serialization should be in UTC. Client applications (Browsers, API Clients) should apply a local time zone conversion(local being defined by the user looking at the data) for viewing as well as while querying. Do not use 'SYSDATE' OR 'java.util.Date()' while storing dates. Consider application time zone while converting to UTC. In general, the intention should be understood well. (MS Calendar, Google Calendar) If an inclination is to shift my application OR current client machine time zone to where it is currently getting operated client time should be considered. WIth UTC all the aggregation and sort will work. Also store the time-zone ( name, timestamp and the offset) where-ever application requires a particular timezone._**


###Hello Kotlin?
1. Kotlin 
```Kotlin
fun main() = println("Hello Kotlin")
```
2. Java
```Java
public class HelloWorld {
    public static void main(String[] args) {
            System.out.println("Hello Java");
        }
}
```
|Name |Email|Github|
|----|-----|-------|  
|Praveen | pmanvi@outlook.com | github.com/pmanvi |



