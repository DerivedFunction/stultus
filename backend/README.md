# Backend Server

The backend has 3 purposes.

1. It serves a copy of the web frontend upon request
2. It responds to http requests with data from the database
3. It adds information from http requests to the database

All tests are on the backend methods, not the json handling, so there are more similar tests that could be written

## Tests

### `PostData`

- Tests for insertion of new post
- Tests for selecting all post or a specific post
- Tests for deletion a current post
- Tests for updating a current post
- Tests for toggling vote on a post

### `UserData`

- Tests for adding a new user
- Tests for selecting a specific user and verifying it
- Tests for updating a user
- Tests for deleting a user

### `Commentdata`

- Tests for adding a new comment
- Tests for selecting a specific comment by post or by user
- Tests for deleting a comment

## Documentation

Javadocs found at [`docs/apidocs/`](./docs/apidocs/)
