# CSE 216 Team 21 Description

_Stultus: A gathering for fools_

Team Stultus is proud to create a message board for people to post, share, and view each
other's ideas. All relavant information about new updates and releases are in [`docs`](docs).

## Details

- Semester: Spring 2024
- Team Number: 21
- Team Name: stultus
- Bitbucket Repository:
  - On the web: <https://bitbucket.org/sml3/cse216_sp24_team_21>
  - SSH: `git clone git@bitbucket.org:sml3/cse216_sp24_team_21.git`
  - https: `git clone https://del226@bitbucket.org/sml3/cse216_sp24_team_21.git`
- Jira Link: <https://cse216-24sp-del226.atlassian.net/jira/software/projects/T2/boards/2>
- Backend URL: <https://team-stultus.dokku.cse.lehigh.edu/>

## Team Members

- Project Manger: **Robert Kilsdonk**, [rok326@lehigh.edu](mailto:rok326@lehigh.edu)
- Admin: **Patrick Boles**, [pjb325@lehigh.edu](mailto:pjb325@lehigh.edu)
- Backend: **Denny Li**, [del226@lehigh.edu](mailto:del226@lehigh.edu)
- Front End:  **Thomas Chakif**, [thc225@lehigh.edu](mailto:thc225@lehigh.edu)

### How to build and run on Dokku

1. Navigate to [`backend`](backend) branch
2. Create the requisite `Procfile`
3. Create an executable `.jar` file with dependencies and manifest.
4. Setup a new git branch `backend-dokku` for dokku.

[CSE 216 Dokku tutorial link](https://www.cse.lehigh.edu/~stevelu/spear-tutorials/viewer.html#cse216_dokku/tut.md)

```bash
# Delete the backend-dokku branch from remote and locally
git stash
git push origin --delete backend-dokku
git branch -D backend-dokku
git push origin --prune
# the following is to be done on your **local** host
# start from the root folder of the master branch
git checkout master

# to start the `backend-dokku` branch
true | git mktree | xargs git commit-tree | xargs git branch backend-dokku

# to fetch into the backend-dokku branch anything you want, for e.g. the backend directory from the backend branch:

git checkout backend-dokku
git read-tree backend:backend
git commit -m "treat backend as root of backend-dokku"
git push origin backend-dokku

# clean up our branch tracking
git checkout master
git branch -D backend-dokku
git checkout -b backend-dokku origin/backend-dokku

# we are now ready to deploy to dokku with (-f if necessary):
git push -f dokku backend-dokku

```

### Dokku configurations

- POSTGRES URL: <postgres://***:***@salt.db.elephantsql.com/***>
- POSTGRES Port: 5432
- PORT: 8998
- NUM_TESTS: 5

### Useful Dokku commands

`ssh -i ~/.ssh/id_ed25519 -t dokku@dokku.cse.lehigh.edu '$COMMAND team-stultus'`

- `ps:start`: start app
- `ps:stop`: stop app
- `ps: restart`: restart app
- `logs`: view logs
- `config:export`: view environment variables
- `config:set ... $ARGS`: Set environment variables
