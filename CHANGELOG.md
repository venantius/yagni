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
