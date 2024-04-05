PORT=$1

curl -X POST \
  http://localhost:"$PORT"/api/check?userId=1 \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName": "firstname1",
    "lastName": "lastname1",
    "middleName": "middlename1",
    "passportSeries": 1,
    "passportNumber": 1,
    "TIN": 1
}'