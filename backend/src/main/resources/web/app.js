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
/**
 * Prevent compiler errors when using jQuery by test
 * setting $ to any
 * @type {Object}
 */
var $;
/**
 * Global variable to be referenced for newEntryForm
 * @type {NewEntryForm}
 */
var newEntryForm;
/**
 * Backend server link to dokku
 * @type {string}
 */
var backendUrl = ""; //"https://team-stultus.dokku.cse.lehigh.edu"; //"https://2024sp-tutorial-del226.dokku.cse.lehigh.edu";
/**
 * Component name to fetch resources
 * @type {string}
 */
var messages = "messages";
/**
 * Component name to fetch resources
 * @type {string}
 */
var user = "user";
/**
 * Component name to fetch resources
 * @type {string}
 */
var comment = "comment";
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
        /**
         * The HTML element for title
         * @type {HTMLInputElement}
         */
        this.title = document.getElementById("newTitle");
        /**
         * The HTML element for message
         * @type {HTMLInputElement}
         */
        this.message = document.getElementById("newMessage");
        /**
         * The HTML element for the container of the entire module
         * @type {HTMLElement}
         */
        this.container = document.getElementById("addElement");
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
        newEntryForm.title.value = "";
        newEntryForm.message.value = "";
        // reset the UI
        hideAll();
        mainList.container.style.display = "block";
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
        var title = "" + newEntryForm.title.value;
        var msg = "" + newEntryForm.message.value;
        if (title === "" || msg === "") {
            InvalidContentMsg("Error: title/msg is not valid", null);
            return;
        }
        /**
         * do a POST (create) and do onSubmitResponse
         * @type {function}
         */
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(user, "/addMessage"), {
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
                                InvalidContentMsg("The server replied not ok:", response);
                            }
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            newEntryForm.onSubmitResponse(data);
                            mainList.refresh();
                        })
                            .catch(function (error) {
                            InvalidContentMsg("Error (Did you sign in?):", error);
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // post AJAX values to console
        doAjax().then().catch(console.log);
    };
    /**
     * Runs when AJAX call in submitForm() returns a result
     * @param data obj returned by server
     * @type {function}
     */
    NewEntryForm.prototype.onSubmitResponse = function (data) {
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
        var _a, _b;
        /**
         * The HTML element for title
         * @type {HTMLInputElement}
         */
        this.title = document.getElementById("editTitle");
        /**
         * The HTML element for message
         * @type {HTMLInputElement}
         */
        this.message = document.getElementById("editMessage");
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
        (_a = document.getElementById("editCancel")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", function (e) {
            editEntryForm.clearForm();
        });
        (_b = document.getElementById("editButton")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", function (e) {
            editEntryForm.submitForm();
        });
    }
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @param data
     * @type {function}
     */
    EditEntryForm.prototype.init = function (data) {
        // Fill the edit form when we receive ok
        if (data.mStatus === "ok") {
            editEntryForm.title.value = data.mData.mSubject;
            editEntryForm.message.value = data.mData.mMessage;
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
    };
    /**
     * Clear the form's input fields
     * @type {function}
     */
    EditEntryForm.prototype.clearForm = function () {
        editEntryForm.title.value = "";
        editEntryForm.message.value = "";
        editEntryForm.id.value = "";
        // reset the UI
        hideAll();
        mainList.container.style.display = "block";
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
        var title = "" + editEntryForm.title.value;
        var msg = "" + editEntryForm.message.value;
        var id = "" + editEntryForm.id.value;
        if (title === "" || msg === "" || id === "") {
            InvalidContentMsg("Error: title, message, or id is not valid", null);
            return;
        }
        /**
         * Set up an AJAX PUT. When the server replies, the result will go to onSubmitResponse
         * @type {function}
         */
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(user, "/editMessage/").concat(id), {
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
                                return Promise.resolve(response.json());
                            }
                            // Otherwise, handle server errors with a detailed popup message
                            else {
                                InvalidContentMsg("The server replied not ok:", response);
                            }
                            // return response;
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            editEntryForm.onSubmitResponse(data);
                        })
                            .catch(function (error) {
                            InvalidContentMsg("Error: ", error);
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // make the AJAX post and output value or error message to console
        doAjax().then().catch(console.log);
    };
    /**
     * onSubmitResponse runs when the AJAX call in submitForm() returns a
     * result.
     * @param data The object returned by the server
     * @type {function}
     */
    EditEntryForm.prototype.onSubmitResponse = function (data) {
        // If we get an "ok" message, clear the form and refresh the main
        // listing of messages
        if (data.mStatus === "ok") {
            editEntryForm.clearForm();
            mainList.refresh();
        }
        // Handle explicit errors with a detailed popup message
        else if (data.mStatus === "err") {
            InvalidContentMsg("The server replied with an error:\n" + data.mMessage, null);
        }
        // Handle other errors with a less-detailed popup message
        else {
            InvalidContentMsg("Unspecified error has occured", null);
        }
    };
    return EditEntryForm;
}());
/**
 * Global variable to be referenced for ElementList
 * @type {ElementList}
 */
var mainList;
/**
 * ElementList provides a way to see the data stored in server
 * @type {class ElementList}
 */
var ElementList = /** @class */ (function () {
    function ElementList() {
        /**
         * The HTML element for the container of the entire module
         * @type {HTMLElement}
         */
        this.container = document.getElementById("showElements");
    }
    /**
     * Refresh updates the messageList
     * @type {function}
     */
    ElementList.prototype.refresh = function () {
        var _this = this;
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(messages), {
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
                                InvalidContentMsg("The server replied not ok:", response);
                            }
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            mainList.update(data);
                        })
                            .catch(function (error) {
                            InvalidContentMsg("Error: ", error);
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // post AJAX values to console
        doAjax().then().catch(console.log);
    };
    /**
     * Simple ajax to call user information based on user id
     * @param userid the userid to find information
     * @return UserDataLite of email, username, and note
     * @type {function}
     */
    ElementList.prototype.getUser = function (userid) {
        var _this = this;
        return new Promise(function (resolve, reject) {
            var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
                var response, data, error_1;
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0:
                            _a.trys.push([0, 3, , 4]);
                            return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(user, "/").concat(userid), {
                                    method: "GET",
                                    headers: {
                                        "Content-type": "application/json; charset=UTF-8",
                                    },
                                })];
                        case 1:
                            response = _a.sent();
                            if (!response.ok) {
                                InvalidContentMsg("The server replied not ok:", response);
                            }
                            return [4 /*yield*/, response.json()];
                        case 2:
                            data = _a.sent();
                            if (data.mStatus === "ok" && data.mData) {
                                resolve(data);
                            }
                            else {
                                throw new Error("User not found or data structure incorrect");
                            }
                            return [3 /*break*/, 4];
                        case 3:
                            error_1 = _a.sent();
                            reject(error_1);
                            return [3 /*break*/, 4];
                        case 4: return [2 /*return*/];
                    }
                });
            }); };
            // Execute AJAX call
            doAjax();
        });
    };
    /**
     * Simple ajax to call current user profile
     * @return UserData of email, username, gender, SO, and note
     * @type {function}
     */
    ElementList.prototype.getMyProfile = function () {
        return new Promise(function (resolve, reject) {
            fetch("".concat(backendUrl, "/").concat(user), {
                method: "GET",
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
            })
                .then(function (response) {
                if (!response.ok) {
                    InvalidContentMsg("The server replied not ok:", response);
                }
                return response.json();
            })
                .then(function (data) {
                var _a;
                if (data.mStatus === "ok" && ((_a = data.mData) === null || _a === void 0 ? void 0 : _a.uID)) {
                    resolve(data.mData.uID);
                }
                else {
                    throw new Error("User ID not found or data structure incorrect");
                }
            })
                .catch(function (error) { return reject(error); });
        });
    };
    /**
     * Simple ajax to update current user profile
     * @param data the /messages JSON object to parse information
     * @type {function}
     */
    ElementList.prototype.update = function (data) {
        return __awaiter(this, void 0, void 0, function () {
            /**
             * function the set up all action buttons, including voting
             * @type {function}
             */
            function setButtons() {
                var all_delbtns = Array.from(document.getElementsByClassName("delbtn"));
                for (var _i = 0, all_delbtns_1 = all_delbtns; _i < all_delbtns_1.length; _i++) {
                    var element = all_delbtns_1[_i];
                    element.addEventListener("click", function (e) {
                        mainList.clickDelete(e);
                    });
                }
                // Find all of the edit buttons, and set their behavior
                var all_editbtns = Array.from(document.getElementsByClassName("editbtn"));
                for (var _a = 0, all_editbtns_1 = all_editbtns; _a < all_editbtns_1.length; _a++) {
                    var element = all_editbtns_1[_a];
                    element.addEventListener("click", function (e) {
                        mainList.clickEdit(e);
                    });
                }
                // Find all of the upvote buttons, and set their behavior
                var all_likebtns = Array.from(document.getElementsByClassName("likebtn"));
                for (var _b = 0, all_likebtns_1 = all_likebtns; _b < all_likebtns_1.length; _b++) {
                    var element = all_likebtns_1[_b];
                    element.addEventListener("click", function (e) {
                        mainList.clickUpvote(e);
                    });
                }
                // Find all of the downvote buttons, and set their behavior
                var all_dislikebtns = Array.from(document.getElementsByClassName("dislikebtn"));
                for (var _c = 0, all_dislikebtns_1 = all_dislikebtns; _c < all_dislikebtns_1.length; _c++) {
                    var element = all_dislikebtns_1[_c];
                    element.addEventListener("click", function (e) {
                        mainList.clickDownvote(e);
                    });
                }
                // Find all of the comment buttons, and set their behavior
                var all_commentbtns = Array.from(document.getElementsByClassName("commentbtn"));
                for (var _d = 0, all_commentbtns_1 = all_commentbtns; _d < all_commentbtns_1.length; _d++) {
                    var element = all_commentbtns_1[_d];
                    element.addEventListener("click", function (e) {
                        mainList.clickComment(e);
                    });
                }
            }
            var myUserId, elem_messageList, table, _loop_1, _i, _a, element;
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0:
                        console.log("Updating main table");
                        return [4 /*yield*/, mainList.getMyProfile()];
                    case 1:
                        myUserId = _b.sent();
                        elem_messageList = document.getElementById("messageList");
                        if (!(elem_messageList !== null)) return [3 /*break*/, 6];
                        table = document.createElement("table");
                        _loop_1 = function (element) {
                            var rowData, td_content, td_actions, tr;
                            return __generator(this, function (_c) {
                                switch (_c.label) {
                                    case 0:
                                        rowData = element;
                                        // Check if user information is available
                                        if (!rowData.mUserID) {
                                            console.log("Skipping row with no user information");
                                            return [2 /*return*/, "continue"];
                                        }
                                        td_actions = "";
                                        // Get the user information asynchronously
                                        return [4 /*yield*/, mainList
                                                .getUser(rowData.mUserID)
                                                .then(function (data) {
                                                // Constructing the HTML for the second column (title, body, and voting)
                                                var titleAndBodyHTML = "\n            <div class=\"postTitle\"><i>#".concat(rowData.mId, ":</i> ").concat(rowData.mSubject, "</div>\n            <div class=\"postAuthor\">By <a href=\"mailto:").concat(data.mData.uEmail, "\" data-value=").concat(rowData.mUserID, " class=\"userLink\">").concat(data.mData.uUsername, "</a></div>\n            <div class=\"postBody\">").concat(rowData.mMessage, "</div>\n            <div class=\"voting-container\">\n              <ul class=\"voting\">\n                <li><button class=\"likebtn\" data-value=\"").concat(rowData.mId, "\">&#8593;</button></li>\n                <li class=\"totalVote\">").concat(rowData.numLikes, "</li>\n                <li><button class=\"dislikebtn\" data-value=\"").concat(rowData.mId, "\">&#8595;</button></li>\n              </ul>\n            </div>\n          ");
                                                td_content = titleAndBodyHTML;
                                                // If the logged-in user is the author of the post, append the edit and delete button
                                                if (rowData.mUserID === myUserId) {
                                                    var editButtonHTML = "<button class=\"editbtn\" data-value=\"".concat(rowData.mId, "\">Edit</button>");
                                                    var delButtonHTML = "<button class=\"delbtn\" data-value=\"".concat(rowData.mId, "\">Delete</button>");
                                                    td_actions = editButtonHTML + delButtonHTML;
                                                }
                                                // Create a comment button
                                                var commentButtonHTML = "<button class=\"commentbtn\" data-value=\"".concat(rowData.mId, "\">Comment</button>");
                                                td_actions += commentButtonHTML;
                                            })
                                                .catch(function (error) {
                                                // Catch any errors
                                                console.error("Failed to get post with no user:", error);
                                            })];
                                    case 1:
                                        // Get the user information asynchronously
                                        _c.sent();
                                        tr = document.createElement("tr");
                                        // Append the body content and the action buttons
                                        tr.innerHTML = "<td>".concat(td_content).concat(td_actions, "</td>");
                                        table.appendChild(tr); // Append the row to the table
                                        return [2 /*return*/];
                                }
                            });
                        };
                        _i = 0, _a = data.mData;
                        _b.label = 2;
                    case 2:
                        if (!(_i < _a.length)) return [3 /*break*/, 5];
                        element = _a[_i];
                        return [5 /*yield**/, _loop_1(element)];
                    case 3:
                        _b.sent();
                        _b.label = 4;
                    case 4:
                        _i++;
                        return [3 /*break*/, 2];
                    case 5:
                        elem_messageList.innerHTML = ""; // Clear the messageList container
                        elem_messageList.appendChild(table); // Append the table to the messageList container
                        _b.label = 6;
                    case 6:
                        // Find all of the action buttons, and set their behavior
                        setButtons();
                        getAllUserBtns();
                        return [2 /*return*/];
                }
            });
        });
    };
    /**
     * clickDelete is the code we run in response to a click of a delete button
     * @param e Event to get the message to be deleted
     * @type {function}
     */
    ElementList.prototype.clickDelete = function (e) {
        var _this = this;
        console.log("Delete called");
        var id = e.target.getAttribute("data-value");
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(user, "/deleteMessage/").concat(id), {
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
                                InvalidContentMsg("The server replied not ok:", response);
                            }
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            mainList.refresh();
                        })
                            .catch(function (error) {
                            InvalidContentMsg("Error: ", error);
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // post AJAX values to console
        doAjax().then().catch(console.log);
    };
    /**
     * clickUpvote is the code we run in response to a click of a upvote button
     * @type {function}
     * @param e Event to get the message to be upvoted
     */
    ElementList.prototype.clickUpvote = function (e) {
        var _this = this;
        console.log("Upvote called");
        var id = e.target.getAttribute("data-value");
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(user, "/upvote/").concat(id), {
                            // Grab the element from "database"
                            method: "POST",
                            headers: {
                                "Content-type": "application/json; charset=UTF-8",
                            },
                        })
                            .then(function (response) {
                            if (response.ok) {
                                return Promise.resolve(response.json());
                            }
                            else {
                                InvalidContentMsg("The server replied not ok:", response);
                            }
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            mainList.refresh();
                        })
                            .catch(function (error) {
                            InvalidContentMsg("Error: ", error);
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // post AJAX values to console
        doAjax().then().catch(console.log);
    };
    /**
     * clickDownvote is the code we run in response to a click of a downvote button
     * @type {function}
     * @param e Event to get the message to be downvoted
     */
    ElementList.prototype.clickDownvote = function (e) {
        var _this = this;
        console.log("Downvote called");
        var id = e.target.getAttribute("data-value");
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(user, "/downvote/").concat(id), {
                            // Grab the element from "database"
                            method: "POST",
                            headers: {
                                "Content-type": "application/json; charset=UTF-8",
                            },
                        })
                            .then(function (response) {
                            if (response.ok) {
                                return Promise.resolve(response.json());
                            }
                            else {
                                InvalidContentMsg("The server replied not ok:", response);
                            }
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            mainList.refresh();
                        })
                            .catch(function (error) {
                            InvalidContentMsg("Error: ", error);
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // post AJAX values to console
        doAjax().then().catch(console.log);
    };
    /**
     * clickEdit is the code we run in response to a click of a edit button
     * @param e Event to get the message to be editGender
     * @type {function}
     */
    ElementList.prototype.clickEdit = function (e) {
        var _this = this;
        // as in clickDelete, we need the ID of the row
        var id = e.target.getAttribute("data-value");
        // Issue an AJAX GET and then pass the result to editEntryForm.init()
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(messages, "/").concat(id), {
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
                                InvalidContentMsg("The server replied not ok:", response);
                            }
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            editEntryForm.init(data);
                        })
                            .catch(function (error) {
                            InvalidContentMsg("Error: ", error);
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // make the AJAX post and output value or error message to console
        doAjax().then().catch(console.log);
    };
    /**
     * clickComment is the code we run in response to a click of a comment button
     * @param e Event to get the message to be commented
     * @type {function}
     */
    ElementList.prototype.clickComment = function (e) {
        var _this = this;
        // as in clickDelete, we need the ID of the row
        var id = e.target.getAttribute("data-value");
        // Issue an AJAX GET and then pass the result to editEntryForm.init()
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(messages, "/").concat(id), {
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
                                InvalidContentMsg("The server replied not ok:", response);
                            }
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            commentForm.init(data);
                        })
                            .catch(function (error) {
                            InvalidContentMsg("Error: ", error);
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // make the AJAX post and output value or error message to console
        doAjax().then().catch(console.log);
    };
    return ElementList;
}());
/**
 * Global variable to be referenced for Profile
 * @type {Profile}
 */
var profile;
/**
 * Profile is all the code to edit and view a current user's profile
 * @type {class Profile}
 */
var Profile = /** @class */ (function () {
    function Profile() {
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
         * @type {HTMLTextAreaElement}
         */
        this.noteBox = document.getElementById("editNote");
        /**
         * The HTML element for the container of the entire module
         * @type {HTMLElement}
         */
        this.container = document.getElementById("profileForm");
        (_a = document.getElementById("UserCancel")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", function (e) {
            profile.clearForm();
        });
        (_b = document.getElementById("editUser")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", function (e) {
            profile.submitForm();
        });
    }
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @type {function}
     */
    Profile.prototype.init = function () {
        return __awaiter(this, void 0, void 0, function () {
            var response, data, error_2;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        console.log("Current User profile called");
                        _a.label = 1;
                    case 1:
                        _a.trys.push([1, 4, , 5]);
                        return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(user), {
                                method: "GET",
                                headers: {
                                    "Content-type": "application/json; charset=UTF-8",
                                },
                            })];
                    case 2:
                        response = _a.sent();
                        if (!response.ok) {
                            InvalidContentMsg("The server replied not ok:", response);
                        }
                        return [4 /*yield*/, response.json()];
                    case 3:
                        data = _a.sent();
                        profile.fillForm(data);
                        return [3 /*break*/, 5];
                    case 4:
                        error_2 = _a.sent();
                        InvalidContentMsg("Error: ", error_2);
                        return [3 /*break*/, 5];
                    case 5: return [2 /*return*/];
                }
            });
        });
    };
    /**
     * Fill out the profile form with user information
     * when clicked
     * @param data the UserData JSON
     * @type {function}
     */
    Profile.prototype.fillForm = function (data) {
        if (data.mStatus === "ok") {
            // Get the username
            profile.nameBox.value = data.mData.uUsername;
            // Get the gender
            var genderValue = data.mData.uGender;
            fillGender(genderValue);
            // Get the SO
            var sO = data.mData.uSO.toLowerCase(); // Convert to lowercase for case-insensitive comparison
            fillSO(sO);
            // Get the note
            profile.noteBox.value = data.mData.uNote;
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
                for (var i = 0; i < profile.soMenu.options.length; i++) {
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
                for (var i = 0; i < profile.genderMenu.options.length; i++) {
                    if (profile.genderMenu.options[i].value === genderValue.toString()) {
                        profile.genderMenu.selectedIndex = i;
                        break;
                    }
                }
            }
        }
    };
    /**
     * Clears the form
     * @type {function}
     */
    Profile.prototype.clearForm = function () {
        hideAll();
        mainList.container.style.display = "block";
    };
    /**
     * Submit the form to update user profile
     * @type {function}
     */
    Profile.prototype.submitForm = function () {
        // Extract values from form fields
        var username = profile.nameBox.value;
        var userGender = profile.genderMenu.value;
        var userSO = profile.soMenu.value;
        var userNote = profile.noteBox.value;
        // Make the AJAX PUT request
        fetch("".concat(backendUrl, "/").concat(user), {
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
            .then(function (response) {
            if (response.ok) {
                return response.json();
            }
            else {
                InvalidContentMsg("The server replied not ok:", response);
                return Promise.reject(response);
            }
        })
            .then(function (data) {
            // Handle the response from the server
            profile.onSubmitResponse(data);
        })
            .catch(function (error) {
            InvalidContentMsg("Error (Did you sign in?):", error);
        });
    };
    /**
     * onSubmitResponse runs when the AJAX call in submitForm() returns a
     * result.
     * @param data The object returned by the server
     */
    Profile.prototype.onSubmitResponse = function (data) {
        console.log("Updating user information");
        // If we get an "ok" message, clear the form and refresh the main
        // listing of messages
        if (data.mStatus === "ok") {
            profile.clearForm();
            mainList.refresh();
        }
        // Handle explicit errors with a detailed popup message
        else if (data.mStatus === "err") {
            InvalidContentMsg("The server replied with an error:\n" + data.mMessage, null);
        }
        // Handle other errors with a less-detailed popup message
        else {
            InvalidContentMsg("Unspecified error has occured", null);
        }
    };
    return Profile;
}());
/**
 * Global variable to be referenced for CommentForm
 * @type {CommentForm}
 */
var commentForm;
/**
 * CommentForm is all the code to add comments to a post
 * @type {class CommentForm}
 */
var CommentForm = /** @class */ (function () {
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @type {function}
     */
    function CommentForm() {
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
         * @type {HTMLTextAreaElement}
         */
        this.textbox = document.getElementById("addComment");
        (_a = document.getElementById("commentCancel")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", function (e) {
            commentForm.clearForm();
        });
        (_b = document.getElementById("commentButton")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", function (e) {
            commentForm.submitForm();
        });
    }
    /**
     * Initialize the object by setting the post's content to form
     * @param data The data object received from the server
     * @type {function}
     */
    CommentForm.prototype.init = function (data) {
        return __awaiter(this, void 0, void 0, function () {
            var userData, error_3;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        _a.trys.push([0, 4, , 5]);
                        if (!(data.mStatus === "ok" && data.mData)) return [3 /*break*/, 2];
                        return [4 /*yield*/, mainList.getUser(data.mData.mUserID)];
                    case 1:
                        userData = _a.sent();
                        // Set post title
                        commentForm.title.innerHTML = "<i>#".concat(data.mData.mId, ":</i> ").concat(data.mData.mSubject);
                        // Set post body
                        commentForm.body.innerHTML = data.mData.mMessage;
                        // Set author's username and email
                        commentForm.author.innerHTML = "By <a href=\"mailto:".concat(userData.mData.uEmail, "\" data-value=").concat(userData.mData.uID, " class=\"userLink\">").concat(userData.mData.uUsername, "</a>");
                        commentForm.id.value = data.mData.mId;
                        return [3 /*break*/, 3];
                    case 2:
                        if (data.mStatus === "err") {
                            InvalidContentMsg("The server replied with an error:\n" + data.mMessage, null);
                        }
                        else {
                            InvalidContentMsg("Unspecified error has occured", null);
                        }
                        _a.label = 3;
                    case 3:
                        hideAll();
                        commentForm.container.style.display = "block";
                        commentForm.getComment(data);
                        return [3 /*break*/, 5];
                    case 4:
                        error_3 = _a.sent();
                        console.error("Error initializing comment form:", error_3);
                        return [3 /*break*/, 5];
                    case 5:
                        getAllUserBtns();
                        return [2 /*return*/];
                }
            });
        });
    };
    /**
     * Get comments
     * @type {function}
     * @param data JSON object of all comments to a post
     */
    CommentForm.prototype.getComment = function (data) {
        var _this = this;
        var id = data.mData.mId;
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(messages, "/").concat(id, "/comments"), {
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
                                InvalidContentMsg("The server replied not ok:", response);
                            }
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            commentForm.createTable(data);
                        })
                            .catch(function (error) {
                            InvalidContentMsg("Error: ", error);
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // post AJAX values to console
        doAjax().then().catch(console.log);
    };
    /**
     * Create a table of comments
     * @param data  JSON of all comments from post
     * @type {function}
     */
    CommentForm.prototype.createTable = function (data) {
        return __awaiter(this, void 0, void 0, function () {
            var myUserId, comment_list, table, _loop_2, _i, _a, element, all_editbtns, _b, all_editbtns_2, element;
            return __generator(this, function (_c) {
                switch (_c.label) {
                    case 0: return [4 /*yield*/, mainList.getMyProfile()];
                    case 1:
                        myUserId = _c.sent();
                        comment_list = document.getElementById("commentList");
                        if (!(comment_list !== null)) return [3 /*break*/, 6];
                        table = document.createElement("table");
                        _loop_2 = function (element) {
                            var rowData, td_content, td_actions, tr;
                            return __generator(this, function (_d) {
                                switch (_d.label) {
                                    case 0:
                                        rowData = element;
                                        // Check if user information is available
                                        if (!rowData.cUserID) {
                                            console.error("Skipping row with no user information");
                                            return [2 /*return*/, "continue"];
                                        }
                                        td_actions = "";
                                        // Get the user information asynchronously
                                        return [4 /*yield*/, mainList
                                                .getUser(rowData.cUserID)
                                                .then(function (data) {
                                                // Constructing the HTML for the comment
                                                var commentHTML = "\n                    <div class=\"commentAuthor\">By <a href=\"mailto:".concat(data.mData.uEmail, "\" data-value=").concat(rowData.cUserID, " class=\"userLink\">").concat(data.mData.uUsername, "</a></div>\n                    <div class=\"commentBody\">").concat(rowData.cMessage, "</div>\n                ");
                                                td_content = commentHTML;
                                                // If the logged-in user is the author of the comment, append the edit button
                                                if (rowData.cUserID === myUserId) {
                                                    var editButtonHTML = "<button class=\"editcommentbtn\" data-value=\"".concat(rowData.cId, "\">Edit</button>");
                                                    td_actions = editButtonHTML;
                                                }
                                            })
                                                .catch(function (error) {
                                                console.error("Failed to get comment with no user:", error);
                                            })];
                                    case 1:
                                        // Get the user information asynchronously
                                        _d.sent();
                                        tr = document.createElement("tr");
                                        tr.innerHTML = "<td>".concat(td_content).concat(td_actions, "</td>");
                                        table.appendChild(tr); // Append the row to the table
                                        return [2 /*return*/];
                                }
                            });
                        };
                        _i = 0, _a = data.mData;
                        _c.label = 2;
                    case 2:
                        if (!(_i < _a.length)) return [3 /*break*/, 5];
                        element = _a[_i];
                        return [5 /*yield**/, _loop_2(element)];
                    case 3:
                        _c.sent();
                        _c.label = 4;
                    case 4:
                        _i++;
                        return [3 /*break*/, 2];
                    case 5:
                        comment_list.innerHTML = ""; // Clear the commentList container
                        comment_list.appendChild(table); // Append the table to the commentList container
                        _c.label = 6;
                    case 6:
                        all_editbtns = Array.from(document.getElementsByClassName("editcommentbtn"));
                        for (_b = 0, all_editbtns_2 = all_editbtns; _b < all_editbtns_2.length; _b++) {
                            element = all_editbtns_2[_b];
                            element.addEventListener("click", function (e) {
                                commentForm.clickEdit(e);
                            });
                        }
                        getAllUserBtns();
                        return [2 /*return*/];
                }
            });
        });
    };
    /**
     * clickEdit is the code we run in response to a click of a edit button
     * @param e Event to edit a Comment
     * @type {function}
     */
    CommentForm.prototype.clickEdit = function (e) {
        var _this = this;
        // as in clickDelete, we need the ID of the row
        var id = e.target.getAttribute("data-value");
        // Issue an AJAX GET and then pass the result to editEntryForm.init()
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(comment, "/").concat(id), {
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
                                InvalidContentMsg("The server replied not ok:", response);
                            }
                            return Promise.reject(response);
                        })
                            .then(function (data) {
                            commentEditForm.init(data);
                        })
                            .catch(function (error) {
                            InvalidContentMsg("Error: ", error);
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // make the AJAX post and output value or error message to console
        doAjax().then().catch(console.log);
    };
    /**
     * clears the contents of the container
     * @type {function}
     */
    CommentForm.prototype.clearForm = function () {
        // Clear the values in it
        commentForm.title.innerText = "";
        commentForm.body.innerText = "";
        commentForm.author.innerHTML = "";
        commentForm.textbox.value = "";
        document.getElementById("commentList").innerHTML = "";
        // Reset the UI
        hideAll();
        mainList.container.style.display = "block";
    };
    CommentForm.prototype.submitForm = function () {
        console.log("Submit comment form called.");
        var commentText = commentForm.textbox.value;
        var msgID = commentForm.id.value;
        // Check if the comment text is not empty
        if (!commentText.trim()) {
            InvalidContentMsg("Error: Comment text is empty", null);
            return;
        }
        // Make the AJAX POST request
        fetch("".concat(backendUrl, "/").concat(user, "/comment/").concat(msgID), {
            method: "POST",
            body: JSON.stringify({
                mMessage: commentText,
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
        })
            .then(function (response) {
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
            .catch(function (error) {
            InvalidContentMsg("Error: ", error);
        });
    };
    return CommentForm;
}());
/**
 * Global variable to be referenced for commentEditForm
 * @type {commentEditForm}
 */
var commentEditForm;
/**
 * CommentEditForm contains all code for editing an comment
 * @type {class CommentEditForm}
 */
var CommentEditForm = /** @class */ (function () {
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @type {function}
     */
    function CommentEditForm() {
        var _a, _b;
        /**
         * The HTML element for comment message
         * @type {HTMLElement}
         */
        this.message = document.getElementById("editCommentMessage");
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
            .getElementById("editCommentCancel")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", function (e) {
            commentEditForm.clearForm();
        });
        (_b = document
            .getElementById("editCommentUpdate")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", function (e) {
            commentEditForm.submitForm();
        });
    }
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @param data comment JSON
     * @type {function}
     */
    CommentEditForm.prototype.init = function (data) {
        // Fill the edit form when we receive ok
        if (data.mStatus === "ok") {
            commentEditForm.message.value = data.mData.cMessage;
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
    };
    /**
     * Clear the form's input fields
     * @type {function}
     */
    CommentEditForm.prototype.clearForm = function () {
        commentEditForm.message.value = "";
        commentEditForm.id.value = "";
        // reset the UI
        hideAll();
        mainList.container.style.display = "block";
    };
    /**
     * Check if the input fields are both valid, and if so, do an AJAX call.
     * @type {function}
     */
    CommentEditForm.prototype.submitForm = function () {
        var _this = this;
        console.log("Submit edit comment called.");
        // get the values of the two fields, force them to be strings, and check
        // that neither is empty
        var msg = "" + commentEditForm.message.value;
        var id = "" + commentEditForm.id.value;
        if (msg === "" || id === "") {
            InvalidContentMsg("Error: comment message, or id is not valid", null);
            return;
        }
        /**
         * set up an AJAX PUT. When the server replies, the result will go to onSubmitResponse
         * @type {function}
         */
        var doAjax = function () { return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, fetch("".concat(backendUrl, "/").concat(user, "/editComment/").concat(id), {
                            method: "PUT",
                            body: JSON.stringify({
                                mMessage: msg,
                            }),
                            headers: {
                                "Content-type": "application/json; charset=UTF-8",
                            },
                        })
                            .then(function (response) {
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
                            .then(function (data) {
                            commentEditForm.onSubmitResponse(data);
                        })
                            .catch(function (error) {
                            InvalidContentMsg("Error: ", error);
                        })];
                    case 1:
                        _a.sent();
                        return [2 /*return*/];
                }
            });
        }); };
        // make the AJAX post and output value or error message to console
        doAjax().then().catch(console.log);
    };
    /**
     * onSubmitResponse runs when the AJAX call in submitForm() returns a
     * result.
     * @param data The object returned by the server
     * @type {function}
     */
    CommentEditForm.prototype.onSubmitResponse = function (data) {
        // If we get an "ok" message, clear the form and refresh the main
        // listing of messages
        if (data.mStatus === "ok") {
            commentEditForm.clearForm();
            mainList.refresh();
        }
        // Handle explicit errors with a detailed popup message
        else if (data.mStatus === "err") {
            InvalidContentMsg("The server replied with an error:\n" + data.mMessage, null);
        }
        // Handle other errors with a less-detailed popup message
        else {
            InvalidContentMsg("Unspecified error has occured", null);
        }
    };
    return CommentEditForm;
}());
/**
 * Global variable to be referenced for commentEditForm
 * @type {ViewUserForm}
 */
var viewUserForm;
/**
 * CommentEditForm contains all code for viewing a user
 * @type {class ViewUserForm}
 */
var ViewUserForm = /** @class */ (function () {
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @type {function}
     */
    function ViewUserForm() {
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
        (_a = document.getElementById("viewReturn")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", function (e) {
            viewUserForm.clearForm();
        });
    }
    /**
     * Intialize the object b setting buttons to do actions
     * when clicked
     * @type {function}
     * @param data JSON of UserDataLite
     */
    ViewUserForm.prototype.init = function (data) {
        if (data.mStatus === "ok") {
            viewUserForm.username.innerText = data.mData.uUsername;
            viewUserForm.note.innerText = data.mData.uNote;
            viewUserForm.email.innerHTML = "<a href=\"mailto:".concat(data.mData.uEmail, "\">").concat(data.mData.uEmail, "</a>");
        }
        else if (data.mStatus === "err") {
            InvalidContentMsg("The server replied with an error:\n" + data.mMessage, null);
        }
        else {
            InvalidContentMsg("Unspecified error has occured", null);
        }
        hideAll();
        viewUserForm.container.style.display = "block";
    };
    /**
     * Clear the form's input fields
     * @type {function}
     */
    ViewUserForm.prototype.clearForm = function () {
        // Clearing inner text of elements
        viewUserForm.username.innerText = "";
        viewUserForm.note.innerText = "";
        viewUserForm.email.innerHTML = "";
        hideAll();
        mainList.container.style.display = "block";
    };
    return ViewUserForm;
}());
/**
 * Run some configuration code when the web page loads
 * @type {function}
 */
document.addEventListener("DOMContentLoaded", function () {
    var _a, _b, _c, _d;
    // Create the object that controls the "New Entry" form
    newEntryForm = new NewEntryForm();
    editEntryForm = new EditEntryForm();
    mainList = new ElementList();
    profile = new Profile();
    commentForm = new CommentForm();
    commentEditForm = new CommentEditForm();
    viewUserForm = new ViewUserForm();
    mainList.refresh();
    // set up initial UI state
    hideAll();
    mainList.container.style.display = "block";
    (_a = document
        .getElementById("showFormButton")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", function (e) {
        hideAll();
        newEntryForm.container.style.display = "block";
    });
    (_b = document
        .getElementById("refreshFormButton")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", function (e) {
        hideAll();
        mainList.container.style.display = "block";
        mainList.refresh();
    });
    (_c = document
        .getElementById("showProfileButton")) === null || _c === void 0 ? void 0 : _c.addEventListener("click", function (e) {
        hideAll();
        profile.container.style.display = "block";
        profile.init();
    });
    (_d = document
        .getElementById("accountStatus")) === null || _d === void 0 ? void 0 : _d.addEventListener("click", function (e) { return __awaiter(void 0, void 0, void 0, function () {
        var response, error_4;
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    _a.trys.push([0, 2, , 3]);
                    return [4 /*yield*/, fetch("".concat(backendUrl, "/logout"), {
                            method: "DELETE",
                            headers: {
                                "Content-type": "application/json; charset=UTF-8",
                            },
                        })];
                case 1:
                    response = _a.sent();
                    if (response.ok) {
                        // Successful logout, redirect to login.html
                        window.location.href = "login.html";
                    }
                    else {
                        InvalidContentMsg("You are probably not logged in:", response);
                        window.location.href = "login.html";
                    }
                    return [3 /*break*/, 3];
                case 2:
                    error_4 = _a.sent();
                    InvalidContentMsg("Something went wrong. ", error_4);
                    return [3 /*break*/, 3];
                case 3: return [2 /*return*/];
            }
        });
    }); });
    console.log("DOMContentLoaded");
}, false);
/**
 * Gets all user links to link it to a user page
 * @type {function}
 */
function getAllUserBtns() {
    var _this = this;
    var all_userbtns = Array.from(document.getElementsByClassName("userLink"));
    var _loop_3 = function (element) {
        element.addEventListener("click", function (e) { return __awaiter(_this, void 0, void 0, function () {
            var id, data;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        e.preventDefault();
                        id = element.getAttribute("data-value");
                        if (!id) return [3 /*break*/, 2];
                        return [4 /*yield*/, mainList.getUser(id)];
                    case 1:
                        data = _a.sent();
                        viewUserForm.init(data);
                        _a.label = 2;
                    case 2: return [2 /*return*/];
                }
            });
        }); });
    };
    for (var _i = 0, all_userbtns_1 = all_userbtns; _i < all_userbtns_1.length; _i++) {
        var element = all_userbtns_1[_i];
        _loop_3(element);
    }
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
    var contentError = document.getElementById("InvalidContent");
    contentError.innerText = message;
    if (error != null)
        contentError.innerText += "\n".concat(error.status, ": ").concat(error.statusText);
    contentError.style.display = "block";
    setTimeout(function () {
        contentError.style.display = "none";
    }, 2000);
}
