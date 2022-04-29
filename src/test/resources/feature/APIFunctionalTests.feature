#Auther Shrikant Firodiya
#Date 27-04-2022
Feature: Mobiquity API Functional Tests

  Scenario Outline: verify email format from comment section for perticular user
    Given User completed the initial setup
    And Verify email format from comment section for user "Delphine"

  Scenario Outline: verify GET call endpoint
    Then User completed <url> GET call <statuscode> with responseBody <bodyIsEmpty>

    Examples: 
      | url               | statuscode | bodyIsEmpty |
      | /posts            |        200 | false       |
      | /posts/1          |        200 | false       |
      | /posts/1/comments |        200 | false       |
      | /posts/-1         |        404 | true        |
      | /posts/0          |        404 | true        |

  Scenario Outline: verify POST call tests
    And User completed POST call <url> and <statuscode>

    Examples: 
      | url       | statuscode |
      | /posts    |        201 |
      | /posts/1  |        404 |
      | /posts/-1 |        404 |
      | /posts/0  |        404 |

  Scenario Outline: verify DELETE call tests
    And User completed DELETE call <url> and <statuscode>

    Examples: 
      | url         | statuscode |
      | /posts/1    |        200 |
      | /posts/-11  |        200 |
      | /posts/0    |        200 |
      | /posts/test |        200 |
