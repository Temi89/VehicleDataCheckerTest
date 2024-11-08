# Car Valuation Validation Project

This project validates vehicle details from a car valuation website based on registration numbers from an input file. It
compares the fetched vehicle details with expected output to ensure data accuracy.

## Table of Contents

1. [Project Overview](#project-overview)
2. [Prerequisites](#prerequisites)
3. [Project Setup](#project-setup)
4. [Usage Instructions](#usage-instructions)
5. [Understanding the Validation Process](#understanding-the-validation-process)
6. [Running the Tests](#running-the-tests)
7. [File Structure](#file-structure)

---

### Project Overview

This project reads vehicle registration numbers from an input file, retrieves details for each vehicle from a valuation
website, and saves these details to an output file. It then compares this output to an expected file to identify any
mismatches.

### Prerequisites

Before running the tests, ensure you have the following installed:

- Java 17 or higher
- Maven
- Git
- Selenium WebDriver
- Firefox browser (default for running tests)
- Node.js (for generating HTML reports)
- Cucumber HTML Reporter (installed via npm)

### Project Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/Temi89/VehicleDataCheckerTest.git
   cd VehicleDataCheckerTest
   ```

2. **Install dependencies** via Maven:
   Please note everything needed to execute the project already exists in the Pom file (node etc)
   ```bash
   mvn clean install
   ```

3. **Set up input and expected output files** in `src/test/resources/`:
    - `car_input.txt`: File containing registration numbers for validation.
    - `car_output.txt`: File containing expected vehicle details for comparison.
    - `output.txt`: Auto-generated file that stores fetched vehicle details.

### Usage Instructions

1. **Add Vehicle Registration Numbers**  
   Update `car_input.txt` or simply create a new file in the same location with registration numbers to validate. Each
   line should contain one registration number.

2. **Update Expected Output**  
   Modify `car_output.txt` with the expected vehicle details corresponding to the registration numbers. The format
   should match the structure used by the program.

3. **Run the Validation Tests**  
   Execute the tests using Maven or your preferred IDE to validate the data.

### Understanding the Validation Process

The validation process consists of the following key steps:

1. **Extract Registration Numbers**: Read vehicle registration numbers from `car_input.txt`.
2. **Fetch Details**: Retrieve vehicle details for each registration from the car valuation website.
3. **Save Details**: Append the retrieved details to `output.txt`.
4. **Compare Files**: Compare `output.txt` with `car_output.txt` to identify mismatches.

### Running the Tests and Generating Html reports
Please note cucumber reports will open automatically
1. **Run with Maven**:
   ```bash
   mvn test verify
   ```

2. **Run Specific Scenarios**  
   Scenarios can be run based on tags. For example:
   ```bash
   mvn test -Dcucumber.options="--tags @weBuyAnyCar" or alternatively you can modify the TestRunner with teh appropriate  tag
   ```

3. **View Results**  
   Test results will indicate if any mismatches are found between `output.txt` and `car_output.txt`, with details of the
   discrepancies printed in the console.

### File Structure

- **src/test/resources**:
    - `car_input.txt`: Input file with registration numbers.
    - `car_output.txt`: Expected vehicle details.
    - `output.txt`: Generated file containing fetched vehicle details for comparison.
- **src/test/java/step_definitions**:
    - `CarValuationSteps.java`: Step definition file with methods for each Gherkin step.
- **src/test/java/Utility**:
    - `FileUtil.java`: Utility class for reading, writing, and comparing files.

### Gherkin Feature File

The feature file `CarValuation.feature` defines the scenarios to test vehicle details extraction and validation. It
specifies:

- `@weBuyAnyCar` scenario: Validates car details using registration numbers from a specified file and compares them with
  expected output.

---

By following the steps outlined above, users can validate vehicle details from a car valuation website efficiently and
identify discrepancies. This README should help onboard new contributors quickly and ensure consistency in the testing
process.
