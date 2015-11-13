---
layout: master
title: GPars History
---

# GPars History

In October 2008 [Vaclav Pech](http://www.vaclavpech.eu/index.html) started a
pet open-source project called
[GParallelizer](http://code.google.com/p/gparallelizer/) with the intention to
build several easy-to-use Groovy-based DSLs around the Fork/Join and the
ParallelArray concept implemented as part of jsr-166y. A thin abstraction
layer allowing to run Groovy closures asynchronously was also included. Under
the impressions of Scala, the actors abstraction was added shortly after and
has gradually evolved from a mere experiment into a usable implementation.
Some time later, inspired by [Jonas Boner's](http://jonasboner.com/)
experiments with dataflow concurrency, the concept of dataflow variables,
streams and tasks has been included as well as the Clojure's agent concept has
been implemented.

In September 2009 Dierk Koenig, Alex Tkachman, Russel Winder and Paul King
joined the team, the project moved to Codehaus under a new name -- **GPars**.

In October 2009 the dataflow abstraction has been enhanced with dataflow
operators and an initial Fork/Join convenience layer.

Since December 2009 GPars has a [logo](GPars_Logo.html) .

The team made their first release under a new project name in December 2009 -
GPars 0.9 came out with a fancy [User
Guide](http://www.gpars.org/guide/index.html) .

From the increasing amount of feedback and comments it become evident that
GPars is gaining some attention. GPars has been presented at several
conferences in Europe and North America (e.g. W-JAX 2009, JAX 2010, Jfokus
2010, GeeCON 2010). You may also check out a more complete list of
[Presentations](Presentations) and [Events](Events.html).

Jon Kerridge from the University of Kent joined GPars and contributed his
[JCSP](http://www.cs.kent.ac.uk/projects/ofa/jcsp/) wrapper library, called
Groovy CSP, to the project code base.

GPars reached another milestones in May 2010 with the 0.10 release, 0.11 in
October 2011, 0.12 in May 2011 and 1.0 in December 2012.

The most recent release, 1.1.0 arrived in July 2013.
