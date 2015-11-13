---
layout: master
title: Concepts Compared
---

# Concepts Compared

People love variety but frequently fail to make their choices. Since GPars
brings several concurrency paradigms to the table, we decided to build a
simplified guide helping users pick the right concept for their task.

## A Short Guide

Data decomposition
\---> Geometric decomposition (Parallel collections)
\---> Recursive (Fork/Join)

Tasks decomposition
\---> Independent tasks (Parallel collections, dataflow tasks)
\---> Recursively dependent tasks (Fork/Join)
\---> Tasks with mutual dependencies (Composable asynchronous functions,
Dataflow tasks, CSP)
\---> Tasks cooperating on the same data (Stm, agents)

Streamed data decomposition
\---> Pipeline (Dataflow channels and operators)
\---> Event-based (Actors, Active objects)

## A Detailed Guide

### Processing a collection in parallel

Whenever you come across a collection that takes a while to process, consider
using parallel collection methods. Although enabling collections for parallel
processing imposes overhead, it frequently outweights the ineffectiveness of
sequential processing. GPars gives you two options here:

* _GParsPool_, which leverages the efficient Fork/Join algorithm using a Fork/Join thread pool
* _GParsExecutorsPool_, which builds on plain old Java 5 executors

### Processing a collection in parallel and chaining several methods

The parallel collection methods, such as _eachParallel_, _findAllParallel_,
etc., discussed above provide an easy migration path from sequential to
concurrent code. However, when chaining multiple collection-processing methods
it is more effective to use the _map/reduce_ principle instead. Use
_GParsPool_-based _map/reduce_ operations to avoid the overhead of creating
and destroying parallel collection for each parallel method in the chain. The
conversion will be done only once - during the call to retrieve the _parallel_
property of a collection. Since then the parallel tree-like data structure
will be reused by all subsequent calls. The _map/reduce_ approach should be
preferred for chained parallel method calls.

### Processing a hierarchical data structure or a divide-n-conquer recursive algorithm

Trees or hierarchies are naturally parallel data structures. _Fork/Join_
algorithms process hierarchical data or problems concurrently. Use _GParsPool_
and its _Fork/Join_ convenience layer was designed to created _Fork/Join_
calculations easily.

### Creating asynchronous functions that will run in the background whenever executed

Long-lasting calculations can be run in the background with very little
syntactic and performance overhead. Use asynchronous functions within either
_GParsPool_ or _GParsExecutorsPool_

* _callAsync()_ to invoke a closure asynchronously
* _asyncFun()_ to create an asynchronous closure out of the original one.
* Asynchronous closures can then be combined just like the original sequential ones

### Building concurrent systems for large data processing

Use dataflow channels and operators to build networks of independent,
asynchronous, event-driven calculations that process data. Dataflow networks
are typically used for data or image processing, data mining or computer
simulations.

### Protecting shared piece of data from concurrent access

In some scenarios shared mutable state models the problem domain better than
other paradigms. Imagine, for example, a shopping cart accessed concurrently
by multiple user requests. To protect such a shared resource from concurrent
modifications use _Agents_. They will wrap the data and guard access to it.

### Protecting multiple shared data elements from concurrent access and preserving their mutual consistency

When the number of shared mutable resources grows and multiple of them need to
be touched without intervention from other threads, use _Software
Transactional Memory_ to demarcate ACI(D) transactions for concurrent blocks
of code that access the data. STM will give you the illusion that threads can
access data without care about the other threads. The STM engine will resolve
all conflicts and automatically re-try aborted transactions.

### Share one-shot values among threads/tasks, e.g. when testing asynchronously calculated results

Use _DataflowVariables_ with the thread-safe single-write multiple-read
semantics. They ensure the reader cannot continue before a value is safely
written to the variable by another thread. Also, they allow for callbacks to
be registered and invoked as soon as a value gets bound.

### Splitting algorithms explicitly into independent asynchronous objects with direct addressing

Use _Actors_ in one of its many flavors. This is exactly their domain -
independent active objects exchanging messages asynchronously.

### Wrapping actors with a POJO facade

_Active Objects_ offer OO interface to actors. You get POJOs, whose methods
are asynchronous and whose state is protected under the same guarantees as
with actors.

### Splitting algorithms explicitly into independent concurrent processes with indirect addressing

Use _Dataflow tasks/processes_ communicating through _dataflow channels_ or
_Groovy CSP_. Unlike with _Actors_, you get deterministic behavior allowing
for re-use and composability. Additionally, you may also combine asynchronous
and synchronous communication channels to limit the number of unprocessed
messages in the network. The ability to address parties indirectly through
channels loosens the coupling between components of the algorithm and makes
tasks such as load-balancing or broadcasting easier to implement.
