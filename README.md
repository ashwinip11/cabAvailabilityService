Cab Availability Service

Run the Main program and from post man hit the below service to save the customer details to DB.

curl --location --request POST 'http://localhost:8081/users/api/vi/users/locations' \
--header 'Content-Type: application/json' \
--data-raw '{
    "customerId" : "cc111",
    "geoLocation": {    
        "geoId": "g111",
        "latitude": 8,
        "longitude": 8
}
}'



Execute the below command to get the top n closest cabs to the customer

curl --location --request GET 'http://localhost:8081/api/v1/cabs?location=cc123&cabs=1'

