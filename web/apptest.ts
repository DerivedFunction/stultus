
/** 
 * Global vars
 * @type {function}
*/
var describe: any; 

/** 
 * Testis Functionality
 * @type {function}
*/
var it: any;

/** 
 * Global vars
 * @type {function}
*/
var expect: any;

/** 
 * Allows backend to sync
 * @type {function}
*/
var beforeEach: any;

/** 
 * Initializes values
 * @type {function}
*/
var beforeAll: any;

/** 
 * Executes after all the tests are complete that resets the system
 * @type {function}
*/
var afterAll: any;

/** 
 * Waiting after executing each test
 * @type {function}
*/
var afterEach: any;


/** 
 * Delay of one second to be used to make the tests viewable 
 * @type {number}
*/
const delay = 1000;

/** 
 * Backup for original title element
 * @type {HTMLElement}
*/
let originalTitle: any;
/** 
 * Backup for original message element
 * @type {HTMLElement}
*/
let originalMsg: any;


/**
 * Test #1: 
 * Edit Preexisting Idea Using The Edit Functionality
 * @type {function(){}}
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
  let dislikeButton: any;

  /**
  * Function to initialize values 
  * @type {function(){}}
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
  * @type {function(){}}
  */
  beforeEach(function (done: () => void) {
    clickEditBtn(); //click the button
    wait(done); //pause for viewer
  });

  /**
  * Tests if edit button works with valid title and message
  * @type {function(){}}
  */
  it("Edit success", function (done: () => void) {
    // fill in the text boxes
    editMessage.value = msg_string;
    editTitle.value = title_string;


    // See if idea values work
    expect(editMessage.value).toEqual(msg_string);
    expect(editTitle.value).toEqual(title_string);


    // The edit Element form (title, msg) is shown
    expect(editElements.style.display).toEqual("block");
    // The data table is hidden
    expect(showElements.style.display).toEqual("none");


    // PUT our changes by clicking the submit edit button
    editButton.click();
    done();
  });

  /**
  * Check edit button works and then edit it 
  * @type {function(){}}
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


    // PUT our changes by clicking the submit edit button
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
  * @type {function(){}}
  */
  afterEach(function (done: () => void) {
    wait(done);
  });

  /**
  * Function to execute after all the tests are complete that resets the system
  * @type {function(){}}
  */
  afterAll(function (done: () => void) {
    clickEditBtn(); //click button to get edit screen to appear

    // Replace our edited elements with the previous value
    setTimeout(function () {
      editMessage.value = originalMsg; //get old message before edits
      editTitle.value = originalTitle; //get old title before edts 
      editButton.click(); // PUT our changes by clicking the submit edit button
      done();
    }, delay);
  });
});




/**
 * Test #2: 
 * Create New Idea Using Add Button Functionality
 * @type {function(){}}
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
  * @type {function(){}}
  */
  beforeAll(function (done: () => void) {
    newMessage = <HTMLInputElement>document.getElementById("newMessage"); //find where the message box is
    newTitle = <HTMLInputElement>document.getElementById("newTitle"); //find where the title box is
    showFormButton = document.getElementById("showFormButton"); //find where the add button is
    addElement = document.getElementById("addElement"); //find where the submit button is
    showElements = document.getElementById("showElements"); //find where cancel button is
    addButton = <HTMLButtonElement>document.getElementById("addButton"); //find where the add button is 
    wait(done); //wait for backend
  });

  /**
  * Function to run before each test to allow backend to sync
  * @type {function(){}}
  */
  beforeEach(function (done: () => void) {
    wait(done);
  });

  /**
  * Tests if add button works with valid title and message and add it
  * @type {function(){}}
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


    // 
    setTimeout(function () {
      mId = document
        .getElementsByClassName("delbtn")[0]
        .getAttribute("data-value");
      done();
    }, delay);
  });
  
  /**
  * Function to wait after executing each test
  * @type {function(){}}
  */
  afterEach(function (done: () => void) {
    wait(done);
  });

  /**
  * Function to execute after all the tests are complete that resets the system
  * @type {function(){}}
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
 * @type {function(){}}
 * 
*/
describe("Like Button Tests", function () {
  /**
  * Function to initialize values (There are none so just wait for backend)
  * @type {function(){}}
  */
  beforeAll(function (done: () => void) {
    wait(done);
  });

  /**
  * Function to run before each test to allow backend to sync
  * @type {function(){}}
  */
  beforeEach(function (done: () => void) {
    wait(done);
  });


  /**
  * Tests if like button works
  * @type {function(){}}
  */
  it("Check like button", function (done: () => void) {
    // Grab the first like value in the first element
    let oldLike = document.getElementsByTagName("td")[0].textContent;


    // Click the like button
    let lbtns = document.getElementsByClassName("likebtn");
    let lbtn = <HTMLButtonElement>lbtns[0];
    lbtn.click();

    //check if everything works
    setTimeout(function () {
      // The like value should be different after a short delay
      expect(oldLike).not.toEqual(
        document.getElementsByTagName("td")[0].textContent
      );


      // Click the like button again (assuming it toggles the like state)
      lbtn.click();


      // Wait for another short period for the like count to update
      setTimeout(function () {
        expect(oldLike).toEqual(
          document.getElementsByTagName("td")[0].textContent
        );


        done();
      }, delay);
    }, delay);
  });

  /**
  * Function to wait after executing each test
  * @type {function(){}}
  */
  afterEach(function (done: () => void) {
    wait(done);
  });

  /**
  * Function to execute after all the tests are complete that resets the system there's nothing to reset
  * @type {function(){}}
  */
  afterAll(function (done: () => void) {
    wait(done);
  });


});


/**
 * TEST FUNCTION: 
 * GET THE ORIGINAL MESSAGE AND TITLE STRING
 * @type {function(){}}
*/
function getOriginal() {
  setTimeout(function () {
    originalTitle = document.getElementById("postTitle")?.textContent; //get title of original post
    originalMsg = document.getElementById("postBody")?.textContent; //get message of original post
  }, delay);
}

/**
 * TEST FUNCTION: 
 * PAUSE PROGRAM FOR 1 SECOND TO BE VIEWED BY USER/TESTER
 * @type {function(){}}
*/
function wait(done: () => void) {
  setTimeout(function () {
    done(); //finish after pause
  }, delay); //pause 
}

/**
 * TEST FUNCTION: 
 * CLICK EDIT BUTTON AND WAIT FOR BACKEND
 * @type {function(){}}
*/
function clickEditBtn() {
  let ebtns = document.getElementsByClassName("editbtn"); //get edit button
  let ebtn = <HTMLButtonElement>ebtns[0];
  ebtn.click(); //click button
}
