Feature: DemoBlaze Web Tests

Scenario: Login into an Application
Given User is on Launch Page
When User navigates to the login Page enters "ShriAthi" and "Shri.01"
Then It should display Home Page

Scenario Outline: Add items to cart
Given User is on Launch Page
When User add an "<items>" to the cart
Then the items added to the cart
Examples:
	| items |
	| Nexus 6 |
	| HTC One M9 |
	| Sony vaio i7 |
	| Samsung galaxy s7 |
	
Scenario: Delete an item
Given User is on Cart Page
When User deletes an item from the cart page
Then the item must be deleted from the cart page

Scenario: Place an order
Given User is on Cart Page
When User places an order
Then the item must be placed