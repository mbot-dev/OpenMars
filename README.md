## OpenDolphin simplified
Kazushi Minagawa


#### 1．Developing Enviroment
 * openjdk 11.0.2 2019-01-15
 * WildFly-16.0.0.Final(Java EE Full & Web Distribution)
 * Postgres 11
 * postgresql-42.2.5.jar(Java postgres driver)
 * Git
 * IntelliJ IDEA Community Edition


#### 2. Import the OpenDolphin project
 * Launch IntelliJ
 * In the start up panel, select Git from the bottom menu - Check out from Version Control, then fill the text box as below
 * URL: git@github.com:mbot-dev/OpenMars.git
 * Directory: Specify the top directory to clone the repository
 * Click the button Clone
 * Select Yes in the continued panel


#### 3．Compile
 * Select the target opendolphin[clean] in the Configurations menu(stays in upper right corner), then click the green arrow button to run the target
 * Select the target opendolphin[package] in the same menu, then run it
 * this cause downloading libraries .... wait until done


#### 4. Launch OpenDolphin Client
  * Select the target client in the configurations, then run it
  * You can see the login dialog of OpenDolphin
