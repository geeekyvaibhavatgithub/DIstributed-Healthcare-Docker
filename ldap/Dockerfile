FROM b3ds:1

RUN yum -y install openldap \
	openldap-servers \
	openldap-clients \
	nss-pam-ldapd

RUN yum -y install tar.x86_64

RUN cp /usr/share/openldap-servers/DB_CONFIG.example /var/lib/ldap/DB_CONFIG
RUN chown ldap. /var/lib/ldap/DB_CONFIG

COPY ldap-config.tar.gz /temp/ldap-config.tar.gz
WORKDIR /temp
RUN tar -xzvf ldap-config.tar.gz

RUN cp -r ldap/openldap /etc/
RUN cp -r ldap/share/openldap-servers /usr/share/

RUN systemctl enable slapd

EXPOSE 389

ENTRYPOINT ["slapd"]

CMD ["/usr/sbin/init"]

