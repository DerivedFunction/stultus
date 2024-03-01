import { TodoList } from "../models/todoList.js";
import { TodoItem } from "./todoItem.js";

/**
 * AppRoot is a custom HTML5 component. <app-root>
 * Uses appRoot.css and appRoot.html
 * Contains a list of elements and a button
 * 
 * Uses a static init function to be self-contained.
 * Must call init to make app-root a valid HTML tag
 */

export class AppRoot extends HTMLElement {
    /// Where the html template is
    static #templatePath = "src/components/appRoot.html";
    //<tag-name>
    static #tagName = "app-root";

    static #template = undefined;
    // HTML tag with all the items
    #items;
    // HTML button for add
    #addBtn;

    // Create the tag
    static async init() {
        // Fetch the template's html to text
        let src = await fetch(AppRoot.#templatePath);
        let txt = await src.text();

        // Inject that template into the document and append it.
        AppRoot.#template = document.createElement("template");
        AppRoot.#template.innerHTML = txt;
        document.head.appendChild(AppRoot.#template);
        // <app-root> tag association
        window.customElements.define(AppRoot.#tagName, AppRoot);
    }

    /**
     * Create a new object for each <app-root></app-root> tag
     */
    constructor() {
        // Create an HTML element first
        super();

        // Attach a shadow DOM to our HTML element (nothing in it yet)
        this.attachShadow({ mode: "open" });
        // Copy our appRoot template to this object
        this.shadowRoot.appendChild(AppRoot.#template.content.cloneNode(true));
        // Find the div that will hold the items and the button
        this.#items = this.shadowRoot.querySelector("div");
        this.#addBtn = this.shadowRoot.querySelector("button");
    }

    /** Run connectedCallback after app-root is done running
     * Make the button clickable
     */
    connectedCallback() {
        this.#addBtn.addEventListener("click", e => this.#addTodoItem(e));
    }

    /**
     * Add a todo-item tag after click from button
     * @param {*} e 
     */
    #addTodoItem(e) {
        e.stopPropagation();
        let data = TodoList.CreateItem();
        let todoItem = document.createElement(TodoItem.tagName);
        todoItem.setData(data);
        this.#items.appendChild(todoItem);
    }

    /** If the app-root is removed, call this function */
    disconnectCallback() {

    }
}
