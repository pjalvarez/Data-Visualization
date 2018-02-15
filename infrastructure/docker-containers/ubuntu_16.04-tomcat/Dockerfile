FROM fiwoo/ubuntu_16.04-tomcat

ARG BUILD_TIMESTAMP=201802121638
ARG TOMCAT_VERSION=8.5.27
ARG MICROSERVICE=Data-Visualization
ARG ENVIRONMENT=dev

ENV BUILD_TIMESTAMP ${BUILD_TIMESTAMP}
ENV TOMCAT_VERSION ${TOMCAT_VERSION}
ENV MICROSERVICE ${MICROSERVICE}
ENV ENVIRONMENT ${ENVIRONMENT}

# Build the web app
RUN /bin/bash -c 'git config --global url."https://github.com/".insteadOf git@github.com: && \
  git clone https://github.com/fiwoo-platform/${MICROSERVICE}.git ${MICROSERVICE} && \
  ln -snf ${MICROSERVICE}/src /src'

RUN /bin/bash -c 'SRC_DIR=/src && \
  cd ${SRC_DIR} && \
  mvn clean && \
  mvn package -DskipTests -P ${ENVIRONMENT}'

RUN mkdir output-war && \
  cd output-war && \
  jar xvf /src/target/*.war && \
  cd $OLDPWD

RUN mkdir -p /assets/opt/apache-tomcat-${TOMCAT_VERSION}/webapps/ROOT/ && \
  rsync -Pavz --delete output-war/ /assets/opt/apache-tomcat-${TOMCAT_VERSION}/webapps/ROOT/ && \
  rm /src
  
EXPOSE 8009 8080

ENTRYPOINT ["/assets/bin/entrypoint"]
