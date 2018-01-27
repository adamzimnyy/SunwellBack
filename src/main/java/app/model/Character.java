package app.model;

import java.util.List;


public class Character {
    String name;
    List<Item> items;
    List<Integer> itemIds;

    public List<Integer> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }

    public float getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(float itemLevel) {
        this.itemLevel = itemLevel;
    }

    float itemLevel;
    String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
