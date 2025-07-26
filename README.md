# Zadanie domowe
- Config ma sie odświeżać bez restartu aplikacji
#### Potencjalne rozwiązania
- AD 1: Actuator (manualnie)
- AD 2: Webhook
- AD 3: Scheduler cyklicznie co 15 sekund (poprzez endpoint actuatora)


# Notatki
-  Repo -> https://github.com/pkozimor/spring-cloud-config-training/blob/main/application.properties

## Serwisy
### eureka
- http://localhost:8761/ -> serwisy zarejestrowane w Eurece
### config-server
- http://localhost:8888/config-client/default -> tutaj domyślnie pokazuje wszystkie informacje zawarte w config-serwerze
### ms1
- http://ip:port/test -> test endpointa
- http://ip:port/actuator/refresh


### Rozwiązanie zadania
1) Actuator -> puszczam request 
[refresh-config.http](ms1/refresh-config.http)
2) Scheduler -> skonfigurowany w klasie RefreshConfig -> odpala się co 15 sekund
