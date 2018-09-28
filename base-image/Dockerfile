FROM centos/systemd

# Add maven also
MAINTAINER "B3 DIgital SOlution"
ENV MAVEN_VERSION=3.5.4

RUN yum -y update; yum clean all

RUN yum -y install epel-release; yum clean all

RUN yum -y install nano \
	bash-completion \ 
	psmisc net-tools \
	which \
	tar.x86_64 \
	wget; yum clean all

WORKDIR /temp
RUN wget --no-check-certificate -c --header "Cookie: oraclelicense=accept-securebackup-cookie" \
	http://download.oracle.com/otn-pub/java/jdk/8u181-b13/96a7b8442fe848ef90c96a2fad6ed6d1/jdk-8u181-linux-x64.tar.gz

RUN tar -xzvf jdk-8u181-linux-x64.tar.gz -C /opt/
ENV JAVA_HOME /opt/jdk1.8.0_181/
ENV PATH="${JAVA_HOME}/bin:${PATH}"

RUN wget http://mirrors.wuchna.com/apachemirror/maven/maven-3/3.5.4/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz

RUN tar -xzvf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /usr/share/ \
	&& rm apache-maven-${MAVEN_VERSION}-bin.tar.gz

ENV MAVEN_HOME /usr/share/apache-maven-${MAVEN_VERSION}
ENV M2_HOME /usr/share/apache-maven-${MAVEN_VERSION}
ENV PATH="${M2_HOME}/bin:${PATH}"

COPY settings.xml ${MAVEN_HOME}/conf/

RUN groupadd maven \
	&& useradd -r -g maven maven \
	&& bash -c "mkdir -p /var/maven/repo"  \
	&& chown maven:maven -R /var/maven/repo

ENV M2_REPO /var/maven/repo

WORKDIR /
CMD ["/usr/sbin/init"]
