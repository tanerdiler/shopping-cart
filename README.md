**Create Artifact**

The command below compile and build executable artifact shoppingcart-executable.jar

`mvn clean package`


**Execute Artifact***

`java -jar target/shoppingcart-jar-with-dependencies.jar`

Category ---> Car

| Product Name  | Quantity      | Unit Price  | Total Price | Total Discount |
| ------------- |:-------------:| -----------:| -----------:| --------------:|
|Ferrari |2     |1000.00        |1440.00      |560.00|
|Tofaş   |4     |50.00          |144.00       |56.00|


 Category ---> Phone
 
| Product Name  | Quantity      | Unit Price  | Total Price | Total Discount |
| ------------- |:-------------:| -----------:| -----------:| --------------:|
| Apple |1  |210.00 |170.10|39.90|
| LG    |3  |110.00 |267.30|62.70|

|  ||
| ------------- |:-------------:|
| Total Amount Before Discount | 2740.00 |
| Total Amount  |2021.40|
| Delivery Cost | 4.99|
