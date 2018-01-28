package app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;


@Entity
public class Item implements Serializable {

    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private int itemLevel;
    private boolean heroic;
    private int quality;
    private String slot;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public boolean isHeroic() {
        return heroic;
    }

    public void setHeroic(boolean heroic) {
        this.heroic = heroic;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    @Override
    public String toString() {
        return "Item{\n" +
                ",\n\tid=" + id +
                ",\n\tname='" + name + '\'' +
                ",\n\titemLevel=" + itemLevel +
                ",\n\theroic=" + heroic +
                ",\n\tquality=" + quality +
                "\n\tslot='" + slot + '\'' +
                '}';
    }
}
