# Stock Market verifier

This project will help us to verify the output of trade for the limit orders given by customers to the actual output. 

# How we can verify the output 

The main class OrderBookVerifierMain.java could be run from the IDE or by executing the command

```mvn exec:java -Dexec.mainClass="com.limitorders.verifier.OrderBookVerifierMain"```

Please make sure you have right path defined for maven.
```export PATH=/opt/apache-maven-3.8.6/bin:$PATH```

Post the application should be running and you can enter your input in the order it should be arriving at the exchange. 
At each submission you can see the traded orders as well as the order book. 

The input should be entered as a string with input as orderid, side(B/S), price, quantity. 

# Assumptions/clarifications 

1. After each input we are processing the current order for the resting orders in out tradebook.
2. We are printing the tradebook as well as the tradedorders if any at the time of submission of our orders.
3. The same code can be modified to print both the tradebook as well as the traded orders just in the end if needed.
4. I am taking the price as double in the input which should be the case practically, but I don't see the expected output
   handling the same as double hence, I am changign that to int while printing the output as it is not specified how to
   handle double in the output format.
5. The traded price is always the sell price.




 
