# https://hub.docker.com/_/eclipse-temurin/
FROM omnifish/glassfish:latest

RUN mkdir -p ${PATH_GF_HOME}/deploy

VOLUME /tmp


# Copy WAR, resource.xml, and JARs to the deploy directory
COPY target/orange-care-payment.war ${PATH_GF_HOME}/deploy/orange-care-payment.war
COPY glassfish-resource/config/resource.xml ${PATH_GF_HOME}/deploy/resource.xml
COPY glassfish-resource/jar/*.jar ${PATH_GF_HOME}/deploy/jar/

# Set working directory to GlassFish bin directory
WORKDIR ${PATH_GF_HOME}/glassfish/bin

CMD ["startserv"]


#RUN ./asadmin start-domain domain1
#RUN pwd
#RUN ps -ef | grep glassfish
#RUN
#ENTRYPOINT ["admin"]
#ENTRYPOINT ["admin"]
#RUN ./asadmin add-library /opt/deploy/jar/
#RUN ./asadmin/deploy ${PATH_GF_HOME}/deploy/orange-care-payment.war
#ENTRYPOINT ["admin"]
#ENTRYPOINT ["admin"]

CMD ["startserv"]

RUN echo admin | ./asadmin add-resource ${PATH_GF_HOME}/deploy/resource.xml
RUN ./asadmin add-library /opt/deploy/jar/


# Command to start GlassFish and deploy the WAR file
#CMD ["bash", "-c", "
#    ./asadmin start-domain && \
#    ./asadmin add-library ${GF_HOME}/deploy/resource.xml && \
#    ./asadmin deploy ${GF_HOME}/deploy/orange-care-payment.war && \
#    tail -f ${GF_HOME}/glassfish/domains/domain1/logs/server.log
#"]
