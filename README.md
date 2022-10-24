# Edrone-Recruitment-Task

## How to run

To connect to your local Postgres database you may need to modify application.properties file with your login and password.

## Open command prompt and execute:

```bash
### To run an app:
$ mvn spring-boot:run

### To run tests:
$ mvn clean test
```

# Endpoints

## GET:

http://localhost:8080/api/input - Returns currently running requests
http://localhost:8080/api/input/{inputId} - Returns requested strings, if request is completed

## Post:

http://localhost:8080/api/input

JSON example:

{
    "minLength": 1,
    "maxLength": 5,
    "str": "abcdef",
    "numberOfStrings": 10
}
