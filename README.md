springmvc-restful
=================

To run the application...
-------------------------
mvn clean package
mvn jetty:run


To access the RESTful Services...
---------------------------------
* Retrieve all Notes in JSON: curl -XGET -H "Accept: application/json" --user admin:password http://localhost:8080/service/note/notes 
* Retrieve Single notes:curl -XGET -H "Accept: application/json" --user admin:password http://localhost:8080/service/note/{replacethiswithanid}
* Add New Note: curl -XPOST -H "Content-Type: application/json" -H "Accept: application/json" --user admin:password http://localhost:8080/service/note  -d '{"message":"hello there"}'

* Field validation errors from REST service - curl -XPOST -H "Content-Type: application/json" -H "Accept: application/json" --user admin:password http://localhost:8080/service/note  -d '{}'
* Single simple error messages from REST service -  curl -XGET -H "Accept: application/json" --user admin:password http://localhost:8080/service/note/error1
* Multiple error messages from REST service -  curl -XGET -H "Accept: application/json" --user admin:password http://localhost:8080/service/note/error2


To Get XML Responses...
-----------------------
Either replace HTTP Header with application/json with application/xml OR append .xml (or .json) extension to the URI. Example
  curl -XGET -H "Accept: application/xml" --user admin:password http://localhost:8080/service/note/notes
  curl -XGET --user admin:password http://localhost:8080/service/note/notes.xml 
 