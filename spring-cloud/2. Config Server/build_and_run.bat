@REM Build and Run Config Server
@echo Build and Run Config Server . . .
cd CubicConfigServer
call mvn clean package
start java -jar target\config_server-0.0.1-SNAPSHOT.jar

@REM Build and Run Customer Service (at default port 2001)
@echo Build and Run Customer Service (at default port 2001) . . .
cd ..\CubicCustomerService
call mvn clean package
start java -jar target\customer-service-0.0.1-SNAPSHOT.jar

cd ..
