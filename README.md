# Homework - Spring Cloud 3.4 - Training03 - Configuration
- Config should be refreshed without restarting the application

# Notes
-  Config Repo -> https://github.com/pkozimor/spring-cloud-3-4-homework-training03-configuration/blob/main/application.properties

## Services
### eureka-server
- http://localhost:8761/ -> services withing eureka-server
### config-server
- http://localhost:8888/config-client/default -> by default all properties for profile `default` within config-server
### ms1
- http://ip:port/test -> test endpoint
- http://ip:port/actuator/refresh -> refresh config (manually)

### Solution
- AD 1: Actuator Endpoint(refresh config manually) -> [refresh-config.http](ms1/refresh-config.http)
- AD 2: Scheduler defined within RefreshConfig.java updates config each 15 seconds (through actuator endpoint)