@REM Build and Run Config Server
@echo.
@echo ---------------------------
@echo Build and Run Config Server
@echo ---------------------------
@pause
cd CubicConfigServer
call mvn clean package
start java -jar target\config_server-0.0.1-SNAPSHOT.jar

@REM Build and Run Eureka Server
@echo.
@echo ---------------------------
@echo Build and Run Eureka Server
@echo ---------------------------
@pause
cd ..\CubicEurekaServer
call mvn clean package
start java -jar target\eureka_server-0.0.1-SNAPSHOT.jar

@REM Build and Run Account Services (at port 2011)
@echo.
@echo --------------------------------------------
@echo Build and Run Account Service (at port 2011)
@echo --------------------------------------------
@pause
cd ..\CubicAccountService
call mvn clean package
start java -jar target\account-service-0.0.1-SNAPSHOT.jar --server.port=2011
# start java -jar target\account-service-0.0.1-SNAPSHOT.jar --server.port=2012

@REM Build and Run Customer Service (at default port 2001)
@echo.
@echo -----------------------------------------------------
@echo Build and Run Customer Service (at default port 2001)
@echo -----------------------------------------------------
@pause
cd ..\CubicCustomerService
call mvn clean package
start java -jar target\customer-service-0.0.1-SNAPSHOT.jar

cd ..
