//global vars
var describe: any; 
var it: any;
var expect: any;
var beforeEach: any;
var beforeAll: any;
var afterAll: any;
var afterEach: any;

//delay of one second to be used to make the tests viewable 
const delay = 1000;

//backup for original message and title elements
let originalTitle: any;
let originalMsg: any;


/**
 * Test #1: 
 * Edit Preexisting Idea Using The Edit Functionality
*/
describe("Edit Tests", function () {

  //post/idea values
  let msg_string = "Testing msg";
  let title_string = "Testing title";

  // Our HTML elements
  let editTitle: any;
  let editMessage: any;
  let editButton: any;
  let editElements: any;
  let showElements: any;
  let editCancel: any;

  //function to initialize values 
  beforeAll(function (done: () => void) {
    editTitle = <HTMLInputElement>document.getElementById("editTitle"); //find where the title box is
    editMessage = <HTMLInputElement>document.getElementById("editMessage"); //find where the message box is
    editButton = <HTMLButtonElement>document.getElementById("editButton"); //find where the edit button is 
    showElements = document.getElementById("showElements"); //find where the show elements procedure in app.ts is
    editElements = document.getElementById("editElement"); //find where the edit elements div in the index is 
    editCancel = document.getElementById("editCancel"); //find where the cancel button is
    getOriginal();
    wait(done); //pause for viewer
  });

  //function to run before each test to allow backend to sync
  beforeEach(function (done: () => void) {
    clickEditBtn(); //click the button
    wait(done); //pause for viewer
  });

  // Tests if edit button works with valid title and message
  it("Edit success", function (done: () => void) {
    // fill in the text boxes
    editMessage.value = msg_string;
    editTitle.value = title_string;


    // See if values set works
    expect(editMessage.value).toEqual(msg_string);
    expect(editTitle.value).toEqual(title_string);


    // The edit Element form (title, msg) is shown
    expect(editElements.style.display).toEqual("block");
    // The data table is hidden
    expect(showElements.style.display).toEqual("none");


    // PUT our changes.
    editButton.click();
    done();
  });

  it("Edit fail: blank", function (done: () => void) {
    // Fill out text boxes, but leave the title blank
    editMessage.value = msg_string;
    editTitle.value = "";


    // See if values set works
    expect(editMessage.value).toEqual(msg_string);
    expect(editTitle.value).toEqual("");


    // The edit Element form (title, msg) is shown
    // Because we failed (empty title)
    expect(editElements.style.display).toEqual("block");
    // The data table is hidden
    expect(showElements.style.display).toEqual("none");


    // PUT our changes.
    editButton.click();

    setTimeout(function () {
      // The edit Element form (title, msg) is still shown
      expect(editElements.style.display).toEqual("block");
      // Cancel it
      editCancel.click();
      done();
    }, delay);
  });

  // function  sure to wait after executing each test
  afterEach(function (done: () => void) {
    wait(done);
  });

  // function to execute after all the tests are complete that resets the system
  afterAll(function (done: () => void) {
    clickEditBtn();
    // Replace our edited elements with the previous value
    setTimeout(function () {
      editMessage.value = originalMsg;
      editTitle.value = originalTitle;
      editButton.click();
      done();
    }, delay);
  });
});




/**
 * Test #2: 
 * Create New Idea Using Add Button Functionality
*/
describe("Add Button Tests", function () {
  //initialize data 
  let title_string = "Hey Howdy"; //Title To be Added
  let msg_string = "It's Me The Title"; //Message To Be Added (Don't ask why they are reversed)
  
  //initialize html elements  
  let newMessage: any;
  let newTitle: any;
  let showFormButton: any;
  let addElement: any;
  let showElements: any;
  let addButton: any;
  let mId: any;
});

describe("Like Button Tests", function () {

});


// Get original content from first element
function getOriginal() {
  setTimeout(function () {
    originalTitle = document.getElementById("postTitle")?.textContent;
    originalMsg = document.getElementById("postBody")?.textContent;
  }, delay);
}


// Add a small delay between each Jasmine test.
function wait(done: () => void) {
  setTimeout(function () {
    done();
  }, delay);
}


/**
 *  Click the edit button for the most recent element
 *  After this, we will need to wait for backend
 *  to send data over to front end.
 */
function clickEditBtn() {
  let ebtns = document.getElementsByClassName("editbtn");
  let ebtn = <HTMLButtonElement>ebtns[0];
  ebtn.click();
}
