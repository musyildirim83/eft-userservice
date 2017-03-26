FROM java:alpine
ADD target/userservice.jar /data/userservice.jar
ADD conf /data/conf
CMD java -jar /data/userservice.jar server /data/conf/local/config.yml
ENV SERVICE_TAGS=rest
ENV SERVICE_NAME=userservice
EXPOSE 8080
