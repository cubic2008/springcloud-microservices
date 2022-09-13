@echo off

REM Start Kafka Server
echo.
echo ------------------
echo Start Kafka Server
echo ------------------
if not defined KAFKA_HOME (
    set KAFKA_HOME=C:\work\workshops\kafka_2.13-3.2.1
    echo "KAFKA_HOME" is NOT defined, it is set to a default value.
) else (
    echo "KAFKA_HOME" is defined.
)
echo KAFKA_HOME = %KAFKA_HOME%
start %KAFKA_HOME%\bin\windows\zookeeper-server-start.bat %KAFKA_HOME%\config\zookeeper.properties
timeout /T 6
start %KAFKA_HOME%\bin\windows\kafka-server-start.bat %KAFKA_HOME%\config\server.properties
timeout /T 3

REM Start Redis Server
echo.
echo ------------------
echo Start Redis Server
echo ------------------
if not defined REDIS_HOME (
    set REDIS_HOME=C:\work\workshops\Redis-x64-2.8.2104
    echo "REDIS_HOME" is NOT defined, it is set to a default value.
) else (
    echo "REDIS_HOME" is defined.
)
echo REDIS_HOME = %REDIS_HOME%
start %REDIS_HOME%\redis-server
timeout /T 3

REM Build and Run Config Server
echo.
echo ---------------------------
echo Build and Run Config Server
echo ---------------------------
cd CubicConfigServer
start mvn clean spring-boot:run
timeout /T 10

REM Build and Run Eureka Server
echo.
echo ---------------------------
echo Build and Run Eureka Server
echo ---------------------------
cd ..\CubicEurekaServer
start mvn clean spring-boot:run
timeout /T 10

REM Build and Run Zuul Server (at port 2301)
echo.
echo ----------------------------------------
echo Build and Run Zuul Server (at port 2301)
echo ----------------------------------------
cd ..\CubicZuulServer
start mvn clean spring-boot:run
timeout /T 10

@REM REM Build and Run Special Route Service (at port 2031)
@REM echo.
@REM echo --------------------------------------------------
@REM echo Build and Run Special Route Service (at port 2031)
@REM echo --------------------------------------------------
@REM cd ..\CubicSpecialRouteService
@REM start mvn clean spring-boot:run
@REM timeout /T 10

REM Build and Run Auth Services (at port 2501)
echo.
echo ------------------------------------------
echo Build and Run Auth Services (at port 2501)
echo ------------------------------------------
cd ..\CubicAuthServer
start mvn clean spring-boot:run
timeout /T 10

REM Build and Run Account Services (at port 2011)
echo.
echo --------------------------------------------
echo Build and Run Account Service (at port 2011)
echo --------------------------------------------
cd ..\CubicAccountService
start mvn clean spring-boot:run
timeout /T 10

@REM REM Build and Run the New Account Services (at port 2021)
@REM echo.
@REM echo -----------------------------------------------------
@REM echo Build and Run the New Account Services (at port 2021)
@REM echo -----------------------------------------------------
@REM cd ..\CubicAccountService-New
@REM start mvn clean spring-boot:run
@REM timeout /T 10

REM Build and Run Customer Service (at default port 2001)
echo.
echo -------------------------------------------------------
echo Build and Run Customer Service 2 (at default port 2001)
echo -------------------------------------------------------
cd ..\CubicCustomerService-2
start mvn clean spring-boot:run

cd ..
