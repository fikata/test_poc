Feature: Api Test
  Scenario: User error password
    Then I'm going to assert error message

  Scenario: User request without header
    Then I'm going to assert error message without header


  Scenario: Valid user
    Given I'm going to create user
    And I'm going to get user information


  Scenario: End to end
    Given I'm going to create user
    And I'm going to get user information
    And Going to add book to the user
    And Going to get books data from user
    Then Going to remove book from user

  Scenario: Delete user
    Given I'm going to create user
    And I'm going to get user information
    And Delete the user


