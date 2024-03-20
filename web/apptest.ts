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

  /**
  * Function to initialize values 
  */
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

  /**
  * Function to run before each test to allow backend to sync
  */
  beforeEach(function (done: () => void) {
    clickEditBtn(); //click the button
    wait(done); //pause for viewer
  });

  /**
  * Tests if edit button works with valid title and message
  */
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

  /**
  * Check edit button works and then edit it 
  */
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

  /**
  * Function  sure to wait after executing each test
  */
  afterEach(function (done: () => void) {
    wait(done);
  });

  /**
  * Function to execute after all the tests are complete that resets the system
  */
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

  /**
  * Function to initialize values 
  */
  beforeAll(function (done: () => void) {
    newMessage = <HTMLInputElement>document.getElementById("newMessage");
    newTitle = <HTMLInputElement>document.getElementById("newTitle");
    showFormButton = document.getElementById("showFormButton");
    addElement = document.getElementById("addElement");
    showElements = document.getElementById("showElements");
    addButton = <HTMLButtonElement>document.getElementById("addButton");
    wait(done);
  });

  /**
  * Function to run before each test to allow backend to sync
  */
  beforeEach(function (done: () => void) {
    wait(done);
  });

  /**
  * Tests if add button works with valid title and message and add it
  */
  it("Add button hides lists", function (done: () => void) {
    //click add button
    showFormButton.click();

    //set message and title to global vars
    newMessage.value = title_string;
    newTitle.value = msg_string;

    // Check if the message and title is valid
    expect(newMessage.value).toEqual(title_string);
    expect(newTitle.value).toEqual(msg_string);
    // The add Element form (newTitle, Message) is shown
    expect(addElement.style.display).toEqual("block");
    // The data table is hidden
    expect(showElements.style.display).toEqual("none");
    // Refresh the UI to main table by clicking add
    addButton.click();


    // Pause
    setTimeout(function () {
      mId = document
        .getElementsByClassName("delbtn")[0]
        .getAttribute("data-value");
      done();
    }, delay);
  });
  
  /**
  * Function to wait after executing each test
  */
  afterEach(function (done: () => void) {
    wait(done);
  });

  /**
  * Function to execute after all the tests are complete that resets the system
  */
  afterAll(function (done: () => void) {
    // Replace our edited elements with the previous value
    setTimeout(function () {
      let dbtns = document.getElementsByClassName("delbtn");
      let dbtn = <HTMLButtonElement>dbtns[0];
      if (dbtn.getAttribute("data-value") === mId) {
        dbtn.click();
      }
      done();
    }, delay);
  });
});

/**
 * Test #3: 
 * Like Idea Using Like Button
*/
describe("Like Button Tests", function () {
  /**
  * Function to initialize values (There are none so just wait for backend)
  */
  beforeAll(function (done: () => void) {
    wait(done);
  });

  /**
  * Function to run before each test to allow backend to sync
  */
  beforeEach(function (done: () => void) {
    wait(done);
  });




  

  /**
  * Function to wait after executing each test
  */
  afterEach(function (done: () => void) {
    wait(done);
  });

  /**
  * Function to execute after all the tests are complete that resets the system there's nothing to reset
  */
  afterAll(function (done: () => void) {
    wait(done);
  });


});


/**
 * TEST FUNCTION: 
 * GET THE ORIGINAL MESSAGE AND TITLE STRING
*/
function getOriginal() {
  setTimeout(function () {
    originalTitle = document.getElementById("postTitle")?.textContent;
    originalMsg = document.getElementById("postBody")?.textContent;
  }, delay);
}

/**
 * TEST FUNCTION: 
 * PAUSE PROGRAM FOR 1 SECOND TO BE VIEWED BY USER/TESTER
*/
function wait(done: () => void) {
  setTimeout(function () {
    done();
  }, delay);
}

/**
 * TEST FUNCTION: 
 * CLICK EDIT BUTTON AND WAIT FOR BACKEND
*/
function clickEditBtn() {
  let ebtns = document.getElementsByClassName("editbtn");
  let ebtn = <HTMLButtonElement>ebtns[0];
  ebtn.click();
}
