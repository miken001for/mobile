package com.example.mad_dienthoai.model;


import java.io.Serializable;

public class Product implements Serializable {
    private int idProduct;
    private double size;
    private String name,category,rating;
    private int idProducer;

    public Product(){

    }
    public Product(int idProduct, String name, String category, double size, String rating,int idProducer){
        this.idProduct=idProduct;
        this.name=name;
        this.category=category;
        this.size=size;
        this.rating=rating;
        this.idProducer=idProducer;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public String getName() {
        return name;
    }

    public double getSize() {
        return size;
    }

    public String getCategory() {
        return category;
    }

    public String getRating() {
        return rating;
    }

    public int getIdProducer(){ return idProducer;}

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRating(String rating) {
            this.rating = rating;
    }

    public void setIdProducer(int idProducer){ this.idProducer=idProducer; }

}
