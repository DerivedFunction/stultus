# Phase 2

[Click here for Google Doc](https://docs.google.com/document/d1tA9TysKklLKxRtsTPn_6faa6bT1jq_oNIiBSduttw50/edit)

## Entity Relationship Diagram

![Entity Relationship Diagram](img-assets/phase3/ERD-phase2.png)

## UML Diagram

![UML Diagram](img-assets/phase3/UML-phase2.png)

## Admin FSM

![Admin FSM Diagram](img-assets/phase3/AdminFSM-phase3.png)


## Web FSM

![Web FSM Diagram](img-assets/phase3/WebFSM_phase3.png)

## Idea FSm

![Idea FSM diagram](img-assets/phase3/IdeasFSM-phase2.png)

## Mock User Interface

![Login Screen](img-assets/phase3/webUI/login.jpg)
![Main Page](img-assets/phase3/webUI/mainPage.jpg)
![View my profile](img-assets/phase3/webUI/myProfile.jpg)
![View user profile](img-assets/phase3/webUI/userProfile.jpg)
![Create a post](img-assets/phase3/webUI/newPost.jpg)
![Comment a post](img-assets/phase3/webUI/commentPost.jpg)
![Edit a comment](img-assets/phase3/webUI/editComment.jpg)
![Display an image](img-assets/phase3/webUI/displayImage.jpg)

## User Stories

As an authorized User

I want to be able to log in

(MUST BE MANUAL TEST BECAUSE GOOGLE USES CAPTCHA)

Because security policy says that I cannot do anything else without it

---

As an authorized User

I want to be able to see my profile

So I can see information that you know about me

(Automated Test with jasmine on frontend)

(Automated Test get on backend.)

Integration test can probably be manual

---

As an authorized user

I want to be able to edit my profile

To correct information I don’t like

(Automated test with jasmine?)

(Test on backend on test user)

Integration test can probably be manual

---

As an authorized user

I want to be able to like, unlike and dislike ideas

To show my approval and disapproval

(Automated test of backend and sql)

(Automated test of frontend with jasmine)

Integration test can probably be manual

---

As a authorized User

I want to be able to see messages

So that I can see other people’s ideas

(Automated test with Jasmine? And mocked routes)

(automated test of sql ->json path)

(Manual test of clicking the refresh button when another user posts on a different browser)

Integration test can be manual of looking at website from full deployment

---

As a authorized User

I want to be able to post messages

So that I can share other people’s ideas

(Automated test of https route and frontend with mocking)

(Automated test of SQL statement coupled with delete (so that test messages are unsure), or the creation of a dedicated testing table)

Integration test by uses of example operation, could only be automated if delete was also automated

---

As an authorized User

I want to be able to edit messages

So that I can change my ideas based on feedback

(Automated test of frontend using Mocking)

(If ACID, can automated test edits on a single test message, otherwise requires a testing DB)

Can be integration tested live

---

As an authorized User

I want to be able to delete messages

So that I can withdraw mistaken submissions

(Automated test of frontend using mocking)

(Automated test of backend using a test message, though this couples to the put route, or using a test DB)

Integration test can probably be manual

---

As an authorized User

I want to be able to comment on messages

So that I can give feedback

(Automated Test of frontend using jasmine and mocking)

(Automated Test of backend using comment on test message)

Integration test manaul

---

As an authorized User

I want to be able to delete my comments

so that I can remove bad feedback

(Automated Test on frontend using mocking)

(Automated Test of backend using the same test comment as the test for creating comments)

Integration test manual

---

As an authorized User

I don't want to be logged out for server issues

so that I don't need to log in again

Testing of this requires messing with docker instances, so must be manual

Other caching includes and enables automated testing

---

As an authorized User

I want to be able to upload files

So that I can share images and other information

This testing can be automated on front and backends

---

As an authorized User

I want to be able to add links to posts

So that I can make references and share work

This testing can be manual on web

---

(All manual integration tests could be replaced if we have a test DB, but should not be replaced unless this is true)

---

As an admin

I want to be able to create tables

So that I can enable the app

Automated testing with PreparedStatements (Create a test DB, first half of test)

---

As an admin

I want to be able to delete tables

So that I can limit costs and resource use

Automated testing with PreparedStatement (second half of test with creating a DB)

---

As an admin

I want to be able to delete messages

So that I can remove sensitive information from improper locations

Automated testing with PreparedStatements (Create a post and delete by id)

---

As an admin

I want to be able to connect to the database

So that I can moderate submissions

Manual testing of command line functions

Automated testing with PreparedStatements (Create a post and delete by id)

---

As an admin

I want to be able remove users

For violating terms of service (or cause they were fired)

Manual or automated test of prepared statment

---

As an admin

I want to be able to prepopulate tables

To allow information to persist across transfers

Manual or Automated Testing of prepared statments to post to all tables

---

As an admin

I want to be able to invalidate messages and reverse this

To hold them for review without removing them

Automated unit testing of prepared statement

---

As an admin

I want to be able to view uploaded documents with access information

So I can see what is being used

Automated unit testing of prepared statement

---

As an admin

I want to be able to remove uploaded documents

So I can free up limited sapce

Automated unit test if also add uploads, otherwise this must be manual

## Description of Tests (Automated)

### Backend

#### `PostData`

- Tests for insertion of new post
- Tests for selecting all post or a specific post
- Tests for deletion a current post
- Tests for updating a current post
- Tests for toggling vote on a post

#### `UserData`

- Tests for adding a new user
- Tests for selecting a specific user and verifying it
- Tests for updating a user
- Tests for deleting a user

#### `Commentdata`

- Tests for adding a new comment
- Tests for selecting a specific comment by post or by user
- Tests for deleting a comment

### Admin

- Create a randomly generated String, and adding that String as a new element in the table
- Ensuring the elements have been added
- Deleting added Elements
- Ensuring Elements added have been deleted.

### Web

- Add button hides lists of rows
- Check if tests values are in text boxes.
- Clicking the like button and deleting the test element

## Routes and details

### Current methods

| Purpose                                    | Route                                | Verb   | Structure                                                             |
| ------------------------------------------ | ------------------------------------ | ------ | --------------------------------------------------------------------- |
| Show all posts                             | `/messages`                          | GET    | JSON `{ArrayList<messages>}`                                          |
| Show one post                              | `/messages/:postID`                  | GET    | JSON `{mTitle, mMessage, numLikes, userID}`                           |
| Create new post                            | `/user/addMessage`                   | POST   | Takes `{mTitle, mMessage}`, all other fields handled by backend       |
| Edit content of a post                     | `/user/editMessage/:postID`          | PUT    | Takes `{mTitle, mMessage}` and backend handles updates                |
| Delete a post                              | `/user/deleteMessage/:postID`        | DELETE | Returns `1` on success                                                |
| Upvote a post                              | `/user/upvote/:postID`               | POST   | Returns `1` on success                                                |
| Downvote a post                            | `/user/downvote/:postID`             | POST   | Returns `1` on success                                                |
| Add a comment                              | `/user/comment/:postID`              | POST   | Takes `{mMessage}`, all other fields handled by backend               |
| Edit a comment                             | `/user/editComment/:commentID`       | PUT    | Takes `{mMessage}`, and backend handles updates                       |
| Show a comment                             | `/comment/:commentID`                | GET    | JSON `{cId, cMessage, cUserID, cPostID}`                              |
| Show comments for specific post            | `/messages/:postID/comments`         | GET    | JSON `{ArrayList<comments>}`                                          |
| Show comments for specific user for a post | `/messages/:postID/comments/:userID` | GET    | JSON `{ArrayList<comments>}`                                          |
| Show comments for a user                   | `/user/:userID/comments`             | GET    | JSON `{ArrayList<comments>}`                                          |
| Show user profile                          | `/user/:userID`                      | GET    | JSON `{uId, uUsername, uEmail, uNote}`                                |
| Show current user profile                  | `/user`                              | GET    | JSON `{uId, uUsername, uEmail, uGender, uSO, uSub, uNote}`            |
| Edit current user profile                  | `/user`                              | PUT    | Takes `{uUsername, uGender, uSO, uNote}`, and backend handles updates |
| Authenticate user                          | `/authenticate`                      | POST   | returns `/.index.html` with `idtoken` and `sub` cookie on success     |
| Logs out a user                            | `/logout`                            | DELETE | returns "Logout success" and deletes cookies                          |

## Documentation for Branches

- [Admin](../admin-cli/README.md)
- [Backend](../backend/README.md)
- [Web](../web/README.md#documentation)
