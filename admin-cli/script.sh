# Set common environment variables
export POSTGRES_IP=salt.db.elephantsql.com
export POSTGRES_PORT=5432
export POSTGRES_USER=
export POSTGRES_PASS=
export DATABASE_URL=postgres://$POSTGRES_USER:$POSTGRES_PASS@$POSTGRES_IP/$POSTGRES_USER
export NUM_TESTS=1
export PORT=8080
export AUTH=1
export CREDENTIAL=
if [ "$1" == "package" ]; then
    # Run Maven clean and package $: ./script.sh package
    mvn clean
    mvn package -Dmaven.test.skip
elif [ "$1" == "run" ]; then
    # Run Maven exec:java $: ./script.sh run
    mvn exec:java
elif [ "$1" == "test" ]; then
    # Run Maven test $: ./script.sh test i
    mvn test
elif [ "$1" == "java" ]; then
    # Run java's jar with dependencies
    java -jar ./target/backend-1.0-SNAPSHOT-jar-with-dependencies.jar
else
    # No arguments
    mvn clean
    mvn package
    mvn exec:java
fi
