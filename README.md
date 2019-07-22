## OpenDolphin simplified
Kazushi Minagawa


#### 1．Developing Environment
 * openjdk 11
 * WildFly-16.0.0.Final(Java EE Full & Web Distribution)
 * Postgres 11
 * postgresql-42.2.5.jar(Java postgres driver)
 * Git
 * IntelliJ IDEA Community Edition


#### 2. Import the OpenDolphin project
 * You need git on your pc.
 * Launch IntelliJ.
 * In the start up panel, select Git from the bottom menu(Check out from Version Control), then fill the boxes as below
 * URL: git@github.com:mbot-dev/OpenMars.git
 * Directory: Specify the top directory to clone the repository
 * Click the button Clone.
 * Select Yes in the continued panel.


#### 3．Compile
 * Select opendolphin[clean] in the Configurations menu(stays in the upper right corner), then click the green arrow button to run the target.
 * Select opendolphin[package] in the same menu, then run it.
 * this cause downloading libraries .... wait until done.


#### 4. Create postgres user and database
 * Create super user dolphin with no password.
 * Create database dolphin with encoding=UTF-8, owner=dolphin
 * Please consult the users manual of Postgres.
 * Edit pg_hba.conf (enter/change entries below).
```
 # IPv4 local connections:
 host    all             all             127.0.0.1/32            trust
```

#### 5. Setup wildfly server
 * Download WildFly-16.0.0.Final

 * Copy wildfly-staff/standalone-full.xml to the directory /path/to/wildfly-16.0.0.Final/standalone/configuration/
 * Copy wildfly-staff/postgressql-42.2.5.jar to the directory /path/to/wildfly-16.0.0.Final/standalone/deployments/
 * Copy wildfly-staff/custom.properties to the directory /path/to/wildfly-16.0.0.Final/
 * (wildfly-staff is the directory contained in your cloned project)

 * Launch wildfly server with command below
 * cd /path/to/wildfly-16.0.0.Final/
 * ./bin/standalone.sh -c standalone-full.xml
 * (ctrl + C to stop it)

 * Copy server/target/opendolphin-server-2.7.0.war in your project to the directory /path/to/wildfly-16.0.0.Final/standalone/deployments/
 * This deploys dolphin server applications which client connects in wildfly.


#### 6. Launch OpenDolphin Client
  * Select [client] in the configurations, then run it.
  * You can see the login dialog of OpenDolphin.
  * Enter admin as password, then click the button login.
