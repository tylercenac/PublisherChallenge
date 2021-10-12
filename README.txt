In order to run my application:

    1. Navigate to the main directory (containing the docker-compose.yml file)
    2. Execute command "docker-compose up --build"
    3. Import the API Requests in Postman from InterviewChallengeAPIRequests.postman_collection.json
    4. Populate the database using the 'Import Publishers' request
    5. Update the database with the most recent files using the 'Update Publishers' request
    6. Print the updated database to output.json using the 'Print Database' request
    7. To view output.json, open the cli inside of the 'publishers-service' and execute the command 'cat output.json'

REQUESTS:

    1. Status Check - Verifies the application is running
    2. Import Publishers - Import publishers from databasePublishers.json to MySQL database
    3. Get All Publishers - Return a list of all publishers found in the database
    4. Get Publisher By ID - Return a publisher identified by provided code
    5. Update Publishers - Find newest file for each publisher in publishersFileList.txt and updates the publishers in the database
    6. Print Database -  Saves database in JSON format to /tmp/output.json in publishers-service container

FURTHER QUESTIONS:

    1. I tested my program using JUnit and Postman. I created several different JUnit test cases to verify the logic of my application, and I used Postman to verify the input and output of my API requests.

    2. The biggest issue I came across was the format of the databasePublishers.json. I chose to use MySQL as the job description states the need for experience in SQL. However, this input would work easier with a NoSQL database. After a little trial and error, I was able to convert the JSON back and forth to an object of type PublisherEntity, allowing me to easily interact with the MySQL database.
       Another little issue I ran into was trying to store multiple properties in the values of a hashMap. My solution was to create an object that would store the properties I needed in the hashMap, and then I stored that object as the value.

    3. One assumption I made is that the input from publishersFileList.txt will not include any publishers that are not included in databasePublishers.json.
       It would be very simple to add the ability to save new publishers to the database, but that feature is not currently in my code.

    4. If there were millions of rows in the database, I would change the input to be handled in real-time individually for each publisher.
       For example, when a file is updated, a call could be made to the API with the Publisher Code and File Name, which updates the file for the appropriate publisher in the database.
       A more advanced solution could be to use Kafka Streams to improve the speed and accessibility of the file updates.
       Further, auto-scaling technology like Kafka, Docker, and Kubernetes allows us to handle requests promptly and ensure the application remains online