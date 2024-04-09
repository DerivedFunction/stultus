# Phase 2 Sprint 9 - PM Report Template

<!-- PM: When editing this template for your submission, you may remove this section -->
## Instructions
Be as thorough and complete as possible, while being brief/concise. Please give detailed answers.

Submit one report per team. This should be submitted by the designated PM, except in approved circumstances. The report should be created as a markdown file (and converted to pdf if required).

In addition to uploading to coursesite, version control this in the `master` branch under the `docs` folder.
Use this form to provide your project manager report.
## Team Information [10 points total]

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

Screenshot of Jira board:
(should match/support list of backlog and tech debt items in subheadings below)


## General questions [15 points total]

1. Did the PM for this week submit this report (If not, why not?)? 
Yes
2. Has the team been gathering for a weekly, in-person meeting(s)? If not, why not?
Yes, on Tuesday during the designated time, and on wednesday on our own as a checkin.
3. Summarize how well the team met the requirements of this sprint.

We met most, but not all of the sprint requirments. Notably, we did not create a profile viewing screen for other users, just for the logged in user. There may be other requirments unmet, especially the non-functional. 

4. Report on each member's progress (sprint and phase activity completion) – "what is the status?"

Admin-Cli: Patrick Boles

Backend: Denny Li

Frontend: Thomas Chakif

5. Summary of "code review" during which each team member discussed and showed their progress – "how did you confirm the status?"
    <!-- PM: When editing this template for your submission, you may remove this guidance -->
   
        * Were these reviews scheduled, or "on-demand" / as completed?
Firstly, throughout the process I requested that each increment of work be paired with a code review to merge into the master branch once it was confirmed to be working, and I checked during that specific implementation details, and that they worked as requested. These were done asynchronously, with responces in Slack or Bitbucket Comments. I tried to get these code reviews done imediately, because I did not want to delay work if I requested changes. I also watched each video from each team member, and compared that to what I had seen in the bitbucket and on Slack. Additionally, we communicated what we were working on and it's status in slack. I also helped people work through known trouble areas.
6. What did you do to encourage the team to be working on phase activities "sooner rather than later"?
Generally, people were pretty self-motivated on this, but I had to continuously and conspicously clear people's blockers, so that they knew that they could start work without waiting for other features, and that things would come together. 
7. What did you do to encourage the team to help one another?
I tried to make sure that people knew where everybody else was, and what was blocking them, and this let them help each other. I also tried to talk through and help with problems that my team was having. 
8. How well is the team communicating?
The team was communicating pretty well, but the semi-asynchronous nature was hard, as when people ran into blockers, they generally asked for help on slack but stopped, since they needed to wait for people to see their messages and respond. This left the final implementation a little delayed, but we worked it out. 
Communication has been mainly about the project, and tools, not getting to know each other. 
9. Discuss expectations the team has set for one another, if any. Please highlight any changes from last week.
    <!-- PM: When editing this template for your submission, you may remove this guidance -->
    * Some examples you may have reached consensus on are: 
        * acceptable and unacceptable behaviors, 
        * frequency of asynchronous communication (e.g. slack), 
        * regularity of "live" check-ins (in person, or on e.g. zoom), 
        * turn-taking or "stage sharing" during live meetings, 
        * safe ways to express discomfort or disagreement should emotions or tensions flare.
We did not really try to set any new expectations this week, but there were two things that I wanted to note. The first is about getting videos, where I tried to encourage team members to make their videos Saturday, but since they were not finished with functionality at that point, they slipped to late Sunday, leading to only a little time to edit. I also tried to encourage pull requests early and often, as they are easier when smaller, and they are the best way that I had of confirming status. 
10. If anything was especially challenging or unclear, please make sure this is [1] itemized, [2] briefly described, [3] its status reported (resolved or unresolved), and [4] includes critical steps taken to find resolution.
    * Challenge: Role Containment
    * Status: (resolved or unresolved) Unresolved
        * Description: One of my team members (Denny Li) knows that they will be on a different role in a few weeks, and wants to start working on it now. However, that is not their current job, and I am worried that it could lead to conflicts between building the backend for their vision of the frontend, and not what we have now. Also, it takes up development time not on their primary responsibilities. 
        * Critical steps taken to find resolution: I talked with them about it, attempting to clarify that their work would not reach the frontend until next phase, due to seperation of concerns points, and that I needed their focus to be on the frontend. I tried to avoid pushing too far, however, because I am not sure how to phrase it, and I recognize that he did wait till he did have the backend working, and playing around with other parts of the app can help understand their purpose and function.
    * Challenge: OAuth Test Coupling
    * Status: Resolved
        * Description: When developing Oauth, there was a coupling, where Web was waiting for the authentication route, thinking that they needed it for Google Identity Services, and Backend was waiting for things in web, as they needed valid tokens to test on, which only the frontend can provide. This left multiple times where team members felt like they did all they could do on their own, and gave up. The neccesity for frontend creation was not obvious in documentation or discussion, but was managable.
        * Critical Steps: The critical steps taken was simply tracing out the dependancy chain that was causing the blockage, by talking to team members and readding the Google Developer documentation. This let me explain what exactly the chain of dependencies was, and how we could use mocking so that people were not waiting on other branches to test their code, and what exactly those actions were. This was helped by being able to work in person and focus on these changes. 

11. What might you suggest the team or the next PM "start", "stop", or "continue" doing in the next sprint?
I would suggest that if possible, videos be requested at least one day early, but this may not be possible. I would also recommend that untangling mutual dependencies be a priority, as figuring out who is responsible for what with Oauth took us almost a week, much of that spent being mutually reliant in order to test. Additionally, I found myself talking through a lot of issues to figure out crucial actions, and this practice should likely continue. 

## Role reporting [75 points total, 15 points each (teams of 4 get 15 free points)]
Report-out on each role, from the PM perspective.
You may seek input where appropriate, but this is primarily a PM related activity.

### Back-end

1. Overall evaluation of back-end development (how was the process? was Jira used appropriately? how were tasks created? how was completion of tasks verified?)
Overall, backend development was largely sucessful, except for the discussed challenges with OAuth. Status was mainly communicated through pull requests, with changes communicated through commit messages. This let things be correlated with Jira, especially as I was the source of all Jira tasks this sprint.
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
In general, the backend code is in a pretty good shape in terms of organization. The most useful development is that many things about routes and formats was centralized as format strings, making distributed changes easier. 
4. Describe the code review process you employed for the back-end
Generally, I looked through the code specifically for changes, and tried to match these changes to default behavior and tools.
5. What was the biggest issue that came up in code review of the back-end server?
Making sure that changes to backend do not introduce regression in frontend. At least once, we broke frontend and frontend testing by not serving the login website, just the main app. 
6. Is the back-end code appropriately organized into files / classes / packages?
Yes
7. Are the dependencies in the `pom.xml` file appropriate? Were there any unexpected dependencies added to the program?
No
8. Evaluate the quality of the unit tests for the back-end
Tests are satisfactory, with at least one basic test for each method, which covers core functionality, and a plan for manual tests of https to https, though this is manual through postman.
9. Describe any technical debt you see in the back-end
Not much is visible, through significant effort in organization and development early in this phase and sprint. 

### Admin

1. Overall evaluation of admin app development (how was the process? was Jira used appropriately? how were tasks created? how was completion of tasks verified?)
The admin app is in slightly worse shape than backend, but has things in pretty good shape. It also was good at communicating status, but because the pull requests were less prolific, they mostly communicated status through jira, which worked well.
2. Describe the tables created by the admin app
User Table - name usrData-  Consists of User ID as primary key, username, email, gender, and so 
Message Table - name tblData- Consists of ID as primary key, subject, message, user id as foriegn key, and likes
Like Table- name likeData- Consists of id as primary key, , vote value, user id as foriegn key, postid as foriegn key
Comment Table- name cmntData- Consists of id as primary key, messsage, post id as foriegn key, user id as foriegn key
3. Assess the quality of the admin code
The Admin app is functional, but organized poorly for readability, though probably not too badly for adding new functionality. T
4. Describe the code review process you employed for the admin app
I went over changes in the code asynchronously, and correlated them with changes to the Jira board, making sure each piece of code matched the requirements. 
5. What was the biggest issue that came up in code review of the admin app?
The biggest issue was some things about SQL formatting that came up last minute, and no large updates to the testing suite. 
6. Is the admin app code appropriately organized into files / classes / packages?
No, not entirely. Both the main and database classes are getting inappropriately big, and might get split up. 
7. Are the dependencies in the `pom.xml` file appropriate? Were there any unexpected dependencies added to the program?
Yes, It was as expected
8. Evaluate the quality of the unit tests for the admin app
Basic, do not cover all or even most functionality. Do not test all tables. Testing is currently manual.
9. Describe any technical debt you see in the admin app
Add unit tests, SQL views, and other such QoL features. 

### Web

1. Overall evaluation of Web development (how was the process? was Jira used appropriately? how were tasks created? how was completion of tasks verified?)
Web frontend was communicated well but suffered alot from blockage. Backend did a lot of development, but did not stabilize interfaces, leading to a bit of a crunch to actually get things to to frontend. Everything ended up there, but only barely. 
2. Describe the different models and other templates used to provide the web front-end's user interface
3. Assess the quality of the Web front-end code
It seems mostly fine, though the very nature of html and TS being split makes some things slightly difficult to follow. 
4. Describe the code review process you employed for the Web front-end
For web frontend, I mainly checked for functionality before merging, because I do not have a ton of TS familiarity, but I also specifically went through Oauth, and tried t make sure that was working.
5. What was the biggest issue that came up in code review of the Web front-end?
No real issues came up, but the web code was given so last minute that I had to confirm functionality through testing, not really have time to work through the changes. 
6. Is the Web front-end code appropriately organized into files / classes / packages?
Yes, though it is now two page instead of single page, as the main app is segregated from login information (which is just GIS)
7. Are the dependencies in the `package.json` file appropriate? Were there any unexpected dependencies added to the program?
Yes, it was as expected. 
8. Evaluate the quality of the unit tests for the Web front-end
The tests on Jasmine are comprehensive, but not complete, and so are waiting to be improved. 
9. Describe any technical debt you see in the Web front-end
Some functionality is still only doubtfully working, and this needs followup and fixing, if they do not work. 

### Mobile

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
We met with our mentor for about an hour on Tuesday, but not much beyond that. 
2. Describe your use of Jira.  Did you have too much detail?  Too little?  Just enough? Did you implement policies around its use (if so, what were they?)?
Use of Jira was not good for the first few days, as I created tasks with good detail, but I was making changes in Jira to match what people were telling me, not having them indicate status through Jira, but I asked for people to do this as it seemed to be a requirement, and this led to significantly better communication. 
3. How did you conduct team meetings?  How did your team interact outside of these meetings?
I generally tried to walk through current issues, and then deal with them, buthtere was not much structure beyond that. Our team interacted mostly on slack, with a single video call to diagnose confusing problems. 
4. What techniques (daily check-ins/scrums, team programming, timelines, Jira use, group design exercises) did you use to mitigate risk? Highlight any changes from last week.
Generally, I used daily slack communication as a tool to check up on status, which worked well. I also tried to make sure that people were aware of the realations of various blockers. 
5. Describe any difficulties you faced in managing the interactions among your teammates. Were there any team issues that arose? If not, what do you believe is keeping things so constructive?
One difficulty I found was in communicating importance and urgency, especially around blockers. There were not any whole team issues, but I am trying to manage how mutch of a nudge I am being about status and focus.
6. Describe the most significant obstacle or difficulty your team faced.
The most siginificant challenge was Oauth, and making sure that we had it ready to build on, as much of the later decisions stemmed from how we implemented Oauth in our software. 
7. What is your biggest concern as you think ahead to the next phase of the project? To the next sprint?
My biggest concern is with coupling, as if we end up with an insecure Oauth implementation, everything rests on that, so we would not be able to do the additional requirement count of next phase.
8. How well did you estimate time during the early part of the phase?  How did your time estimates change as the phase progressed?
Our estimation of time once we figured out Oauth was pretty good, but we did not and could not predict how long that would take, other than a while. While the time for tasks was not unexpected, the time that people were able to take was, leading to unpredictibility. 
9. What aspects of the project would cause concern for your customer right now, if any?
That we said we had comments ready, but they are not actually fully deployed. 
