package logistic.web.models;

import logistic.web.repositories.CitiesRepository;
import logistic.web.repositories.UsersRepository;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "calendar")
public class Calendar implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    @Column
    protected java.sql.Date date;

    @Column(name = "user_id")
    protected int userId;

    @Column(name= "city_id")
    protected int cityId;

    public Calendar() {
        super();
    }

    public Calendar(String date, int userId, int cityId) {
        this.date = java.sql.Date.valueOf(date);
        this.userId = userId;
        this.cityId = cityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        if (this.getCityId() == 0) {
            return "-";
        }
        return CitiesRepository.getInstance().getById(this.getCityId()).getName();
    }

    public String getDetails() {
        if (this.getCityId() == 0) {
            return "-";
        }
        return "Пункт пребывания: " + CitiesRepository.getInstance().getById(this.getCityId()).getName()
                + "; Перевозчик: " + UsersRepository.getInstance().getById(this.getUserId()).getName();
    }

}
