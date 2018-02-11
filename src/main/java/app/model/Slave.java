package app.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by adamz on 09.02.2018.
 */
@Entity
public class Slave {

    @Id
    String name;
    int gearscore = 0;
    int level = 0;
    int price = 0;
    String clas;
    String race;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGearscore() {
        return gearscore;
    }

    public void setGearscore(int gearscore) {
        this.gearscore = gearscore;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    @Override
    public String toString() {
        return "Slave{" +
                "\n\tname='" + name + '\'' +
                ",\n\tgearscore=" + gearscore +
                ",\n\tlevel=" + level +
                ",\n\tprice=" + price +
                ",\n\tclas='" + clas + '\'' +
                ",\n\trace='" + race + '\'' +
                '}';
    }
}
