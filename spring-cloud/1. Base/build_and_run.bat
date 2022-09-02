@REM Build and Run Customer Service (at default port 2001)
@echo Build and Run Customer Service (at default port 2001) . . .
cd CubicCustomerService
call mvn clean package
start java -jar target\customer-service-0.0.1-SNAPSHOT.jar --server.port=2001

@REM Build and Run the Front-end App (Angular)
@echo Build and Run the Front-end App (Angular) . . .
cd ..\..\microservices-angular
call npm install
start ng serve

cd "..\1. Base"
