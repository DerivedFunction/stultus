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
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (g && (g = 0, op[0] && (_ = 0)), _) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
/** Prevent compiler errors when using jQuery by test
 * setting $ to any
 * @type any
 */
var $;
/** Global variable to be referenced for newEntryForm
 * @type {Object.<string, string>}
*/
var newEntryForm;
/**
 * Backend server link to dokku
 * @type {string}
 */
var backendUrl = "https://team-stultus.dokku.cse.lehigh.edu"; //"https://2024sp-tutorial-del226.dokku.cse.lehigh.edu"; //https://team-stultus.dokku.cse.lehigh.edu
/**
 * Component name to fetch resources
 * @type {string}
 */
var componentName = "messages";
/**
 * NewEntryForm has all the code for the form for adding an entry
 * @type {class NewEntryForm}
 */
var NewEntryForm = /** @class */ (function () {
    /**
     * Intialize the object  setting buttons to do actions when clicked
     * @return {NewEntryForm}
     */
    function NewEntryForm() {
        var _a, _b;
        (_a = document.getElementById("addCancel")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", function (e) {
            newEntryForm.clearForm();
        });
        (_b = document.getElementById("addButton")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", function (e) {
            newEntryForm.submitForm();
        });
    }
    /**
     * Clear the input fields
     * @type {function}
     */
    NewEntryForm.prototype.clearForm = function () {
        document.getElementById("newTitle").value = "";
        document.getElementById("newMessage").value = "";
        // reset the UI
        document.getElementById("editElement").style.display =
            "none";
        document.getElementById("addElement").style.display = "none";
        document.getElementById("showElements").style.display =
            "block";
    };
    /**
     * Check if input is valid before submitting with AJAX call
     * @type {function}
     */
    NewEntryForm.prototype.submitForm = function () {
        var _this = this;
        console.log("Submit form called");
        // Get the values of the fields and convert to strings
        // Check for bad input
        var title = "" + document.getElementById("newTitle").value;
        var msg = "" + document.getElementById("newMessage").value;
        if (title === "" || msg === "") {
            InvalidContentMsg();
            console.log("Error: title/msg is not valid");
            return;
        }
        /**
        * do a POST (create) and do onSubmitResponse
        * @type {function}
        */
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(componentName), {
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
                            .then(function (response) {
                            if (response.ok) {
                                // Return the json after ok message
                                return Promise.resolve(response.json());
                            }
                            else {
                                console.log("The server replied not ok: ".concat(response.status, "\n") +
                                    response.statusText);
                            }
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            newEntryForm.onSubmitResponse(data);
                            mainList.refresh();
                            console.log(data);
                        })
                            .catch(function (error) {
                            console.warn("Something went wrong", error);
                            console.log("Unspecified error");
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // post AJAX values to console
        doAjax().then(console.log).catch(console.log);
    };
    /**
     * Runs when AJAX call in submitForm() returns a result
     * @param data obj returned by server
     */
    NewEntryForm.prototype.onSubmitResponse = function (data) {
        if (data.mStatus === "ok") {
            // Sucess, clear for for new entry
            newEntryForm.clearForm();
        }
        else if (data.mStatus === "error") {
            // Handle error w/ msg
            console.log("The server replied with error:\n" + data.mMessage);
        }
        else {
            // others
            console.log("Unspecified error");
        }
    };
    NewEntryForm.prototype.updateUserProfile = function (data) {
        var userId = "user_id"; //needs to be updated
        fetch("".concat(backendUrl, "/user/profile/").concat(userId), {
            method: "PUT",
            body: JSON.stringify(data),
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
        })
            .then(function (response) { return response.json(); })
            .then(function (data) {
            console.log("Profile updated:", data);
        })
            .catch(function (error) {
            console.error("Error updating profile:", error);
        });
    };
    return NewEntryForm;
}());
/** Global variable to be referenced for ElementList
 * @type {EditEntryForm}
*/
var editEntryForm;
/**
 * EditEntryForm contains all code for editing an entry
 * @type {class EditEntryForm}
 */
var EditEntryForm = /** @class */ (function () {
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @return {EditEntryForm}
     */
    function EditEntryForm() {
        var _a, _b, _c;
        (_a = document.getElementById("editCancel")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", function (e) {
            editEntryForm.clearForm();
        });
        (_b = document.getElementById("editButton")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", function (e) {
            editEntryForm.submitForm();
        });
        (_c = document.getElementById("dislikeButton")) === null || _c === void 0 ? void 0 : _c.addEventListener("click", function (e) {
            editEntryForm.submitForm();
        });
    }
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @param data
     */
    EditEntryForm.prototype.init = function (data) {
        // Fill the edit form when we receive ok
        if (data.mStatus === "ok") {
            document.getElementById("editTitle").value =
                data.mData.mSubject;
            document.getElementById("editMessage").value =
                data.mData.mMessage;
            document.getElementById("editId").value =
                data.mData.mId;
            document.getElementById("editCreated").value =
                data.mData.mCreated;
        }
        else if (data.mStatus === "error") {
            console.log("The server replied with an error:\n" + data.mMessage);
        }
        // Handle other errors with a less-detailed popup message
        else {
            console.log("Unspecified error");
        }
        // show the edit form
        document.getElementById("editElement").style.display =
            "block";
        document.getElementById("addElement").style.display = "none";
        document.getElementById("showElements").style.display =
            "none";
    };
    /**
     * Clear the form's input fields
     * @type {function}
     */
    EditEntryForm.prototype.clearForm = function () {
        document.getElementById("editTitle").value = "";
        document.getElementById("editMessage").value = "";
        document.getElementById("editId").value = "";
        document.getElementById("editCreated").value = "";
        // reset the UI
        document.getElementById("editElement").style.display =
            "none";
        document.getElementById("addElement").style.display = "none";
        document.getElementById("showElements").style.display =
            "block";
    };
    /**
     * Check if the input fields are both valid, and if so, do an AJAX call.
     * @type {function}
     */
    EditEntryForm.prototype.submitForm = function () {
        var _this = this;
        console.log("Submit edit form called.");
        // get the values of the two fields, force them to be strings, and check
        // that neither is empty
        var title = "" + document.getElementById("editTitle").value;
        var msg = "" + document.getElementById("editMessage").value;
        var id = "" + document.getElementById("editId").value;
        if (title === "" || msg === "" || id === "") {
            InvalidContentMsg();
            console.log("Error: title, message, or id is not valid");
            return;
        }
        /** set up an AJAX PUT. When the server replies, the result will go to onSubmitResponse
         * @type {function}
        */
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(componentName, "/").concat(id), {
                            method: "PUT",
                            body: JSON.stringify({
                                mTitle: title, //mTitle
                                mMessage: msg,
                            }),
                            headers: {
                                "Content-type": "application/json; charset=UTF-8",
                            },
                        })
                            .then(function (response) {
                            // If we get an "ok" message, return the json
                            if (response.ok) {
                                // return response.json();
                                return Promise.resolve(response.json());
                            }
                            // Otherwise, handle server errors with a detailed popup message
                            else {
                                console.log("The server replied not ok: ".concat(response.status, "\n") +
                                    response.statusText);
                            }
                            // return response;
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            editEntryForm.onSubmitResponse(data);
                            console.log(data);
                        })
                            .catch(function (error) {
                            console.warn("Something went wrong.", error);
                            console.log("Unspecified error");
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // make the AJAX post and output value or error message to console
        doAjax().then(console.log).catch(console.log);
    };
    /**
     * onSubmitResponse runs when the AJAX call in submitForm() returns a
     * result.
     * @param data The object returned by the server
     */
    EditEntryForm.prototype.onSubmitResponse = function (data) {
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
    };
    EditEntryForm.prototype.updateUserProfile = function (data) {
        var userId = "user_id"; //needs to be updated
        fetch("".concat(backendUrl, "/user/profile/").concat(userId), {
            method: "PUT",
            body: JSON.stringify(data),
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
        })
            .then(function (response) { return response.json(); })
            .then(function (data) {
            console.log("Profile updated:", data);
        })
            .catch(function (error) {
            console.error("Error updating profile:", error);
        });
    };
    return EditEntryForm;
}());
/** Global variable to be referenced for ElementList
 * @type {var}
*/
var mainList;
/**
 * ElementList provides a way to see the data stored in server
 * @type {class name}
 */
var ElementList = /** @class */ (function () {
    function ElementList() {
    }
    ElementList.prototype.submitComment = function (postId, comment) {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/user/comments/").concat(postId), {
                            method: "POST",
                            body: JSON.stringify({
                                postId: postId,
                                comment: comment,
                            }),
                            headers: {
                                "Content-type": "application/json; charset=UTF-8",
                            },
                        })
                            .then(function (response) { return response.json(); })
                            .then(function (data) {
                            console.log("Comment submitted", data);
                        })
                            .catch(function (error) { return console.error("Error submitting comment:", error); })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        });
    };
    ElementList.prototype.refresh = function () {
        var _this = this;
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(componentName), {
                            method: "GET",
                            headers: {
                                "Content-type": "application/json; charset=UTF-8",
                            },
                        })
                            .then(function (response) {
                            if (response.ok) {
                                return Promise.resolve(response.json());
                            }
                            else {
                                console.log("The server replied not ok: ".concat(response.status, "\n") +
                                    response.statusText);
                            }
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            mainList.update(data);
                            console.log(data);
                        })
                            .catch(function (error) {
                            console.warn("Something went wrong", error);
                            console.log("Unspecified error");
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // post AJAX values to console
        doAjax().then(console.log).catch(console.log);
    };
    /**
     * Update the data
     * @param data to be updated
     */
    ElementList.prototype.update = function (data) {
        var _this = this;
        var elem_messageList = document.getElementById("messageList");
        if (elem_messageList !== null) {
            elem_messageList.innerHTML = "";
            var fragment = document.createDocumentFragment();
            var table = document.createElement("table");
            var _loop_1 = function (i) {
                var tr = document.createElement("tr");
                var td_title = document.createElement("td");
                var td_id = document.createElement("td");
                var td_like = document.createElement("td");
                var td_dislike = document.createElement("td");
                td_title.innerHTML = "<div id = \"postTitle\">" + data.mData[i].mSubject + "</div><br><div id = \"postBody\">" + data.mData[i].mMessage + "</div>";
                td_id.innerHTML = data.mData[i].mId;
                td_like.innerHTML = data.mData[i].numLikes;
                td_dislike.innerHTML = data.mData[i].numLikes;
                var dislikeButton = document.createElement("button");
                dislikeButton.textContent = "Dislike";
                dislikeButton.className = "dislikebtn";
                td_dislike.appendChild(dislikeButton);
                tr.appendChild(td_like);
                tr.appendChild(td_dislike);
                tr.appendChild(this_1.buttons(data.mData[i].mId));
                // Add a comment form for each post
                var commentForm = document.createElement("form");
                commentForm.innerHTML = "\n          <input type=\"text\" name=\"comment\" placeholder=\"Write a comment...\" />\n          <button type=\"submit\">Comment</button>\n        ";
                commentForm.onsubmit = function (e) {
                    e.preventDefault();
                    _this.submitComment(data.mData[i].mId, commentForm.comment.value);
                };
                tr.appendChild(commentForm);
                table.appendChild(tr);
            };
            var this_1 = this;
            for (var i = 0; i < data.mData.length; ++i) {
                _loop_1(i);
            }
            fragment.appendChild(table);
            elem_messageList.appendChild(fragment);
        }
        // Find all of the delete buttons, and set their behavior
        var all_delbtns = (document.getElementsByClassName("delbtn"));
        for (var i = 0; i < all_delbtns.length; ++i) {
            all_delbtns[i].addEventListener("click", function (e) {
                mainList.clickDelete(e);
            });
        }
        // Find all of the edit buttons, and set their behavior
        var all_editbtns = (document.getElementsByClassName("editbtn"));
        for (var i = 0; i < all_editbtns.length; ++i) {
            all_editbtns[i].addEventListener("click", function (e) {
                mainList.clickEdit(e);
            });
        }
        var all_likebtns = (document.getElementsByClassName("likebtn"));
        for (var i = 0; i < all_likebtns.length; ++i) {
            all_likebtns[i].addEventListener("click", function (e) {
                mainList.clickLike(e);
            });
        }
        var all_dislikebtns = (document.getElementsByClassName("dislikebtn"));
        for (var i = 0; i < all_dislikebtns.length; ++i) {
            all_dislikebtns[i].addEventListener("click", function (e) {
                mainList.clickDislike(e);
            });
        }
    };
    /**
     * Adds a delete, edit button to the HTML for each row
     * @param id
     * @returns a new button
     */
    ElementList.prototype.buttons = function (id) {
        var fragment = document.createDocumentFragment();
        var td = document.createElement("td");
        // create edit button, add to new td, add td to returned fragment
        var btn = document.createElement("button");
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
    };
    /**
     * Delete the item off the table
     * @param e to be deleted
     */
    ElementList.prototype.clickDelete = function (e) {
        var _this = this;
        var id = e.target.getAttribute("data-value");
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(componentName, "/").concat(id), {
                            // Grab the element from "database"
                            method: "DELETE",
                            headers: {
                                "Content-type": "application/json; charset=UTF-8",
                            },
                        })
                            .then(function (response) {
                            if (response.ok) {
                                return Promise.resolve(response.json());
                            }
                            else {
                                console.log("The server replied not ok: ".concat(response.status, "\n") +
                                    response.statusText);
                            }
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            mainList.refresh();
                            console.log(data);
                        })
                            .catch(function (error) {
                            console.warn("Something went wrong. ", error);
                            console.log("Unspecified error");
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // post AJAX values to console
        doAjax().then(console.log).catch(console.log);
    };
    /**
     * Ajax function that sends HTTP function to update like count
     * @param e
     */
    ElementList.prototype.clickLike = function (e) {
        var _this = this;
        var id = e.target.getAttribute("data-value");
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/user/upvote/").concat(id), {
                            // Grab the element from "database"
                            method: "PUT",
                            headers: {
                                "Content-type": "application/json; charset=UTF-8",
                            },
                        })
                            .then(function (response) {
                            if (response.ok) {
                                return Promise.resolve(response.json());
                            }
                            else {
                                console.log("The server replied not ok: ".concat(response.status, "\n") +
                                    response.statusText);
                            }
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            mainList.refresh();
                            console.log(data);
                        })
                            .catch(function (error) {
                            console.warn("Something went wrong. ", error);
                            console.log("Unspecified error");
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // post AJAX values to console
        doAjax().then(console.log).catch(console.log);
    };
    /**
   * Ajax function that sends HTTP function to update like count (by decrementing)
   * @param e
   */
    ElementList.prototype.clickDislike = function (e) {
        var _this = this;
        var id = e.target.getAttribute("data-value");
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/user/downvote/").concat(id), {
                            // HTTP PUT request for disliking a post
                            method: "PUT",
                            headers: {
                                "Content-type": "application/json; charset=UTF-8",
                            },
                        })
                            .then(function (response) {
                            if (response.ok) {
                                return Promise.resolve(response.json());
                            }
                            else {
                                console.log("The server replied not ok: ".concat(response.status, "\n") +
                                    response.statusText);
                            }
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            // Refresh the main message list to reflect the changes
                            mainList.refresh();
                            console.log(data);
                        })
                            .catch(function (error) {
                            console.warn("Something went wrong. ", error);
                            console.log("Unspecified error");
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // Execute the AJAX request
        doAjax().then(console.log).catch(console.log);
    };
    /**
     * clickEdit is the code we run in response to a click of a delete button
     * @param e
     */
    ElementList.prototype.clickEdit = function (e) {
        var _this = this;
        // as in clickDelete, we need the ID of the row
        var id = e.target.getAttribute("data-value");
        // Issue an AJAX GET and then pass the result to editEntryForm.init()
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(componentName, "/").concat(id), {
                            method: "GET",
                            headers: {
                                "Content-type": "application/json; charset=UTF-8",
                            },
                        })
                            .then(function (response) {
                            if (response.ok) {
                                return Promise.resolve(response.json());
                            }
                            else {
                                console.log("The server replied not ok: ".concat(response.status, "\n") +
                                    response.statusText);
                            }
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            editEntryForm.init(data);
                            console.log(data);
                        })
                            .catch(function (error) {
                            console.warn("Something went wrong.", error);
                            console.log("Unspecified error");
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // make the AJAX post and output value or error message to console
        doAjax().then(console.log).catch(console.log);
    };
    return ElementList;
}());
/**
 * Run some configuration code when the web page loads
 * @type {function}
 */
document.addEventListener("DOMContentLoaded", function () {
    var _a, _b;
    // Run configuration code when the web page loads
    document.addEventListener("DOMContentLoaded", function () {
        // Existing code...
        // Handle profile update form submission
        var profileForm = document.getElementById("profileForm");
        if (profileForm) {
            profileForm.addEventListener("submit", function (event) {
                event.preventDefault();
                var formData = new FormData(profileForm);
                var userProfile = {
                    username: formData.get("username"),
                    email: formData.get("email"),
                    sexualIdentity: formData.get("sexualIdentity"),
                    genderOrientation: formData.get("genderOrientation"),
                    note: formData.get("note"),
                };
                newEntryForm.updateUserProfile(userProfile);
                editEntryForm.updateUserProfile(userProfile);
            });
        }
        // Existing code...
    });
    // set up initial UI state
    document.getElementById("editElement").style.display =
        "none";
    document.getElementById("addElement").style.display = "none";
    document.getElementById("showElements").style.display =
        "block";
    (_a = document
        .getElementById("showFormButton")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", function (e) {
        document.getElementById("addElement").style.display =
            "block";
        document.getElementById("showElements").style.display =
            "none";
    });
    (_b = document
        .getElementById("refreshFormButton")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", function (e) {
        mainList.refresh();
    });
    // Create the object that controls the "New Entry" form
    newEntryForm = new NewEntryForm();
    editEntryForm = new EditEntryForm();
    mainList = new ElementList();
    mainList.refresh();
    console.log("DOMContentLoaded");
}, false);
/**
 * Unhide error message from HTML index for a moment
 * @returns {HTMLBodyElement}
 */
function InvalidContentMsg() {
    var contentError = document.getElementById("InvalidContent");
    contentError.style.display = "block";
    setTimeout(function () {
        contentError.style.display = "none";
    }, 2000);
}
