# Solution

## Class design
The solution is structured in 2 layers;

#### Service
Business Logic layer to interact with Marvel apis and return data.


#### Controller
Class for exposing api, each method defines the service request path, http method and request params.


#### Service Test
Implemented for testing service layar methods.

#### Controller/API Test
Implemented for testing the API invocation, for example returns response body.
MockMvc is used in order to execute the web services invocation.

## prerequisite
1. Java version: 11
2. Gradle
    
## Build project(Go to project folder location: marvel-api/)
Execute with:
    ./gradlew build
    
## Execute test
Execute with:
    ./gradlew clean test

## Test report
marvel-api⁩ ▸ ⁨build⁩ ▸ ⁨reports⁩ ▸ ⁨tests⁩ ▸ ⁨test⁩▸index.html
        
## Run application
Execute with:
    ./gradlew bootRun
    
## Access endpoint
Access with:
    http://localhost:8080/characters
    http://localhost:8080/characters/1011334    
         
     
