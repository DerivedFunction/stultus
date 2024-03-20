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
    editTitle = <HTMLInputElement>document.getElementById("editTitle");
    editMessage = <HTMLInputElement>document.getElementById("editMessage");
    editButton = <HTMLButtonElement>document.getElementById("editButton");
    showElements = document.getElementById("showElements");
    editElements = document.getElementById("editElement");
    editCancel = document.getElementById("editCancel");
    getOriginal();
    wait(done);
  });

  //function to run before each test to allow backend to sync
  beforeEach(function (done: () => void) {
    clickEditBtn();
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

  // function  sure to wait after executing each test
  afterEach(function (done: () => void) {
    wait(done);
  });


  it("UI Test: Add button hides lists", function () {
    console.log("html function");
    (<HTMLElement>document.getElementById("showFormButton")).click();
    let msg = <HTMLInputElement>document.getElementById("newMessage");
    let title = <HTMLInputElement>document.getElementById("newTitle");
    msg.value = "Hey Howdy";
    title.value = "It's Me The Title";


    // See if values set works
    expect(msg.value).toEqual("Hey Howdy");
    expect(title.value).toEqual("It's Me The Title");
    // The add Element form (title, msg) is shown
    expect(
      (<HTMLElement>document.getElementById("addElement")).style.display
    ).toEqual("block");
    // The data table is hidden
    expect(
      (<HTMLElement>document.getElementById("showElements")).style.display
    ).toEqual("none");


    // Refresh the UI to main table by clicking add
    (<HTMLElement>document.getElementById("addButton")).click();
    // (<HTMLElement>document.getElementById("refreshFormButton")).click();
    setTimeout(function () {
      console.log("like and delete button")
      let lbtns = document.getElementsByClassName("likebtn");
      let lbtn = <HTMLButtonElement>lbtns[0];
      lbtn.click();
      let dbtns = document.getElementsByClassName("delbtn");
      let dbtn = <HTMLButtonElement>dbtns[0];
      dbtn.click();
    }, 1000);
  });
});

describe("Add Button Tests", function () {

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
