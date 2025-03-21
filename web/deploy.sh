TARGETFOLDER=../backend/src/main/resources

# This is the folder that we used with the Spark.staticFileLocation command
WEBFOLDERNAME=web

# step 1: make sure we have someplace to put everything.  We will delete the
#         old folder tree, and then make it from scratch
echo "deleting $TARGETFOLDER and creating an empty $TARGETFOLDER/$WEBFOLDERNAME"
rm -rf $TARGETFOLDER
mkdir $TARGETFOLDER
mkdir $TARGETFOLDER/$WEBFOLDERNAME

# there are many more steps to be done.  For now, we will just copy an HTML file
echo "copying html to $TARGETFOLDER/$WEBFOLDERNAME"
cp *.html $TARGETFOLDER/$WEBFOLDERNAME/
rm $TARGETFOLDER/$WEBFOLDERNAME/spec_runner.html
# step 2: update our npm dependencies
echo "updating npm dependencies"
npm update

# step 4: copy css files
echo "copying css to $TARGETFOLDER/$WEBFOLDERNAME"
cp *.css $TARGETFOLDER/$WEBFOLDERNAME

# step 5: compile TypeScript files
echo "Compiling typescript files"
npx tsc