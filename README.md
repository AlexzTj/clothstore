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

View demo images
add product page https://cloud.githubusercontent.com/assets/5672960/26550214/498fe25a-4485-11e7-950d-7c8ff7d89e9d.png  <br />
detail page https://cloud.githubusercontent.com/assets/5672960/26550212/498f65e6-4485-11e7-83a5-4f90dce21f94.png  <br />
orders page https://cloud.githubusercontent.com/assets/5672960/26550211/498ee80a-4485-11e7-960c-700fff9ffb4d.png  <br />
maintain products page https://cloud.githubusercontent.com/assets/5672960/26550215/4990b216-4485-11e7-8024-9a928c553cad.png  <br />
shopping cart page https://cloud.githubusercontent.com/assets/5672960/26550213/498fb03c-4485-11e7-83d5-83a45966a78e.png  <br />
