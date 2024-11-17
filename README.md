MPG Exchange is a web based application that utilizes EPA API to retrieve data regarding fuel economy for Toyota models from 2021 to 2025. MPG Exchange utilizes object oriented concepts, aggregation, CORS, jackson library, springboot, nodejs, react js, JSON data, and rest controllers.

CORS is important to use due the different domains used in which the front end sends requests from a host url to the backend server. CORS allows for safe communication and security for the web browser, CORS also handls requests sent between the front and back end.

Jackson Library is important to use due to the JSON data recieved from the API, the Jackson Library allows us to create objects using the JSON data making it easier to comprehend and translate to string.

REST controller is useful due to the use of API and the ease of use in Spring boot.

Spring boot, nodeJS, reactJS and IntelliJ Idea were all used to create and develop MPG Exchange allowing use to create a web based application.

We ran into issues with the API returning MPG data for models, the EPA API is supposed to return MPG values for combined mpg, highway mpg, and city mpg however we had issues interpreting the JSON data returned. We also ran into issues when both years and the model name were entered in which the program was unable to find matching data to the models.

We hope to add features that allow the user to view the information in a more appealing way such as adding graphs and charts that allow the user to compare the mgp data for different models and years. We also hope to add caching to allow for quicker responses and ease of access to data.
