Feature: Some Test

  Scenario: For now login form

    Given I'm going to run Browser
    And I'm going to Admin page
    And I'm login to the admin panel with user = admin@phptravels.com and password = demoadmin
    And Going to open Hotels section and edit first room from section Rooms
    And I'm going to change the price = 445.00 and get values for later use
    And Go to the web page
    And I'm navigating to the login form
    And I'm login into web page with user = user@phptravels.com and password = demouser
    And Search by hotel name and select it
    Then Verify room price during booking will be equal with changed one = 445.00
    And Go to book page
    And Verify final price and booked the order
    And Close Browser




