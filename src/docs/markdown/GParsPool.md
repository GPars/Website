---
layout: master
title: GParsPool
---

# GParsPool

## Concurrent collection processing

Dealing with data frequently involves manipulating collections. Lists, arrays,
sets, maps, iterators, strings and lot of other data types can be viewed as
collections of items. The common pattern to process such collections is to
take elements sequentially, one-by-one, and make an action for each of the
items in row. Take, for example, the _min()_ function, which is supposed to
return the smallest element of a collection. When you call the _min()_ method
on a collection of numbers, the caller thread will create an _accumulator_ or
_so-far-the-smallest-value_ initialized to the minimum value of the given
type, let say to zero. And then the thread will iterate through the elements
of the collection and compare them with the value in the _accumulator_ . Once
all elements have been processed, the minimum value is stored in the
_accumulator_ .

This algorithm, however simple, is **totally wrong** on multi-core hardware.
Running the _min()_ function on a dual-core chip can leverage **at most 50%**
of the computing power of the chip. On a quad-core it would be only 25%.
Correct, this algorithm effectively **wastes 75% of the computing power** of
the chip.

Tree-like structures proved to be more appropriate for parallel processing.
The _min()_ function in our example doesn't need to iterate through all the
elements in row and compare their values with the _accumulator_ . What it can
do instead is relying on the multi-core nature of your hardware. A
_parallel_min()_ function could, for example, compare pairs (or tuples of
certain size) of neighboring values in the collection and promote the smallest
value from the tuple into a next round of comparison. Searching for minimum in
different tuples can safely happen in parallel and so tuples in the same round
can be processed by different cores at the same time without races or
contention among threads.

The _GParsPool_ class enables a **ParallelArray**-based (from JSR-166y) DSL on
collections.

Examples of use:

{% highlight groovy linenos %}
GParsPool.withPool {
    def selfPortraits = images.findAllParallel{
        it.contains me}.collectParallel {it.resize()
    }

    //a map-reduce functional style
    def smallestSelfPortrait = images.parallel
        .filter{it.contains me}
        .map{it.resize()}
        .min{it.sizeInMB}
}
{% endhighlight %}

The _GParsExecutorsPool_ class provides similar functionality to the
_GParsPool_ class, however uses JDK thread pools instead of the more efficient
**ParallelArray**-based (from JSR-166y) implementation.

Examples of use:

{% highlight groovy linenos %}
GParsExecutorsPool.withPool {
    def selfPortraits = images
        .findAllParallel{it.contains me}
        .collectParallel {it.resize()}
}
{% endhighlight %}

See the [Parallel Collection section in the User
Guide](http://gpars.org/guide/guide/dataParallelism.html) for more
information.
