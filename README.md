# Spring Cloud & Microservices

## Environment

### Java Environment

This project uses Java 1.8. All dependant libraries will be automatically downloaded by
Maven.

### JCE Unlimited Strength Jurisdiction Policy Files

You need to download "Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction 
Policy Files for Java 1.8" from Oracle website.

- https://www.oracle.com/ca-en/java/technologies/javase-jce8-downloads.html

After download the package, unzip it and then copy/overwrite the following two files to 
`<JavaHome>/jre/lib/security` folder.

- local_policy.jar
- US_export_policy.jar

### Database

Each package contains a database schema file: `src/main/resources/db.sql`. You should use it
to create the database schema. 

The default database name is: `springcloud_db`

The default database connectivity information are stored under `app-conf/<package#>` directory.
It connects to `MySQL` database on the `localhost` at the port `3306`.
If you want to change this information, you need to make corresponding changes in these
configuration files.

## Package #1: Run Angular Frontend App + SpringBoot Backend (Customer Service)

This package is a pure Angular Frontend App + SpringBoot Backend. It does not engage any
SpringCloud framework.

To build and run, execute the following command:

```windows
C:spring-cloud> cd "1. Base"
C:spring-cloud> build_and_run.bat
```

It will start two windows, one is the web server that hosts the Angular app, the other one
is the Customer Service SpringBoot application.

You then can go to `http://localhost:4200` on a browser and see the following output.

The default port that the backend Customer Service listens at is `2001`.

![img.png](doc-images/pic1-1.png)

![img.png](doc-images/pic1-2.png)

## Package #2: Config Server

This package contains two services: Config Server and Customer Service.

To build and run, execute the following command:

```windows
C:spring-cloud> cd "2. Config Server"
C:spring-cloud> build_and_run.bat
```

It will start two windows, one is the Config Server, the other is the Customer Service 
SpringBoot application.

The default port at which the Config Server listens is: `2101`.
The default port at which the Customer Service listens is: `200`.

You can use the same frontend Angular app of Package #1 to access the Customer Service.

Additionally, you can use PostMan to access the Config Server and the Customer Service.

![img.png](doc-images/pic2-1.png)

![img_1.png](doc-images/pic2-2.png)

![img_2.png](doc-images/pic2-3.png)

![img_3.png](doc-images/pic2-4.png)

## Package #3: Config Server + Eureka Server

This package is extended based on Package #2 and mainly demonstrates the Service Registration
and Discovery (using Eureka and Ribbon). It contains four services: 
- 
- Config Server
- Eureka Server
- Customer Service
- Account Service

To build and run, execute the following command:

```windows
C:spring-cloud> cd "2. Config Server"
C:spring-cloud> build_and_run.bat
```

It will start five servers/services:

- one Config Server: listens at port `2101`
- one Eureka Server: listens at port `2201`
- one Customer Service: listens at port `2001`
- two Account Services: listens at port `2011` and `2012`

You can use the same frontend Angular app of Package #1 to access the Customer Service.

Additionally, you can use PostMan to access these servers

After Eureka starts but before Customer Service and Account Services start, you can access 
the admin console of Eureka server on browser using URL: http://localhost:2201

![img.png](doc-images/pic3-1.png)

After all servers are up running, you can see the status of these services in the admin
console of Eureka server.

![img_1.png](doc-images/pic3-2.png)

Then you can issue a REST API invocation to the Customer Service, or from the frontend
app, and observe logs of the two Account Services. The Customer Service uses the service
name of Account Service via Ribbon to access them using round-robin strategy. If you bring
down one of the Account Service, the Customer Service will not be impacted. You can observe
all REST API invocations occurring in the only running Account Service.

![img_2.png](doc-images/pic3-3.png)



