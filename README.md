# Yagni

No matter how it happens, sooner or later an application is going to end up
with dead code. It's time to call Yagni, the exterminator.

Yagni works by identifying all interned functions in the namespaces findable
within your `:source-paths`, and then walking all interned forms in those
namespaces to find references to those functions. If it can't find a reference
for a function, it emits a warning.

## Installation

Merge the following into your `~/.lein/profiles.clj`:

```clojure
{:user {:plugins [[venantius/yagni "0.1.0-SNAPSHOT"]]}}
```

## Usage

To run Yagni, just:

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
![](https://cloud.githubusercontent.com/assets/1824859/6697788/3a6cf4b0-ccb0-11e4-9d78-7626a125ef9f.png)

## License

Copyright © 2015 W. David Jarvis

Distributed under the Eclipse Public License, the same as Clojure.
