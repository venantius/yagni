# Yagni
[![Build Status](https://travis-ci.org/venantius/yagni.svg?branch=master)](https://travis-ci.org/venantius/yagni)
[![Dependency Status](https://www.versioneye.com/user/projects/5585e7f0363861001b000215/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5585e7f0363861001b000215)
[![Clojars Project](https://img.shields.io/clojars/v/venantius/yagni.svg)](https://clojars.org/venantius/yagni)

[Yagni](http://martinfowler.com/bliki/Yagni.html) - You Aren't Gonna Need It.

Yagni is a static code analyzer that helps you find unused code in your 
applications and libraries.

I've written a blog post describing Yagni in greater depth [here](http://blog.venanti.us/yagni/).

## Background

No matter how it happens, sooner or later an application is going to end up
with unused code. It's time to call Yagni, the exterminator. 

Yagni works by identifying all of the interned vars and declared Java classes
in the namespaces findable within your `:source-paths`, and then walking the 
forms of those vars and declarations.

As it walks the forms, it builds a graph of references to other vars and 
declarations. It then searches the graph from a set of entrypoints (by default
your project's `:main` method), and emits warnings for anything that it 
couldn't find in the graph's search.

## Installation

Merge the following into your `~/.lein/profiles.clj`:

```clojure
{:user {:plugins [[venantius/yagni "0.1.7"]]}}
```

## Usage

To have Yagni search for dead code, just run:

    $ lein yagni

## Configuration

Yagni works by searching your codebase from an initial set of entrypoints.
By default, Yagni assumes that the only entrypoint for your project is the one
listed in your project.clj's `:main` key. Obviously, this is only useful for
applications and tools with CLI invocations.

As libraries, multi-main programs, and certain other types of projects either
tend to have no `:main` or many entrypoint methods, you can instead enumerate
a list of entrypoints for your project in a `.lein-yagni` file in the root 
directory of your project. Feel free to take a look at the one in this project
as an example.

## Examples

Running `lein yagni` on the sample project located [here](https://github.com/venantius/yagni-test) will emit the following output:

```
$ lein yagni
=================== WARNING: Parents ======================
== Could not find any references to the following vars. ===
===========================================================

secondns/func-the-second

================== WARNING: Children ======================
== The following vars have references to them, but their ==
== parents do not.                                       ==
===========================================================

secondns/notafunc
```

## Contributing

In general, bug reports, fixes, and code cleanup are always appreciated. 
Feature requests are liable to be subject to a bit more discussion. 

When filing issues, please include the following:

 * The operating system
 * The JDK version
 * The Leiningen version
 * The Clojure version
 * Any plugins and dependencies in your `project.clj` and your `~/.lein/profiles.clj`

## License

Copyright © 2018 W. David Jarvis

Distributed under the Eclipse Public License, the same as Clojure.
