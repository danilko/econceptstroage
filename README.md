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

Using JAX-RS with Apache CXF, Spring, OpenJPA, PostgreSQL, BoneCP to achieve online file storage concept

Environment Variable DATA_DIR must be present and point to a writable location in order to ensure application functions correctly

Database information currently store in main/resources/application.properties

===============


TODO
===============
Clean up code documentation

===============

OpenShift
===============

The Code is currently deployed to OpenShift
http://econcept-econcept.rhcloud.com/econceptstorage/

===============

LOCALLY DEPLOYMENT INSTRUCTION
===============

Setup the Postgresql Database Connection

```
#Install Postgres and run econcept_ods.sql to generate necessary tables
psql -f econcept_ods.sql
```

Export following environment variables in the current session, below is the example of OpenShift

Folder for File Persistence Information
Absolute file path for folder for file persistence (It must already exist)

```
#FILE PERSISTENCE
mkdir - p ${OPENSHIFT_DATA_DIR}
export DATA_DIR=${OPENSHIFT_DATA_DIR}
```

JDBC Connection Information
Connection to database for ODS (Opeartion Data Store)

```
#JDBC
export JDBC_DRIVERCLASSNAME=org.postgresql.Driver
export JDBC_URL=jdbc:postgresql://${OPENSHIFT_POSTGRESQL_DB_HOST}:${OPENSHIFT_POSTGRESQL_DB_PORT}/${OPENSHIFT_APP_NAME}
export JDBC_USERNAME=${OPENSHIFT_POSTGRESQL_DB_USERNAME}
export JDBC_PASSWORD=${OPENSHIFT_POSTGRESQL_DB_PASSWORD}
export JDBC_MAXCONNECTIONPERPARTITION=8
export JDBC_MINCONNECTIONPERPARTITION=2
```

MONGODB Connection Information
Connection to mongodb database for file persistence (Optional for now)

```
#MONGODB
export MONODB_HOST=${OPENSHIFT_MONGODB_DB_HOST}
export MONODB_PORT=${OPENSHIFT_MONGODB_DB_PORT}
export MONODB_DBNAME=${OPENSHIFT_APP_NAME}
export MONODB_USERNAME=${OPENSHIFT_MONGODB_DB_USERNAME}
export MONODB_PASSWORD=${OPENSHIFT_MONGODB_DB_PASSWORD}
```

Execute maven to setup local tomcat 7

```
# Execute embedded maven
mvn clean package tomcat7:run -DskipTests
```


You should be able to see it in localhost:8080/econceptstorage

===============

OPENSHIFT DEPLOYMENT INSTRUCTION
===============

Create Openshift gears and embedded PostgreSQL cartridge

Create JBossews - 2.0 Cartridge

```
#Create JBosses-2.0 gear
rhc app create -a ${GEAR_NAME} -t jbossews-2.0
```

Create PostgreSQL Cartridge and attached to above web cartridge

```
#Add postgresql cartridge
rhc cartridge-add -a ${GEAR_NAME} -c postgresql-9.2
```

Create MongoDB Cartridge and attached to above web cartridge (Optional)

```
#Add mongodb cartridge (Optional)
rhc cartridge-add -a ${GEAR_NAME} -c mongodb-2.0
```

Code Deployment

Need to perform in same session

Export the absolute file path to the root folder that contain this github project
Example

```
export SOURCE_GIT_REPO=/home/danilko/econceptstorage
```

Following is a real code

```
#Absolute path to the root folder that conatin this gitub project
#Example
#export SOURCE_GIT_REPO=/home/danilko/econceptstorage
export SOURCE_GIT_REPO=/home/danilko/econceptstorage
```

Export the absolute file path to the root folder that contain this github project
Example

```
export GEAR_GIT_REPO=/home/danilko/econceptstorage_openshift
```

Following is a real code

```
#Absolute path to GEAR_REPO
#Example
#export GEAR_GIT_REPO=/home/danilko/econceptstorage_openshift
export GEAR_GIT_REPO=/home/danilko/econceptstorage_openshift
```

Export ssh for the OpenShift gear
It can be obtained through OpenShift web console 
or RHC command 

```
#Get the gear info
rhc app show ${GEAR_NAME}
```

Example

```
export SSH_PATH=sdfsa311sdafd4678@econcept-econceptstroage.local.openshift.com
```

Following is a real code

```
#SSH_PATH for the gear
#Example
#export SSH_PATH=sdfsa311sdafd4678@econcept-econceptstroage.local.openshift.com
export SSH_PATH=sdfsa311sdafd4678@econcept-econceptstroage.local.openshift.com
```

Copy the SQL to the gear and execute it

```
#Run econcept_ods.sql to generate necessary tables
scp  ${SOURCE_GIT_REPO}/econcept_ods.sql ${SSH_PATH}:app-root/data/econcept_ods.sql
ssh  ${SSH_PATH} "psql -f app-root/data/econcept_ods.sql; rm -rf app-root/data/econcept_ods.sql; exit;"
```

Execute following code to compile binary, copy it to local gear repo, and push it

```
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
	rm -rf ${GEAR_GIT_REPO}/webapps/.openshift/action_hooks
fi

cp ${SOURCE_GIT_REPO}/target/econceptstorage.war ${GEAR_GIT_REPO}/webapps
cp -rf ${SOURCE_GIT_REPO}/.openshift/action_hooks ${GEAR_GIT_REPO}/.openshift

# Push the change into the gear
cd ${GEAR_GIT_REPO}
git add -A .
git update-index --chmod=+x .openshift/action_hooks/*
# Get the git revision
git commit -m "SOURCE GIT HEAD `cat ${SOURCE_GIT_REPO}/.git/refs/heads/master`"
git push origin master
```

Example of Complete Script

```
#Absolute path to the root folder that conatin this gitub project
#Example
#export SOURCE_GIT_REPO=/home/danilko/econceptstorage
export SOURCE_GIT_REPO=/home/danilko/econceptstorage

#Absolute path to the root folder that contain the gear repo
#Example
#export GEAR_GIT_REPO=/home/danilko/econceptstorage_openshift
export GEAR_GIT_REPO=/home/danilko/econceptstorage_openshift

#SSH_PATH for the gear
#Example
#export SSH_PATH=sdfsa311sdafd4678@econcept-econceptstroage.local.openshift.com
export SSH_PATH=sdfsa311sdafd4678@econcept-econceptstroage.local.openshift.com

#Run econcept_ods.sql to generate necessary tables
scp  ${SOURCE_GIT_REPO}/econcept_ods.sql ${SSH_PATH}:app-root/data/econcept_ods.sql
ssh  ${SSH_PATH} "psql -f app-root/data/econcept_ods.sql; rm -rf app-root/data/econcept_ods.sql; exit;"

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
	rm -rf ${GEAR_GIT_REPO}/webapps/.openshift/action_hooks
fi

cp ${SOURCE_GIT_REPO}/target/econceptstorage.war ${GEAR_GIT_REPO}/webapps
cp -rf ${SOURCE_GIT_REPO}/.openshift/action_hooks ${GEAR_GIT_REPO}/.openshift

# Push the change into the gear
cd ${GEAR_GIT_REPO}
git add -A .
git update-index --chmod=+x .openshift/action_hooks/*
# Get the git revision
git commit -m "SOURCE GIT HEAD `cat ${SOURCE_GIT_REPO}/.git/refs/heads/master`"
git push origin master
```

===============