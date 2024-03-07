var describe: any;
var it: any;
var expect: any;
describe("Tests", function () {
  /*it("Adding 0 + 1 = 1", function () {
        var foo = 0;
        foo++;
        expect(foo).toEqual(1);
    });
    it("Subtracting 0 - 1 = -1", function () {
        var foo = 0;
        foo--;
        expect(foo).toEqual(-1);
    });*/
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
