Feature: DemoBlazeWebTest

Scenario: Valid login
Given User is on the login Page
When User enters "ShriAthi" and "Shri.01" in the login page
Then It navigates to the Home Page

Scenario Outline: Adding products to cart
When User add "<products>" to the cart
Then products must be added to cart
Examples:
	| products |
	|	Nokia lumia 1520 |
	| HTC One M9 |
	| Sony vaio i5 |
	
Scenario: Delete an item in the cart page
When List of added items must be available in the cart page
Then Delete an item in the cart

Scenario: Place an order
When User snaps the place order
Then Products should be placed