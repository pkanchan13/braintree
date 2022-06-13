@add_customer
Feature: Add Customer

  Scenario Outline: Add Customer Success Flow

    Given an add customer request
    And  set <first_name> as first name
    And  set <last_name> as last name
    And  set <phone> as phone
    And  set <email> as email
    When instrument onboard api is called
    Then a non-empty customer_id should be generated
    And "<message>" returned as  response message must match
    And "<code>" returned  as response code must match
    And "<first_name>" should be returned as 'first_name'
    And "<last_name>" should be returned as 'last_name'
    And "<phone>" should be returned as 'phone'
    And "<email>" should be returned as 'email'



    Examples:
      | first_name  | last_name | phone      | email           | message | code
      | Arjun       | Singh     | 7836234567 |ar@jun@gmail.com | OK       | 200
