gradle-test-kit
===============

Some experiments in a kit for testing custom Gradle logic (e.g. plugins/tasks)

Compatiblity
============

This library is for use with Gradle 1.10+

Usage
=====

Add the following to your `build.gradle` file:

```groovy
repositories {
  maven {
    url 'http://dl.bintray.com/johnrengelman/gradle-plugins'
  }
}
dependencies {
  testCompile 'org.gradle.api.plugins:gradle-test-kit:0.1'
}
```