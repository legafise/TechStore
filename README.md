# Electronics store "TechStore"
The system is an online electronics store. 
The system has a catalog of goods that can be sorted according to different criteria. 
The user can add goods to the basket and then place an order by pre-replenishing his balance. 
The moderator manages orders by confirming and changing their status. 
Moderators also add goods to the catalog and can change it.
To place an order, the user must be logged in. 
The administrator has the rights of a moderator, and he can also manage user accounts.
### User roles and functions available to them:
<br/>

|Function|	ADMIN| 	MODER| 	USER|  GUEST|
|---------|-------|-------|------|-----|
change language |*|*|*|*|
change user role(admin,moder,user) |*| | | |		
view all users and their information |*|*| | |
find user by login |*|*| | |
bloc, unblock users |*|*| | |			
view orders	|*|*|*| |
view all orders |*|*| | |		
change goods |*|*| | |		
change order status |*|*| | |		
add goods in catalog |*|*| | |	
change account information |*|*|*| |	
change all account information |*| | | |	
create orders |*|*|*| |
top up the balance |*|*|*| |
send email to user |*|*| | |
logout |*|*|*| |
sing in	| | | |*|
create new account | | | |*| <br/>	
# Order lifecycle
1. Order creating.<br/>
   The user can put goods in the basket, then order from the balance. When you create an order, it has the status "The order is being processed".
2. Order confirmation.<br/>
   A moderator or administrator can see a list of orders.
   The moderator contacts the client to confirm the order and then changes its status to "The order is being executed". 
3. Order is completed.<br/>
   After the user has received the order, his status changes to "The order is completed".
   # Database tables
   ![](https://sun9-82.userapi.com/impg/xGI5g2-3eL8Aykdg187j7f1YE_staAsoRcjQCw/7tUrtZUKvcE.jpg?size=638x684&quality=96&sign=7f1b41dd54f2cf24967b123922678840&type=album)