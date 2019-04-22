# Online Data Analytics API
Submit data for analysis

# Running Locally
Use MariaDB or MySQL and create a database `neural2`. Grant all privileges to `mariago` identified by `mariawent`, or change `application.properties` accordingly.

`mvn clean package -DskipTests`

IntelliJ: edit your run configuration and in VM options specify '-Dspring.profiles.active=local'

Run the application: `Application.java`

[http://localhost:8080]()

### API Documentation (Swagger)

[http://localhost:8080/swagger-ui.html]()

#### fire

```
curl -v -X POST -H "Content-Type: application/json" -d '{"categoryId": 555, "description": "new data", "rawCsvData": "111,222,333"}' "http://localhost:8080/fire"
```

```
curl -v -X GET -H "Content-Type: application/json" "http://localhost:8080/fire/1"

```
