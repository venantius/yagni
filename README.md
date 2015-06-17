# Yagni

[Yagni](http://martinfowler.com/bliki/Yagni.html) - You Aren't Gonna Need It.

No matter how it happens, sooner or later an application is going to end up
with dead code. It's time to call Yagni, the exterminator.

Yagni works by identifying all of the interned vars in the namespaces
findable within your `:source-paths`, and then walking all of the interned
forms within those namespaces to count references to those vars.

It emits warnings for any vars for which it hasn't found any outside references.

## Installation

Merge the following into your `~/.lein/profiles.clj`:

```clojure
{:user {:plugins [[venantius/yagni "0.1.0"]]}}
```

## Usage

To have Yagni search for unused code, just run:

    $ lein yagni

## Contributing

In general, bug reports, fixes, and code cleanup are always appreciated. Feature requests are liable to be subject to a bit more discussion. 

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
