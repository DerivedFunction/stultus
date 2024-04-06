/** Prevent compiler errors when using jQuery by test
 * setting $ to any
 * @type any
 */
var $: any;

/** Global variable to be referenced for newEntryForm 
 * @type {Object.<string, string>}
*/
var newEntryForm: NewEntryForm;

/**
 * Backend server link to dokku
 * @type {string}
 */
const backendUrl = "https://team-stultus.dokku.cse.lehigh.edu"; //"https://2024sp-tutorial-del226.dokku.cse.lehigh.edu";

/**
 * Component name to fetch resources
 * @type {string}
 */
const componentName = "messages";

interface UserProfile {
  username: string;
  email: string;
  sexualIdentity: string;
  genderOrientation: string;
  note: string;
}
/**
 * NewEntryForm has all the code for the form for adding an entry
 * @type {class NewEntryForm}
 */
class NewEntryForm {

  /**
   * Intialize the object  setting buttons to do actions when clicked
   * @return {NewEntryForm}
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
   * @type {function}
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
   * @type {function}
   */
  submitForm() {
    console.log("Submit form called");
    // Get the values of the fields and convert to strings
    // Check for bad input
    let title =
      "" + (<HTMLInputElement>document.getElementById("newTitle")).value;
    let msg =
      "" + (<HTMLInputElement>document.getElementById("newMessage")).value;
    if (title === "" || msg === "") {
      InvalidContentMsg();
      console.log("Error: title/msg is not valid");
      return;
    }
    /**
    * do a POST (create) and do onSubmitResponse
    * @type {function}
    */
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
            console.log(
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
          console.log("Unspecified error");
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
      console.log("The server replied with error:\n" + data.mMessage);
    } else {
      // others
      console.log("Unspecified error");
    }
  }
  updateUserProfile(data: UserProfile) {
    const userId = "user_id"; //needs to be updated
    fetch(`${backendUrl}/user/profile/${userId}`, {
      method: "PUT",
      body: JSON.stringify(data),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Profile updated:", data);
      })
      .catch((error) => {
        console.error("Error updating profile:", error);
      });
  }
}

/** Global variable to be referenced for ElementList 
 * @type {EditEntryForm}
*/
var editEntryForm: EditEntryForm;

/**
 * EditEntryForm contains all code for editing an entry
 * @type {class EditEntryForm}
 */
class EditEntryForm {
  /**
   * Intialize the object b setting buttons to do actions
   * when clicked
   * @return {EditEntryForm}
   */
  constructor() {
    document.getElementById("editCancel")?.addEventListener("click", (e) => {
      editEntryForm.clearForm();
    });
    document.getElementById("editButton")?.addEventListener("click", (e) => {
      editEntryForm.submitForm();
    });
    document.getElementById("dislikeButton")?.addEventListener("click", (e) => {
      editEntryForm.submitForm();
    });
  }

  /**
   * Intialize the object b setting buttons to do actions
   * when clicked
   * @param data
   */
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
      console.log("The server replied with an error:\n" + data.mMessage);
    }
    // Handle other errors with a less-detailed popup message
    else {
      console.log("Unspecified error");
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
   * @type {function}
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
   * @type {function}
   */
  submitForm() {
    console.log("Submit edit form called.");
    // get the values of the two fields, force them to be strings, and check
    // that neither is empty
    let title =
      "" + (<HTMLInputElement>document.getElementById("editTitle")).value;
    let msg =
      "" + (<HTMLInputElement>document.getElementById("editMessage")).value;
    let id = "" + (<HTMLInputElement>document.getElementById("editId")).value;
    if (title === "" || msg === "" || id === "") {
      InvalidContentMsg();
      console.log("Error: title, message, or id is not valid");
      return;
    }

    /** set up an AJAX PUT. When the server replies, the result will go to onSubmitResponse
     * @type {function}
    */
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
            console.log(
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
          console.log("Unspecified error");
        });
    };

    // make the AJAX post and output value or error message to console
    doAjax().then(console.log).catch(console.log);
  }
  /**
   * onSubmitResponse runs when the AJAX call in submitForm() returns a
   * result.
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
      console.log("The server replied with an error:\n" + data.mMessage);
    }
    // Handle other errors with a less-detailed popup message
    else {
      console.log("Unspecified error");
    }
  }
  updateUserProfile(data: UserProfile) {
    const userId = "user_id"; //needs to be updated
    fetch(`${backendUrl}/user/profile/${userId}`, {
      method: "PUT",
      body: JSON.stringify(data),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Profile updated:", data);
      })
      .catch((error) => {
        console.error("Error updating profile:", error);
      });
  }
}

/** Global variable to be referenced for ElementList 
 * @type {var}
*/
var mainList: ElementList;

/**
 * ElementList provides a way to see the data stored in server
 * @type {class name}
 */
class ElementList {
  private async submitComment(postId: string, comment: string) {
    await fetch(`${backendUrl}/user/comments/${postId}`, {
      method: "POST",
      body: JSON.stringify({
        postId: postId,
        comment: comment,
      }),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
    })
    .then(response => response.json())
    .then(data => {
      console.log("Comment submitted", data);
    })
    .catch(error => console.error("Error submitting comment:", error));
  }


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
            console.log(
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
          console.log("Unspecified error");
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
        let td_like = document.createElement("td");
        let td_dislike = document.createElement("td");
        td_title.innerHTML = "<div id = \"postTitle\">" + data.mData[i].mSubject +"</div><br><div id = \"postBody\">"+ data.mData[i].mMessage +"</div>";

        td_id.innerHTML = data.mData[i].mId;
        td_like.innerHTML = data.mData[i].numLikes;
        td_dislike.innerHTML = data.mData[i].numLikes;

        let dislikeButton = document.createElement("button");
        dislikeButton.textContent = "Dislike";
        dislikeButton.className = "dislikebtn"; 

        td_dislike.appendChild(dislikeButton);
        tr.appendChild(td_like);
        tr.appendChild(td_dislike);
        tr.appendChild(this.buttons(data.mData[i].mId));

        // Add a comment form for each post
        let commentForm = document.createElement("form");
        commentForm.innerHTML = `
          <input type="text" name="comment" placeholder="Write a comment..." />
          <button type="submit">Comment</button>
        `;
        commentForm.onsubmit = (e) => {
          e.preventDefault();
          this.submitComment(data.mData[i].mId, commentForm.comment.value);
        };
        tr.appendChild(commentForm);

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
    const all_dislikebtns = <HTMLCollectionOf<HTMLInputElement>>(
      document.getElementsByClassName("dislikebtn")
    );
    for (let i = 0; i < all_dislikebtns.length; ++i) {
      all_dislikebtns[i].addEventListener("click", (e) => {
        mainList.clickDislike(e);
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

    td = document.createElement("td");
    btn = document.createElement("button");
    btn.classList.add("dislikebtn");
    btn.setAttribute("data-value", id);
    btn.innerHTML = "Dislike";
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
            console.log(
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
          console.log("Unspecified error");
        });
    };
    // post AJAX values to console
    doAjax().then(console.log).catch(console.log);
  }

  /**
   * Ajax function that sends HTTP function to update like count
   * @param e 
   */
  private clickLike(e: Event) {
    const id = (<HTMLElement>e.target).getAttribute("data-value");

    const doAjax = async () => {
      await fetch(`${backendUrl}/user/upvote/${id}`, {
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
            console.log(
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
          console.log("Unspecified error");
        });
    };
    // post AJAX values to console
    doAjax().then(console.log).catch(console.log);
  }
    /**
   * Ajax function that sends HTTP function to update like count (by decrementing)
   * @param e 
   */
  private clickDislike(e: Event) {
    const id = (<HTMLElement>e.target).getAttribute("data-value");
  
    const doAjax = async () => {
      await fetch(`${backendUrl}/user/downvote/${id}`, {
        // HTTP PUT request for disliking a post
        method: "PUT",
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
      })
        .then((response) => {
          if (response.ok) {
            return Promise.resolve(response.json());
          } else {
            console.log(
              `The server replied not ok: ${response.status}\n` +
                response.statusText
            );
          }
          return Promise.reject(response);
        })
        .then((data) => {
          // Refresh the main message list to reflect the changes
          mainList.refresh();
          console.log(data);
        })
        .catch((error) => {
          console.warn("Something went wrong. ", error);
          console.log("Unspecified error");
        });
    };
  
    // Execute the AJAX request
    doAjax().then(console.log).catch(console.log);
  }

  /**
   * clickEdit is the code we run in response to a click of a delete button
   * @param e
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
            console.log(
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
          console.log("Unspecified error");
        });
    };

    // make the AJAX post and output value or error message to console
    doAjax().then(console.log).catch(console.log);
  }
}

/**
 * Run some configuration code when the web page loads
 * @type {function}
 */
document.addEventListener(
  "DOMContentLoaded",
  () => {

// Run configuration code when the web page loads
document.addEventListener("DOMContentLoaded", () => {
  // Existing code...

  // Handle profile update form submission
  const profileForm = document.getElementById("profileForm");
  if (profileForm) {
    profileForm.addEventListener("submit", (event) => {
      event.preventDefault();
      const formData = new FormData(profileForm as HTMLFormElement);
      const userProfile: UserProfile = {
        username: formData.get("username") as string,
        email: formData.get("email") as string,
        sexualIdentity: formData.get("sexualIdentity") as string,
        genderOrientation: formData.get("genderOrientation") as string,
        note: formData.get("note") as string,
      };
      newEntryForm.updateUserProfile(userProfile);
      editEntryForm.updateUserProfile(userProfile);
    });
  }

  // Existing code...
});
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
    console.log("DOMContentLoaded");
    
  },
  
  false
);

/**
 * Unhide error message from HTML index for a moment 
 * @returns {HTMLBodyElement}
 */
function InvalidContentMsg() {
  var contentError = (<HTMLElement>document.getElementById("InvalidContent"));
  contentError.style.display = "block";
  setTimeout(function () {
    contentError.style.display = "none";
  }, 2000);
}


