# Init_Inventory

Initnventory Workflow ( InitDine -----> InitInventory -----> InitDine )

Creating a new shop from HQ

1.Log in to InitDine with username and password. (HQ, username , password)
2.Add Area (Go to Settings -> Setup-> Area ).
3.Go to Settings -> Setup-> Shops.
4.Enter Mandatory data and also must check IsCustomer field(so that the shop can act as a customer while purchase order on InitInventory).
5.Select Business Type from list( Sales - Only sales And Production - Sales + Production )
6.Click on Update Button. And Click on the menu Shops.
7.Click on Shop Code and this opens Edit page of corresponding shop.Click on the Publish button on the bottom.
8.After Publiching, the database for this shop will be generated.
9.Then Go to Utilities -> Utilities -> Publish Data.
10.Select Publish Date , if there is any data on the table , click on the checkbox then click on the ‘Sync DB’ button on top corner.
11.Then Go to Utilities -> Utilities ->shop Target.
12.Select Shop from dropdownlist and Enter 0 for Sales Target , Labour Hour and Labour Cost.Then Click on ‘Update Button’.
13.Need to change the server name in the ‘shop_db_settings’ table on HQ Database.(By Admin)
14.Sign out from initDine.Log in again with shop, select shop name from list and enter credentials.Thus we can log in to the new shop. 


After successful log in we add item Class and items in Sales and Stock item.


Step 0 : 
Add master data - InitDine

1.Log in to InitDine.(if Log out).
2.Add Item Class ( Settings -> Products -> Item Class ).
3.Add Production Department ( Settings -> Setup-> Production Department ).
4.Add Item Category ( Settings -> Setup-> Stock Item Category ).


Add master data - InitInventory

1.Log in to InitInventory.(if Log out).
2.Add Supplier ( Settings -> Setup -> Supplier ).
3.Add Time Slot ( Settings -> Setup-> Time Slot ).
4.Add Production Cost ( Settings -> Setup-> Production Cost ).



Step 1 : Add Item in Sales/Stock(InitDine)

1.Log in to InitDine.(if Log out) 
2.Home -> Settings -> Products -> Stock & Sales Items.
3.Click on Add Button (+) , a popup window will appear.
4.For Adding items for Dish , select IsManufactured. For ingredient , unselect IsManufactured.
5.Select IsSellable if the item is sellable items.
6.Select Stock Management if the item should be managed on stock level.
7.Enter Essential fields in window.
8.Click on submit button to save.


Step 2 : Define Dish in ItemBOM(InitInventory)

1.Log in to InitInventory. 
2.Home -> Settings -> Products -> ItemBOM.
3.Click on Add Button (+) , a window will appear.
4.For Definig Dish, Select Dish from Stock Item Name,Standard Product Qty,add ingredients and its Qty.Also, add Production cost type and its rate.It will automatically calculates sales price and click on save button to save.
5.Saved dish will be listed on ItemBOM starting page.
6.Current stock will zero for the dish previosly added.
7.Since Current Stock is zero, so ,we have to produce the dish using available ingredients in stock. 


If the ingredients is out of stock, then we have purchase them with Step 3.
If the ingredients is available on stock , continue with Step 6.


Step 3: Purchase the Dish from Purchase Order(InitInventory)

1.Home -> Store -> Stock-> Purchase Order.
2.Click on Add Button (+) , a new window will appear.
3.Add Essential Fields like Supplier Name, Payment Mode , Supplier Reference and then select item Name , Unit , Qty , Rate , Tax and click on the save button to save.
4.After successful purchase , Finalize the purchase order to confirm the purchase and stock will be updated automatically.(default stock dept is General Store).


Step 4 : Transferring Stock item from General Store to Departments(InitInventory)

1.Home -> Store -> Stock-> Stock Request.
2.Click on Add Button (+) , a new window will appear.
3.Enter Request From Department and Enter Items to be transferred with unit and qty.
4.After successful transfer, Finalize the Request. Then check the page Stock Summary (Home -> Store -> Stock-> Stock Summary) of General Store.


Step 5 : Transferring Stock item from Departments to Departments(InitInventory)

1.Home -> Store -> Stock-> Stock Out.
2.Click on Add Button (+) , a new window will appear.
3.Enter Request From Source Department to Destination Department and Enter Items to be transferred with unit and qty.Click Save button to save.
4.After successful transfer, Finalize the Request. Then check the page Stock Summary (Home -> Store -> Stock-> Stock Summary) of each Departments.


After Required item reached in stock, we can initialize Dish Production


Step 6 : Initialize Dish Production (InitInventory)

1.Home -> Production-> Production-> Production Order.
2.Click on Add Button (+) , a new window will appear.
3.Select one Type of Order from Shops and Customers. If type of order is Shops, then select shop from list or if type of order is Customers, can select existing customer from previous transanction or add new customer with customer type, code, name, address,contact etc.And Select item and its qty.Click on the save button to save.


After Verified Production Order , send it to the In Production


Step 7 : Sending Verified orders to Production (InitInventory)

1.Home -> Production-> Production-> Production Processing.
2.Select Type of Order within Shops and Customers. After select order from from list it will automatically shows order details.
3.Select the order from the list and click on the sent to production.and Cofirm yes.
4.In the inproduction tab, after the production is completed , select Production date,Delivery Time, Desired department and search the order,enter prod.qty,damage.qty and click on mark finished.
5.Check on Stock Summary to verify the stock value of the Dish produced.
6.Produced Item will be listed on Daily Production( Home -> Production-> Production-> Daily Production ).

After Successful production of Item(Dish) , we can transfer the item to Sales(InitDine)


Step 8 : Sending Produced Item to Sales(InitInventory)

1.Home -> Production-> Production-> Stock Transfer.
2.Select transfer date,select Transfer Type within External and Internal ( External - Company to Department And Internal - Department to Department), and select item to be transferred.Then Click on save button to save.


After completing Stock transfer , open InitDine to verify the stock of that item transferred


Step 9 : Verify the Stock Transferred from InitInventory to InitDine(InitDine)

1.Home -> Settings -> Stock Control-> Incoming Stock Approval.
2.Transferred Item with Request No, Item name, Received Qty and Approved Qty(can change Approved Qty) will be listed.
3.We can select the Item from list and Click on the Approve button to Add the transferred item stock to Sales.

----------------------------------------------------------------------------------------------------------------------------------------------------------------------

