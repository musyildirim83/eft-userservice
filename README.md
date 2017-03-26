Dropwizard - Rest Services
======================================

Build and package the example app:

    mvn package

start the service:

    java -jar target/userservice.jar server conf/local/config.yml 
    
urls:
    
    GET     /userservice/users
    POST    /userservice/users
    DELETE  /userservice/users/{userId} 
    GET     /userservice/users/{userId} 
    PUT     /userservice/users/{userId}

