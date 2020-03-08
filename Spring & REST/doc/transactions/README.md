## Transactions
Transaction - Unit of work (set of db instructions) that must be:
* atomic (it must either complete in its entirety or have no effect whatsoever);
* consistent (it must conform to existing constraints in the database / form valid state to valid state);
* isolated (it must not affect other transactions);
* durable (it must get written to persistent storage).

## Isolation levels


             


|Isolation level\Read phenomena|Lost updates|Dirty reads|Non-repeatable reads|Phantoms|
|:---:|:---:|:---:|:---:|:---:
Read Uncommitted| 	don't occur |	may occur 	|   may occur 	|   may occur
Read Committed| 	don't occur |	don't occur |	may occur 	|   may occur
Repeatable Read| 	don't occur |	don't occur |	don't occur |	may occur
Serializable| 	    don't occur |   don't occur |	don't occur |	don't occur 

