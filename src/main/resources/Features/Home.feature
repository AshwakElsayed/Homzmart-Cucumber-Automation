Feature: items in cart feature
  @happy
  Scenario: Check that user can enter product to cart with correct details
    Given User Hover on Storage
    When Confirm that the dropdown menu appears and including Kitchen Storage subcategory.
    And Click on Kitchen Storage
    And Choose two random products and add them to the cart
    And Open the cart preview
    Then Wait until making sure that all elements are added to the cart
    Then Compare every product’s name and price in the cart with product details
    Then Check that the total price in the cart is calculated correctly


