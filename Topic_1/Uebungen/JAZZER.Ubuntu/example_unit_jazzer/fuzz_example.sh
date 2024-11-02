
JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64
CLASSPATH=./:$JAVA_HOME/lib:../jazzer_standalone.jar:./jazzer-junit-0.22.1.jar:./junit5-4.6.14.jar

javac -cp $CLASSPATH Example.java
javac -cp $CLASSPATH MagicStringsFuzzTest.java

java -cp $CLASSPATH com.code_intelligence.jazzer.Jazzer --target_class=MagicStringsFuzzTest 

