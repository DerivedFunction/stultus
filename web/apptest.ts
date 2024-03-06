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
    (<HTMLElement>document.getElementById("showFormButton")).click();
    let msg = <HTMLInputElement>document.getElementById("newMessage");
    let title = <HTMLInputElement>document.getElementById("newTitle");
    title.value = "Stultus Frontend Test";
    msg.value = "CSE 216 Team 21";

    // See if values set works
    expect(msg.value).toEqual("CSE 216 Team 21");
    expect(title.value).toEqual("Stultus Frontend Test");
    // The add Element form (title, msg) is shown
    expect(
      (<HTMLElement>document.getElementById("addElement")).style.display
    ).toEqual("block");
    // The data table is hidden
    expect(
      (<HTMLElement>document.getElementById("showElements")).style.display
    ).toEqual("none");
    expect(
      (<HTMLElement>document.getElementById("showElements")).style.display
    ).toEqual("none");

    // Refresh the UI to main table by clicking add
    (<HTMLElement>document.getElementById("addButton")).click();
  });
});
