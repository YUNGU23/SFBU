Feature: Amazon Product Purchase

  Scenario Outline: Purchase a Product
    Given I open the Amazon website
    When I search for "<product>"
    And I select the product "<product>"
    And I compare the prices
    And I add the product to cart
    Then I should see the product added to cart

    Examples:
      | product      |
      | harry potter |
