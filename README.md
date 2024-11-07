# test

This project uses Quarkus, the Supersonic Subatomic Java Framework.

## Running the application in local mode 
This project includes :
- Unit test 
- The apis
- Dockerization of the application (db and backend)
I added also swagger ui on http://localhost:8080/q/swagger-ui/

I created 2 api Patient and Visit that handle the task requested in the task (no ui was implemented)

You can run your application in dev mode :
Starting a database
```shell script
docker run -d --rm --name my_reative_db -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=my_db -p 5432:5432 -v pgdata:/var/lib/postgresql/data postgres:14
```

Starting the application 
```shell script
./mvnw compile quarkus:dev
```
The application can be run also by running ("prod" mode)
```shell script
docker-compose up 
```


