Feature: Product Store Page Test

Scenario: Login into App
Given User into the login page
When User enters login credientials
Then Should Display Home Page

Scenario Outline: Add Items To Cart
When Add an Item "<catagory>" to "<item>" Cart
Then Items must be added to cart
Examples: 
	|catagory|item|
	|Laptops|MacBook air|
	|Phones|Samsung galaxy s6|
	
Scenario: Delete an Item
When List of items should be available in Cart
Then Delete an item from cart

Scenario: Place Order
When Items Should be available in Cart
Then Place order
And Purchase Items