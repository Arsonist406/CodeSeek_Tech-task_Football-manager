# Football Manager

- Java 21
- Maven 3.9.11
- PostgreSQL 17.3

# Datasource setting by default 

- url: jdbc:postgresql://localhost:5432/football-manager
- username: postgres
- password: 123456789

# Postman workspace

https://www.postman.com/research-physicist-15264027/workspace/football-manager

# Running the application

- Clone project
- Create empty database in PostgreSQL
- In *path to project*\CodeSeek_Tech-task_Football-manager run commands:
- "mvn clean package"
- "java -jar target/Football-manager-3.5.4.jar"
  OR if you want to set custome database settings
  "java -jar target/Football-manager-3.5.4.jar --DB_URL=jdbc:postgresql://localhost:5432/*your database name* --DB_USERNAME=*your username* --DB_PASSWORD=*your password*"

- Application is running!

  
