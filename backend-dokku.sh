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
git read-tree web:backend
git commit -m "treat backend as root of backend-dokku"
git push origin backend-dokku

# clean up our branch tracking
git checkout master
git branch -D backend-dokku
git checkout -b backend-dokku origin/backend-dokku

# we are now ready to deploy to dokku with (-f if necessary):
git push -f dokku backend-dokku

# Go back to backend
git checkout web
