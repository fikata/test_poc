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
    And I'm going to create 2 books
    Then Going to get books data
    And Going to add more book
    Then Going to get books data
    And Going to remove one book
    Then Going to get books data

  Scenario: Delete user
    Given I'm going to create user
    And Delete this user
    Then Check the user is deleted


