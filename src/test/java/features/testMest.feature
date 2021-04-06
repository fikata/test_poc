Feature: Some Test

  Scenario: Fill most fields submit form and assert

    Given I'm going to run Browser
    And I'm landing directly to the form
    And I'm fill all fields with valid data firstname etc
    Then I'm asserting all fields
    And Close Browser




