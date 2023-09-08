This repository has the code to reproduce [this micronaut issue](https://github.com/micronaut-projects/micronaut-core/issues/9843). 

It implements a Micronaut 4.1.x service that counts the number of bytes of https://micronaut.io/launch landing page.

# How to reproduce
Use Java 17. You may reproduce it in two different ways.

## 1. Run tests

1. Run tests:
   ```
    ./gradlew test --info
   ```
2. Check that, when the delay matches the read timeout (1.9s), the requests start failing.

## 2. Run service and execute curl

1. Run service:
   ```
    ./gradlew run
   ```
2. In a new terminal run the delayed curls:
   ```
   while true; do
     /usr/bin/time curl localhost:8080 -w "\n"
     echo "---sleeping 1.9s---"; sleep 1.9
   done
   ```
3. Check that some requests fail. Try changing the sleep to see that the service works fine otherwise.
