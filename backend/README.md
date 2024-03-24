# Backend Server 

The backend has 3 purposes.
1. It serves a copy of the web frontend upon request
2. It responds to http requests with data from the database
3. It adds information from http requests to the database

All tests are on the backend methods, not the json handling, so there are more similar tests that could be written

Tests
* Test for insert and selectall database method
* Test for delete (relies on insert)
* Test for update 
* Test for toggling likes
* Test that single row select and multi row select return the same rows

All tests test at least one random subject message pair

May wish to add edge case testing (such as overlength messages or empty messages)

Javadocs found at docs/apidocs/index.html for main documentation, with other documentation in the docs/apidocs folder