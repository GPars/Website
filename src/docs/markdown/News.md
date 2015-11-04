---
layout: master
title: News
---

# News

[ ![User icon:
venca](http://docs.codehaus.org/images/icons/profilepics/default.png)
](http://gpars.codehaus.org/display/~venca) [Remoting for
GPars](http://docs.codehaus.org/display/GPARS/2014/09/06/Remoting+for+GPars)

[Vaclav Pech](http://gpars.codehaus.org/display/~venca) posted on Sep 06, 2014

This is quick overview of Remoting for GPars realized during Google Summer of
Code 2014 by Rafal Slawik. More details can be found in the official
documentation in section on
[Remoting](http://gpars.org/SNAPSHOT/aguide/index.html#_remoting). The
implementation has already been made part of GPars 1.3-SNAPSHOT is is
[available for immediate use](Integration).

Behind the scenes, the **Netty** library and the standard serialization
mechanism were used as the transportation layer. Basically, you can use
_Dataflows_ with any data type, send custom messages to _Actors_ and store
custom states in _Agents_ as long as these objects are seralizable.

The Dataflow structures that support remoting: _DataflowVariable_,
_DataflowBroadcast_, _DataflowQueue_.

General use of our remoting implementation requires:

* at host A: creating a context and publishing a structure (variable, queue, actor, etc.) under some name
* at host B: creating a context and retrieval of a structure with that name

The **context** concept is useful for testing (one can have original instance
and a remote proxy withing that same VM) or other purposes (eg. each thread
has its own remote proxy). What's important, such a remote proxy has the same
interface and therefore can be used like if it was a regular intstance. Let's
see an example on how to use remoting for _DataflowVariables_:

1. At host A:

    def remoteDataflows = RemoteDataflows.create() // creates context
    remoteDataflows.startServer HOST PORT // starts server that waits for requests at HOST:PORT
    def variable = new DataflowVariable() // creates variable instance
    remoteDataflows.publish variable "my-first-variable" // registers it within the context under given name

2. At host B:

    def remoteDataflows = RemoteDataflows.create() // creates context
    def remoteVariablePromise = remoteDataflows.getVariable HOST, PORT, "my-first-variable" // retrieves promise of variable with given name
    def remoteVariable = remoteVariablePromise.get() // extracts remote proxy from promise

You can find more examples in samples package:
_groovyx.gpars.samples.remote.dataflow.*_

Now, let's take a look at remoting for _Actors_ and consider Ping-Pong example
(_groovyx.gpars.samples.remote.actor.pingpong_). Let's start with creating an
actor that responds to every message with "PONG". Such actor can look as
follows:

    def pongActor = Actors.actor { loop { react { println it reply "PONG" } } }

It waits in loop from messages and when one arrives it prints it and replies
with "PONG". To be able to access this actor from remote host it has to be
published:

    def remoteActors = RemoteActors.create() // creates context
    remoteActors.startServer HOST, PORT // starts server that waits for requests at HOST:PORT
    remoteActors.publish pongActor, "pong" // registers pongActor within context under name "pong"

What's left is to retrieve the proxy object to that actor at remote host. It
can be done as follows:

    def remoteActors = RemoteActors.create() // creates context
    def pingActor = Actors.actor {
        def remotePongActor = remoteActors.get HOST, PORT, "pong" get() // gets remote proxy to actor name "pong" at HOST:PORT
        remotePongActor << "PING" // sends message to it
        react {
            println it // prints reply from remote actor
        }
    }

An extended example can be found in
_groovyx.gpars.samples.remote.actor.pingpong_. More examples of remoting for
Actors are available in _groovyx.gpars.samples.remote.actor.*_. An example of
remoting for _Agents_ is available in _groovyx.gpars.samples.remote.agent._

In the future we can introduce multiplexing of connections between hosts
(currenly each get opens new connection) and some discovery mechanism (to
avoid using explicit HOST:PORT).

[ ![User icon:
venca](http://docs.codehaus.org/images/icons/profilepics/default.png)
](http://gpars.codehaus.org/display/~venca) [Here comes GPars
1.1](http://docs.codehaus.org/display/GPARS/2013/07/25/Here+comes+GPars+1.1)

[Vaclav Pech](http://gpars.codehaus.org/display/~venca) posted on Jul 25, 2013

The GA release of GPars 1.1.0 has just been published and is ready for you to
grab. It brings gradual improvements into dataflow as well as a few other
domains. Some highlights:

* LazyDataflowVariable added to allow for lazy asynchronous values
* Timeout for Selects
* Added a Promise-based API for value selection through the Select class
* Enabled listening for bind errors on DataflowVariables
* Minor API improvement affecting Promise and DataflowReadChannel
* Protecting agent's blocking methods from being called from within commands
* Updated to the latest 0.7.0 GA version of Multiverse
* Migrated to Groovy 2.0
* Used @CompileStatic where appropriate
* A few bug fixes

You can [download](Download) GPars 1.1.0 directly or [grab it from the maven
repo](Integration).

Have a lot of fun trying out GPars 1.1.0!

[ ![User icon:
venca](http://docs.codehaus.org/images/icons/profilepics/default.png)
](http://gpars.codehaus.org/display/~venca) [Arriving at
1.1.0](http://docs.codehaus.org/display/GPARS/2013/07/17/Arriving+at+1.1.0)

[Vaclav Pech](http://gpars.codehaus.org/display/~venca) posted on Jul 17, 2013

A first release candidate for GPars 1.1.0 has been made available. The final
1.1.0 GA should be expected in a few days. The 1.1.0 release is a gradual
improvement of 1.0.0 with additions mostly in the Dataflow domain. Starting
with 1.1 GPars requires Groovy 2.0 or higher. Check out the most noteworthy
new capabilities:

Dataflow

* LazyDataflowVariable added to allow for lazy asynchronous values
* Timeout for Selects
* Added a Promise-based API for value selection through the Select class
* Enabled listening for bind errors on DataflowVariables
* Minor API improvement affecting Promise and DataflowReadChannel

## Agent

* Protecting agent's blocking methods from being called from within commands

## Stm

* Updated to the latest 0.7.0 GA version of Multiverse

## Other

* Migrated to Groovy 2.0
* Used @CompileStatic where appropriate

[Get GPars 1.1.0-rc1](http://gpars.org/download/1.1.0-rc1/), take it for a
spin and please report all issues so we can fix them before GA.

[ ![User icon:
venca](http://docs.codehaus.org/images/icons/profilepics/default.png)
](http://gpars.codehaus.org/display/~venca) [GPars 1.0
arrived](http://docs.codehaus.org/display/GPARS/2012/12/19/GPars+1.0+arrived)

[Vaclav Pech](http://gpars.codehaus.org/display/~venca) posted on Dec 19, 2012

I'm happy to announce that after four years of development
[GPars](index.html), the Groovy concurrency library, has just reached its 1.0
mark. A fresh and crispy GPars 1.0.0 is now ready for you to
[grab](Integration) or [download](Download) and use on your projects. Also,
the up-coming Groovy releases will bundle GPars 1.0.

Compared to the previous release, 1.0 brings several performance enhancements,
considerable API updates, polished documentation and numerous functionality
improvements, mostly in the dataflow area. Please, check out the What's new
section of the [user guide](http://www.gpars.org/1.0.0/guide) for the details.
Full [release notes](http://jira.codehaus.org/secure/ReleaseNote.jspa?projectI
d=12030&version=17007) are also available.

I would like to use this opportunity to thank all the Groovy people, who have
over time contributed in one way or another to the success of GPars. It is my
honour to be part of such a helpful and encouraging community. In particular,
I would like to thank my colleague [GPars commiters](Team), namely Paul King,
Dierk Koenig, Alex Tkatchman and Russel Winder, who we've been consistently
pushing the project forward and without whom it would hardly ever get this
far. I also greatly appreciate the support we received from Guillaume Laforge,
the Groovy supreme commander. Thank you all gentlemen!

Groovy concurrency times ahead!

Vaclav

[ ![User icon:
venca](http://docs.codehaus.org/images/icons/profilepics/default.png)
](http://gpars.codehaus.org/display/~venca) [The first release candidate of
1.0 is available](http://docs.codehaus.org/display/GPARS/2012/12/11/The+first+
release+candidate+of+1.0+is+available)

[Vaclav Pech](http://gpars.codehaus.org/display/~venca) posted on Dec 11, 2012

We are almost there. The 1.0 release is just round the corner. To ensure that
1.0 meets your quality expectations we first prepared a release candidate to
test the waters. To take GPars for a test ride, please [download](Download) or
[grab](Integration) it at the usual places, check out the [release notes](http
://jira.codehaus.org/secure/ReleaseNote.jspa?projectId=12030&version=17007)
and let us know if something is missing.

Vaclav

[ ![User icon:
venca](http://docs.codehaus.org/images/icons/profilepics/default.png)
](http://gpars.codehaus.org/display/~venca) [Beta 3 is
out](http://docs.codehaus.org/display/GPARS/2012/09/10/Beta+3+is+out)

[Vaclav Pech](http://gpars.codehaus.org/display/~venca) posted on Sep 10, 2012

GPars-1.0-beta-3 has been made available for you to try out.
Apart from the usual doze of features and fixes, including speed-up for some
operations on parallel collections or lifecycle events for dataflow operators,
there is one major change compared to beta-2 worth pointing out explicitly:

  * GPars no longer depends on the extra166y artifact. The parallel array library by Doug Lea has been integrated into GPars. The jsr166y (Fork/Join) jar still remains in the dependency list until we migrate GPars to jdk7.

Grab [gpars-1.0-beta-3](http://gpars.org/download/1.0-beta-3/) and have a lot
of fun with the new release.

[ ![User icon:
venca](http://docs.codehaus.org/images/icons/profilepics/default.png)
](http://gpars.codehaus.org/display/~venca) [GPars 1.0 beta-1 ready for a test
ride](http://docs.codehaus.org/display/GPARS/2011/12/30/GPars+1.0+beta-1+ready
+for+a+test+ride)

[Vaclav Pech](http://gpars.codehaus.org/display/~venca) posted on Dec 30, 2011

 Our first step towards the 1.0 release has been achieved. The _beta-1_
release is now available for you to [grab](Integration) or
[download](Download). Have fun and if you feel somethings needs our attention,
please let us know.

 The GPars team

[ ![User icon:
venca](http://docs.codehaus.org/images/icons/profilepics/default.png)
](http://gpars.codehaus.org/display/~venca) [Parallel Game of
Life](http://docs.codehaus.org/display/GPARS/2011/09/01/Parallel+Game+of+Life)

[Vaclav Pech](http://gpars.codehaus.org/display/~venca) posted on Sep 01, 2011

I'd like to direct you to my recent blog post detailing the use of Dataflow
operators. It uses the popular Game of Life coding excercise to illustrate the
principles of the dataflow concept. Check it out at [my personal
blog](http://www.jroller.com/vaclav/entry/parallel_game_of_life).

[ ![User icon:
venca](http://docs.codehaus.org/images/icons/profilepics/default.png)
](http://gpars.codehaus.org/display/~venca) [GPars turns 0.12 today](http://do
cs.codehaus.org/display/GPARS/2011/06/02/GPars+turns+0.12+today)

[Vaclav Pech](http://gpars.codehaus.org/display/~venca) posted on Jun 02, 2011

We have some great news to all the parallel souls out there - GPars 0.12 has
just hit [the shelves](Download). The new version comes with lots of big and
small improvements, out of which these are the most notable ones:

* Composable asynchronous functions
* The newest version of Doug Lea's Fork/Join framework (aka jsr-166y)
* Active Objects
* Initial stub at Software Transactional Memory support using Multiverse

Check out the full [release notes](http://jira.codehaus.org/secure/ReleaseNote
.jspa?projectId=12030&version=16994) for more details.
To quickly get up-to-speed with GPars, check out our updated [User
Guide](http://gpars.org/0.12/guide/index.html), which is now also available in
[pdf format](http://gpars.org/0.12/guide/gpars-guide-0.12.pdf).

Your GPars team

[ ![User icon:
venca](http://docs.codehaus.org/images/icons/profilepics/default.png)
](http://gpars.codehaus.org/display/~venca) [JVM Concurrency and Actors with G
Pars](http://docs.codehaus.org/display/GPARS/2011/04/26/JVM+Concurrency+and+Ac
tors+with+GPars)

[Vaclav Pech](http://gpars.codehaus.org/display/~venca) posted on Apr 26, 2011

Dr.Dobb's has just published my overview article on actors in GPars. You may
check it out at ﻿<http://drdobbs.com/high-performance-computing/229402193>

Vaclav
