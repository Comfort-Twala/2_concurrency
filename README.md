# CSC2002S - Assignment 2
# Concurrent Programming: Falling Words
## Goal
### design a multithreaded Java program, ensuring both thread safety and sufficient concurrency for it to work well.

## Part 1: Program
### Build
```unix
$ make
```
### clean
```
$ make clean
```
### run Program
Default run which runs the game with default dictionary with a total of 10 words, and 5 on screen
```
$ make play
```
Run with custom text file which should be stored in dictionary folder.
Specify total words, words on screen and the custom dictionary file.
```
$ make run tot_words=<total words> on_screen=<words on screen> dict=<dictionary file>
```

## Documentation
### generate Javadocs
```
$ make docs
```
### clean Javadocs
```
$ make clean-doc
```