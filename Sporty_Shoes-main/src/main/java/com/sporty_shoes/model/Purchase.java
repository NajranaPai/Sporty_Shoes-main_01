package com.sporty_shoes.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Purchase{

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @OneToOne
    private User user;
    
    @OneToOne
    private Product product;

    @Temporal(TemporalType.DATE)
    private Date date ;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return "Purchase [date=" + date + ", id=" + id + ", product=" + product + ", user=" + user + "]";
    }
    
}