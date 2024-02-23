package com.example.fetchandroid;

/**
 * Represents an item object in the Item List.
 */
public class Item {
    private int id;
    private int listId;
    private String name;

    /**
     * Constructs a new Item with the specified id, listId, and name.
     *
     * @param id     The unique identifier of the item.
     * @param listId The group identifier to which this item belongs.
     * @param name   The name or description of the item.
     */
    public Item(int id, int listId, String name) {
        this.id = id;
        this.listId = listId;
        this.name = name;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getListId() { return listId; }
    public void setListId(int listId) { this.listId = listId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}