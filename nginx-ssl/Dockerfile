FROM nginxinc/nginx-unprivileged:stable
MAINTAINER sfgroups
USER root

RUN apt-get --assume-yes update && \
	apt-get --assume-yes upgrade && \
 	apt-get --assume-yes dist-upgrade && \
	apt-get --assume-yes install ca-certificates 


COPY --chown=101:101 server.key server.crt /etc/ssl/

COPY --chown=101:101 default.conf ssl.conf /etc/nginx/conf.d/
RUN chmod 755 /etc/nginx/conf.d/default.conf /etc/nginx/conf.d/ssl.conf

USER nginx
STOPSIGNAL SIGTERM

EXPOSE 8080
EXPOSE 8443

