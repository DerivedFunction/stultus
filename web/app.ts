/** Prevent compiler errors when using jQuery by
 * setting $ to any
 */
var $: any;

/** Global variable to be referenced for newEntryForm */
var newEntryForm: NewEntryForm;

/**
 * Backend server link to dokku
 */
const backendUrl = "https://team-stultus.dokku.cse.lehigh.edu"; //"https://2024sp-tutorial-del226.dokku.cse.lehigh.edu";
/**
 * Component name to fetch resources
 */
const componentName = "messages";
/**
 * NewEntryForm has all the code for the form for adding an entry
 */
class NewEntryForm {
  /**
   * Intialize the object  setting buttons to do actions
   * when clicked
   */
  constructor() {
    document.getElementById("addCancel")?.addEventListener("click", (e) => {
      newEntryForm.clearForm();
    });
    document.getElementById("addButton")?.addEventListener("click", (e) => {
      newEntryForm.submitForm();
    });
  }

  /**
   * Clear the input fields
   */
  clearForm() {
    (<HTMLInputElement>document.getElementById("newTitle")).value = "";
    (<HTMLInputElement>document.getElementById("newMessage")).value = "";
    // reset the UI
    (<HTMLElement>document.getElementById("editElement")).style.display =
      "none";
    (<HTMLElement>document.getElementById("addElement")).style.display = "none";
    (<HTMLElement>document.getElementById("showElements")).style.display =
      "block";
  }

  /**
   * Check if input is valid before submitting with AJAX call
   */
  submitForm() {
    window.alert("Submit form called");
    // Get the values of the fields and convert to strings
    // Check for bad input
    let title =
      "" + (<HTMLInputElement>document.getElementById("newTitle")).value;
    let msg =
      "" + (<HTMLInputElement>document.getElementById("newMessage")).value;
    if (title === "" || msg === "") {
      window.alert("Error: title/msg is not valid");
      return;
    }

    // do a POST (create) and do onSubmitResponse
    const doAjax = async () => {
      await fetch(`${backendUrl}/${componentName}`, {
        method: "POST",
        body: JSON.stringify({
          // from backend
          mTitle: title, //mTitle
          mMessage: msg,
        }),
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
      })
        .then((response) => {
          if (response.ok) {
            // Return the json after ok message
            return Promise.resolve(response.json());
          } else {
            window.alert(
              `The server replied not ok: ${response.status}\n` +
                response.statusText
            );
          }
          return Promise.reject(response);
        })
        .then((data) => {
          newEntryForm.onSubmitResponse(data);
          mainList.refresh();
          console.log(data);
        })
        .catch((error) => {
          console.warn("Something went wrong", error);
          window.alert("Unspecified error");
        });
    };
    // post AJAX values to console
    doAjax().then(console.log).catch(console.log);
  }

  /**
   * Runs when AJAX call in submitForm() returns a result
   * @param data obj returned by server
   */
  private onSubmitResponse(data: any) {
    if (data.mStatus === "ok") {
      // Sucess, clear for for new entry
      newEntryForm.clearForm();
    } else if (data.mStatus === "error") {
      // Handle error w/ msg
      window.alert("The server replied with error:\n" + data.mMessage);
    } else {
      // others
      window.alert("Unspecified error");
    }
  }
}

/** Global variable to be referenced for ElementList */
var editEntryForm: EditEntryForm;

/**
 * EditEntryForm contains all code for editing an entry
 */
class EditEntryForm {
  /**
   * Intialize the object b setting buttons to do actions
   * when clicked
   */
  constructor() {
    document.getElementById("editCancel")?.addEventListener("click", (e) => {
      editEntryForm.clearForm();
    });
    document.getElementById("editButton")?.addEventListener("click", (e) => {
      editEntryForm.submitForm();
    });
  }
  init(data: any) {
    // Fill the edit form when we receive ok
    if (data.mStatus === "ok") {
      (<HTMLInputElement>document.getElementById("editTitle")).value =
        data.mData.mSubject;
      (<HTMLInputElement>document.getElementById("editMessage")).value =
        data.mData.mMessage;
      (<HTMLInputElement>document.getElementById("editId")).value =
        data.mData.mId;
      (<HTMLInputElement>document.getElementById("editCreated")).value =
        data.mData.mCreated;
    } else if (data.mStatus === "error") {
      window.alert("The server replied with an error:\n" + data.mMessage);
    }
    // Handle other errors with a less-detailed popup message
    else {
      window.alert("Unspecified error");
    }
    // show the edit form
    (<HTMLElement>document.getElementById("editElement")).style.display =
      "block";
    (<HTMLElement>document.getElementById("addElement")).style.display = "none";
    (<HTMLElement>document.getElementById("showElements")).style.display =
      "none";
  }

  /**
   * Clear the form's input fields
   */
  clearForm() {
    (<HTMLInputElement>document.getElementById("editTitle")).value = "";
    (<HTMLInputElement>document.getElementById("editMessage")).value = "";
    (<HTMLInputElement>document.getElementById("editId")).value = "";
    (<HTMLInputElement>document.getElementById("editCreated")).value = "";
    // reset the UI
    (<HTMLElement>document.getElementById("editElement")).style.display =
      "none";
    (<HTMLElement>document.getElementById("addElement")).style.display = "none";
    (<HTMLElement>document.getElementById("showElements")).style.display =
      "block";
  }

  /**
   * Check if the input fields are both valid, and if so, do an AJAX call.
   */
  submitForm() {
    window.alert("Submit edit form called.");
    // get the values of the two fields, force them to be strings, and check
    // that neither is empty
    let title =
      "" + (<HTMLInputElement>document.getElementById("editTitle")).value;
    let msg =
      "" + (<HTMLInputElement>document.getElementById("editMessage")).value;
    let id = "" + (<HTMLInputElement>document.getElementById("editId")).value;
    if (title === "" || msg === "" || id === "") {
      window.alert("Error: title, message, or id is not valid");
      return;
    }

    // set up an AJAX PUT.
    // When the server replies, the result will go to onSubmitResponse
    const doAjax = async () => {
      await fetch(`${backendUrl}/${componentName}/${id}`, {
        method: "PUT",
        body: JSON.stringify({
          mTitle: title, //mTitle
          mMessage: msg,
        }),
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
      })
        .then((response) => {
          // If we get an "ok" message, return the json
          if (response.ok) {
            // return response.json();
            return Promise.resolve(response.json());
          }
          // Otherwise, handle server errors with a detailed popup message
          else {
            window.alert(
              `The server replied not ok: ${response.status}\n` +
                response.statusText
            );
          }
          // return response;
          return Promise.reject(response);
        })
        .then((data) => {
          editEntryForm.onSubmitResponse(data);
          console.log(data);
        })
        .catch((error) => {
          console.warn("Something went wrong.", error);
          window.alert("Unspecified error");
        });
    };

    // make the AJAX post and output value or error message to console
    doAjax().then(console.log).catch(console.log);
  }
  /**
   * onSubmitResponse runs when the AJAX call in submitForm() returns a
   * result.
   *
   * @param data The object returned by the server
   */
  private onSubmitResponse(data: any) {
    // If we get an "ok" message, clear the form and refresh the main
    // listing of messages
    if (data.mStatus === "ok") {
      editEntryForm.clearForm();
      mainList.refresh();
    }
    // Handle explicit errors with a detailed popup message
    else if (data.mStatus === "error") {
      window.alert("The server replied with an error:\n" + data.mMessage);
    }
    // Handle other errors with a less-detailed popup message
    else {
      window.alert("Unspecified error");
    }
  }
}

/** Global variable to be referenced for ElementList */
var mainList: ElementList;

/**
 * ElementList provides a way to see the data stored in server
 */
class ElementList {
  /**
   * Refresh updates the messageList
   */
  refresh() {
    const doAjax = async () => {
      await fetch(`${backendUrl}/${componentName}`, {
        method: "GET",
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
      })
        .then((response) => {
          if (response.ok) {
            return Promise.resolve(response.json());
          } else {
            window.alert(
              `The server replied not ok: ${response.status}\n` +
                response.statusText
            );
          }
          return Promise.reject(response);
        })
        .then((data) => {
          mainList.update(data);
          console.log(data);
        })
        .catch((error) => {
          console.warn("Something went wrong", error);
          window.alert("Unspecified error");
        });
    };
    // post AJAX values to console
    doAjax().then(console.log).catch(console.log);
  }
  /**
   * Update the data
   * @param data to be updated
   */
  private update(data: any) {
    let elem_messageList = document.getElementById("messageList");

    if (elem_messageList !== null) {
      elem_messageList.innerHTML = "";

      let fragment = document.createDocumentFragment();
      let table = document.createElement("table");

      for (let i = 0; i < data.mData.length; ++i) {
        let tr = document.createElement("tr");
        let td_title = document.createElement("td");
        let td_id = document.createElement("td");
        td_title.innerHTML = data.mData[i].mSubject;
        td_id.innerHTML = data.mData[i].mId;
        tr.appendChild(td_id);
        tr.appendChild(td_title);
        tr.appendChild(this.buttons(data.mData[i].mId));
        table.appendChild(tr);
      }
      fragment.appendChild(table);
      elem_messageList.appendChild(fragment);
    }
    // Find all of the delete buttons, and set their behavior
    const all_delbtns = <HTMLCollectionOf<HTMLInputElement>>(
      document.getElementsByClassName("delbtn")
    );
    for (let i = 0; i < all_delbtns.length; ++i) {
      all_delbtns[i].addEventListener("click", (e) => {
        mainList.clickDelete(e);
      });
    }
    // Find all of the edit buttons, and set their behavior
    const all_editbtns = <HTMLCollectionOf<HTMLInputElement>>(
      document.getElementsByClassName("editbtn")
    );
    for (let i = 0; i < all_editbtns.length; ++i) {
      all_editbtns[i].addEventListener("click", (e) => {
        mainList.clickEdit(e);
      });
    }
    const all_likebtns = <HTMLCollectionOf<HTMLInputElement>>(
      document.getElementsByClassName("likebtn")
    );
    for (let i = 0; i < all_likebtns.length; ++i) {
      all_likebtns[i].addEventListener("click", (e) => {
        mainList.clickLike(e);
      });
    }
  }

  /**
   * Adds a delete, edit button to the HTML for each row
   * @param id
   * @returns a new button
   */
  private buttons(id: string): DocumentFragment {
    let fragment = document.createDocumentFragment();
    let td = document.createElement("td");

    // create edit button, add to new td, add td to returned fragment
    let btn = document.createElement("button");
    btn.classList.add("editbtn");
    btn.setAttribute("data-value", id);
    btn.innerHTML = "Edit";
    td.appendChild(btn);
    fragment.appendChild(td);

    // create delete button, add to new td, add td to returned fragment
    td = document.createElement("td");
    btn = document.createElement("button");
    btn.classList.add("delbtn");
    btn.setAttribute("data-value", id);
    btn.innerHTML = "Delete";
    td.appendChild(btn);
    fragment.appendChild(td);

    td = document.createElement("td");
    btn = document.createElement("button");
    btn.classList.add("likebtn");
    btn.setAttribute("data-value", id);
    btn.innerHTML = "Like";
    td.appendChild(btn);
    fragment.appendChild(td);

    return fragment;
  }

  /**
   * Delete the item off the table
   * @param e to be deleted
   */
  private clickDelete(e: Event) {
    const id = (<HTMLElement>e.target).getAttribute("data-value");

    const doAjax = async () => {
      await fetch(`${backendUrl}/${componentName}/${id}`, {
        // Grab the element from "database"
        method: "DELETE",
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
      })
        .then((response) => {
          if (response.ok) {
            return Promise.resolve(response.json());
          } else {
            window.alert(
              `The server replied not ok: ${response.status}\n` +
                response.statusText
            );
          }
          return Promise.reject(response);
        })
        .then((data) => {
          mainList.refresh();
          console.log(data);
        })
        .catch((error) => {
          console.warn("Something went wrong. ", error);
          window.alert("Unspecified error");
        });
    };
    // post AJAX values to console
    doAjax().then(console.log).catch(console.log);
  }

  private clickLike(e: Event) {
    const id = (<HTMLElement>e.target).getAttribute("data-value");

    const doAjax = async () => {
      await fetch(`${backendUrl}/${componentName}/${id}`, {
        // Grab the element from "database"
        method: "PUT",
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
      })
        .then((response) => {
          if (response.ok) {
            return Promise.resolve(response.json());
          } else {
            window.alert(
              `The server replied not ok: ${response.status}\n` +
                response.statusText
            );
          }
          return Promise.reject(response);
        })
        .then((data) => {
          mainList.refresh();
          console.log(data);
        })
        .catch((error) => {
          console.warn("Something went wrong. ", error);
          window.alert("Unspecified error");
        });
    };
    // post AJAX values to console
    doAjax().then(console.log).catch(console.log);
  }

  /**
   * clickEdit is the code we run in response to a click of a delete button
   */
  private clickEdit(e: Event) {
    // as in clickDelete, we need the ID of the row
    const id = (<HTMLElement>e.target).getAttribute("data-value");

    // Issue an AJAX GET and then pass the result to editEntryForm.init()
    const doAjax = async () => {
      await fetch(`${backendUrl}/${componentName}/${id}`, {
        method: "GET",
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
      })
        .then((response) => {
          if (response.ok) {
            return Promise.resolve(response.json());
          } else {
            window.alert(
              `The server replied not ok: ${response.status}\n` +
                response.statusText
            );
          }
          return Promise.reject(response);
        })
        .then((data) => {
          editEntryForm.init(data);
          console.log(data);
        })
        .catch((error) => {
          console.warn("Something went wrong.", error);
          window.alert("Unspecified error");
        });
    };

    // make the AJAX post and output value or error message to console
    doAjax().then(console.log).catch(console.log);
  }
}

// Run some configuration code when the web page loads
document.addEventListener(
  "DOMContentLoaded",
  () => {
    // set up initial UI state
    (<HTMLElement>document.getElementById("editElement")).style.display =
      "none";
    (<HTMLElement>document.getElementById("addElement")).style.display = "none";
    (<HTMLElement>document.getElementById("showElements")).style.display =
      "block";

    document
      .getElementById("showFormButton")
      ?.addEventListener("click", (e) => {
        (<HTMLElement>document.getElementById("addElement")).style.display =
          "block";
        (<HTMLElement>document.getElementById("showElements")).style.display =
          "none";
      });
    document
      .getElementById("refreshFormButton")
      ?.addEventListener("click", (e) => {
        mainList.refresh();
      });
    // Create the object that controls the "New Entry" form
    newEntryForm = new NewEntryForm();
    editEntryForm = new EditEntryForm();
    mainList = new ElementList();
    mainList.refresh();
    window.alert("DOMContentLoaded");
  },
  false
);
