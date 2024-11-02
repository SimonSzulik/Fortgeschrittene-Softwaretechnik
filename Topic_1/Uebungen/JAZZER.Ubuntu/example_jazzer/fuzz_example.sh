
JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64
CLASSPATH=./:$JAVA_HOME/lib:../jazzer_standalone.jar

javac -cp $CLASSPATH ExampleFuzzer.java

javac -cp $CLASSPATH ExampleFuzzerHooks.java

../jazzer --target_class=ExampleFuzzer --custom_hooks=ExampleFuzzerHooks --custom_hooks=ExampleFuzzerHooks
# java  com.code_intelligence.jazzer.Jazzer --target_class=ExampleFuzzer --custom_hooks=ExampleFuzzerHooks



