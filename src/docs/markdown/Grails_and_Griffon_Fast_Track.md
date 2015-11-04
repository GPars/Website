---
layout: master
title: Grails and Griffon Fast Track
---

# Grails and Griffon Fast Track

GPars has been designed as a concurrency library for the Groovy programming
language. This naturally implies GPars is very friendly to Grails and Griffon
frameworks and offers applications, which build on top of these frameworks,
the ability to:

* Run expensive computations in the background
* Parallelize access to remote services or databases and so limit the effect of latency of these services
* Leverage the high-level concurrency concepts like actors, agents, parallel collections, dataflow and others

To enable GPars in your Grails application, alter the _BuildConfig.groovy_
file as follows:

{% highlight groovy %}
repositories {
    mavenCentral()
    mavenRepo 'http://repository.jboss.org/maven2/'
}
dependencies {
    build 'org.codehaus.gpars:gpars:1.1.0'
}
{% endhighlight %}

In Griffon, use the following:

{% highlight groovy %}
griffon.project.dependency.resolution = {
    inherits "global"

    default dependencies
        repositories {
            griffonHome()
            mavenCentral()
        }
        dependencies {
            runtime org.codehaus.gpars:gpars:1.1.0
        }
    }
}
{% endhighlight %}

Once the dependency has been specified, you can start using GPars in your
project. You may now want to check out the [Groovy Fast
Track](Groovy_Fast_Track.html) to do your first GPars experiments. Alternatively go
directly to the [User Guide](http://gpars.org/guide/index.html) and have fun!
