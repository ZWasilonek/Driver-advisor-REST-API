# Driving-advisor REST API
Application for drivers who have the opportunity to improve their driving technique in theory. The user has access to the content of advice related to the selected issue, can recommend it, share it and if the issue contains training to consolidate the advice, we can go to the quiz.

Technologies: 

* Spring Boot
* Spring Security 
* Spring Data JPA
* Hibernate
* MySQL

Running the app
Insert database connection details and configuration for email in application_db.properties file: src/main/resources

```sh
Provide your login to connect to the local database:
spring.datasource.username=
```

```sh
Provide your password to connect to the local database:
spring.datasource.password=
```

```sh
Provide your email address:
spring.mail.username=
```

```sh
Provide your email password:
spring.mail.password=
```
