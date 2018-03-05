# energyconsumption-automation
Automation of REST services using RestAssured and constructing a test report via Allure

*** ABOUT THE PROJECT ***
This project represents an exercise project for automating REST services using RestAssured. Testing framework is TestNG. Reports are created using Allure ((http://allure.qatools.ru/)

*** WORK IN PROGRESS ***

Due to time limitations, this project still needs work. I will name some of the improvements that need to be implemented, along with explanations of parts of the code that is written : 

1. In each test, you will see BeforeMethod() and AfterMethod(), this is done mainly because of "imagined" data preparation for each test. The purpose is that each test has the starting condition before running
2. Need to find more elegant solution for loading test data, originally I wanted to introduce a Helper class and create a method for this, but couldn't because of time limitations. (needs more researching - Helper class doesn't sound too elegant either)
3. All constants and environment properties are placed in Environment class. This needs to be separated. All environment properties should be in a different file (called environment.properties), Environment class should be renamed to "Constants" and properties like status codes and MONTH_XXX should remain there
4. CONSUMPTION functionality was LEFT OUT - it highly depends on correct implementation of creating meter readings, which currently has a bug and needs fixing (faulty implementation of meter readings formula)
5. ADD COMMENTS AND JAVADOCs!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 
