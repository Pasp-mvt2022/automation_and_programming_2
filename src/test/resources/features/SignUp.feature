Feature: Sign up to Mail Chimp

  Scenario Outline:
    Given I opened Mailchimp signup in "<browser>"
    And I have entered an emailAddress "<emailAddress>"
    And I have entered a "<random>" username that is "<tooLong>"
    And I have entered an "<password>" password
    When I click signup
    Then the account creation is "<accepted>"

    Examples:
      | browser | emailAddress | random | tooLong | password | accepted |
      | chrome | true | random | false | Test1234! | true |
      | chrome | true | random | true | Test1234! | false |
      | chrome | true | fixed | false | Test1234! | false |
      | chrome | false | random | false | Test1234! | false |
      | edge | true | random | false | Test1234! | true |
      | edge | true | notRandom | true | Test1234! | false |
      | edge | true | fixed | false | Test1234! | false |
      | edge | false | random | false | Test1234! | false |