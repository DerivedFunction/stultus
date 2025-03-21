# CSE 216 Team 21 Description

_Stultus: A gathering for fools_

Team Stultus is proud to create a message board for people to post, share, and view each
other's ideas. All relavant information about new updates and releases are in [`docs`](docs).

## Details

- Semester: Spring 2024
- Team Number: 21
- Team Name: stultus

## Team Members
**Patrick Boles**, **Robert Kilsdonk**, **Thomas Chakif**, **Denny Li**

### How to build and run on Dokku

1. Navigate to [`backend`](backend) branch
2. Create the requisite `Procfile`
3. Create an executable `.jar` file with dependencies and manifest.
4. Setup a new git branch `backend-dokku` for dokku.

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

# go back to backend branch
git checkout backend

```

### Dokku configurations

- `POSTGRES_URL`: <postgres://***:***@****/***>
- `POSTGRES_PORT`: 5432
- `PORT`: 8998
- `NUM_TESTS`: 3
- `CLIENT_ID`: \*\*\*\*
- `CLIENT_SECRET`: \*\*\*\*
- `AUTH`: (`1` = need verification, `0` = no verification)
- `SAME_DOMAIN`: `true` or `false` if only certain domains are allowed
- `logging`: `true` or `false` if extra logging information is necessary
- `SESSION_LENGTH`: length of user session randomization string

### Useful Dokku commands

`ssh -i ~/.ssh/id_ed25519 -t ***@***.lehigh.edu '$COMMAND team-stultus'`

- `ps:start`: start app
- `ps:stop`: stop app
- `ps: restart`: restart app
- `logs`: view logs
- `config:export`: view environment variables
- `config:set ... VAR=ARGS`: Set environment variables
