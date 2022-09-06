@REM Build and Run Config Server
@echo.
@echo ---------------------------
@echo Build and Run Config Server
@echo ---------------------------
@pause
cd CubicConfigServer
call mvn clean package
start java -jar target\config_server-0.0.1-SNAPSHOT.jar

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
