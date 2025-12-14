#  Giftwrapping service

The Giftwrapping Service provides three options to package your products for the christmas season.

## Building locally

The Giftwrapping service uses gradlew to compile/install/distribute. Gradle wrapper is already part of the source code. To build Giftwrapping service, run:

```
./gradlew installDist
```
It will create executable script src/giftwrappingservice/build/install/giftwrappingservice/bin/GiftWrappingService

### Upgrading gradle version
If you need to upgrade the version of gradle then run

```
./gradlew wrapper --gradle-version <new-version>
```

## Building docker image

From `src/giftwrappingservice/`, run:

```
docker build ./
```

