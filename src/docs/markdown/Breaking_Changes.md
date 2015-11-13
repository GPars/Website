---
layout: master
title: Breaking Changes
---

# Breaking Changes

## 1.2.0

* No breaking changes

## 1.1.0

* Deprecated _foldParallel()_ in favor of _injectParallel()_ on parallel collections

## 1.0.0

* Removed all deprecated methods and classes
* The _stop()_ method on Dataflow operators has been renamed to _terminate()_ to preserve naming consistency accross concepts
* The_ reportError()_ method on Dataflow operators has been replaced with _addErrorHandler()_
* The _RightShift_ (>>) operator of _DataflowVariables_ and channels now calls _then()_ instead of _whenBound()_ and so can be chained

## 0.12

* Deprecated the _makeTransparent()_ method (use _makeConcurrent()_ instead)
* Removed deprecated actor classes - _AbstractPooledActor_
* Created _BlockingActor_ for fast thread-bound actors

## 0.11

* Deprecated _AbstractPooledActor_
* Actor timeout doesn't terminate the actor, but passes a TIMEOUT message to the message handler
* Created _DefaultActor_ as a replacement for _AbstractPooledActor_with the following differences
* removed the _receive_ method for blocking message read
* messages are not enhanced with the _reply()_ method nor the _sender_ property
* The _react()_ method doesn't throw controlled exception to clear the stack. It is the user responsibility to return from the actor body in order to allow the react block to be scheduled

## 0.10

## Renaming hints

* Parallelizer -> GParsPool
* Asynchronizer -> GParsExecutorsPool
* doParallel() -> withPool()
* withParallelizer() -> withPool()
* withExistingParallelizer() -> withExistingPool()
* withAsynchronizer() -> withPool()
* withExistingAsynchronizer() -> withExistingPool()
* orchestrate() -> runForkJoin()
* ActorGroup -> PGroup
* PooledActorGroup -> DefaultPGroup
* NonDaemonActorGroup -> NonDaemonPGroup
* Safe -> Agent
