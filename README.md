# Getting Started

### build project

```shell
gradlew clean build
```

### run project

```shell
gradlew bootRun
```

### weather endpoint

```shell
curl -X GET 'http://localhost:8080/weather?city=Melbourne&country=AU' -H 'API_KEY: key1'
```

### landing page

[http://localhost:8080](http://localhost:8080)

### Open Api Docs

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### h2 Database (as cache)

h2 in-memory database is being used as cache here. any successful response from api.openweathermap.org is being stored
in h2. Any subsequent request for same city and country combination will get data from h2 rather than
api.openweathermap.org.

### Api Key rate limit logic

ConcurrentHashMap stores request **count** and **duration window** for each request

for Incoming request
> **if** duration is more than 1 hour from first request
>> then reset request count and duration window and **allow**
>
> **else**
> > **if** count is <= 5 then increase count and **allow**
>
> > **else** reject request as **too many request**

**count** and **duration window** are customizable from properties file

```
apiKeyTimeWindowInSeconds: 360 (1 hour)
apiKeyAllowedCountWithinTimeWindow: 5
```

**API_KEY** needs to passed as a *header* in request. for now allowed api keys are configurable through property file.
Allowed values are -

- key1
- key2
- key3
- key4
- key5

### Improvements

* e2e Integration test could have been added using wiremock or spring cloud contract

> As of now Integration test rely on api.openweathermap.org to be available

* more code level documentation can be added.