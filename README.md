# DESCRIPTION
Simple Netty-based web server that solves the human pyramid problem.

# BUILD AND EXECUTION STEPS
From the command line:  
1. change the current directory to be the "project" folder (i.e. the one containing the pom.xml file);  
2. type "mvn clean package" or "mvn clean package -Dmaven.test.skip=true" to build with or without tests respectively and wait for maven to generate the jar file in the "project/target" folder;  
3. from that directory, type "java -jar <artifactId>-<version>-jar-with-dependencies".