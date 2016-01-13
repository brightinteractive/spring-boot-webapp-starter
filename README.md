Spring Boot webapp starter
==========================

A starting point for web applications developed in Spring Boot. Includes:

* Login
* Change password
* User management (adding/editing)
* Method based security
* Example forms using FreeMarker
* Flyway to manage database upgrades
* Tests

Prerequisites
-------------

* Java 8
* PostgreSQL 9


Developer Installation
----------------------

Clone the repo:

    git clone git@github.com:brightinteractive/spring-boot-webapp-starter.git

The application expects a PostgreSQL database. Here are the PostgreSQL commands for creating the default db and user *from a SQL client (e.g. the PostgreSQL client)*:

    CREATE USER appstarter WITH PASSWORD '<appstarter password>';
    CREATE USER appstarter_test WITH PASSWORD '<appstarter_test password>';
    CREATE DATABASE appstarter WITH OWNER = appstarter;
    CREATE DATABASE appstarter_test WITH OWNER = appstarter_test;

You should then provide the passwords to the application by creating an application.properties file with the following content

    spring.datasource.password = <appstarter password>

...and an application-test.properties file with the following content

    spring.datasource.password = <appstarter_test password>
	
Put these files in /config

The application expects an SMTP server, but a local stub implementation can be used.
You can then provide any extra connection details in the application.properties file.
See [email settings](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-email.html) for available options.

Running
-------

To run the tests:

    ./gradlew test

To run the application from a JAR:

    grunt build && ./gradlew build && java -jar build/libs/appstarter-*.jar
    open http://localhost:8080/

