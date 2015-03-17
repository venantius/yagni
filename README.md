# Yagni

No matter how it happens, sooner or later an application is going to end up
with dead code. It's time to call Yagni, the exterminator.

Yagni works by identifying all interned functions in the namespaces findable
within your `:source-paths`, and then walking all interned forms in those
namespaces to find references to those functions. If it can't find a reference
for a function, it emits a warning.

## Installation

Put `[yagni "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your
`:user` profile.

Merge the following into your `~/.lein/profiles.clj`:

```clojure
{:user {:plugins [[venantius/yagni "0.1.0-SNAPSHOT"]]}}
```

## Usage

To run Yagni, just:

    $ lein yagni

## License

Copyright © 2015 W. David Jarvis

Distributed under the Eclipse Public License, the same as Clojure.
