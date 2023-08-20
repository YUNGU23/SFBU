Feature: Negative functionality on search page of Application

  Scenario Outline: Search a product name by category name
    Given Open the Chrome and launch the Amazon application
    When Maximize window size
    And Select a search category as "<categoryName>"
    When Input a product item name as "<productName>" and click Search
    Then Take a screenshot with file name "<productName>"
    Then Click the link Best Sellers
    Then Navigate to ebay.com then go back
    And Close browser and exit

    Examples:
      | categoryName | productName |
      | Books        | Harry Potter|
      | electronics  | laptop      |