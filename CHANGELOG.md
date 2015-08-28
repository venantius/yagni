## 0.1.4
 * Failed to bump the version injection in 0.1.3, so this is actually what 0.1.3 should have been.

## 0.1.3
 * Fixed a ClassNotFound exception when Yagni encountered a function with a name like a class constructor (e.g. `->Something`)
 * Removed the log4j dependency entirely.
 * Cleaned up the main `run-yagni` function to expose a cleaner, more functional interface.

## 0.1.2
 * Removed logging configuration so as to not interfere with higher level
 logging config.
 * Added nonzero exit codes when parents or exits are found.
 * Better JVM interop - specifically, superior handling for `defprotocol`,
 `deftype` and `defrecord` forms.

## 0.1.1
 * Fixed a dependency bug that caused Yagni to point at a snapshot rather 
 than the current release.

## 0.1.0
 * Initial release with form-walking, graph search.
