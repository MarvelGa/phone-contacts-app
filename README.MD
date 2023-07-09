   How run the app:
1. Set your properties of MySQL database: username and password. You can create "phone_contacts" database in advance or it will be created automatically.
2. USE the "http://localhost:8080/phone-contacts/ " url. And you will see the greeting message.
3. Register user: POST method:
   http://localhost:8080/phone-contacts/users :
```diff
 {
   "login":"userTest1",
   "email":"usertest1@gmail.com",
   "password":"test19TT"
   }
```
4. Login user: POST method:
   http://localhost:8080/phone-contacts/auth/login
```diff
  {
    "login":"userTest1",
    "password":"test19TT"
}
```
5. ADD contact: POST method:
 http://localhost:8080/phone-contacts/contacts
```diff
{

    "name": "xxx",

    "emails": ["xxx@xxx.com","yyy@yyy.com"],

    "phones": ["+380939333333","+380939333334","+380939333335"]

}
```
6. EDIT contact: PUT method:
   http://localhost:8080/phone-contacts/contacts/c3e51107-a93d-4508-b337-659cb00ee49c
```diff
{
  "name": "John111 Doe",
  "emails": [
    "john.doe11111@example.com",
    "johndoe111@gmail.com"
  ],
  "phones": [
    "+1-123-45617-7890",
    "+1-987-654-3210"
  ]
}
```
7. DELETE a Contact: DELETE Method:
   http://localhost:8080/phone-contacts/contacts/75b2a5aa-d2a6-4292-8a3f-430fd6debcd6
8. GET All User's Contacts: GET Method:
   http://localhost:8080/phone-contacts/contacts