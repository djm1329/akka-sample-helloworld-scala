## The Obligatory Hello World

NOTE This project is a modified version of https://github.com/akka/akka-samples/tree/2.5/akka-sample-main-scala

Since every programming paradigm needs to solve the tough problem of printing a well-known greeting to the console we’ll introduce you to the actor-based version.

Open [HelloWorld.scala](src/main/scala/sample/hello/HelloWorld.scala).

The `HelloWorld` actor is the application’s “main” class; when it terminates it will terminate the application. The main business logic happens in the `preStart` method, where a `Greeter` actor is created and instructed to issue that greeting we crave for. When the greeter is done it will respond so by sending back a message, and when that message has been received the `HelloWorld` actor will stop itself. The `postStop` method, which is executed automatically by Akka after an actor is stopped, is coded to shut down the application by calling `system.terminate`. 

## The Greeter

You will be very curious to see how the `Greeter` actor performs the actual task. Open [Greeter.scala](src/main/scala/sample/hello/Greeter.scala).

This is extremely simple now: after its creation this actor will not do anything until someone sends it a message, and if that happens to be an invitation to greet the world then the `Greeter` complies and informs the requester that the deed has been done.

## Main class

Start the application main class: `sbt run`. In the log output you can see the "Hello World!" greeting.

[HelloApp.scala](src/main/scala/sample/hello/HelloApp.scala) is a Scala object that acts as the program entry point (`main`) by extending `App`. The HelloApp body creates the infrastructure needed for running the actors and starts the main actor `HelloWorld`. 


