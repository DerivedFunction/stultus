"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
/**
 * Prevent compiler errors when using jQuery by test
 * setting $ to any
 * @type {Object}
 */
let $;
/**
 * Global variable to be referenced for newEntryForm
 * @type {NewEntryForm}
 */
let newEntryForm;
/**
 * Backend server link to dokku
 * @type {string}
 */
const backendUrl = ""; //"https://team-stultus.dokku.cse.lehigh.edu"; //"https://2024sp-tutorial-del226.dokku.cse.lehigh.edu";
/**
 * Component name to fetch resources
 * @type {string}
 */
const messages = "messages";
/**
 * Component name to fetch resources
 * @type {string}
 */
const user = "user";
/**
 * Component name to fetch resources
 * @type {string}
 */
const comment = "comment";
const blankText = "<p><br></p>";
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
        var _a, _b;
        /**
         * The HTML element for title
         * @type {HTMLInputElement}
         */
        this.title = document.getElementById("newTitle");
        /**
         * The HTML element for message
         * @type {HTMLDivElement}
         */
        this.message = document.querySelector("#addElement .ql-editor");
        /**
         * The HTML element for the container of the entire module
         * @type {HTMLElement}
         */
        this.container = document.getElementById("addElement");
        (_a = document.getElementById("addCancel")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", (e) => {
            newEntryForm.clearForm();
        });
        (_b = document.getElementById("addButton")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", (e) => {
            newEntryForm.submitForm();
        });
    }
    /**
     * Clear the input fields
     * @type {function}
     */
    clearForm() {
        newEntryForm.title.value = "";
        newEntryForm.message.innerHTML = "";
        // reset the UI
        hideAll();
        mainList.container.style.display = "block";
    }
    /**
     * Check if input is valid before submitting with AJAX call
     * @type {function}
     */
    submitForm() {
        console.log("Submit form called");
        // Get the values of the fields and convert to strings
        // Check for bad input
        let title = "" + newEntryForm.title.value;
        let msg = `${newEntryForm.message.innerHTML}`;
        if (title === "" || msg === blankText) {
            InvalidContentMsg("Error: title/msg is not valid", null);
            return;
        }
        /**
         * do a POST (create) and do onSubmitResponse
         * @type {function}
         */
        const doAjax = () => __awaiter(this, void 0, void 0, function* () {
            yield fetch(`${backendUrl}/${user}/addMessage`, {
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
                }
                else {
                    InvalidContentMsg("The server replied not ok:", response);
                }
                return Promise.reject(response);
            })
                .then((data) => {
                newEntryForm.onSubmitResponse(data);
                mainList.refresh(false);
            })
                .catch((error) => {
                InvalidContentMsg("Error (Did you sign in?):", error);
            });
        });
        // post AJAX values to console
        doAjax().then().catch(console.log);
    }
    /**
     * Runs when AJAX call in submitForm() returns a result
     * @param data obj returned by server
     * @type {function}
     */
    onSubmitResponse(data) {
        if (data.mStatus === "ok") {
            // Sucess, clear for for new entry
            newEntryForm.clearForm();
        }
        else if (data.mStatus === "err") {
            // Handle error w/ msg
            InvalidContentMsg("The server replied with error:\n" + data.mMessage, null);
        }
        else {
            // others
            InvalidContentMsg("Unspecified error has occured", null);
        }
    }
}
/** Global variable to be referenced for ElementList
 * @type {EditEntryForm}
 */
let editEntryForm;
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
        var _a, _b;
        /**
         * The HTML element for title
         * @type {HTMLInputElement}
         */
        this.title = document.getElementById("editTitle");
        /**
         * The HTML element for message
         * @type {HTMLDivElement}
         */
        this.message = document.querySelector("#editElement .ql-editor");
        /**
         * The HTML element for id
         * @type {HTMLInputElement}
         */
        this.id = document.getElementById("editId");
        /**
         * The HTML element for the container of the entire module
         * @type {HTMLElement}
         */
        this.container = document.getElementById("editElement");
        (_a = document.getElementById("editCancel")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", (e) => {
            editEntryForm.clearForm();
        });
        (_b = document.getElementById("editButton")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", (e) => {
            editEntryForm.submitForm();
        });
    }
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @param data
     * @type {function}
     */
    init(data) {
        // Fill the edit form when we receive ok
        if (data.mStatus === "ok") {
            editEntryForm.title.value = data.mData.mSubject;
            editEntryForm.message.innerHTML = data.mData.mMessage;
            editEntryForm.id.value = data.mData.mId;
        }
        else if (data.mStatus === "err") {
            InvalidContentMsg("The server replied with an error:\n" + data.mMessage, null);
        }
        // Handle other errors with a less-detailed popup message
        else {
            InvalidContentMsg("Unspecified error has occured.", null);
        }
        // show the edit form
        hideAll();
        editEntryForm.container.style.display = "block";
    }
    /**
     * Clear the form's input fields
     * @type {function}
     */
    clearForm() {
        editEntryForm.title.value = "";
        editEntryForm.message.innerHTML = "";
        editEntryForm.id.value = "";
        // reset the UI
        hideAll();
        mainList.container.style.display = "block";
    }
    /**
     * Check if the input fields are both valid, and if so, do an AJAX call.
     * @type {function}
     */
    submitForm() {
        console.log("Submit edit form called.");
        // get the values of the two fields, force them to be strings, and check
        // that neither is empty
        let title = "" + editEntryForm.title.value;
        let msg = `${editEntryForm.message.innerHTML}`;
        let id = "" + editEntryForm.id.value;
        if (title === "" || msg === blankText || id === "") {
            InvalidContentMsg("Error: title, message, or id is not valid", null);
            return;
        }
        /**
         * Set up an AJAX PUT. When the server replies, the result will go to onSubmitResponse
         * @type {function}
         */
        const doAjax = () => __awaiter(this, void 0, void 0, function* () {
            yield fetch(`${backendUrl}/${user}/editMessage/${id}`, {
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
                    return Promise.resolve(response.json());
                }
                // Otherwise, handle server errors with a detailed popup message
                else {
                    InvalidContentMsg("The server replied not ok:", response);
                }
                // return response;
                return Promise.reject(response);
            })
                .then((data) => {
                // Update the local storage
                const messageList = localStorage.getItem("messages");
                if (messageList !== null) {
                    const cacheData = JSON.parse(messageList);
                    // Find the index of the item with the matching mId
                    const index = cacheData.data.mData.findIndex((item) => parseInt(item.mId) === parseInt(id));
                    if (index !== -1) {
                        // Update the mTitle and mMessage fields of the item at the found index
                        cacheData.data.mData[index].mSubject = title;
                        cacheData.data.mData[index].mMessage = msg;
                        // Update the localStorage with the modified cacheData
                        localStorage.setItem("messages", JSON.stringify(cacheData));
                    }
                }
                editEntryForm.onSubmitResponse(data);
            })
                .catch((error) => {
                InvalidContentMsg("Error: ", error);
            });
        });
        // make the AJAX post and output value or error message to console
        doAjax().then().catch(console.log);
    }
    /**
     * onSubmitResponse runs when the AJAX call in submitForm() returns a
     * result.
     * @param data The object returned by the server
     * @type {function}
     */
    onSubmitResponse(data) {
        // If we get an "ok" message, clear the form and refresh the main
        // listing of messages
        if (data.mStatus === "ok") {
            editEntryForm.clearForm();
            mainList.refresh(true);
        }
        // Handle explicit errors with a detailed popup message
        else if (data.mStatus === "err") {
            InvalidContentMsg("The server replied with an error:\n" + data.mMessage, null);
        }
        // Handle other errors with a less-detailed popup message
        else {
            InvalidContentMsg("Unspecified error has occured", null);
        }
    }
}
/**
 * Global variable to be referenced for ElementList
 * @type {ElementList}
 */
let mainList;
/**
 * ElementList provides a way to see the data stored in server
 * @type {class ElementList}
 */
class ElementList {
    constructor() {
        /**
         * The HTML element for the container of the entire module
         * @type {HTMLElement}
         */
        this.container = document.getElementById("showElements");
    }
    /**
     * Refresh updates the messageList
     * @param cache true if using localstorage, false if call network
     * @type {function}
     */
    refresh(cache) {
        const doAjax = () => __awaiter(this, void 0, void 0, function* () {
            const cacheKey = `messages`;
            const cachedData = localStorage.getItem(cacheKey);
            const currentTime = new Date().getTime();
            if (cache && cachedData) {
                const { data, timestamp } = JSON.parse(cachedData);
                if (currentTime - timestamp < 10 * 60 * 1000) {
                    // Data found in cache and within expiration time, return it
                    mainList.update(data);
                    return;
                }
            }
            yield fetch(`${backendUrl}/${messages}`, {
                method: "GET",
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
            })
                .then((response) => {
                if (response.ok) {
                    return Promise.resolve(response.json());
                }
                else {
                    InvalidContentMsg("The server replied not ok:", response);
                }
                return Promise.reject(response);
            })
                .then((data) => {
                // Store data in cache along with timestamp
                const cacheData = { data, timestamp: currentTime };
                localStorage.setItem(cacheKey, JSON.stringify(cacheData));
                mainList.update(data);
            })
                .catch((error) => {
                InvalidContentMsg("Error: ", error);
            });
        });
        // post AJAX values to console
        doAjax().then().catch(console.log);
    }
    getUser(userid) {
        return new Promise((resolve, reject) => {
            const cacheKey = `userData_${userid}`;
            const cachedData = localStorage.getItem(cacheKey);
            const currentTime = new Date().getTime();
            if (cachedData) {
                const { data, timestamp } = JSON.parse(cachedData);
                if (currentTime - timestamp < 10 * 60 * 1000) {
                    // Data found in cache and within expiration time, return it
                    resolve(data);
                    return;
                }
            }
            // Cache expired or not found, fetch data from server
            fetch(`${backendUrl}/user/${userid}`, {
                method: "GET",
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
            })
                .then((response) => {
                if (response.ok) {
                    return response.json();
                }
                else {
                    InvalidContentMsg("The server replied not ok:", response);
                }
                return Promise.reject(response);
            })
                .then((data) => {
                if (data.mStatus === "ok" && data.mData) {
                    // Store data in cache along with timestamp
                    const cacheData = { data, timestamp: currentTime };
                    localStorage.setItem(cacheKey, JSON.stringify(cacheData));
                    resolve(data);
                }
                else {
                    throw new Error("User not found or data structure incorrect");
                }
            })
                .catch((error) => {
                reject(error);
            });
        });
    }
    /**
     * Simple ajax to call current user profile
     * @return UserData of email, username, gender, SO, and note
     * @type {function}
     */
    getMyProfile() {
        return new Promise((resolve, reject) => {
            fetch(`${backendUrl}/${user}`, {
                method: "GET",
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
            })
                .then((response) => {
                if (!response.ok) {
                    InvalidContentMsg("The server replied not ok:", response);
                }
                return response.json();
            })
                .then((data) => {
                var _a;
                if (data.mStatus === "ok" && ((_a = data.mData) === null || _a === void 0 ? void 0 : _a.uID)) {
                    resolve(data.mData.uID);
                }
                else {
                    InvalidContentMsg("User ID not found or data structure incorrect", null);
                }
            })
                .catch((error) => reject(error));
        });
    }
    /**
     * Simple ajax to update current user profile
     * @param data the /messages JSON object to parse information
     * @type {function}
     */
    update(data) {
        return __awaiter(this, void 0, void 0, function* () {
            console.log("Updating main table");
            // Retrieve the logged-in user's ID
            const myUserId = yield mainList.getMyProfile();
            // Get the messageList div to store the table
            let elem_messageList = document.getElementById("messageList");
            if (elem_messageList !== null) {
                let table = document.createElement("table"); // Create a new table element
                for (const element of data.mData) {
                    // For each row, we append the information
                    let rowData = element;
                    // Check if user information is available
                    if (!rowData.mUserID) {
                        console.log("Skipping row with no user information");
                        continue; // Skip this iteration of the loop
                    }
                    // Set up row content
                    let td_content;
                    // Set up action buttons
                    let td_actions = "";
                    // Get the user information asynchronously
                    yield mainList
                        .getUser(rowData.mUserID)
                        .then((data) => {
                        // Constructing the HTML for the second column (title, body, and voting)
                        let titleAndBodyHTML = `
            <div class="postTitle"><i>#${rowData.mId}:</i> ${rowData.mSubject}</div>
            <div class="postAuthor">By <a href="mailto:${data.mData.uEmail}" data-value=${rowData.mUserID} class="userLink">${data.mData.uUsername}</a></div>
            <div class="postBody">${rowData.mMessage}</div>
            <div class="voting-container">
              <ul class="voting">
                <li><button class="likebtn" data-value="${rowData.mId}">&#8593;</button></li>
                <li class="totalVote" data-value="${rowData.mId}">${rowData.numLikes}</li>
                <li><button class="dislikebtn" data-value="${rowData.mId}">&#8595;</button></li>
              </ul>
            </div>
          `;
                        td_content = titleAndBodyHTML;
                        // If the logged-in user is the author of the post, append the edit and delete button
                        if (rowData.mUserID === myUserId) {
                            let editButtonHTML = `<button class="editbtn" data-value="${rowData.mId}">Edit</button>`;
                            let delButtonHTML = `<button class="delbtn" data-value="${rowData.mId}">Delete</button>`;
                            td_actions = editButtonHTML + delButtonHTML;
                        }
                        // Create a comment button
                        let commentButtonHTML = `<button class="commentbtn" data-value="${rowData.mId}">Comment</button>`;
                        td_actions += commentButtonHTML;
                    })
                        .catch((error) => {
                        // Catch any errors
                        console.error("Failed to get post with no user:", error);
                    });
                    // Create a row to append the column
                    let tr = document.createElement("tr");
                    // Append the body content and the action buttons
                    tr.innerHTML = `<td>${td_content}${td_actions}</td>`;
                    table.appendChild(tr); // Append the row to the table
                }
                elem_messageList.innerHTML = ""; // Clear the messageList container
                elem_messageList.appendChild(table); // Append the table to the messageList container
            }
            // Find all of the action buttons, and set their behavior
            setButtons();
            getAllUserBtns();
            /**
             * function the set up all action buttons, including voting
             * @type {function}
             */
            function setButtons() {
                const all_delbtns = Array.from(document.getElementsByClassName("delbtn"));
                for (const element of all_delbtns) {
                    element.addEventListener("click", (e) => {
                        mainList.clickDelete(e);
                    });
                }
                // Find all of the edit buttons, and set their behavior
                const all_editbtns = Array.from(document.getElementsByClassName("editbtn"));
                for (const element of all_editbtns) {
                    element.addEventListener("click", (e) => {
                        mainList.clickEdit(e);
                    });
                }
                // Find all of the upvote buttons, and set their behavior
                const all_likebtns = Array.from(document.getElementsByClassName("likebtn"));
                for (const element of all_likebtns) {
                    element.addEventListener("click", (e) => {
                        mainList.clickUpvote(e);
                    });
                }
                // Find all of the downvote buttons, and set their behavior
                const all_dislikebtns = Array.from(document.getElementsByClassName("dislikebtn"));
                for (const element of all_dislikebtns) {
                    element.addEventListener("click", (e) => {
                        mainList.clickDownvote(e);
                    });
                }
                // Find all of the comment buttons, and set their behavior
                const all_commentbtns = Array.from(document.getElementsByClassName("commentbtn"));
                for (const element of all_commentbtns) {
                    element.addEventListener("click", (e) => {
                        mainList.clickComment(e);
                    });
                }
            }
        });
    }
    /**
     * clickDelete is the code we run in response to a click of a delete button
     * @param e Event to get the message to be deleted
     * @type {function}
     */
    clickDelete(e) {
        console.log("Delete called");
        const id = e.target.getAttribute("data-value");
        const doAjax = () => __awaiter(this, void 0, void 0, function* () {
            yield fetch(`${backendUrl}/${user}/deleteMessage/${id}`, {
                // Grab the element from "database"
                method: "DELETE",
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
            })
                .then((response) => {
                if (response.ok) {
                    return Promise.resolve(response.json());
                }
                else {
                    InvalidContentMsg("The server replied not ok:", response);
                }
                return Promise.reject(response);
            })
                .then((data) => {
                // Update the local storage
                const messageList = localStorage.getItem("messages");
                if (messageList !== null) {
                    const cacheData = JSON.parse(messageList);
                    // Find the index of the item with the matching mId
                    const index = cacheData.data.mData.findIndex((item) => item.mId === id);
                    if (index !== -1) {
                        // If the item is found, remove it from the array
                        cacheData.data.mData.splice(index, 1);
                        // Update the localStorage with the modified cacheData
                        localStorage.setItem("messages", JSON.stringify(cacheData));
                    }
                }
                mainList.refresh(false);
            })
                .catch((error) => {
                InvalidContentMsg("Error: ", error);
            });
        });
        // post AJAX values to console
        doAjax().then().catch(console.log);
    }
    /**
     * clickUpvote is the code we run in response to a click of a upvote button
     * @type {function}
     * @param e Event to get the message to be upvoted
     */
    clickUpvote(e) {
        console.log("Upvote called");
        const id = e.target.getAttribute("data-value");
        const doAjax = () => __awaiter(this, void 0, void 0, function* () {
            yield fetch(`${backendUrl}/${user}/upvote/${id}`, {
                // Grab the element from "database"
                method: "POST",
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
            })
                .then((response) => {
                if (response.ok) {
                    return Promise.resolve(response.json());
                }
                else {
                    InvalidContentMsg("The server replied not ok:", response);
                }
                return Promise.reject(response);
            })
                .then(() => {
                mainList.updateVote(id);
            })
                .catch((error) => {
                InvalidContentMsg("Error: ", error);
            });
        });
        // post AJAX values to console
        doAjax().then().catch(console.log);
    }
    /**
     * Updates the vote count for the post
     * @param id the id of post
     */
    updateVote(id) {
        const doAjax = () => __awaiter(this, void 0, void 0, function* () {
            try {
                const data = yield this.fetchPostData(id);
                // Update totalVote element
                const totalVoteElement = document.querySelector(`li.totalVote[data-value="${data.mData.mId}"]`);
                if (totalVoteElement) {
                    totalVoteElement.innerHTML = data.mData.numLikes;
                    // Update localStorage
                    const messageList = localStorage.getItem("messages");
                    if (messageList != null) {
                        const cacheData = JSON.parse(messageList);
                        cacheData.data.mData.forEach((item) => {
                            if (item.mId === data.mData.mId) {
                                item.numLikes = data.mData.numLikes;
                            }
                        });
                        localStorage.setItem("messages", JSON.stringify(cacheData));
                    }
                }
            }
            catch (error) {
                InvalidContentMsg("Error: ", error);
            }
        });
        doAjax();
    }
    /**
     * Fetches post data from the backend
     * @param id the id of post
     * @returns Promise<PostData>
     */
    fetchPostData(id) {
        return __awaiter(this, void 0, void 0, function* () {
            const response = yield fetch(`${backendUrl}/${messages}/${id}`, {
                method: "GET",
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
            });
            if (response.ok) {
                return response.json();
            }
            else {
                throw new Error(`The server replied not ok: ${response.status}`);
            }
        });
    }
    /**
     * clickDownvote is the code we run in response to a click of a downvote button
     * @type {function}
     * @param e Event to get the message to be downvoted
     */
    clickDownvote(e) {
        console.log("Downvote called");
        const id = e.target.getAttribute("data-value");
        const doAjax = () => __awaiter(this, void 0, void 0, function* () {
            yield fetch(`${backendUrl}/${user}/downvote/${id}`, {
                // Grab the element from "database"
                method: "POST",
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
            })
                .then((response) => {
                if (response.ok) {
                    return Promise.resolve(response.json());
                }
                else {
                    InvalidContentMsg("The server replied not ok:", response);
                }
                return Promise.reject(response);
            })
                .then(() => {
                mainList.updateVote(id);
            })
                .catch((error) => {
                InvalidContentMsg("Error: ", error);
            });
        });
        // post AJAX values to console
        doAjax().then().catch(console.log);
    }
    /**
     * clickEdit is the code we run in response to a click of a edit button
     * @param e Event to get the message to be editGender
     * @type {function}
     */
    clickEdit(e) {
        // as in clickDelete, we need the ID of the row
        const id = e.target.getAttribute("data-value");
        // Issue an AJAX GET and then pass the result to editEntryForm.init()
        const doAjax = () => __awaiter(this, void 0, void 0, function* () {
            yield fetch(`${backendUrl}/${messages}/${id}`, {
                method: "GET",
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
            })
                .then((response) => {
                if (response.ok) {
                    return Promise.resolve(response.json());
                }
                else {
                    InvalidContentMsg("The server replied not ok:", response);
                }
                return Promise.reject(response);
            })
                .then((data) => {
                editEntryForm.init(data);
            })
                .catch((error) => {
                InvalidContentMsg("Error: ", error);
            });
        });
        // make the AJAX post and output value or error message to console
        doAjax().then().catch(console.log);
    }
    /**
     * clickComment is the code we run in response to a click of a comment button
     * @param e Event to get the message to be commented
     * @type {function}
     */
    clickComment(e) {
        // as in clickDelete, we need the ID of the row
        const id = e.target.getAttribute("data-value");
        // Issue an AJAX GET and then pass the result to editEntryForm.init()
        const doAjax = () => __awaiter(this, void 0, void 0, function* () {
            yield fetch(`${backendUrl}/${messages}/${id}`, {
                method: "GET",
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
            })
                .then((response) => {
                if (response.ok) {
                    return Promise.resolve(response.json());
                }
                else {
                    InvalidContentMsg("The server replied not ok:", response);
                }
                return Promise.reject(response);
            })
                .then((data) => {
                commentForm.init(data);
            })
                .catch((error) => {
                InvalidContentMsg("Error: ", error);
            });
        });
        // make the AJAX post and output value or error message to console
        doAjax().then().catch(console.log);
    }
}
/**
 * Global variable to be referenced for Profile
 * @type {Profile}
 */
let profile;
/**
 * Profile is all the code to edit and view a current user's profile
 * @type {class Profile}
 */
class Profile {
    constructor() {
        var _a, _b;
        /**
         * The HTML element for username
         * @type {HTMLInputElement}
         */
        this.nameBox = document.getElementById("editUsername");
        /**
         * The HTML element for gender
         * @type {HTMLSelectElement}
         */
        this.genderMenu = document.getElementById("editGender");
        /**
         * The HTML element for SO
         * @type {HTMLSelectElement}
         */
        this.soMenu = document.getElementById("editSO");
        /**
         * The HTML element for user note
         * @type {HTMLDivElement}
         */
        this.noteBox = document.querySelector("#profileForm .ql-editor");
        /**
         * The HTML element for the container of the entire module
         * @type {HTMLElement}
         */
        this.container = document.getElementById("profileForm");
        (_a = document.getElementById("UserCancel")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", (e) => {
            profile.clearForm();
        });
        (_b = document.getElementById("editUser")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", (e) => {
            profile.submitForm();
        });
    }
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @type {function}
     */
    init() {
        return __awaiter(this, void 0, void 0, function* () {
            console.log("Current User profile called");
            try {
                const response = yield fetch(`${backendUrl}/${user}`, {
                    method: "GET",
                    headers: {
                        "Content-type": "application/json; charset=UTF-8",
                    },
                });
                if (!response.ok) {
                    InvalidContentMsg("The server replied not ok:", response);
                }
                const data = yield response.json();
                profile.fillForm(data);
            }
            catch (error) {
                InvalidContentMsg("Error: ", error);
            }
        });
    }
    /**
     * Fill out the profile form with user information
     * when clicked
     * @param data the UserData JSON
     * @type {function}
     */
    fillForm(data) {
        if (data.mStatus === "ok") {
            // Get the username
            profile.nameBox.value = data.mData.uUsername;
            // Get the gender
            const genderValue = data.mData.uGender;
            fillGender(genderValue);
            // Get the SO
            const sO = data.mData.uSO.toLowerCase(); // Convert to lowercase for case-insensitive comparison
            fillSO(sO);
            // Get the note
            profile.noteBox.innerHTML = data.mData.uNote;
        }
        else if (data.mStatus === "err") {
            InvalidContentMsg("The server replied with an error:\n" + data.mMessage, null);
        }
        else {
            InvalidContentMsg("Unspecified error has occured", null);
        }
        /**
         * Select the correct SO
         * @param sO the SO string
         * @type {function}
         */
        function fillSO(sO) {
            if (sO !== undefined && sO !== null) {
                for (let i = 0; i < profile.soMenu.options.length; i++) {
                    if (profile.soMenu.options[i].value.toLowerCase() === sO) {
                        // Compare lowercase values
                        profile.soMenu.selectedIndex = i;
                        break;
                    }
                }
            }
        }
        /**
         * Select the correct gender
         * @param sO the gender string
         * @type {function}
         */
        function fillGender(genderValue) {
            if (genderValue !== undefined && genderValue !== null) {
                for (let i = 0; i < profile.genderMenu.options.length; i++) {
                    if (profile.genderMenu.options[i].value === genderValue.toString()) {
                        profile.genderMenu.selectedIndex = i;
                        break;
                    }
                }
            }
        }
    }
    /**
     * Clears the form
     * @type {function}
     */
    clearForm() {
        hideAll();
        mainList.container.style.display = "block";
    }
    /**
     * Submit the form to update user profile
     * @type {function}
     */
    submitForm() {
        // Extract values from form fields
        const username = profile.nameBox.value;
        const userGender = profile.genderMenu.value;
        const userSO = profile.soMenu.value;
        const userNote = profile.noteBox.innerHTML;
        // Make the AJAX PUT request
        fetch(`${backendUrl}/${user}`, {
            method: "PUT",
            body: JSON.stringify({
                uUsername: username,
                uGender: userGender,
                uSO: userSO,
                uNote: userNote,
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
        })
            .then((response) => {
            if (response.ok) {
                return response.json();
            }
            else {
                InvalidContentMsg("The server replied not ok:", response);
                return Promise.reject(response);
            }
        })
            .then((data) => {
            // Handle the response from the server
            profile.onSubmitResponse(data);
        })
            .catch((error) => {
            InvalidContentMsg("Error (Did you sign in?):", error);
        });
    }
    /**
     * onSubmitResponse runs when the AJAX call in submitForm() returns a
     * result.
     * @param data The object returned by the server
     */
    onSubmitResponse(data) {
        console.log("Updating user information");
        // If we get an "ok" message, clear the form and refresh the main
        // listing of messages
        if (data.mStatus === "ok") {
            profile.clearForm();
            mainList.refresh(true);
        }
        // Handle explicit errors with a detailed popup message
        else if (data.mStatus === "err") {
            InvalidContentMsg("The server replied with an error:\n" + data.mMessage, null);
        }
        // Handle other errors with a less-detailed popup message
        else {
            InvalidContentMsg("Unspecified error has occured", null);
        }
    }
}
/**
 * Global variable to be referenced for CommentForm
 * @type {CommentForm}
 */
let commentForm;
/**
 * CommentForm is all the code to add comments to a post
 * @type {class CommentForm}
 */
class CommentForm {
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @type {function}
     */
    constructor() {
        var _a, _b;
        /**
         * The HTML element for post title
         * @type {HTMLElement}
         */
        this.title = document.getElementById("messageTitle");
        /**
         * The HTML element for post body
         * @type {HTMLElement}
         */
        this.body = document.getElementById("messageBody");
        /**
         * The HTML element for post author
         * @type {HTMLElement}
         */
        this.author = document.getElementById("messageAuthor");
        /**
         * The HTML element for post id
         * @type {HTMLElement}
         */
        this.id = document.getElementById("messageId");
        /**
         * The HTML element for the container of the entire module
         * @type {HTMLElement}
         */
        this.container = document.getElementById("showComment");
        /**
         * The HTML element for comment body
         * @type {HTMLDivElement}
         */
        this.textbox = document.querySelector("#showComment .ql-editor");
        (_a = document.getElementById("commentCancel")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", (e) => {
            commentForm.clearForm();
        });
        (_b = document.getElementById("commentButton")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", (e) => {
            commentForm.submitForm();
        });
    }
    /**
     * Initialize the object by setting the post's content to form
     * @param data The data object received from the server
     * @type {function}
     */
    init(data) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                // Fill the post information if the status is "ok"
                if (data.mStatus === "ok" && data.mData) {
                    const userData = yield mainList.getUser(data.mData.mUserID);
                    // Set post title
                    commentForm.title.innerHTML = `<i>#${data.mData.mId}:</i> ${data.mData.mSubject}`;
                    // Set post body
                    commentForm.body.innerHTML = data.mData.mMessage;
                    // Set author's username and email
                    commentForm.author.innerHTML = `By <a href="mailto:${userData.mData.uEmail}" data-value=${userData.mData.uID} class="userLink">${userData.mData.uUsername}</a>`;
                    commentForm.id.value = data.mData.mId;
                }
                else if (data.mStatus === "err") {
                    InvalidContentMsg("The server replied with an error:\n" + data.mMessage, null);
                }
                else {
                    InvalidContentMsg("Unspecified error has occured", null);
                }
                hideAll();
                commentForm.container.style.display = "block";
                commentForm.getComment(data);
            }
            catch (error) {
                console.error("Error initializing comment form:", error);
            }
            getAllUserBtns();
        });
    }
    /**
     * Get comments
     * @type {function}
     * @param data JSON object of all comments to a post
     */
    getComment(data) {
        const id = data.mData.mId;
        const doAjax = () => __awaiter(this, void 0, void 0, function* () {
            yield fetch(`${backendUrl}/${messages}/${id}/comments`, {
                method: "GET",
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
            })
                .then((response) => {
                if (response.ok) {
                    return Promise.resolve(response.json());
                }
                else {
                    InvalidContentMsg("The server replied not ok:", response);
                }
                return Promise.reject(response);
            })
                .then((data) => {
                commentForm.createTable(data);
            })
                .catch((error) => {
                InvalidContentMsg("Error: ", error);
            });
        });
        // post AJAX values to console
        doAjax().then().catch(console.log);
    }
    /**
     * Create a table of comments
     * @param data  JSON of all comments from post
     * @type {function}
     */
    createTable(data) {
        return __awaiter(this, void 0, void 0, function* () {
            // Retrieve the logged-in user's ID
            const myUserId = yield mainList.getMyProfile();
            let comment_list = document.getElementById("commentList");
            if (comment_list !== null) {
                let table = document.createElement("table"); // Create a new table element
                for (const element of data.mData) {
                    let rowData = element;
                    // Check if user information is available
                    if (!rowData.cUserID) {
                        console.error("Skipping row with no user information");
                        continue; // Skip this iteration of the loop
                    }
                    let td_content;
                    let td_actions = "";
                    // Get the user information asynchronously
                    yield mainList
                        .getUser(rowData.cUserID)
                        .then((data) => {
                        // Constructing the HTML for the comment
                        let commentHTML = `
                    <div class="commentAuthor">By <a href="mailto:${data.mData.uEmail}" data-value=${rowData.cUserID} class="userLink">${data.mData.uUsername}</a></div>
                    <div class="commentBody">${rowData.cMessage}</div>
                `;
                        td_content = commentHTML;
                        // If the logged-in user is the author of the comment, append the edit button
                        if (rowData.cUserID === myUserId) {
                            let editButtonHTML = `<button class="editcommentbtn" data-value="${rowData.cId}">Edit</button>`;
                            td_actions = editButtonHTML;
                        }
                    })
                        .catch((error) => {
                        console.error("Failed to get comment with no user:", error);
                    });
                    let tr = document.createElement("tr");
                    tr.innerHTML = `<td>${td_content}${td_actions}</td>`;
                    table.appendChild(tr); // Append the row to the table
                }
                comment_list.innerHTML = ""; // Clear the commentList container
                comment_list.appendChild(table); // Append the table to the commentList container
            }
            // Find all of the edit buttons, and set their behavior
            const all_editbtns = Array.from(document.getElementsByClassName("editcommentbtn"));
            for (const element of all_editbtns) {
                element.addEventListener("click", (e) => {
                    commentForm.clickEdit(e);
                });
            }
            getAllUserBtns();
        });
    }
    /**
     * clickEdit is the code we run in response to a click of a edit button
     * @param e Event to edit a Comment
     * @type {function}
     */
    clickEdit(e) {
        // as in clickDelete, we need the ID of the row
        const id = e.target.getAttribute("data-value");
        // Issue an AJAX GET and then pass the result to editEntryForm.init()
        const doAjax = () => __awaiter(this, void 0, void 0, function* () {
            yield fetch(`${backendUrl}/${comment}/${id}`, {
                method: "GET",
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
            })
                .then((response) => {
                if (response.ok) {
                    return Promise.resolve(response.json());
                }
                else {
                    InvalidContentMsg("The server replied not ok:", response);
                }
                return Promise.reject(response);
            })
                .then((data) => {
                commentEditForm.init(data);
            })
                .catch((error) => {
                InvalidContentMsg("Error: ", error);
            });
        });
        // make the AJAX post and output value or error message to console
        doAjax().then().catch(console.log);
    }
    /**
     * clears the contents of the container
     * @type {function}
     */
    clearForm() {
        // Clear the values in it
        commentForm.title.innerText = "";
        commentForm.body.innerHTML = "";
        commentForm.author.innerHTML = "";
        commentForm.textbox.innerHTML = "";
        document.getElementById("commentList").innerHTML = "";
        // Reset the UI
        hideAll();
        mainList.container.style.display = "block";
    }
    submitForm() {
        console.log("Submit comment form called.");
        const msg = commentForm.textbox.innerHTML;
        const msgID = commentForm.id.value;
        // Check if the comment text is not empty
        if (msg === blankText) {
            InvalidContentMsg("Error: Comment text is empty", null);
            return;
        }
        // Make the AJAX POST request
        fetch(`${backendUrl}/${user}/comment/${msgID}`, {
            method: "POST",
            body: JSON.stringify({
                mMessage: msg,
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
        })
            .then((response) => {
            if (response.ok) {
                // If the request is successful, clear the comment form
                commentForm.clearForm();
                console.log("Comment submitted successfully");
            }
            else {
                // If there's an error, log the error message
                InvalidContentMsg("The server replied not ok:", response);
            }
        })
            .catch((error) => {
            InvalidContentMsg("Error: ", error);
        });
    }
}
/**
 * Global variable to be referenced for commentEditForm
 * @type {commentEditForm}
 */
let commentEditForm;
/**
 * CommentEditForm contains all code for editing an comment
 * @type {class CommentEditForm}
 */
class CommentEditForm {
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @type {function}
     */
    constructor() {
        var _a, _b;
        /**
         * The HTML element for comment message
         * @type {HTMLDivElement}
         */
        this.message = document.querySelector("#editComment .ql-editor");
        /**
         * The HTML element for comment id
         * @type {HTMLInputElement}
         */
        this.id = document.getElementById("commentEditId");
        /**
         * The HTML element for the container of the entire module
         * @type {HTMLElement}
         */
        this.container = document.getElementById("editComment");
        (_a = document
            .getElementById("editCommentCancel")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", (e) => {
            commentEditForm.clearForm();
        });
        (_b = document
            .getElementById("editCommentUpdate")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", (e) => {
            commentEditForm.submitForm();
        });
    }
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @param data comment JSON
     * @type {function}
     */
    init(data) {
        // Fill the edit form when we receive ok
        if (data.mStatus === "ok") {
            commentEditForm.message.innerHTML = data.mData.cMessage;
            commentEditForm.id.value = data.mData.cId;
        }
        else if (data.mStatus === "err") {
            InvalidContentMsg("The server replied with an error:\n" + data.mMessage, null);
        }
        // Handle other errors with a less-detailed popup message
        else {
            InvalidContentMsg("Unspecified error has occured", null);
        }
        // show the edit form
        hideAll();
        commentEditForm.container.style.display = "block";
    }
    /**
     * Clear the form's input fields
     * @type {function}
     */
    clearForm() {
        commentEditForm.message.innerHTML = "";
        commentEditForm.id.value = "";
        // reset the UI
        hideAll();
        mainList.container.style.display = "block";
    }
    /**
     * Check if the input fields are both valid, and if so, do an AJAX call.
     * @type {function}
     */
    submitForm() {
        console.log("Submit edit comment called.");
        // get the values of the two fields, force them to be strings, and check
        // that neither is empty
        let msg = `${commentEditForm.message.innerHTML}`;
        let id = "" + commentEditForm.id.value;
        if (msg === blankText || id === "") {
            InvalidContentMsg("Error: comment message, or id is not valid", null);
            return;
        }
        /**
         * set up an AJAX PUT. When the server replies, the result will go to onSubmitResponse
         * @type {function}
         */
        const doAjax = () => __awaiter(this, void 0, void 0, function* () {
            yield fetch(`${backendUrl}/${user}/editComment/${id}`, {
                method: "PUT",
                body: JSON.stringify({
                    mMessage: msg,
                }),
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
            })
                .then((response) => {
                // If we get an "ok" message, return the json
                if (response.ok) {
                    return Promise.resolve(response.json());
                }
                // Otherwise, handle server errors with a detailed popup message
                else {
                    InvalidContentMsg("The server replied not ok:", response);
                }
                // return response;
                return Promise.reject(response);
            })
                .then((data) => {
                commentEditForm.onSubmitResponse(data);
            })
                .catch((error) => {
                InvalidContentMsg("Error: ", error);
            });
        });
        // make the AJAX post and output value or error message to console
        doAjax().then().catch(console.log);
    }
    /**
     * onSubmitResponse runs when the AJAX call in submitForm() returns a
     * result.
     * @param data The object returned by the server
     * @type {function}
     */
    onSubmitResponse(data) {
        // If we get an "ok" message, clear the form and refresh the main
        // listing of messages
        if (data.mStatus === "ok") {
            commentEditForm.clearForm();
            mainList.refresh(true);
        }
        // Handle explicit errors with a detailed popup message
        else if (data.mStatus === "err") {
            InvalidContentMsg("The server replied with an error:\n" + data.mMessage, null);
        }
        // Handle other errors with a less-detailed popup message
        else {
            InvalidContentMsg("Unspecified error has occured", null);
        }
    }
}
/**
 * Global variable to be referenced for commentEditForm
 * @type {ViewUserForm}
 */
let viewUserForm;
/**
 * CommentEditForm contains all code for viewing a user
 * @type {class ViewUserForm}
 */
class ViewUserForm {
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @type {function}
     */
    constructor() {
        var _a;
        /**
         * The HTML element for username
         * @type {HTMLElement}
         */
        this.username = document.getElementById("viewUsername");
        /**
         * The HTML element for note
         * @type {HTMLElement}
         */
        this.note = document.getElementById("viewNote");
        /**
         * The HTML element for email
         * @type {HTMLElement}
         */
        this.email = document.getElementById("viewEmail");
        /**
         * The HTML element for the container of the entire module
         * @type {HTMLElement}
         */
        this.container = document.getElementById("userForm");
        (_a = document.getElementById("viewReturn")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", (e) => {
            viewUserForm.clearForm();
        });
    }
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @type {function}
     * @param data JSON of UserDataLite
     */
    init(data) {
        if (data.mStatus === "ok") {
            viewUserForm.username.innerText = data.mData.uUsername;
            viewUserForm.note.innerHTML = data.mData.uNote;
            viewUserForm.email.innerHTML = `<a href="mailto:${data.mData.uEmail}">${data.mData.uEmail}</a>`;
        }
        else if (data.mStatus === "err") {
            InvalidContentMsg("The server replied with an error:\n" + data.mMessage, null);
        }
        else {
            InvalidContentMsg("Unspecified error has occured", null);
        }
        hideAll();
        viewUserForm.container.style.display = "block";
    }
    /**
     * Clear the form's input fields
     * @type {function}
     */
    clearForm() {
        // Clearing inner text of elements
        viewUserForm.username.innerText = "";
        viewUserForm.note.innerHTML = "";
        viewUserForm.email.innerHTML = "";
        hideAll();
        mainList.container.style.display = "block";
    }
}
/**
 * Run some configuration code when the web page loads
 * @type {function}
 */
document.addEventListener("DOMContentLoaded", () => {
    var _a, _b, _c, _d;
    // Create the object that controls the "New Entry" form
    newEntryForm = new NewEntryForm();
    editEntryForm = new EditEntryForm();
    mainList = new ElementList();
    profile = new Profile();
    commentForm = new CommentForm();
    commentEditForm = new CommentEditForm();
    viewUserForm = new ViewUserForm();
    mainList.refresh(false);
    // set up initial UI state
    hideAll();
    mainList.container.style.display = "block";
    (_a = document
        .getElementById("showFormButton")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", (e) => {
        hideAll();
        newEntryForm.container.style.display = "block";
    });
    (_b = document
        .getElementById("refreshFormButton")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", (e) => {
        hideAll();
        mainList.container.style.display = "block";
        mainList.refresh(false);
    });
    (_c = document
        .getElementById("showProfileButton")) === null || _c === void 0 ? void 0 : _c.addEventListener("click", (e) => {
        hideAll();
        profile.container.style.display = "block";
        profile.init();
    });
    (_d = document
        .getElementById("accountStatus")) === null || _d === void 0 ? void 0 : _d.addEventListener("click", (e) => __awaiter(void 0, void 0, void 0, function* () {
        try {
            const response = yield fetch(`${backendUrl}/logout`, {
                method: "DELETE",
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
            });
            if (response.ok) {
                // Successful logout, redirect to login.html
                window.location.href = "login.html";
            }
            else {
                InvalidContentMsg("You are probably not logged in:", response);
                window.location.href = "login.html";
            }
        }
        catch (error) {
            InvalidContentMsg("Something went wrong. ", error);
        }
    }));
    getAllFileInput();
    console.log("DOMContentLoaded");
}, false);
/**
 * Gets all user links to link it to a user page
 * @type {function}
 */
function getAllUserBtns() {
    const all_userbtns = Array.from(document.getElementsByClassName("userLink"));
    for (const element of all_userbtns) {
        element.addEventListener("click", (e) => __awaiter(this, void 0, void 0, function* () {
            e.preventDefault();
            let id = element.getAttribute("data-value");
            if (id) {
                let data = yield mainList.getUser(id);
                viewUserForm.init(data);
            }
        }));
    }
}
function getAllFileInput() {
    const allFileUploads = Array.from(document.getElementsByClassName("fileInput"));
    for (const fileInput of allFileUploads) {
        fileInput.addEventListener("change", (e) => __awaiter(this, void 0, void 0, function* () {
            const file = fileInput.files ? fileInput.files[0] : null;
            if (file) {
                const reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = () => __awaiter(this, void 0, void 0, function* () {
                    // Ensure that reader.result is not null
                    if (reader.result) {
                        yield uploadImage(reader.result, fileInput.getAttribute("parent"));
                        fileInput.value = "";
                    }
                    else {
                        console.error("Failed to read the file.");
                    }
                });
            }
            else {
                console.error("No file selected.");
            }
        }));
    }
}
function uploadImage(dataFile, location) {
    return __awaiter(this, void 0, void 0, function* () {
        const base64 = dataFile.split(",")[1];
        const body = {
            image: base64,
        };
        let area = document.querySelector(`#${location} .ql-editor`);
        yield fetch(`${backendUrl}/uploadImage`, {
            method: "POST",
            body: JSON.stringify(body),
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then((response) => {
            if (response.ok) {
                // Return the json after ok message
                return Promise.resolve(response.json());
            }
            else {
                InvalidContentMsg("The server replied not ok:", response);
            }
            return Promise.reject(response);
        })
            .then((data) => {
            console.log("Image upload success", data);
            area.innerHTML += `<img src="${data.mData.map.data.map.display_url}" width="50%" >.`;
        })
            .catch((error) => {
            console.log("Error", error);
            area.innerHTML += `<img src="${dataFile}" width="50%" alt="${error.statusText}"> Error uploading Image`;
        });
    });
}
/**
 * Hides every module
 * @type {function}
 */
function hideAll() {
    editEntryForm.container.style.display = "none";
    newEntryForm.container.style.display = "none";
    mainList.container.style.display = "none";
    profile.container.style.display = "none";
    commentForm.container.style.display = "none";
    commentEditForm.container.style.display = "none";
    viewUserForm.container.style.display = "none";
}
/**
 * Display error message from HTML index for a moment
 * @type {function}
 */
function InvalidContentMsg(message, error) {
    let contentError = document.getElementById("InvalidContent");
    contentError.innerText = message;
    if (error != null)
        contentError.innerText += `\n${error.status}: ${error.statusText}`;
    contentError.style.display = "block";
    setTimeout(function () {
        contentError.style.display = "none";
    }, 2000);
}
