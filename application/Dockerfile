FROM b3ds:1

ENV MAVEN_VERSION=3.5.4


WORKDIR /temp

# Copy applications
COPY apps /temp/apps

RUN mkdir -p /opt/PacksApplication/bin \
	mkdir -p /opt/PacksApplication/config

RUN echo "building dependency for Ditributed Packs Application" \
	&& cd /temp/apps/DicomHdfs \
	&& mvn -B install -s ${M2_HOME}/conf/settings.xml dependency:resolve

RUN echo "Dependency build successfully" \
	&& echo "Building Distributed Packs Application" 
#RUN cd /temp/apps/Distributed-Packs 

WORKDIR /temp/apps/Distributed-Packs

RUN mvn -X clean compile assembly:single

RUN pwd 
RUN mv target/Distributed-Packs-0.0.1-SNAPSHOT-jar-with-dependencies.jar target/Distributed-Packs.jar 
RUN mv target/Distributed-Packs.jar /opt/PacksApplication/bin/Distributed-Packs.jar 
RUN mv /temp/apps/runpacks.sh /opt/PacksApplication/bin/runpacks.sh 
RUN chmod 755 /opt/PacksApplication/bin/runpacks.sh 
RUN rm -r /temp/apps/Distributed-Packs

ENV PACKS_HOME /opt/PacksApplication
ENV PATH="${PACKS_HOME}/bin:${PATH}"

ENTRYPOINT ["runpacks.sh"]
