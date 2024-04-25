/**
 * Global vars
 * @type {function}
 */
let describe: any;

/**
 * Testing Functionality
 * @type {function}
 */
let it: any;

/**
 * Global vars
 * @type {function}
 */
let expect: any;

/**
 * Allows backend to sync
 * @type {function}
 */
let beforeEach: any;

/**
 * Initializes values
 * @type {function}
 */
let beforeAll: any;

/**
 * Executes after all the tests are complete that resets the system
 * @type {function}
 */
let afterAll: any;

/**
 * Waiting after executing each test
 * @type {function}
 */
let afterEach: any;

/**
 * Delay of one second to be used to make the tests viewable
 * @type {number}
 */
const delay = 1750;

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
 * @type {HTMLInputElement} - find where the message box is
 */
let newMessage = <HTMLInputElement>document.getElementById("newMessage");

/**
 * @type {HTMLInputElement} - find where the title box is
 */
let newTitle = <HTMLInputElement>document.getElementById("newTitle");

/**
 * @type {HTMLButtonElement} - find where the add button is
 */
let showFormButton = <HTMLButtonElement>(
  document.getElementById("showFormButton")
);

/**
 * @type {HTMLDivElement} - find where the submit button is
 */
let addElement = <HTMLDivElement>document.getElementById("addElement");

/**
 * @type {HTMLButtonElement} - find where the submit button is
 */
let addCancel = <HTMLButtonElement>document.getElementById("addCancel");

/**
 * @type {HTMLDivElement} - find where cancel button is
 */
let showElements = <HTMLDivElement>document.getElementById("showElements");

/**
 * @type {HTMLButtonElement} - find where the add button is
 */
let addButton = <HTMLButtonElement>document.getElementById("addButton");

/**
 * @type {HTMLInputElement} - find where the title box is
 */
let editTitle = <HTMLInputElement>document.getElementById("editTitle");

/**
 * @type {HTMLInputElement} - find where the message box is
 */
let editMessage = <HTMLInputElement>document.getElementById("editMessage");

/**
 * @type {HTMLButtonElement} - find where the edit button is
 */
let editButton = <HTMLButtonElement>document.getElementById("editButton");

/**
 * @type {HTMLDivElement} - find where the edit elements div in the index is
 */
let editElements = <HTMLDivElement>document.getElementById("editElement");

/**
 * @type {HTMLButtonElement} - find where the cancel button is
 */
let editCancel = <HTMLButtonElement>document.getElementById("editCancel");

/**
 * @type {HTMLButtonElement} - find where showing profile button is
 */
let showProfileButton = <HTMLButtonElement>(
  document.getElementById("showProfileButton")
);

/**
 * @type {HTMLDivElement} - find where showing profile container is
 */
let profileForm = <HTMLDivElement>document.getElementById("profileForm");

/**
 * @type {HTMLButtonElement} - find where canceling profile button is
 */
let profileCancel = <HTMLButtonElement>document.getElementById("UserCancel");

/**
 * @type {HTMLDivElement} - find comment container
 */
let showComment = <HTMLDivElement>document.getElementById("showComment");

/**
 * @type {HTMLButtonElement} - find where canceling comment button is
 */
let commentCancel = <HTMLButtonElement>document.getElementById("commentCancel");

/**
 * @type {string} - Message string for adding
 */
let msg_string = "Adding msg";

/**
 * @type {string} - Title string for adding
 */
let title_string = "Adding title";

/**
 * Test #2:
 * Create New Idea Using Add/Edit Button Functionality
 * @type {function(){}}
 */
describe("Add Button Tests", function () {
  let mId: any;
  /**
   * Function to initialize values
   * @type {function(){}}
   */
  beforeAll(function (done: () => void) {
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
  it("Add button", function (done: () => void) {
    //click add button
    testAdd();
    function testAdd() {
      showFormButton.click();
      // The add Element form (newTitle, Message) is shown
      expect(addElement.style.display).toEqual("block");
      // The data table is hidden
      expect(showElements.style.display).toEqual("none");
      // Refresh the UI to main table by clicking add
      addCancel.click();
      done();
    }
  });

  /**
   * Tests if add button works with valid title and message and add it
   * @type {function(){}}
   */
  it("Add button fail on empty", function (done: () => void) {
    //click add button
    showFormButton.click();
    // The add Element form (newTitle, Message) is shown
    expect(addElement.style.display).toEqual("block");
    // The data table is hidden
    expect(showElements.style.display).toEqual("none");
    // Refresh the UI to main ta  ble by clicking add
    addButton.click();
    // The data table is hidden
    expect(showElements.style.display).toEqual("none");
    //
    addCancel.click();
    done();
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
describe("Vote Button Tests", function () {
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
  it("Check upvote button", function (done: () => void) {
    // Grab the first like value in the first element
    let oldLike = document.getElementsByClassName("totalVote")[0].textContent;

    // Click the like button
    let lbtns = document.getElementsByClassName("likebtn");
    let lbtn = <HTMLButtonElement>lbtns[0];
    lbtn.click();

    //check if everything works
    setTimeout(function () {
      // The like value should be different after a short delay
      expect(oldLike).not.toEqual(
        document.getElementsByClassName("totalVote")[0].textContent
      );

      // Click the like button again (assuming it toggles the like state)
      lbtn.click();

      // Wait for another short period for the like count to update
      setTimeout(function () {
        expect(oldLike).toEqual(
          document.getElementsByClassName("totalVote")[0].textContent
        );
        done();
      }, delay);
    }, delay);
  });

  /**
   * Tests if downvote button works
   * @type {function(){}}
   */
  it("Check downvote button", function (done: () => void) {
    // Grab the first like value in the first element
    let oldLike = document.getElementsByClassName("totalVote")[0].textContent;

    // Click the like button
    let lbtns = document.getElementsByClassName("dislikebtn");
    let lbtn = <HTMLButtonElement>lbtns[0];
    lbtn.click();

    //check if everything works
    setTimeout(function () {
      // The like value should be different after a short delay
      expect(oldLike).not.toEqual(
        document.getElementsByClassName("totalVote")[0].textContent
      );

      // Click the like button again (assuming it toggles the like state)
      lbtn.click();

      // Wait for another short period for the like count to update
      setTimeout(function () {
        expect(oldLike).toEqual(
          document.getElementsByClassName("totalVote")[0].textContent
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
 * Test #4:
 * Click user profile
 * @type {function(){}}
 *
 */
describe("User profile test", function () {
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
   * Function to check if the profile function is working without submitting any changes
   * @type {function(){}}
   */
  it("Click on my profile", function (done: () => void) {
    showProfileButton.click();
    expect(profileForm.style.display).toEqual("block");
    expect(showElements.style.display).toEqual("none");
    profileCancel.click();
    expect(showElements.style.display).toEqual("block");
    wait(done);
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
 * Test #5:
 * Click comments
 * @type {function(){}}
 *
 */
describe("Comment test", function () {
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
   * Function to check if the comment function is working without submitting any comments
   * @type {function(){}}
   */
  it("Comment a post", function (done: () => void) {
    let commentbtn = <HTMLButtonElement>(
      document.getElementsByClassName("commentbtn")[0]
    );
    commentbtn.click();
    setTimeout(function () {
      expect(showComment.style.display).toEqual("block");
      expect(showElements.style.display).toEqual("none");
      commentCancel.click();
      expect(showElements.style.display).toEqual("block");
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
   * Function to execute after all the tests are complete that resets the system there's nothing to reset
   * @type {function(){}}
   */
  afterAll(function (done: () => void) {
    wait(done);
  });
});

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
