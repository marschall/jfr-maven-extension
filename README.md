JFR Maven Extension  [![Build Status](https://github.com/marschall/jfr-maven-extension/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/marschall/jfr-maven-extension/actions?query=branch%3Amaster) [![Maven Central](https://img.shields.io/maven-central/v/com.github.marschall/jfr-maven-extension?color=31c653&label=maven%20central)](https://central.sonatype.com/artifact/com.github.marschall/jfr-maven-extension) [![Javadocs](https://www.javadoc.io/badge/com.github.marschall/jfr-maven-extension.svg)](https://www.javadoc.io/doc/com.github.marschall/jfr-maven-extension)
===================

This Maven extension generates JFR events for a Maven build.

Usage
-----


```xml
<build>
  <extensions>
    <extension>
      <groupId>com.github.marschall</groupId>
      <artifactId>jfr-maven-extension</artifactId>
      <version>0.2.0</version>
    </extension>
  </extensions>
</build>
```

To create a recording you need the following or similar options

```
export MAVEN_OPTS="-XX:StartFlightRecording:filename=recording.jfr -XX:FlightRecorderOptions:stackdepth=256"
```

If you want to have the unit test execution in the same recording you need to set

```xml
<forkCount>0</forkCount>
```

in the configuration of the `maven-surefire-plugin`

Screenshot
----------

<img src="https://github.com/marschall/jfr-maven-extension/raw/master/src/main/javadoc/jfr-maven-extension.png" width="665" height="280" alt="Sample Screenshot"/>
