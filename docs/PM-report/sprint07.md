# Phase 1' Sprint 7 - PM Report Template
Use this form to provide your project manager report for Phase 1' (Prime).

<!-- PM: When editing this template for your submission, you may remove this section -->
## Instructions
Be as thorough and complete as possible, while being brief/concise. Please give detailed answers.

Submit one report per team. This should be submitted by the designated PM, except in approved circumstances. The report should be created as a markdown file (and converted to pdf if required).

In addition to uploading to coursesite, version control this in the `master` branch under the `docs` folder.

## Team Information [10 points total]

### Team Information:


- Number: 21

- Name: team21

- Mentor: **Emir Veziroglu** [env225@lehigh.edu](mailto:env225@lehigh.edu)

- Weekly live & synchronous meeting:

  - without mentor: Wednesday 10:45am-12pm

  - with mentor: Tuesday 7pm



### Team Roles:



- Project Manger: **Denny Li**, [del226@lehigh.edu](mailto:del226@lehigh.edu)

- Admin: **Thomas Chakif**, [thc225@lehigh.edu](mailto:thc225@lehigh.edu)

- Backend: **Robert Kilsdonk**, [rok326@lehigh.edu](mailto:rok326@lehigh.edu)

- Front End/Mobile Web: **Patrick Boles**, [pjb325@lehigh.edu](mailto:pjb325@lehigh.edu)



### Essential links for this project:



- Team's Dokku URL(s)

  - <https://2024sp-team-stultus.dokku.cse.lehigh.edu/>

- Team's software repo (bitbucket)

  - <https://bitbucket.org/sml3/cse216_sp24_team_21>

- Team's Jira board

  - <https://cse216-24sp-del226.atlassian.net/jira/software/projects/T2/boards/2>




## Beginning of Phase 1' [20 points total]
Report out on the Phase 1 backlog and any technical debt accrued during Phase 1.

1. What required Phase 1 functionality was not implemented and why? 
    <!-- PM: When editing this template for your submission, you may remove this guidance -->
    * These high-priority items are to be added to the Phase 1' backlog that appears on your Jira board.
    * This should be a list of items from the Phase 1 backlog that were not "checked off".
    * List this based on a component-by-component basis, as appropriate.
        * Admin-cli
            1. Missing functionality 1: View and implement likeData table
                * Why: Early implemention and we are still using the `like` (deprecated soon) column in tblData.
        * Backend
             1. Missing functionality 1: View and implement likeData table
                * Why: Early implemention and we are still using the `like` (deprecated soon) column in tblData.
        * Web FE
            1. Missing functionality 1: None
2. What technical debt did the team accrue during Phase 1?
    <!-- PM: When editing this template for your submission, you may remove this guidance -->
    * These items are to be added to the Phase 1' backlog that appears on your Jira board.
    * List this based on a component-by-component basis, as appropriate.
        * Admin-cli
            1. Tech debt item 1: Tests are not modular, Will need to change to static variables or use functionality of backend's tests
        * Backend
            1. Tech debt item 1: Will need to change how toggling likes work with likeData table. Some code can be refactored.
        * Web FE
            1. Tech debt item 1: Bare and minimal UI and UX experience. It is currently not important on how the UI looks. Some code can be refactored


## End of Phase 1' [20 points total]
Report out on the Phase 1' backlog as it stands at the conclusion of Phase 1'.

1. What required Phase 1 functionality still has not been implemented or is not operating properly and why?
    <!-- PM: When editing this template for your submission, you may remove this guidance -->
    * List this based on a component-by-component basis, as appropriate.

2. What technical debt remains?
    <!-- PM: When editing this template for your submission, you may remove this guidance -->
    * List this based on a component-by-component basis, as appropriate.

3. If there was any remaining Phase 1 functionality that needed to be implemented in Phase 1', what did the PM do to assist in the effort of getting this functionality implemented and operating properly?

4. Describe how the team worked together in Phase 1'. Were all members engaged? Was the work started early in the week or was there significant procrastination?
    > Yes, all members were engaged on Slack or in the weekly meetings. Work was started as early as possible to issues are fixed in time.
5. What might you suggest the team or the next PM "start", "stop", or "continue" doing in the next Phase?
    > > Use Slack as often as possible. Be ready to know the expectations of the current sprint requirements. Make sure
    > everyone is on track and on time for their work. Poke members to add as much comments as needed so it is easier to swap roles.

## Role reporting [50 points total]
Report-out on each team members' activity, from the PM perspective (you may seek input where appropriate, but this is primarily a PM related activity).
**In general, when answering the below you should highlight any changes from last week.**

### Back-end
What did the back-end developer do during Phase 1'?
1. Overall evaluation of back-end development (how was the process? was Jira used appropriately? how were tasks created? how was completion of tasks verified?)
    > The backend under Robert worked very well. Jira was used to check against the rubric using child issues.
2. List your back-end's REST API endpoints

| Purpose                | Route               | Verb   | Purpose                     | Structure                                                     |
| ---------------------- | ------------------- | ------ | --------------------------- | ------------------------------------------------------------- |
| Show all messages      | `/messages`         | GET    | Return post data to display | JSON `{ArrayList<messages>}`                                  |
| Show one post          | `/messages/id`      | GET    | Return single post data     | JSON `{title, message, numLikes}`                             |
| Create new Post        | `/messages`         | POST   | Creates a post              | Takes `{title, message}`, all other fields handled by backend |
| Edit content of a post | `/messages/id`      | PUT    | Edits a post                | Takes `{title, message}` and backend handles updates          |
| Delete a post          | `/messages/id`      | DELETE | Deletes a post              | Returns `status`                                              |
| Like a post            | `/messages/id/like` | PUT    | Likes a post                | Returns new number of `numLikes`                              |
3. Assess the quality of the back-end code
    > Robert was able to refactor out a lot of the code. The code works as intended.
4. Describe the code review process you employed for the back-end
    > The best way to test the backend is to run Unit Tests. After that, it is to run the Front End tests to see if everything works as expected
5. What was the biggest issue that came up in code review of the back-end server?
    > There has been no issues for now. The biggest thing is running it on dokku. 
6. Is the back-end code appropriately organized into files / classes / packages?
    > Yes, there is an `App.java`, `Database.java`, and `Apptest.java`
7. Are the dependencies in the `pom.xml` file appropriate? Were there any unexpected dependencies added to the program?
    > No, no other dependencies were added other than the javadoc plugin.
8. Evaluate the quality of the unit tests for the back-end
    > Robert's tests were extensive and modular.
9. Describe any technical debt you see in the back-end
    > Recreating our like functionality to meet the future requirements of user likes.

### Admin
What did the admin front-end developer do during Phase 1'?
1. Overall evaluation of admin app development (how was the process? was Jira used appropriately? how were tasks created? how was completion of tasks verified?)
    > The Admin under Thomas worked very well. Jira was used to check against the rubric using child issues.
2. Describe the tables created by the admin app
| ID                     | Subject             | Message                     | Likes      |
| ---------------------- | ------------------- | --------------------------- | ---------- |
|    [001]               | Hello World         | Return post data to display | [001]       |
3. Assess the quality of the admin code
    > The code is very simple yet it has all the functionality required.
4. Describe the code review process you employed for the admin app
    > Make sure the unit tests run. Test the cmd-line to see if it works as expected.
5. What was the biggest issue that came up in code review of the admin app?
    > Not much issues other than initially adding the like column
6. Is the admin app code appropriately organized into files / classes / packages?
    > Yes, there is an `App.java`, `Database.java`, and `Apptest.java`
7. Are the dependencies in the `pom.xml` file appropriate? Were there any unexpected dependencies added to the program?
     > No, no other dependencies were added other than the javadoc plugin.
8. Evaluate the quality of the unit tests for the admin app
    > The unit tests were okay except it is done linearly under one test. Might have to copy backend's test structure since they are nearly identical.
9. Describe any technical debt you see in the admin app
    > Making the unit test modular.
### Web
What did the web front-end developer do during Phase 1'?
1. Overall evaluation of Web development (how was the process? was Jira used appropriately? how were tasks created? how was completion of tasks verified?)
    > The Web under Patrick worked well enough. Jira was used to check against the rubric using child issues.
2. Describe the different models and other templates used to provide the web front-end's user interface
    > It was done using plain HTML, CSS, and Javascript. Chrome Dev Tools were used to make and test changes.
3. Assess the quality of the Web front-end code
    > For now, it works. However, there may be a need for some improvements in organization for UI.
4. Describe the code review process you employed for the Web front-end
    > The Web's test complement with the backend: if the backend works as intended, then the front end must as well.
5. What was the biggest issue that came up in code review of the Web front-end?
    > Need to use `setTimeout` so that our tests can send and receive data from the backend.
6. Is the Web front-end code appropriately organized into files / classes / packages?
    > Yes, though there could be some to make it even better.
7. Are the dependencies in the `package.json` file appropriate? Were there any unexpected dependencies added to the program?
    > Added typedoc and typedoc-to-markdown to create typescript documentation.
8. Evaluate the quality of the unit tests for the Web front-end 
    > The tests were more extensive at testing the front end as well as the backend code.
9. Describe any technical debt you see in the Web front-end
    > Polishing the UI, reorganizing some code.

### Project Management
Self-evaluation of PM performance
1. When did your team meet with your mentor, and for how long?
    > 30 min on Tuesday Night.
2. Describe your use of Jira.  Did you have too much detail?  Too little?  Just enough? Did you implement policies around its use (if so, what were they?)?
    > Jira was only used to check against the rubric. Other issues were done verbally or on Slack.
3. How did you conduct team meetings?  How did your team interact outside of these meetings?
    > Wednesday weekly meetings to see if the tests and functionality are good to go.
4. What techniques (daily check-ins/scrums, team programming, timelines, Jira use, group design exercises) did you use to mitigate risk? Highlight any changes from last week.
   > We mitigated many risks when we met up in our team meeting. We were able to iron out most issues with our code.
5. Describe any difficulties you faced in managing the interactions among your teammates. Were there any team issues that arose? If not, what do you believe is keeping things so constructive?
   > There was little difficulty for team interactions. Other than the fact that I leave Lehigh after 4:30 everyday except for Tuesdays.
6. Describe the most significant obstacle or difficulty your team faced.
   > Most difficult was getting the backend deployed on dokku _with the like functionality_. The front end can't work if the backend on Dokku is outdated. Right now,
   > the like functionality we currently have will be deprecated soon.
7. What is your biggest concern as you think ahead to the next phase of the project? To the next sprint?
   > The biggest concern is the backend like button logic for unique users. Robert got the toggle like to work. Now we need to think in the future
   > to implement how to store a user's liked posts.
8. How well did you estimate time during the early part of the phase? How did your time estimates change as the phase progressed?
   > We didn't estimate time at all. We initally thought the backend and front end was going to take a while, but we got it done in time.
9. What aspects of the project would cause concern for your customer right now, if any?
   > Slightly poor UI and only a single like button (max like = 1, min like = 0).