# Driver advisor REST API

### See the application on HEROKU
https://driver-advisor-rest-api.herokuapp.com/

Application for drivers who have the opportunity to improve their driving technique in theory. The user has access to the content of advice related to the selected issue. The user can recommend them, and if the issue includes training, he can solve them for which he receives in return a certain number of points, enrolling in his account advice. Training and tips may include photos, gifs or a video.

## Technologies: 

* Spring Boot
* Spring Security 
* Spring Data JPA
* Hibernate
* MySQL

## Running the application locally
To run the application, insert in the application.properties file located in src/main/resources:
 
* database connection details 

```sh
Provide your login to connect to the local database:
spring.datasource.username=
```

```sh
Provide your password to connect to the local database:
spring.datasource.password=
```
* complete the data needed to log in to your email address. It must be a working Gmail account.
Note: due to the not secure mechanism used when logging in, it is necessary to disable additional security - https://myaccount.google.com/lesssecureapps)

```sh
Provide your email address:
spring.mail.username=
```

```sh
Provide your email password:
spring.mail.password=
```

* run application

    - using maven: <strong>mvn package spring-boot:run</strong>
    - with java -jar command: <strong>java -jar target/portfoliolab-driver-0.0.1-SNAPSHOT.jar</strong>
    - IDE: run DrivingAdvisorApplication.java class

## Swagger documentation from HEROKU

https://driver-advisor-rest-api.herokuapp.com/v2/api-docs

## Spring security

To facilitate the presentation of the project for quick login you can use:

* a user with the role "USER"
```sh
username: user
password: user
```

He has access to the endpoints: "user/create", "/user/update" and all others except the others "/\*\*/create", "/\*\*/update", all "/\*\*/remove" and "/role/\*\*"

* or a user with the role "ADMIN"
```sh
username: admin
password: admin
```
He has access to all endpoints

* you can create your own user with free access to the endpoint "/user/create" whose default role will be "USER"

## Sample interface for the application

Prepared by the CodersLab bootcamp

![alt text](https://trello-attachments.s3.amazonaws.com/5eb5216f15b31b30dd85db60/5eb5216f15b31b30dd85db88/958x530/b3f18604d02f42e14ceadedbf91b8ad6/zalacznik.png)