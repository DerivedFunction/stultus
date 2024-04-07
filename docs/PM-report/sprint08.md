# Phase 2 Sprint 8 - PM Report

## Team Information

### Team Information:

* Number: 21
* Name: Stultus
* Mentor: <Emir Veziroglu, env225@lehigh.edu>
* Weekly live & synchronous meeting:
    * without mentor: Normally wednesday 10:45, but not this week
    * with mentor: Tuesday at 7:15pm

### Team Roles:

* Project Manger: <Robert Kilsdonk, rok326@lehigh.edu>
    
    This has changed as all roles have rotated for the new phase
* Backend developer: <Denny Li, del226@lehigh.edu>
* Admin developer: <Thomas Chakif, thc225@lehigh.edu>
* Web developer: <Patrick Boles, pjb325@lehigh.edu>

### Essential links for this project:


  - <https://team-stultus.dokku.cse.lehigh.edu/>

- Team's software repo (bitbucket)

  - <https://bitbucket.org/sml3/cse216_sp24_team_21>

- Team's Jira board

  - <https://cse216-24sp-del226.atlassian.net/jira/software/projects/T2/boards/2>


## General questions

1. Did the PM for this week submit this report (If not, why not?)? 

Yes

2. Has the team been gathering for a weekly, in-person meeting(s)? If not, why not?

Yes, two meetings, one Wednesday, scheduled by us, and one Tuesday, with our grader.

3. Summarize how well the team met the requirements of this sprint.
We met the requirements of the sprint pretty well, though there were a couple of notable issues with regards to organization and finalization. To be specific, one member had trouble pushing to git, leading to their work not reaching master, and there was one part of the ERD that we did not decide on, how to handle comments.
4. Report on each member's progress (sprint and phase activity completion) – "what is the status?"

Denny Li, working on backend, was asked to create documents in relation to his role, especially the ERD and route table. This was mostly completed, but we did not decide on how to cover comments, and so they do not show up in the database.

Thomas Chakif did all of his work in his git repository, but it was based in an out of date version of master, so he had trouble merging in his changes. 

Patrick Boles is completely good, with all of the requests to him done, and all pushed to bitbucket.

5. Summary of "code review" during which each team member discussed and showed their progress – "how did you confirm the status?"

I reviewed all changes to the shared documents asynchronously, with people describing what changes they made either in pull request messages or on slack, and them me reading them and handling merges either via bitbucket or by bringing the changes to my local machine and using git merge. I also checked the final state myself through testing, (in this case just by looking at the document), and by communicating the results of these reviews in person. 

6. What did you do to encourage the team to be working on phase activities "sooner rather than later"?

This sprint was so short that there was not much to do for this, as there were only three days, and it took me the first to get organized. We just started work in class time, and continued during our meeting.

7. What did you do to encourage the team to help one another?

Generally, this was done by assigning tasks to design and make documents based on areas of responsibility, so that my teammates could work out together how their parts ought to work between the technical stakeholders.

8. How well is the team communicating?

The team is communicating pretty well, with good communication in person about who is doing what, but this is not translating to jira, where I am needing to document these conversations. Also, things are doing well on slack, with information moving as needed. There have not been delays, but the communication is mostly buisnesslike.

9. Discuss expectations the team has set for one another, if any. Please highlight any changes from last week.

One expectation that I tried to set, which is for the convience of code reviews, is that code reviews are requested more often on smaller pieces of code, potentially from branches made from specific commits instead of being all changes on a branch since the last merge, as otherwise some of the changes were a bit difficult to keep track of, especially with which commit they are related to 

The rest I cannot tease out, but there is the expectation that videos come in a little before the deadline, which was pre-existing, and that people say what they are working on.

10. If anything was especially challenging or unclear, please make sure this is [1] itemized, [2] briefly described, [3] its status reported (resolved or unresolved), and [4] includes critical steps taken to find resolution.

    * Challenge: Merge Conficts
        * Status : Resolved Thursday
        * Description : Thomas ran into merge conflicts trying to push his changes from his local device to the bitbucket repository, which were complicated by the fact that we added the same binary file twice in different branches, which conflicted merging. I am not sure what else was going on, but once we were able to force a push to a test branch so that I had a copy of his working tree, I was able to resolve the conflicts and merge in his changes. 

## Project Management
Self-evaluation of PM performance

0. When did your team meet with your mentor, and for how long?

We met with Emir for 30 minutes to an hour on Tuesday, where we went over a few questions about requirements. 

1. Describe your use of Jira.  Did you have too much detail?  Too little?  Just enough? Did you implement policies around its use (if so, what were they?)?

Our use of Jira had just about the right amount of detail, with each issue being a particular size such that it was the smallest individible unit of work that would not rely overmuch on other pieces, and not so large that it made assignment awkwards. The only problem was that Jira tended to lag other communication, with people saying things but not making the same chages on Jira, in part because I was making those changes before they could. 

2. How did you conduct team meetings?  How did your team interact outside of these meetings?

We did conduct a single team meeting, and worked in slack before and after then. 

3. What techniques (daily check-ins/scrums, team programming, timelines, Jira use, slack use, group design exercises) did you use to mitigate risk? Highlight any changes from last week.

There were a few techniques that we used. The main one was delegation and pairing. Specifically, some tasks were assigned for two people to do together. We also used Jira to track who is responsible for what.

4. Describe any difficulties you faced in managing the interactions among your teammates. Were there any team issues that arose? If not, what do you believe is keeping thins so constructive?

There were not any significant issues for interaction this sprint. This is mainly due to the fact that we are working on things apart in many cases, and are good at communicating what we need. 

5. What is your biggest concern as you think ahead to the next sprint?

The biggest concern for the next sprint is that most things are waiting on OAuth Implementation, and so they are coupled. Also, I don't fully get OAuth, but have to make decisions about it. 

6. Describe the most significant obstacle or difficulty your team faced.

The two most significant difficulty was the merging conflicts, but that was covered elsewhere, so I will discuss the second greatest challenge, which was coordination. Specifically, figuring out what ought to carry over from last phase, and what changes were needed, especially as these things depend on OAuth's details, which we still have not fully understood. 

7. What might you suggest the team or the next PM "start", "stop", or "continue" doing in the next sprint?

One thing that I suggest should continue is the communication about tasks and the practice of creating pull requests often. One thing that should start is more meetings, at least trying to get the Wednesday meeting more consistent. 
One thing that should stop is that we often take to the second day to start delegating tasks, when we should try to start day one. 

8. How well did you estimate time during the early part of the sprint? How did your time estimates change as the sprint progressed?

Our time estimates for most things were good, except for the actual editing of the sprint video, which took me longer than I thought it would, and the merge conflict, which I knew how to resolve easily but which took Thomas significant time. 

9. What aspects of the project would cause concern for your customer right now, if any?

The lack of preparations for handling comments appears to be of concern, as well as the development of OAuth features. 
