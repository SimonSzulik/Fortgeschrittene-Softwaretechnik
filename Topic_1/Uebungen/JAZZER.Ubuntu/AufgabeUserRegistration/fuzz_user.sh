JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64
CLASSPATH=./:$JAVA_HOME/lib:../jazzer_standalone.jar

javac -cp $CLASSPATH UserFuzzer.java

for i in {1..150}
do
    ../jazzer --target_class=UserFuzzer 
done


