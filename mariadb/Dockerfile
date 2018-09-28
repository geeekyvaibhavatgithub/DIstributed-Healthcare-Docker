FROM b3ds:1

# User/Group
ENV MY_USER mysql
ENV MY_GROUP mysql
ENV MY_UID 48
ENV MY_GID 48

# Files
ENV MYSQL_BASE_INCL="/etc/my.cnf.d"
ENV MYSQL_CUST_INCL1="/etc/mysql/conf.d"
ENV MYSQL_CUST_INCL2="/etc/mysql/docker-default.d"
ENV MYSQL_DEF_DAT="/var/lib/mysql"
ENV MYSQL_DEF_LOG="/var/log/mysql"
ENV MYSQL_DEF_PID="/var/run/mysqld"
ENV MYSQL_DEF_SCK="/var/sock/mysqld"

ENV MYSQL_LOG_SLOW="${MYSQL_DEF_LOG}/slow.log"
ENV MYSQL_LOG_ERROR="${MYSQL_DEF_LOG}/error.log"
ENV MYSQL_LOG_QUERY="${MYSQL_DEF_LOG}/query.log"

###
### Install
###
RUN groupadd -g ${MY_GID} -r ${MY_GROUP} && \
	adduser ${MY_USER} -u ${MY_UID} -M -s /sbin/nologin -g ${MY_GROUP}

RUN mkdir -p /home/$MY_USER
RUN \
	{ \
		echo "# MariaDB 10.1 CentOS repository list - created 2016-10-28 06:40 UTC"; \
		echo "# http://downloads.mariadb.org/mariadb/repositories/"; \
		echo "[mariadb]"; \
		echo "name = MariaDB"; \
		echo "baseurl = http://yum.mariadb.org/10.1/centos7-amd64"; \
		echo "gpgkey=https://yum.mariadb.org/RPM-GPG-KEY-MariaDB"; \
		echo "gpgcheck=1"; \
	} > /etc/yum.repos.d/mariadb-10.1.repo && \
	yum clean all

RUN yum -y update && yum -y install \
	MariaDB-server


##
## Bootstrap Scipts
##
RUN mkdir -p /home/mysql

COPY ./scripts/start.sh /home/mysql/start.sh
RUN chmod +x /home/mysql/start.sh

##
## Ports
##
EXPOSE 3306


##
## Volumes
##
VOLUME /var/lib/mysql
VOLUME /var/log/mysql
VOLUME /var/sock/mysqld
VOLUME /etc/mysql/conf.d
VOLUME /etc/mysql/docker-default.d

USER mysql
WORKDIR /home/mysql
##
## Entrypoint
##

RUN yum -y install openldap-clients \
	nss-pam-ldapd \
	pam \
	pam-devel \
	wget

RUN ls /home/mysql
ENTRYPOINT ["/home/mysql/start.sh"]

RUN authconfig --enableldap \
	--enableldapauth \
	--ldapserver=localhost
	--ldapbasedn="dc=support,dc=mariadb" \
	--enablemkhomedir \
	--update

RUN wget https://raw.githubusercontent.com/MariaDB/server/10.1/plugin/auth_pam/mapper/pam_user_map.c


CMD ["/usr/sbin/init"]
