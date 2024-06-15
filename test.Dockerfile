FROM maven:3.9.7-eclipse-temurin-17 as BUILD_IMAGE
ENV SRC=/opt/finbite-src
WORKDIR $SRC
COPY . .
RUN mvn clean install

FROM eclipse-temurin:17-jre

RUN addgroup --system finbite && adduser finbitecli --system --shell /bin/false --ingroup finbite

WORKDIR /opt/finbitecli
COPY --from=BUILD_IMAGE /opt/finbite-src/target/finbite*.jar finbite.jar

RUN chown -R finbitecli:finbite * && chmod -R 744 *
USER finbitecli

ENTRYPOINT ["java", "-jar" , "finbite.jar"]