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

###commonResult
for the commonResult, responseCode is the thing for frontEnd instead of message<br>
frontEnd can map the commonResult code with commonResult message ,and they can do internationalization by that

### run DemoApplication 
mvn spring-boot:run
### spec http://localhost:8081/swagger-ui.html#/validation-controller

### others
I spend around 9 hours doing this project,2.5 hours in development and 6.5 ours in testing
