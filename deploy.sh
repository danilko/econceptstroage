cd ..

rhc app create -a econcept -t jbossews-2.0 -p qAqAxtqWRe
rhc cartridge-add -a econcept -c postgresql-9.2 -p qAqAxtqWRe

#Absolute path to the root folder that conatin this gitub project
#Example
#export SOURCE_GIT_REPO=/home/danilko/econceptstorage
export SOURCE_GIT_REPO=/C/Users/KAI-TING/Desktop/econceptstroage

#Absolute path to the root folder that contain the gear repo
#Example
#export GEAR_GIT_REPO=/home/danilko/econceptstorage_openshift
export GEAR_GIT_REPO=/C/Users/KAI-TING/Desktop/test

#SSH_PATH for the gear
#Example
#export SSH_PATH=sdfsa311sdafd4678@econcept-econceptstroage.local.openshift.com
export SSH_PATH=52645f92e0b8cddd3c000278@test-econcept.rhcloud.com

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