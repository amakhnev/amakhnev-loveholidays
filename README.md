# Assignment: Flights finder.

## Challenge

The following matrix represents the cost of flying between Castle Black, Winterfell, Riverrun and King's Landing.
```
costs =
[[0, 15, 80, 90],
[0, 0, 40, 50],
[0, 0, 0, 70],
[0, 0, 0, 0]]
```

For example:
* Flying from Castle Black to Winterfell costs 15 Silver Stags ( costs[0][1] )
* Flying from Castle Black to King's Landing costs 90 Silver Stags ( costs[0][3] )
* Flying from Riverrun to King's Landing costs 70 Silver Stags ( costs[2][3] )

You can only fly from north to south, e.g you can fly from Winterfell to King's Landing, but not from King's Landing to Riverrun.

Write an algorithm to find the cost for each possible flight path from one location to another, where the two locations are given
as inputs, and write them to standard output.
The expected end solution should work like this:
```
./bin/list-flight-paths "Castle Black" "Winterfell"
Castle Black -> Winterfell: 15

./bin/list-flight-paths "Castle Black" "Riverrun"
Castle Black -> Winterfell -> Riverrun: 55
Castle Black -> Riverrun: 80
```
Where the command should be:
```
./bin/list-flight-paths "[location1]" "[location2]"
```

We expect that the command outlined in this document is adhered to when writing the submission, as it's how we test the code.

There are a few things we'd expect from a submission:
* A README to understand how to run it - if there are packages to install, etc
* The solution should work with any square, up to 8x8 flight costs matrix
* The solution should be properly tested
* The program should use standard streams and exit codes

A few notes on the test:
* We don’t mind what programming language you use
* We value simple systems that are easy to communicate and understand

## Additional assumptions
- input data needs to be validated, i.e. city name should present in corresponding DAO
- in case of any error only code and meaningful message should be shown to user
- no path found is an error
- price is integer
- routes output is sorted by price


## Solution description

### Architecture and decisions explained
Application consists of follow layers:
- FlightsFinderApp - command line application which receives input data as parameters / writes output to system console and handles received checked exceptions.
- 
- Original problem does not sound complex, hence decision was made to complete without using of dependency injection frameworks, i.e. Spring. However even on this small project it's evident DI framework would benefit in better wiring (between usage of service bean, factory can be simplified) so if I would do it again I prefer to use Spring.

### How to build
Open project in IDE of choice as Java Maven project, execute maven `package` goal.

Alternatively with maven installed,go to project folder and execute `mvn package` command

This would trigger tests and creation of flightsfinder-1.0-SNAPSHOT.jar file in {project home}/target directory

### How to run
####prerequisites:
* JRE installed, version 11+. Can check with running `java --version`
* flightsfinder-1.0-SNAPSHOT.jar has been created (see previous section) 

####execution:
App can be triggered by executing follow command (Windows) 
```
./list-flight-paths.cmd "[location1]" "[location2]"
```

Alternatively, follow command can be triggered to call app explicitly
```
java -cp .\target\flightsfinder-1.0-SNAPSHOT.jar com.amakhnev.loveholidays.flightsfinder.FlightsFinderApp {name of departure city} {name of destination city}
```
####running from IDE
Execute method main in `com.amakhnev.loveholidays.flightsfinder.FlightsFinderApp` class, adding list of cities as input parameters in the running configuration



