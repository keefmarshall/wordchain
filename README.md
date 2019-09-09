Word Chain
==========

Internal development challenge: build a solution to calculate the shortest chain between two words, changing only 
one letter at a time, with every element in the chain a valid word from the supplied dictionary.

To build:
```
mvn package
```

By default, this will run against an input file called `wordpairs.txt` in the current directory. Or you
can specify the filename on the command line.

```
java -jar target/wordchain-1.0-SNAPSHOT-jar-with-dependencies.jar
java -jar target/wordchain-1.0-SNAPSHOT-jar-with-dependencies.jar <filename>
```

Additionally, it can run tests on an output file to check that it meets some validity requirements

```
java -jar target/wordchain-1.0-SNAPSHOT-jar-with-dependencies.jar -test <testfile>
```

