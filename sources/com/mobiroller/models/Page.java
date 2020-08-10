package com.mobiroller.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Page {
    private List<Item> items = new ArrayList();

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> list) {
        this.items = list;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void swapItems(int i, int i2) {
        Collections.swap(this.items, i, i2);
    }

    public Item removeItem(int i) {
        Item item = (Item) this.items.get(i);
        this.items.remove(i);
        return item;
    }

    public void deleteItem(int i) {
        this.items.remove(i);
    }
}
