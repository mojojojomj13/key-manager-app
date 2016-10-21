# ReadMe Documentation
----------------------------------------------------------------------

- **Project** : API KEY Manager 
- **Owner** : CRM
- **Author** : Prithvish Mukherjee
- **Date** : 19/10/2016
----------------------------------------------------------------------
# Application Description



# Installation

#### Prerequisites
The list of softwares/ applications required to run the project on local machine

- Git
- Eclipse
- Maven
- JDK 1.8 or above
- Postgress client

#### Initial Setup
Run the DB scripts committed under scripts folder in the following order

1. CreateTable.sql


#### First Time deployment
```sh
git config user.email 'your emailid configurred in heroku'
git init
git add .
git commit -m "Initial Commit"
heroku login		
	
--Create the Heroku application							
heroku create <<APP_NAME>>

--Add the postgres DB addon to the application just created
heroku addons:create heroku-postgresql:standard-0

--List all the environment variables
heroku config -a <<APP_NAME>>

--Promote the newly added DB as primary DB.
heroku pg:promote 'HEROKU_POSTGRESQL_ORANGE_URL' -a <<APP_NAME>>

--Set LOG_LEVEL config var 
heroku config:set LOG_LEVEL="INFO"
git push heroku master
```

#### Subsequent deployments
```sh
cd 'Location where project is cloned'
git config user.email 'your emailid configurred in heroku'
git init
git add .
git commit -m "Commit comments"
heroku login		
heroku git:remote -a <<APP_NAME>>
git push heroku master
```

