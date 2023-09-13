# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.agitrubard.auth-side-services' is invalid and this project uses 'com.agitrubard.authside' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.2/maven-plugin/reference/html/#build-image)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.1.2/reference/htmlsingle/index.html#actuator)
* [Docker Compose Support](https://docs.spring.io/spring-boot/docs/3.1.2/reference/htmlsingle/index.html#features.docker-compose)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.2/reference/htmlsingle/index.html#web)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.1.2/reference/htmlsingle/index.html#web.security)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.1.2/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/3.1.2/reference/htmlsingle/index.html#web.servlet.spring-mvc.template-engines)
* [Java Mail Sender](https://docs.spring.io/spring-boot/docs/3.1.2/reference/htmlsingle/index.html#io.email)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)

### Docker Compose support
This project contains a Docker Compose file named `docker-compose.yml`.
In this file, the following services have been defined:

* database: [`mysql:latest`](https://hub.docker.com/_/mysql)
* application: `auth-side-services:latest`

Please review the tags of the used images and set them to the same as you're running in production.

