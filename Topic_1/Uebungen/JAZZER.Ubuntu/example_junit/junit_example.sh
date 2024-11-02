
JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64
CLASSPATH=./:$JAVA_HOME/lib:./hamcrest-core-1.3.jar:./junit-4.13.2.jar

javac -cp $CLASSPATH Example.java 

java -cp $CLASSPATH Example blabla

java -cp $CLASSPATH Example 7a7a7a7a7a7a7a7a

javac -cp $CLASSPATH MagicStringsTest.java 

java -cp $CLASSPATH org.junit.runner.JUnitCore MagicStringsTest


