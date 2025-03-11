FROM amazoncorretto:21
WORKDIR /pets-management

ADD target/*.jar pets-management.jar

ENTRYPOINT ["java", "-Xms2g", "-Xmx30g", "-XX:MaxDirectMemorySize=64M", "-jar", "pets-management.jar"]