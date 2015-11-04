---
layout: master
title: Code Style
---

# Code Style

A few simple rules to follow when contributing to the GPars project:

1. Prefer _final_ wherever applicable - variables, parameters, methods and classes
1. Single-statement conditions, _else_ branches or loops don't need braces - _if (cond) latch.countDown()_
1. Place the openning brace on the same line - _class Foo {_
1. Follow the common Java/Groovy naming and capitalization conventions
1. _Tab_ size is four characters
1. The _Code Style_ IntelliJ IDEA inspection profile is the ultimate guide and verification mechanism for the GPars code base. The regular reports can be checked (after login) at [the particular TeamCity page](http://teamcity.jetbrains.com/viewType.html?buildTypeId=bt183&tab=buildTypeStatusDiv).
1. Any apidoc for methods with Closure arguments like in Promise.java should declare which arguments are passed into the closure call and which return values (if any) are expected.
