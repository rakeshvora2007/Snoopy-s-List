# SnoopysList


Start WildFly
-------------------------

1. Open a command line and navigate to the root of the wildfly server directory.
2. Type

```sh
     sh wildfly-10.1.0.Final/bin/standalone.sh 
```
        
Build and Deploy 
-------------------------
1. Navigate to the root directory of the project
2. Type to deploy the project

```sh
     mvn clean package wildfly:deploy
```

Access the application
-------------------------
The application will be running at the following URL: http://<Host Name>:<Port Name>/snoopyslist/.

Undeploy 
-------------------------
1. Navigate to the root directory of this project.
```sh
 mvn wildfly:undeploy
```

#How to run
- cd into the project file and start the database
 

        java -jar h2-1.4.194.jar


Url for database is : jdbc:h2:mem:snoopyDB

username : admin
password: admin

-Change:

```
<jta-data-source>java:jboss/datasources/snoopyslistDS</jta-data-source>
<class>com.snoopyslist.DBmodels.User</class>
```

-To:
```
<jta-data-source>java:jboss/datasources/snoopyDS</jta-data-source>
```

After saving your changes, if the data returns "500 Reserve Error", Reimport all Maven Projects in IntelliJ



