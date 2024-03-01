import { TodoList } from "../models/todoList.js";

export class TodoItem extends HTMLElement {
    /** The html file to use for the template */
    static #templatePath = "src/components/todoItem.html";
    /** The tag name associated with this element */
    static tagName = "todo-item";
    /** The HTML template to use when making this component */
    static #template = undefined;
    /** The HTML checkbox */
    #check;
    /** The HTML text input box */
    #input;
    /** The HTML span */
    #display;
    /** The HTML button for deleting */
    #deleteBtn;
    /** The database id associated with this todo-item */
    #id;

    /** Create the todo-item tag */
    static async init() {
        let src = await fetch(TodoItem.#templatePath);
        let txt = await src.text();
        TodoItem.#template = document.createElement("template");
        TodoItem.#template.innerHTML = txt;
        document.head.appendChild(TodoItem.#template);
        window.customElements.define(TodoItem.tagName, TodoItem);
    }

    constructor() {
        super();

        this.attachShadow({ mode: "open" });
        this.shadowRoot.appendChild(TodoItem.#template.content.cloneNode(true));
        let item = this.shadowRoot.querySelector(".todo-item");
        this.#check = item.querySelector("input[type=checkbox]");
        this.#input = item.querySelector("input[type=text]");
        this.#display = item.querySelector("span");
        this.#deleteBtn = item.querySelector("button");
    }

    /** Set the fields of this view based on the data from the model */
    setData(data) {
        this.#input.value = data.data;
        this.#display.innerHTML = data.data;
        this.#id = 0 + data.id;
        this.#check.checked = data.complete;
        if (data.complete) {
            this.#display.classList.add("strike-through");
        }
    }
    /**
     * Set up the listeners for the various events of interest
     */
    connectedCallback() {
        this.#display.addEventListener("click", e => this.#editContent(e));
        this.#deleteBtn.addEventListener("click", e => this.#deleteTodoItem(e));
        this.#check.addEventListener("click", e => this.#toggleCheck(e));
        this.#input.addEventListener("focusout", e => this.#saveContent(e));
    }
    /**
     * switch to editing mode 
     * Only when clicking on display text
     * @param {*} e the click event
     * @returns 
     */
    #editContent(e) {
        e.stopPropagation();
        // Only edit if not checked
        if (this.#check.enabled) return;

        // Hide the display, show the input, and select all text
        this.#display.hidden = true;
        this.#input.hidden = false;
        this.#input.select();
    }
    /**
     * When editing box loses focus, update the model and reset
     * the view
     */
    #saveContent() {
        if (TodoList.UpdateData(this.#id, this.#input.value))
            this.#display.innerHTML = this.#input.value;
        else
            this.#input.value = this.#display.innerHTML;
        // Switch visibility
        this.#input.hidden = true;
        this.#display.hidden = false;
    }
    /** 
     * Handle clicks on the check box by toggling strikethrough and updating the
     * model
     */
    #toggleCheck(e) {
        e.stopPropagation();
        if (TodoList.UpdateCompleted(this.#id, e.target.checked)) {
            if (e.target.checked)
                this.#display.classList.add("strike-through");
            else
                this.#display.classList.remove("strike-through");
        }
    }

    /** 
     * When a delete button is pressed, update the model and remove the element
     */
    #deleteTodoItem(e) {
        e.stopPropagation();
        if (TodoList.DeleteItem(this.#id))
            this.parentNode.removeChild(this); // Tell parent to remove
    }

    /** When a TodoItem tag is removed, remove its click listener */
    disconnectedCallback() {
        this.#check.removeEventListener("click", e => this.#toggleCheck(e));
    }

}