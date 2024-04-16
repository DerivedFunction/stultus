# Phase 2' Sprint 10 - PM Report Template
Use this form to provide your project manager report for Phase 2' (Prime).

<!-- PM: When editing this template for your submission, you may remove this section -->
## Instructions
Be as thorough and complete as possible, while being brief/concise. Please give detailed answers.

Submit one report per team. This should be submitted by the designated PM, except in approved circumstances. The report should be created as a markdown file (and converted to pdf if required).

In addition to uploading to coursesite, version control this in the `master` branch under the `docs` folder.

### Team Information:
* Number: 21
* Name: Stultus
* Mentor: <Emir Veziroglu, env225@lehigh.edu>
* Weekly live & synchronous meeting:
    * without mentor: Normally wednesday 10:45, but not this week
    * with mentor: Tuesday at 7:15pm

### Team Roles:

* Project Manger: <Robert Kilsdonk, rok326@lehigh.edu>
    
* Backend developer: <Denny Li, del226@lehigh.edu>
* Admin developer: <Thomas Chakif, thc225@lehigh.edu>
* Web developer: <Patrick Boles, pjb325@lehigh.edu>

### Essential links for this project:

* Team's Dokku URL(s)

- <https://team-stultus.dokku.cse.lehigh.edu/>

- Team's software repo (bitbucket)

  - <https://bitbucket.org/sml3/cse216_sp24_team_21>

- Team's Jira board

  - <https://cse216-24sp-del226.atlassian.net/jira/software/projects/T2/boards/2>



## Beginning of Phase 2' [20 points total]
Report out on the Phase 2 backlog and any technical debt accrued during Phase 2.

1. What required Phase 2 functionality was not implemented and why? 
    * These high-priority items are to be added to the Phase 2' backlog that appears on your Jira board.
    * This should be a list of items from the Phase 2 backlog that were not "checked off".
    * List this based on a component-by-component basis, as appropriate.
        * Admin-cli
            1. Backlog 1: Invalidating Users
            2. Backlog 2: SQL views
        * Backend
            No backlog
        * Mobile FE
            Viewing Comments
            Viewing users
        * Web FE

2. What technical debt did the team accrue during Phase 2?
    
        * Admin-cli
            1. Expanding testing
        * Backend
            1. No issues
        * Mobile FE
            1. Does not exist
        * Web FE
            1. Expanding unit testing

## End of Phase 2' [20 points total]
Report out on the Phase 2' backlog as it stands at the conclusion of Phase 2'.

1. What required Phase 2 functionality still has not been implemented or is not operating properly and why?
    Everything is clear.
    Everything could always use more tests
    Other than that
    Admin-cli wants re-organization
    Backend is clean
    Frontend needs unification.
3. If there was any remaining Phase 2 functionality that needed to be implemented in Phase 2', what did the PM do to assist in the effort of getting this functionality implemented and operating properly?
Generally, the big change was learning that it was okay to pull in work from other team members. I also tried to keep up with the status, but there was not much to do other than working that functionality in. 
4. Describe how the team worked together in Phase 2'. Were all members engaged? Was the work started early in the week or was there significant procrastination?
The team worked okay in Phase 2'. There was not significant procrastination, though work was not super quick. Work was started early in the phase, but taken steadily, especially Thomas and Patrick, who had active backlog items. 

5. What might you suggest the team or the next PM "start", "stop", or "continue" doing in the next Phase?
One thing we might want to start doing is running retrospectives and briefing each other on components.
One thing we should continue to do is communicating our status and any dependencies between frontend and backend. 
## Role reporting [50 points total]
Report-out on each team members' activity, from the PM perspective (you may seek input where appropriate, but this is primarily a PM related activity).

### Back-end
What did the back-end developer do during Phase 2'? They helped with other branches and made a few small changes, having finished requirements lasst sprint. 
1. Overall evaluation of back-end development (how was the process? was Jira used appropriately? how were tasks created? how was completion of tasks verified?)
Overall, back-end development went well in this phase, with changes as neeeded to support invalidation and to fix a few things about the Oauth implementation. 
2. List your back-end's REST API endpoints
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


3. Assess the quality of the back-end code
Backend code quality is generally good, with parts separated well and reasonable documentation.
4. Describe the code review process you employed for the back-end
Generally, I looked through the changes in the code, and tracked this with the changes needed in Jira.
There were not many changes this week, so not much to review.
5. What was the biggest issue that came up in code review of the back-end server?
I asked that we switch out regular cookies without lifespan, to secure cookies which had it, so that it is slightly harder to steal sessions. 
6. Is the back-end code appropriately organized into files / classes / packages?
Yes.
7. Are the dependencies in the `pom.xml` file appropriate? Were there any unexpected dependencies added to the program?
The dependencies are approrpiate and none were unexpected.
8. Evaluate the quality of the unit tests for the back-end
The backend unit tests are good and very thorough. 
9. Describe any technical debt you see in the back-end
There does not seem to be much technical debt on the backend. 

### Admin
What did the admin front-end developer do during Phase 2'? Went over SQL views and added invalidation and deletion. 
1. Overall evaluation of admin app development (how was the process? was Jira used appropriately? how were tasks created? how was completion of tasks verified?)
Overall, admin app development went pretty well, with good communication, especially on Jira. 
2. Describe the tables created by the admin app
User Table - name usrData-  Consists of User ID as primary key, username, email, gender, so, and an int for if it is banned.  
Message Table - name tblData- Consists of ID as primary key, subject, message, user id as foriegn key, likes, and an int for if it is invalidated.
Like Table- name likeData- Consists of id as primary key, , vote value, user id as foriegn key, postid as foriegn key
Comment Table- name cmntData- Consists of id as primary key, messsage, post id as foriegn key, user id as foriegn key and int for if it is invalidated.
3. Assess the quality of the admin code
Admin code is somewhat okay, but is in a few big classes which creates unclear flow.
4. Describe the code review process you employed for the admin app
I went over the changes in the code to make sure they were as described.
5. What was the biggest issue that came up in code review of the admin app?
No major issues came up in code review.
6. Is the admin app code appropriately organized into files / classes / packages?
No, there are a few big classes with a lot of internal flow, which likely need to be seperated. Also, structure is somewhat unclear.
7. Are the dependencies in the `pom.xml` file appropriate? Were there any unexpected dependencies added to the program?
The dependencies are appropritates and as expected. 
8. Evaluate the quality of the unit tests for the admin app
The unit tests for admin are not very thorough, and need signficant expansion to cover all cases. 
9. Describe any technical debt you see in the admin app
The main technical debt is readding unit tests and reorganizing the large classes. 

### Web
What did the web front-end developer do during Phase 2'? Fixed functionality for comments, likes and profiles. 
1. Overall evaluation of Web development (how was the process? was Jira used appropriately? how were tasks created? how was completion of tasks verified?)
The web development was somewhat unclear on the process side, with exact status not communicated very well. This is in part because it was not put either on Jira or communicated in person due to lack of prompting. 
2. Describe the different models and other templates used to provide the web front-end's user interface
They seem basically fine, and well enough organized.
3. Assess the quality of the Web front-end code
It seems basically okay, without too many issues. The main important thing is making sure that you are actually expecting the right thing from methods. 
4. Describe the code review process you employed for the Web front-end
Generally, I just checked that changes were in the areas that needed them, and that they did not have obvious defeciencies. 
5. What was the biggest issue that came up in code review of the Web front-end?
No issues came up in code review of the Web front-end. 
6. Is the Web front-end code appropriately organized into files / classes / packages?
The Web front-end does not seem to have problems with organization, especially because most of the frontend logic is pretty simple. 
7. Are the dependencies in the `package.json` file appropriate? Were there any unexpected dependencies added to the program?
The dependencies are appropriate and as expected. 
8. Evaluate the quality of the unit tests for the Web front-end 
The web frontend unit tests seem very thorough, covering everything neccesary for full functionality.
9. Describe any technical debt you see in the Web front-end
I do not see technical debt in the web frontend.

### Mobile
What did the mobile front-end developer do during Phase 2'?
1. Overall evaluation of Mobile development (how was the process? was Jira used appropriately? how were tasks created? how was completion of tasks verified?)
2. Describe the activities that comprise the Mobile app
3. Assess the quality of the Mobile code
4. Describe the code review process you employed for the Mobile front-end
5. What was the biggest issue that came up in code review of the Mobile front-end?
6. Is the Mobile front-end code appropriately organized into files / classes / packages?
7. Are the dependencies in the `pubspec.yaml` (or build.gradle) file appropriate? Were there any unexpected dependencies added to the program?
8. Evaluate the quality of the unit tests for the Mobile front-end here
9. Describe any technical debt you see in the Mobile front-end here

### Project Management
Self-evaluation of PM performance
1. When did your team meet with your mentor, and for how long?
We met with our mentor for about an hour in the shared time, and not much beyond that.
2. Describe your use of Jira.  Did you have too much detail?  Too little?  Just enough? Did you implement policies around its use (if so, what were they?)?
Our use of Jira was not as good this week, as there were fewer things on the board, and things moved somewhat slower, but otherwise detail seemed right.
3. How did you conduct team meetings?  How did your team interact outside of these meetings?
I ended up doing just individual check-ins in meetings, not a really whole group thing, which was not perfect. Our team interacted outside of the meetings mostly professionally through Slack, with not much else going on. 
4. What techniques (daily check-ins/scrums, team programming, timelines, Jira use, group design exercises) did you use to mitigate risk? Highlight any changes from last week.
The main technique for mitigating risk this week was check-ins, trying to make sure everything came together on time. The other notable technique was communicating useful details of implementation when in person.
5. Describe any difficulties you faced in managing the interactions among your teammates. Were there any team issues that arose? If not, what do you believe is keeping things so constructive?
There were not that many team difficulties, though that was because there was not a ton of interaction. Convincing people to come to more in-person time was not as succesful as last week. 
6. Describe the most significant obstacle or difficulty your team faced.
The most significant obstacle was making sure that things were moving 
7. What is your biggest concern as you think ahead to the next phase of the project? To the next sprint? What steps can the team take to reduce your concern?
The biggest concern that I have is that unexpected coupling, discovered late, will likely cause outsized delays.
8. How well did you estimate time during the early part of the phase?  How did your time estimates change as the phase progressed?
My time estimation was not great nor well communicated, I thought we would be completely done by Friday, but finishing happened over the weekend. This is fine, but unexpected and unplanned.
9. What aspects of the project would cause concern for your customer right now, if any?
The biggest cause for concern seems to be the time estimation. We thought things were finished, when they were not, and my internal estimate of timing slid significantly. 
