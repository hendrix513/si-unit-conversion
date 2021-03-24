# si-unit-conversion
microservice for converting unit names to their SI equivalents

Code implements a version of Dijkstra's two-stack algorithm for parsing input

to run:
- docker-compose up --build

to use:
curl localhost:8090/units/si?units=[input_unit_string]

*A valid input_unit_string is a string containing any number of units multiplied or divided, potentially containing parentheses. Valid examples include degree , degree/minute , (degree/(minute*hectare)) , ha*° 

Given more time I would like to
- improve the application's error handling. Currently illegal input will trigger a 500 error rather than
 any meaningful explanation of what was wrong
- add regression tests
