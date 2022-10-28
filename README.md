# String Generator API

## How to run

To connect to your local Postgres database you may need to modify 'application.properties' file (task/src/main/resources) with your login and password.

### Open command prompt, navigate to project's root directory (task) and execute:

```bash
# Navigate to 'task' directory
$ cd task

# To run an app:
$ mvn spring-boot:run

# or from 'task' directory navigate to 'target' directory and run .jar file
$ cd target
$ java -jar .\task-0.0.1-SNAPSHOT.jar

# To run tests; in root directory execute:
$ mvn clean test
```

## Endpoints

### POST:

http://localhost:8080/api/input

body example in JSON format:
```
{
    "minLength": 1,
    "maxLength": 5,
    "str": "abcdef",
    "numberOfStrings": 10
}
```

### GET:

http://localhost:8080/api/input - Returns currently running requests

http://localhost:8080/api/input/{inputId} - Returns requested strings, if the request is completed


