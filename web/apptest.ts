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

  beforeAll(function (done: () => void) {
  
  });

  beforeEach(function (done: () => void) {
    
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