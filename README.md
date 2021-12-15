# Electronics store "TechStore"
The system is an online electronics store. 
The system has a catalog of goods. 
The user can add goods to the basket and then place an order by pre-replenishing his balance. 
The moderator manages orders by confirming and changing their status. 
Moderators also add goods to the catalog and can change it.
To place an order, the user must be logged in. 
The administrator has the rights of a moderator, and he can also manage user accounts.
### User roles and functions available to them:
<br/>

|Function|	ADMIN| 	MODER| 	USER|  GUEST|
|---------|-------|-------|------|-----|
Change language |*|*|*|*|
Change user role(admin,moder,user) |*| | | |		
View all users and their information |*| | | |
Change user roles |*| | | |	
Bloc, unblock users |*| | | |			
Create orders | | |*| |
View orders	| | |*| |
View all orders |*| | | |		
Change goods |*|*| | |		
Change order status |*|*| | |		
Add goods in catalog |*|*| | |	
Change account information |*|*|*| |	
Top up the balance | | |*| |
Logout |*|*|*| |
Sign in	| | | |*|
Leave reviews | | |*| |
Create new account | | | |*| <br/>	
# Order lifecycle
1. Order creating.<br/>
   The user can put goods in the basket, then order from the balance. When you create an order, it has the status "The order is being processed".
2. Order confirmation.<br/>
   A moderator or administrator can see a list of orders.
   The moderator contacts the client to confirm the order and then changes its status to "The order is being executed". 
3. Order is completed.<br/>
   After the user has received the order, his status changes to "The order is completed" and then user can leave reviews about received goods
   # Accounts
   * Administrator's account - email: techstore@gmail.com; password: Admin
   
   * Moderator's account -  email: techstore.suport@gmail.com; password: Moder
   # Database tables
   ![](https://sun9-77.userapi.com/impf/uajMVpZFRgWZPz5lAik3ygysmugs9e222Aqhow/bQ_JRrsSfeM.jpg?size=640x684&quality=96&sign=032cf4558436a694917e52c76f9721ad&type=album)