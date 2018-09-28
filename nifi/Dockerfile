FROM b3ds:1


ENV NIFI_VERSION=1.5.0


#RUN wget https://archive.apache.org/dist/nifi/1.5.0/nifi-${NIFI_VERSION}.tar.gz

COPY nifi-${NIFI_VERSION}-bin.tar.gz /temp

COPY nifi /temp/nifi

WORKDIR /temp

RUN tar -xzvf nifi-${NIFI_VERSION}-bin.tar.gz -C /opt \
	&& cp /temp/nifi/nifi.properties /opt/nifi-${NIFI_VERSION}/conf/ \
	&& cp /temp/nifi/hl7.xml.gz /opt/nifi-${NIFI_VERSION}/conf

RUN rm nifi-${NIFI_VERSION}-bin.tar.gz
RUN rm -r nifi

ENV NIFI_HOME /opt/nifi-${NIFI_VERSION}

ENV PATH="${NIFI_HOME}/bin:${PATH}"

RUN groupadd nifi \
	&& useradd -r -g nifi nifi \
	&& bash -c "mkdir -p ${NIFI_HOME}/{database_repository,flowfile_repository,content_repository,provenance_repository}" \
	&& chown nifi:nifi -R ${NIFI_HOME}

VOLUME ["${NIFI_HOME}/database_repository", \
	"${NIFI_HOME}/flowfile_repository", \
	"${NIFI_HOME}/content_repository", \
	"${NIFI_HOME}/provenance_repository"]

COPY start.sh /usr/bin/start.sh
RUN chmod 755 /usr/bin/start.sh
USER nifi

EXPOSE 7000 8443 10000

WORKDIR /

ENTRYPOINT ["/usr/bin/start.sh"]

#WORKDIR /

#CMD 
