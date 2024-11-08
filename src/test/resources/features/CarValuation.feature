@weBuyAnyCar
Feature: Car Valuation Test
  As a user, I want to validate vehicle details from a car valuation website
  using registration numbers extracted from an input file.

  Scenario Outline: Extract and validate vehicle details for each registration number using <InputFile>
    Given I extract registration numbers from "<InputFile>"
    When I fetch vehicle details from the car valuation site for each registration number
    And I save the extracted vehicle details to "<Output>"
    And I compare "output.txt" with "<ExpOutPut>"
    Then I expect no mismatches in the files

    Examples:
      | InputFile     | ExpOutPut      | Output     |
      | car_input.txt | car_output.txt | output.txt |

    @happyPath @smoke
    Examples:
      | InputFile         | ExpOutPut           | Output     |
      | revised_input.txt | filtered_output.txt | output.txt |