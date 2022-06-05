# InnovaDemo

##introductions
implement a password validation service

##functions
####validate passwords with rules<br>
1.Must consist of a mixture of lowercase letters and numerical digits only, with at least one
of each.<br>
2.Must be between 5 and 12 characters in length.<br>
3.Must not contain any sequence of characters immediately followed by the same sequence.

for the rule 3<br>
"aa1bb"   "a" equals "a" but  the length is 1,<br>
base on the conversion with manager, I define it not a sequence of characters<br>
so in this case it will pass and "abab" it will be against the rule

####operation the validation rules runtime<br>

1.change which validation logic in runtime<br>
2.the issue is if there are multiple pods we need to use redis channel to reach to every pods(send to the service and it publishes and other pods subscribe that channel and operate<br>
3.there is still one issue which is when the service restart all the operation will be lost <br>
4.we can store a validationService list in redis and we modify that list in redis, every time we run validate method we can check the verificationList in redis and decide if we need to operate that

###response
for the response, responseCode is the thing for frontEnd instead of message<br>
frontEnd can map the response code with response message ,and they can do internationalization by that

### run DemoApplication 
mvn spring-boot:run
### spec http://localhost:8081/swagger-ui.html#/validation-controller

### others
I spend around 9 hours doing this project,2.5 hours in development and 6.5 ours in testing
