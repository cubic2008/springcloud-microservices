@echo off

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

REM Build and Run Special Route Service (at port 2031)
echo.
echo --------------------------------------------------
echo Build and Run Special Route Service (at port 2031)
echo --------------------------------------------------
cd ..\CubicSpecialRouteService
start mvn clean spring-boot:run
timeout /T 3

REM Build and Run Account Services (at port 2011)
echo.
echo --------------------------------------------
echo Build and Run Account Service (at port 2011)
echo --------------------------------------------
cd ..\CubicAccountService
start mvn clean spring-boot:run
timeout /T 3

REM Build and Run the New Account Services (at port 2021)
echo.
echo -----------------------------------------------------
echo Build and Run the New Account Services (at port 2021)
echo -----------------------------------------------------
cd ..\CubicAccountService-New
start mvn clean spring-boot:run
timeout /T 3

REM Build and Run Customer Service (at default port 2001)
echo.
echo -----------------------------------------------------
echo Build and Run Customer Service (at default port 2001)
echo -----------------------------------------------------
cd ..\CubicCustomerService
start mvn clean spring-boot:run

cd ..
