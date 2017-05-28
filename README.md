# Clothstore, E-commerce theme

This project is a simple e-commerce template, build with Spring MVC + Thymeleaf + AngularJS

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them

Install H2 database, you can find it [here](http://www.h2database.com/html/main.html)

### Running the project
* Open project in IDE
* Then run project with jetty
```
jetty:run
```
Default project url is 
```
http://localhost:8080
```
Add admin user to H2 database
```
insert into users (username,enabled,password) values('admin',1,'admin');
insert into user_roles values(1,'ROLE_ADMIN','admin');
```

