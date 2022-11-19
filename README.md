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

