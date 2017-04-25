package logistic.web.models;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.String;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    public static final int TYPE_OPERATOR = 1;
    public static final int TYPE_CLIENT = 2;
    public static final int TYPE_CARRIER = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    @Column
    protected String name;

    @Column
    protected String email;

    @Column
    protected String password;

    @Column
    protected String phone;

    @Column
    protected double maxweight;

    @Column
    protected double width;

    @Column
    protected double height;

    @Column
    protected double length;

    @Column
    protected int type;

    @OneToMany(cascade=CascadeType.ALL, targetEntity=logistic.web.models.Order.class, mappedBy="clientId")
    protected List<Order> clientOrders;

    @OneToMany(cascade=CascadeType.ALL, targetEntity=logistic.web.models.Calendar.class, mappedBy="userId")
    protected List<Calendar> Calendar;

    public User() {
        super();
    }

    public User(String name, String email, String password, String phone, int type) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setPhone(phone);
        this.maxweight = 0;
        this.width = 0;
        this.height = 0;
        this.length = 0;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getMaxWeight() {
        return this.maxweight;
    }

    public void setMaxWeight(double maxweight) {
        this.maxweight = maxweight;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLength() {
        return this.length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public List<Order> getClientOrders() {
        return clientOrders;
    }

    public void setClientOrders(List<Order> clientOrders) {
        this.clientOrders = clientOrders;
    }

    public List<logistic.web.models.Calendar> getCalendar() {
        return Calendar;
    }

    public void setCalendar(List<logistic.web.models.Calendar> calendar) {
        Calendar = calendar;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}