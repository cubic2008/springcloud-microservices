@REM Build and Run Config Server
@echo Build and Run Config Server . . .
cd CubicConfigServer
call mvn clean package
start java -jar target\config_server-0.0.1-SNAPSHOT.jar

@REM Build and Run Eureka Server
@echo Build and Run Eureka Server . . .
cd ..\CubicEurekaServer
call mvn clean package
start java -jar target\eureka_server-0.0.1-SNAPSHOT.jar

@REM Build and Run Customer Service (at default port 2001)
@echo Build and Run Customer Service (at default port 2001) . . .
cd ..\CubicCustomerService
call mvn clean package
start java -jar target\customer-service-0.0.1-SNAPSHOT.jar

@REM Build and Run 2 Account Services (at port 2011 and 2012)
@echo Build and Run 2 Account Services (at port 2011 and 2012) . . .
cd ..\CubicAccountService
call mvn clean package
start java -jar target\account-service-0.0.1-SNAPSHOT.jar --server.port=2011
start java -jar target\account-service-0.0.1-SNAPSHOT.jar --server.port=2012

cd ..
