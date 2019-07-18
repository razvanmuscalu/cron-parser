# cron-parser

Command line application which parses a cron string and expands each field to show the times at which it will run.



# package and run

You can run `mvn clean package` to run tests and create a runnable JAR which will be created under `target/docker-src` folder.

You can then run `java -jar <JAR file> arg1 arg2 arg3 arg4 arg5 arg6` where

- `arg1` is the minute element
- `arg2` is the hour element
- `arg3` is the day of month element
- `arg4` is the month element
- `arg5` is the day of week element
- `arg6` is the command element

I included a JAR called `cron-parser.jar` already under the root directory if you want to use that.



# Overview

The exercise is coded in Scala.

I opted for a domain oriented design as opposed to a more classical design which includes a `Service` class.
This is mainly because the exercise is about transforming from a model in the shape of A to a model in the shape of B.
It does not involve calling third-party services and does not require a store (e.g. database) which would call for the use of `Service` classes.

`ArgsHelper` collects the input into a `CronCommand` case class. And, after applying the business logic, a resulting `CronOutcome` case class is constructed which is then formatted in the expected output.

All the business logic is inside the `CronElement` file which consists of case objects (e.g. `Minute`, `Hour`, etc.) and an object method `fromString(value: String, element: CronElement)` which is called once for each element.

The main way of communicating back throughout the project is `Either[String, T]` where `String` holds various error messages thrown mainly from the business logic engine.



# Project Timeline


## Phase 1 (reaching desired project structure) (approx 1h - 1h 15m)

- I started with a very classical service-oriented architecture
	- Parse input into a case class
	- Service receives an input case class, performs logic, and returns an output case class
	- Format the output case class and print it

- This was quite verbose as introducing a new cron element (e.g. second) meant touching too many files

- The exercise is solely about transforming from model A to model B => so a service layer is not necessary
	- Everything can be achieved with a type/domain driven design

- So I removed the service layer and made the input and output data/domain classes perform the logic straight inside


## Phase 2 (initial coding of a few use cases for minute and hour) (approx 30-45m)

- Changed a bit the return values (using `Either[String, T]`) throughout the project to handle errors in the same place where I am handling the actual logic
    - Because the two (error handling and business logic) are very closely linked
- Wanted to code quickly a few different use cases to get a feel of the complexity involved for the rest of the use cases


### Phase 2.1 (refactoring) (approx 30-45m)

- With lots of tests in place and a few good use cases, I decided to refactor quite massively to avoid code duplication
- I also added two extra use cases (step value and month cron element) to gain confidence the refactor can support new functionality (both horizontally and vertically)
- I performed few other smaller refactorings to break the main `fromString` function into smaller functions for better code readability


### Phase 2.2 (handling more edge cases) (approx 30m)

- Here I mainly handled edge cases around bad input (e.g. letters) 
- This is the point in the project timeline where I felt that the project transitioned from exploratory into BAU mode


This is roughly the point in the project timeline where I put approx 3-4 hours into the project and I considered “showcasing” the exercise as complete.
The rest I did as pure fun because I really enjoyed the exercise.


## Phase 3 (readme) (approx 5-10m)