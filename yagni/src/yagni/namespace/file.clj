(ns yagni.namespace.file
  "Utility functions for working with namespace files.")

(defn canonical-filename
  "Returns the canonical file name for the given file name.  A
canonical file name is platform dependent, but is both absolute and
unique.  See the Java docs for getCanonicalPath for some more details,
and the examples below.
    http://docs.oracle.com/javase/7/docs/api/java/io/File.html#getCanonicalPath%28%29
Examples:
Context: A Linux or Mac OS X system, where the current working
directory is /Users/jafinger/clj/dolly
user=> (ns/canonical-filename \"README.md\")
\"/Users/jafinger/clj/dolly/README.md\"
user=> (ns/canonical-filename \"../../Documents/\")
\"/Users/jafinger/Documents\"
user=> (ns/canonical-filename \"../.././clj/../Documents/././\")
\"/Users/jafinger/Documents\"
Context: A Windows 7 system, where the current working directory is
C:\\Users\\jafinger\\clj\\dolly
user=> (ns/canonical-filename \"README.md\")
\"C:\\Users\\jafinger\\clj\\dolly\\README.md\"
user=> (ns/canonical-filename \"..\\..\\Documents\\\")
\"C:\\Users\\jafinger\\Documents\"
user=> (ns/canonical-filename \"..\\..\\.\\clj\\..\\Documents\\.\\.\\\")
\"C:\\Users\\jafinger\\Documents\"

Copied from `eastwood.lint`"
  [fname]
  (let [^java.io.File f (if (instance? java.io.File fname)
                          fname
                          (java.io.File. ^String fname))]
    (.getCanonicalPath f)))
