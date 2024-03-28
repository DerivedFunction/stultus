# Phase 1 Sprint 6 - PM Report Template

Use this form to provide your project manager report for Phase 1 Sprint 6.

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

  - <https://team-stultus.dokku.cse.lehigh.edu/>

- Team's software repo (bitbucket)

  - <https://bitbucket.org/sml3/cse216_sp24_team_21>

- Team's Jira board

  - <https://cse216-24sp-del226.atlassian.net/jira/software/projects/T2/boards/2>

## General questions [15 points total]

1. Did the PM for this week submit this report (If not, why not?)?

   > Yes, I, Denny Li, is the PM. No one else have the copy of this PM report. How else did you read this?

2. Has the team been gathering for a weekly, in-person meeting(s)? If not, why not?

   > Yes, we did it on Wednesday to discuss team names, plans for future, etc.

3. Summarize how well the team met the requirements of this sprint.

   > We were able to get the backend dokku running, the like functionality works, and the front end looks much better
   > than the previous sprint. We added extra tests which were successful.

4. Report on each member's progress (sprint and phase activity completion) – "what is the status?"
   > Everyone had their portion completed.
5. Summary of "code review" during which each team member discussed and showed their progress – "how did you confirm the status?"

   > If a team member is done with a certain function, I asked them to test it on their computer to see if it works.
   > Then, I ask them to push and pull request to their respective branches. After that, I pulled the code on my computer
   > and test it out as well. If I don't like something, I'll ask them to fix it. Once everything works, I approve the pull
   > request and merge it with master.

6. What did you do to encourage the team to be working on phase activities "sooner rather than later"?
   > Maybe because I gave them clear ideas of what they should implement and test such that we were able to get it done.
   > Maybe because Robert was able to set up a working backend on dokku relatively quickly such that Patrick was able
   > to use it on his front end.
7. What did you do to encourage the team to help one another?
   > Communication is a key thing to help one another. If someone were to spot an error, it should to communicated
   > early so that it can be fixed.
8. How well is the team communicating?

   > Most of our core functionality happened during our team meeting. This allows everyone to spot errors. For example,
   > the like button was supposed to work by sending a PUT, but it didn't because Robert had SQL errors on Dokku. That
   > allows us to quickly breeze through it.

9. Discuss expectations the team has set for one another, if any. Please highlight any changes from last week.

   > Everyone is expected to complete their portion of work on time. Commit as often as possible. Once certain functions are
   > working, push it and have the PM test it as soon as possible. Use Slack, Jira to keep track of issues.

10. If anything was especially challenging or unclear, please make sure this is [1] itemized, [2] briefly described, [3] its status reported (resolved or unresolved), and [4] includes critical steps taken to find resolution.

    > We had trouble figuring out what our dokku url is. We though it was 2024p-team-stultus instead of team-stultus.

11. What might you suggest the team or the next PM "start", "stop", or "continue" doing in the next sprint?
    > Use Slack as often as possible. Be ready to know the expectations of the current sprint requirements. Make sure
    > everyone is on track and on time for their work.

## Role reporting [75 points total, 15 points each (teams of 4 get 15 free points)]

Report-out on each role, from the PM perspective.
You may seek input where appropriate, but this is primarily a PM related activity.

### Back-end

1. Overall evaluation of back-end development (how was the process? was Jira used appropriately? how were tasks created? how was completion of tasks verified?)
   > The backend development was probably one of the hardest part of this sprint. Luckily, Robert is good at doing this.
   > Because he was able to deploy on dokku so early, it was the backbone on how quickly we got the front end to work.
   > Jira was used to keep track of backend requirements from the rubric via child issues. Child issues were marked off
   > as complete when finished. Additional minor issues or ideas were added to the backlog, to be done later.
2. List your back-end's REST API endpoints
   | Purpose | Route | Verb | Purpose | Structure |
   |---------------------------|------------------|-------|-----------------------------------|-------------------------------------------|
   | Show all messages | /messages | GET | Return post data to display | Json{ArrayList<messages>} |
   | Show one post | /messages/id | GET | Return single post data | Json{message} |
   | Create new Post | /messages | POST | Creates a post | Takes {title, message}, all other fields handled by backend |
   | Edit content of a post | /messages/id | PUT | Edits a post | Takes {title, message} like creation |
   | Delete a post | /messages/id | DELETE| Deletes a post | Returns status |
   | Like a post | /messages/id/like| PUT | Likes a post | Returns new number of likes |
3. Assess the quality of the back-end code
   > The backend code was pretty efficient. All the logic were done in the backend, and the front end only had to call these routes
   > when clicking the button. Refactoring was done so that the `(request, response)` was moved out of each route as a separate
   > function. He deleted unused sql statements and code.
4. Describe the code review process you employed for the back-end
   > First, we must make sure the basic tests passed. After that, it is just if the front end can access these methods.
5. What was the biggest issue that came up in code review of the back-end server?
   > The app was locked when Robert pressed Ctrl-C. As of March 7, the front end of dokku is still the tutorial one. We also
   > probably needed better tests.
6. Is the back-end code appropriately organized into files / classes / packages?
   > Yes, we removed the edu....backend with just backend and changed the `pom.xml`.
7. Are the dependencies in the `pom.xml` file appropriate? Were there any unexpected dependencies added to the program?
   > No, nothing extra has been added.
8. Evaluate the quality of the unit tests for the back-end
   > For now, it is good at testing SQL statements on test elements and then deleting it.
9. Describe any technical debt you see in the back-end
   > For now, `messages/id/likes` is probably not a good route. It may be changed to `messages/id/method?=likes` later on.
   > It will require the front end and dokku to use the same routes. The tests are also all under one function.
   > The tests also run on the same database for "public use." We where able to delete the test elements.

### Admin

1. Overall evaluation of admin app development (how was the process? was Jira used appropriately? how were tasks created? how was completion of tasks verified?)
   > Admin development was probably the easiest of them all: change some SQL statements to include the extra column for likes.
   > Add additional formatting for the new column and change how it displays the elements.
   > Jira was used to keep track of admin requirements from the rubric via child issues. Child issues were marked off
   > as complete when finished. Additional minor issues or ideas were added to the backlog, to be done later.
2. Describe the tables created by the admin app
   > The formatting is by `mid | Title | Message | Likes `. We limited the title to only display 30 chars and
   > message to 40 chars to keep the table consistent.
3. Assess the quality of the admin code
   > We've done some refactoring on unused code, redundant strings, etc.
4. Describe the code review process you employed for the admin app
   > The admin just need to run his tests. On both sides, we tested the command line to see the formatting
   > and element manipulation.
5. What was the biggest issue that came up in code review of the admin app?
   > Not many issues. Maybe better tests?
6. Is the admin app code appropriately organized into files / classes / packages?
   > Yes, we removed the edu....admin with just admin and changed the `pom.xml`.
7. Are the dependencies in the `pom.xml` file appropriate? Were there any unexpected dependencies added to the program?
   > No, nothing has been added
8. Evaluate the quality of the unit tests for the admin app
   > The admin tests adds, then deletes the test elements from the database.
9. Describe any technical debt you see in the admin app
   > Right now, the admin test and the backend test roughly use the same tests.

### Web

1. Overall evaluation of Web development (how was the process? was Jira used appropriately? how were tasks created? how was completion of tasks verified?)
   > Web development was probably the 2nd complex: Revamp the front end UI to match our mockup.
   > Add additional formatting for the new like button and change how it displays the elements.
   > Jira was used to keep track of admin requirements from the rubric via child issues. Child issues were marked off
   > as complete when finished. Additional minor issues or ideas were added to the backlog, to be done later.
2. Describe the different models and other templates used to provide the web front-end's user interface
   > We kept the tutorial UI. Initially, Patrick had to `local-deploy.sh` multiple times to see if the changes were working.
   > Then, we realized that we could just test it using Chrome's Inspect Element to make css changes and then add it to
   > VSCode. With this process, it was a lot faster and smoother. We had to use online sources to find out how to keep the
   > header fixed.
3. Assess the quality of the Web front-end code
   > Right now, the web front end is just a copy and paste to add functionality for the like button. The best thing is
   > that the logic is all handled by the backend. The CSS might needed to be fixed and changed to make the UI look better.
4. Describe the code review process you employed for the Web front-end
   > The code review is get the UI working. Everyone in our team looked at the latest look on Patrick's screen and then
   > ask for changes if needed. After a final mockup, I will then test it on my end to see if it works.
5. What was the biggest issue that came up in code review of the Web front-end?
   > Getting the UI to look correct and work as intended. It may be a slow process, but it was sped up when Patrick used
   > the Chrome tools instead of manually changing and redeploying. Tests may not be the best.
6. Is the Web front-end code appropriately organized into files / classes / packages?
   > Yes, it retains the same structure as the tutorial code.
7. Are the dependencies in the `package.json` file appropriate? Were there any unexpected dependencies added to the program?
   > We added the jasmine dependencies that was missing.
8. Evaluate the quality of the unit tests for the Web front-end
   > It clicks new post, sets and checks the title and message, then clicks "add post." After a second, it clicks the like
   > button and clicks the delete button to delete the test element. It works okay for now. More efficient tests would be
   > needed later down the line.

### Project Management

Self-evaluation of PM performance

1. When did your team meet with your mentor, and for how long?
   > We meet with out mentor on Tuesdays for < 1hr. This is because we had relatively few problems due to our communication and teamwork.
2. Describe your use of Jira. Did you have too much detail? Too little? Just enough? Did you implement policies around its use (if so, what were they?)?
   > We used Jira to list out the rubric requirements. We also added minor issues and ideas to it.
3. How did you conduct team meetings? How did your team interact outside of these meetings?
   > Meetings are done Wednesdays in Packard Lab. Outside those meetings, it is just Slack.
4. What techniques (daily check-ins/scrums, team programming, timelines, Jira use, group design exercises) did you use to mitigate risk? Highlight any changes from last week.
   > We mitigated many risks when we met up in our team meeting. We were able to iron out most issues with our code.
5. Describe any difficulties you faced in managing the interactions among your teammates. Were there any team issues that arose? If not, what do you believe is keeping things so constructive?
   > There was little difficulty for team interactions. Other than the fact that I leave Lehigh after 4:30 everyday except for Tuesdays.
6. Describe the most significant obstacle or difficulty your team faced.
   > Most difficult was getting the backend deployed on dokku _with the like functionality_. The front end can't work if the backend on Dokku is outdated.
7. What is your biggest concern as you think ahead to the next phase of the project? To the next sprint?
   > The biggest concern is the backend like button logic for unique users. Robert got the toggle like to work. Now we need to think in the future
   > to determine how to store a user's liked posts. Also, I believe we have relatively weak automatic tests. Lots of manual tests to see if it works.
8. How well did you estimate time during the early part of the phase? How did your time estimates change as the phase progressed?
   > We didn't estimate time at all. We initally thought the backend and front end was going to take a while, but we got it done early.
9. What aspects of the project would cause concern for your customer right now, if any?
   > Slightly poor UI and only a single like button (max like = 1, min like = 0).
