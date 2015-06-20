# Yagni
[![Build Status](https://travis-ci.org/venantius/yagni.svg?branch=master)](https://travis-ci.org/venantius/yagni)
[![Dependency Status](https://www.versioneye.com/user/projects/5585e7f0363861001b000215/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5585e7f0363861001b000215)

[Yagni](http://martinfowler.com/bliki/Yagni.html) - You Aren't Gonna Need It.

No matter how it happens, sooner or later an application is going to end up
with dead code. It's time to call Yagni, the exterminator.

Yagni works by identifying all of the interned vars in the namespaces
findable within your `:source-paths`, and then walking the forms of those vars.

As it walks the forms, it builds a graph of references to other vars. It then
searches the graph from a set of entrypoints (by default just your project's
`:main` method), and emits warnings for any vars that it couldn't find in the
graph's search.

## Installation

Merge the following into your `~/.lein/profiles.clj`:

```clojure
{:user {:plugins [[venantius/yagni "0.1.0"]]}}
```

## Usage

To have Yagni search for dead code, just run:

    $ lein yagni

## Configuration

By default, Yagni assumes that the only entrypoint for your project is the one
listed in your project.clj's `:main` key. Obviously, this is only useful for
applications and tools with CLI invocations.

As libraries and certain other types of projects tend not to have `:main`
methods, you can instead define the public API / entrypoints for those
projects by enumerating them in a `.lein-yagni` file in the root directory
of your project.

## Contributing

In general, bug reports, fixes, and code cleanup are always appreciated. 
Feature requests are liable to be subject to a bit more discussion. 

When filing issues, please include the following:

 * The operating system
 * The JDK version
 * The Leiningen version
 * The Clojure version
 * Any plugins and dependencies in your `project.clj` and your `~/.lein/profiles.clj`

## Special Thanks

The following have sponsored work on Yagni:  
![](https://cloud.githubusercontent.com/assets/1824859/6809419/63b4a31a-d217-11e4-9427-11d910410b10.png)

## License

Copyright © 2015 W. David Jarvis

Distributed under the Eclipse Public License, the same as Clojure.
