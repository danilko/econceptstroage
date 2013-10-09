econceptstroage
===============

License
===============

The MIT License (MIT)

Copyright (c) 2013 Kai-Ting (Danil) Ko

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

===============


Description
===============

Using JAX-RS with Apache CXF, Spring, OpenJPA, PostgreSQL to achieve online file storage concept

Environment Variable DATA_DIR must be present and point to a writable location in order to ensure application functions correctly

Database information currently store in main/resources/application.properties

===============


TODO
===============
<li>Solve clear text password problem</li>
<li>Clean up code documentation</li>
<li>Move Database information to environment variable based</li>
<li>More unit tests</li>

===============

OpenShift
===============

The Code is currently deployed to OpenShift
http://econcept-econcept.rhcloud.com/econceptstorage/

===============

LOCALLY DEPLOYMENT INSTRUCTION
===============

#Install Postgres and run econcept_ods.sql to generate necessary tables

#Export following environment variables in the current session, below is the example of OpenShift

#FILE PERSISTENCE
export DATA_DIR=${OPENSHIFT_DATA_DIR}

#JDBC
export JDBC_DRIVERCLASSNAME=org.postgresql.Driver
export JDBC_URL=jdbc:postgresql://${OPENSHIFT_POSTGRESQL_DB_HOST}:${OPENSHIFT_POSTGRESQL_DB_PORT}/${OPENSHIFT_APP_NAME}
export JDBC_USERNAME=${OPENSHIFT_POSTGRESQL_DB_USERNAME}
export JDBC_PASSWORD=${OPENSHIFT_POSTGRESQL_DB_PASSWORD}
export JDBC_MAXCONNECTIONPERPARTITION=8
export JDBC_MINCONNECTIONPERPARTITION=2

#MONGODB
export MONODB_HOST=${OPENSHIFT_MONGODB_DB_HOST}
export MONODB_PORT=${OPENSHIFT_MONGODB_DB_PORT}
export MONODB_DBNAME=${OPENSHIFT_APP_NAME}
export MONODB_USERNAME=${OPENSHIFT_MONGODB_DB_USERNAME}
export MONODB_PASSWORD=${OPENSHIFT_MONGODB_DB_PASSWORD}

# Execute embedded maven
mvn clean package tomcat7:run -DskipTests

#You should be able to see it in localhost:8080/econceptstorage

===============

OPENSHIFT DEPLOYMENT INSTRUCTION
===============

#Create JBosses-2.0 gear and add postgresql
rhc app create -a ${GEAR_NAME} -t jbossews-2.0
rhc cartridge-add -a ${GEAR_NAME} -c postgresql-9.2

#Export following variables
#Absolute path to SOURCE_REPO
export SOURCE_GIT_REPO=
#Absolute path to GEAR_REPO
export GEAR_GIT_REPO=
#SSH_PATH for the gear

#Run econcept_ods.sql to generate necessary tables
scp  ${SOURCE_GIT_REPO}/econcept_ods.sql ${GEAR_SSH}:app-root/data
ssh  ${GEAR_SSH}
psql -f app-root/data/econcept_ods.sql
exit

# Execute maven to package the war
cd ${SOURCE_GIT_REPO}
mvn clean package -DskipTests

if [ -f ${GEAR_GIT_REPO}/pom.xml ]
then
	rm ${GEAR_GIT_REPO}/pom.xml 
fi

if [ -d ${GEAR_GIT_REPO}/src ]
then
	rm -rf ${GEAR_GIT_REPO}/src
fi

if [ -d ${GEAR_GIT_REPO}/webapps ]
then
	rm -rf ${GEAR_GIT_REPO}/webapps
	mkdir -p ${GEAR_GIT_REPO}/webapps
fi

if [ -d ${GEAR_GIT_REPO}/.openshift ]
then
	rm -rf ${GEAR_GIT_REPO}/webapps
fi

cp ${SOURCE_GIT_REPO}/target/econceptstorage.war ${GEAR_GIT_REPO}/webapps
cp -rf ${SOURCE_GIT_REPO}/.openshift ${GEAR_GIT_REPO}

cd ${GEAR_GIT_REPO}
git add -A .
git update-index --chmod=+x .openshift/action_hooks/*
git commit -m "SOURCE GIT HEAD `cat ${SOURCE_GIT_REPO}/.git/refs/heads/master`"
git push origin master

===============
