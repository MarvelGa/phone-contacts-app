   How run the app:
1. Set your properties of MySQL database: username and password. You can create "phone_contacts" database in advance or it will be created automatically.
2. USE the "http://localhost:8080/phone-contacts/ " url. And you will see the greeting message.
3. Register user: POST method:
   http://localhost:8080/phone-contacts/users :
   {
   "userName":"userTest",
   "email":"usertest@gmail.com",
   "password":"test19TT"
   }
4. Login user: POST method:
   {
   "userName":"userTest",
   "password":"test19TT"
   }
5. 