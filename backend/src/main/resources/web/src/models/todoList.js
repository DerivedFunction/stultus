/**
 * TodoList is the data model to be used.
 * 
 * @param id the unique id of the object
 * @param data the data associated with object
 * With static variables, it is a singleton patter (cannot create
 * more than one TodoList)
 * */
export class TodoList {
    static #State = [];
    static #IdGenerator = 0;
    /** Create a new item */
    static CreateItem() {
        let id = this.#IdGenerator++;
        let complete = false;
        let data = "Task " + id;

        let res = { id, data, complete };
        this.#State.push(res);
        return res;
    }
    /**
     * Get and return item from list
     * @param {*} id  to scan
     * @returns matching id
     */
    static ReadItem(id) {
        return this.#State.find(t => t.id == id);
    }
    /**
     * Update/Overwrite the data
     * @param {*} id the id
     * @param {*} data the new data
     * @returns true on success, false otherwise
     */
    static UpdateData(id, data) {
        if (!data) return false; // reject empty field
        let todoItem = this.#State.find(t => t.id == id);
        if (!todoItem) return false;
        todoItem.data = data;
        return true;
    }

    /** Set the 'completed' state of the item whose id matches `id` */
    static UpdateCompleted(id, completed) {
        let todoItem = this.#State.find(t => t.id == id);
        if (!todoItem) return false;
        todoItem.completed = completed;
        return true;
    }

    /** Remove the Todo List item whose id matches `id` */
    static DeleteItem(id) {
        let s = this.#State.length;
        // Filter out the matching id's object
        this.#State = this.#State.filter((t) => t.id != id);
        return this.#State.length == s - 1;
    }

}