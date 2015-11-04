---
layout: master
title: Integration
---

# Integration

Check out how to integrate GPars into your projects.

## Grape

Integration using _Grape_ is very straightforward:

    @Grab(group='org.codehaus.gpars', module='gpars', version='1.2.1')

### Snapshots

    @GrabResolver(name='gpars', root='http://snapshots.repository.codehaus.org/', m2Compatible=true)
    @Grab(group='org.codehaus.gpars', module='gpars', version='1.3-SNAPSHOT')

#### Grape config (optional)

GPars optionally relies on a couple of third-party libraries. Most of them
reside in the central maven repository, however, since since the Netty lib
resides in the jboss repository only, to leverage the (prototype) remoting
capabilities, you either need to use the _GrabResolver_ annotation (in Groovy
1.7):

    @GrabResolver(name='jboss', root='http://repository.jboss.org/maven2/')

or make sure your grapeConfig.xml file contains all the necessary maven
repositories:

   <!-- based on http://groovy.codehaus.org/Grape#Grape-CustomizeIvysettings -->
   <ivysettings>
     <settings defaultResolver="downloadGrapes"/>
     <resolvers>
       <chain name="downloadGrapes">
         <filesystem name="cachedGrapes">
           <ivy pattern="${user.home}/.groovy/grapes/[organisation]/[module]/ivy-[revision].xml"/>
           <artifact pattern="${user.home}/.groovy/grapes/[organisation]/[module]/[type]s/[artifact]-[revision].[ext]"/>
         </filesystem>
         <!-- todo add 'endorsed groovy extensions' resolver here -->
         <filesystem name="m2local" m2compatible="true">
           <artifact pattern="${user.home}/.m2/repository/[organisation]/[module]/[revision]/[module]-[revision].[ext]" />
         </filesystem>
         <ibiblio name="ibiblio" m2compatible="true"/>
         <ibiblio name="codehaus" root="http://repository.codehaus.org/" m2compatible="true"/>
         <ibiblio name="jboss" root="http://repository.jboss.org/maven2/" m2compatible="true"/><!-- added by me -->
         <ibiblio name="java.net2" root="http://download.java.net/maven/2/" m2compatible="true"/>
         <!-- <ibiblio name="codehaus.snapshot" root="http://snapshots.repository.codehaus.org/" m2compatible="true"/> --><!-- Enable for GPars snapshots -->
       </chain>
     </resolvers>
   </ivysettings>

## Gradle

GPars is stored in the central maven repository at
<http://repo1.maven.org/maven2/org/codehaus/gpars/> and also in the Codehaus
maven repository at <http://repository.codehaus.org/org/codehaus/gpars/gpars/>
. To include GPars in your project add either of the following repositories
and also the dependency into your project:

    repositories {
        mavenCentral()
        //add repositories for optional dependencies
        maven{url 'http://repository.jboss.org/maven2/'}
    }

    dependencies {
        compile "org.codehaus.gpars:gpars:1.2.1"
    }

### Snapshots

    repositories {
        maven{url 'http://snapshots.repository.codehaus.org/'}
        //add repositories for optional dependencies
        maven{url 'http://repository.jboss.org/maven2/'}
    }
    dependencies {
        compile "org.codehaus.gpars:gpars:1.3-SNAPSHOT"
    }

## Maven

To include GPars in your project add the following (optional) repositories and
dependencies into your project:

    <repositories>
        <repository>
            <id>jboss</id>
            <url>http://repository.jboss.org/maven2/</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>org.codehaus.gpars</groupId>
            <artifactId>gpars</artifactId>
            <version>1.2.1</version>
        </dependency>
    </dependencies>

### Sample maven-based Java API demo project

You may also like [a stand-alone maven-based Java API sample
project](http://gpars.org/download/1.2.0/gpars-mvn-java-demo-1.2.0.zip)
showing how to use GPars from Java and how to integrate GPars with Maven.

And the same [sample project](http://bamboo.ci.codehaus.org/browse/GPARS-
DEF/latest/artifact) is available for the SNAPSHOT release.

    <repositories>
        <repository>
            <id>jboss</id>
            <url>http://repository.jboss.org/maven2/</url>
        </repository>
        <repository>
            <id>codehaus.snapshots</id>
            <url>http://snapshots.repository.codehaus.org</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>org.codehaus.gpars</groupId>
            <artifactId>gpars</artifactId>
            <version>1.3-SNAPSHOT</version>
        </dependency>
    </dependencies>


### Snapshots

## Grails

### Grails since 1.2

Leveraging the built-in dependency management you can instead of installing the
plugins update the BuildConfig.groovy file accordingly:

    repositories {
        mavenCentral()
        //  maven{url 'http://snapshots.repository.codehaus.org'}  //enable if using GPars snapshots
        maven{url 'http://repository.jboss.org/maven2/'}
    }
    dependencies {
        build 'org.codehaus.gpars:gpars:1.2.1'
    }

## Griffon

Using the Griffon built-in dependency management:

    griffon.project.dependency.resolution = {
        inherits "global"

        default dependencies
            repositories {
                griffonHome()
                mavenCentral()
            }
            dependencies {
                runtime org.codehaus.gpars:gpars:1.2.1
            }
        }
    }

You may also consider using the plugins to enable GPars for older Griffon
versions.

## Dependencies

GPars itself depends on a couple of libraries from the maven central
repository. Check out the GPars public pom:

  <?xml version="1.0" encoding="UTF-8"?>
  <project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <modelVersion>4.0.0</modelVersion>
    <groupId>org.codehaus.gpars</groupId>
    <artifactId>gpars</artifactId>
    <version>1.2.1</version>
    <name>GPars</name>
    <description>The Groovy and Java high-level concurrency library offering actors, dataflow, CSP, agents, parallel collections, fork/join and more</description>
    <url>http://gpars.codehaus.org</url>
    <inceptionYear>2009</inceptionYear>
    <licenses>
      <license>
        <name>The Apache Software License, Version 2.0</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <dependencies>

      <dependency>
        <groupId>org.multiverse</groupId>
        <artifactId>multiverse-core</artifactId>
        <version>0.7.0</version>
        <scope>compile</scope>
      </dependency>

      <dependency>
        <groupId>org.codehaus.jcsp</groupId>
        <artifactId>jcsp</artifactId>
        <version>1.1-rc5</version>
        <scope>compile</scope>
        <optional>true</optional>
      </dependency>

      <dependency>
        <groupId>org.codehaus.jsr166-mirror</groupId>
        <artifactId>jsr166y</artifactId>
        <version>1.7.0</version>
        <scope>compile</scope>
      </dependency>

      <dependency>
        <groupId>org.codehaus.groovy</groupId>
        <artifactId>groovy-all</artifactId>
        <version>2.1.9</version>
        <scope>compile</scope>
        <optional>true</optional>
      </dependency>

      <dependency>
        <groupId>org.jboss.netty</groupId>
        <artifactId>netty</artifactId>
        <version>3.2.9.Final</version>
        <scope>compile</scope>
        <optional>true</optional>
      </dependency>
    </dependencies>
  </project>

## License

Licensed under APL 2.0 - [License](License)
