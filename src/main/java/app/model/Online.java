package app.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by adamz on 30.01.2018.
 */
@Entity
public class Online {

    @Id
    @GeneratedValue
    int id;
    int feronis;
    int angrathar;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss Z")
    Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFeronis() {
        return feronis;
    }

    public void setFeronis(int feronis) {
        this.feronis = feronis;
    }

    public int getAngrathar() {
        return angrathar;
    }

    public void setAngrathar(int angrathar) {
        this.angrathar = angrathar;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
