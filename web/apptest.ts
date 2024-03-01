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
    title.value = "Denny Li's Test";
    msg.value = "CSE 216 from Denny Li";

    // See if values set works
    expect(msg.value).toEqual("CSE 216 from Denny Li");
    expect(title.value).toEqual("Denny Li's Test");
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
    (<HTMLElement>document.getElementById("refreshFormButton")).click();
  });
});
