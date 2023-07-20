# Stock Market verifier

This project will help us to verify the output of trade for the limit orders given by customers to the actual output. 

# How we can verify the output 

The main class OrderBookVerifierMain.java could be run from the IDE or by executing the command
mvn exec:java -Dexec.mainClass="com.limitorders.verifier.OrderBookVerifierMain"

Please make sure you have right path defined for maven -  export PATH=/opt/apache-maven-3.8.6/bin:$PATH  

Post the application should be running and you can enter your input in the order it should be arriving at the exchange. 
At each submission you can see the traded orders as well as the order book. 

The input should be entered as a string with input as orderid, side(B/S), price, quantity. 


 
