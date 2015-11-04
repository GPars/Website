---
layout: master
title: Dataflow
---

# Dataflow

Dataflow concurrency offers an alternative concurrency model, which is
inherently safe and robust.

## Introduction

Check out the small example written in Groovy using GPars, which sums results
of calculations performed by three concurrently run tasks:

{% highlight groovy linenos %}
import static groovyx.gpars.dataflow.Dataflow.task
final def x = new DataflowVariable()
final def y = new DataflowVariable()
final def z = new DataflowVariable()

task {
    z << x.val + y.val
    println "Result: ${z.val}"
}

task {
    x << 10
}

task {
    y << 5
}
{% endhighlight %}

We start three logical tasks, which can run in parallel and perform their
particular activities. The tasks need to exchange data and they do so using
**Dataflow Variables**. Think of Dataflow Variables as one-shot channels
safely and reliably tranferring data from producers to their consumers.

The Dataflow Variables have a pretty straightforward semantics. When a task
needs to read a value from _DataflowVariable_(through the val property), it
will block until the value has been set by another taks or thread (using the
'<<' operator). Each _DataflowVariable_ can be set **only once**in its
lifetime. Notice that you don't have to bother with ordering and synchronizing
the tasks or threads and their access to shared variables. The values are
magically transferred among tasks at the right time without your intervention.
The data flow seamlessly among tasks / threads without your intervention or
care.

**Implementation detail:** The three tasks in the example **do not necessarily need to be mapped to three
  physical threads**. Tasks represent so-called "green" or "logical" threads and can be mapped under the
  covers to any number of physical threads. The actual mapping depends on the scheduler, but the outcome of
  dataflow algorithms doesn't depend on the actual scheduling.

## Benefits

Here's what you gain by using Dataflow Concurrency (by Jonas Boner
<http://www.jonasboner.com> ):

* No race-conditions
* No live-locks
* Deterministic deadlocks
* Completely deterministic programs
* BEAUTIFUL code.

This doesn't sound bad, does it?

If you'd like to know more on this interesting concept, check out [the
Dataflow concurrency section of the User
Guide](http://gpars.org/guide/guide/dataflow.html).

## Further reading

[http://github.com/jboner/scala-
dataflow/tree/f9a38992f5abed4df0b12f6a5293f703aa04dc33/src Scala Dataflow
library by Jonas Boner](http://github.com/jboner/scala-dataflow/tree/f9a38992f5abed4df0b12f6a5293f703aa04dc33/src Scala Dataflow
library by Jonas Bon??r)

[http://jonasboner.com/talks/state_youre_doing_it_wrong/html/all.html JVM
concurrency presentation slides by Jonas
Boner](http://jonasboner.com/talks/state_youre_doing_it_wrong/html/all.html JVM concurrency presentation slides by Jonas Bon??r)

<http://github.com/larrytheliquid/dataflow/tree/master Dataflow Concurrency
library for Ruby>
