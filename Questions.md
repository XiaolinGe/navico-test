### General Q1:
You have been asked to debug a random crash in a very large program with millions of lines of code. Initial analysis indicates that the crash occurs on a different line of code each time.
How would you debug this problem? Describe any tools that you may have used to aid with this type of investigation in the past.

### A:

I usually have a look at the stacktrace to find exactly where the exception was thrown and the root cause in an application.


### General Q2:
What is the most inventive or innovative thing you’ve done (in a SW development context)? ​
Pick one​ and describe what problem were you trying to solve and the solution you came up with?

### A:
The most innovative thing I've done is building the Collison FX platform. It's an online foreign exchange platform that enables customers to transfer money to bank accounts around the world. 
We built this system to allow customers and staff to do everything online including check rates, create a deal, and settle the payment in one click. 
We also provide a real-time rate that updates more than 20 currency pairs rates per second. It's my first time to participate in refactoring monolith to microservice architect incrementally. 
Using docker containers, CI/CD. load testing, Redis for caching. also, I've learned a new language which is Kotlin, and then became a big fan of it. 
There was a lot of learning and pressure, as business was growing very fast, the business department pushed us very hard. 
But we managed to introduce new and innovative things to solve problems for business in a limited time, that's why I think this is the most innovative thing I've done.

The most challenging part of this project that I was trying to solve is real-time API. This API needs to be called on the home page every second to reflect the real-time rate. 
It worked well for a while, but as the business was growing, our server couldn't handle the traffic and affected the whole application, which caused downtime sometime. 

For solving this problem, We moved the project to microservices, the rate API was moved out from the monolith, and then it was scaled to multiple instances which made our system much better, 
and we also introduced Redis to store base rates and rate-related data instead of storing them in MySQL, which also improved the performance. We also used gatling to help the load testing.

Specifically, we developed two services. First, we have a rate-reader service which uses a java ssh library to talk to our rate provider, 
which is a ssh server, producing 20 pairs of currencies' rates per second, our service write the rates to a file called base_rates.
Then, we have a rate-writer which uses a java tailer library to listen on the base_rates file, if any new lines added to the file, this service can parse the line to a base rate object and write it into Reids.
Finally, our rate-API read the base rate from Redis, do some logic to add a margin.

This solution is not only solves the problem but provides an excellent user experience as well.


### Kotlin Programming​ ​Exercise Q1:
Which design patterns have you used in the past? Pick ​one​ and provide a ​simple​ example of how this is used.

### A:

I have used strategy pattern, and a simple example below:

```kotlin
class Product(val name: String, val price: Double, val discount: (Double) -> Double) {
    fun finalPrice() = discount(price)
}

val noDiscount = { fee: Double -> fee }
val discount = { fee: Double -> fee/2 }
val product = Product("Pear", 10.0, noDiscount)
val productWithDiscount = Product("Apple", 10.0, discount)
```

In my opinion, using design patterns like the strategy pattern is to avoid duplicate code. 

But kotlin, a functional programming language, is more expressive that allows us to use higher order functions to solve code duplication problem.

There is a singleton pattern `object Validator` used in this task test, which can be found in Validator.kt file:

```kotlin
object Validator {}
```

### Kotlin Programming​ ​Exercise Q3:
How would you test your classes? Write some pseudocode for the unit tests you might write.

### A:
I would like to write positive and negative test cases. Please find these test cases in the `ViewTest` file in test folder.
