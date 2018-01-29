package app.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@Entity
public class Character {
    @Id
    String name;

    @Transient
    List<Item> items;

    String itemIds;
    Date lastUpdated;

    float itemLevel;
    String info;

    int gearscore=0;

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }

    public int getGearscore() {
        return gearscore;
    }

    public void setGearscore(int gearscore) {
        this.gearscore = gearscore;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<Integer> getItemIds() {
        String[] split = this.itemIds.split(",");
        List<Integer> ints = new ArrayList<>();
        for(String s : split)
            ints.add(Integer.parseInt(s));
        return ints;
    }

    public void setItemIds(List<Integer> itemIds) {
        List<String> s = new ArrayList<>();
        for(Integer i : itemIds) s.add(i+"");
        this.itemIds = String.join(",",s);
    }

    public float getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(float itemLevel) {
        this.itemLevel = itemLevel;
    }


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
