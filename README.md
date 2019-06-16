# Books
Books Library Rest API Testing (Java Automation)

Used Rest Assured and TestNG framework to write the test class. All tests are in the class : BookOperationsTest.java 

What are the challenges faced while hitting the end point?
- Specification for all the REST operations are not defined.
- Unable to identify the request payload for POST operation.
- Exploratory tests were performed to identify the request payload for PUT operations


What are the assumptions made?
- No authorization required
- Specification of the API
- Assumed that the attributes and its values are same in items : Name, ispublic,createddate
- Assertion w.r.t Get operation cannot be validated against the expected value. Alternatively, we can update an attribute and fetch the value to validate the same.
- Inputs to these tests can be parameterised using @DataProvider. I have not considered in this, as it is to perform single test.
- Added Priority to handle the delete operation as the last test method.

Enhancements :
- Property file can be created for ease of access and modification of attributes like endpoint/host etc.(This is not added here)
- Cleanup of created book (POST operation) by using the Delete operation in the tear down.

