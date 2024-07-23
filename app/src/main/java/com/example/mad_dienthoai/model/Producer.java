package com.example.mad_dienthoai.model;

import java.io.Serializable;

public class Producer implements Serializable {
    private int idProducer;
    private String nameProducer,description;

    public Producer(){

    }
    public Producer(int idProducer, String nameProducer, String description){
        this.idProducer=idProducer;
        this.nameProducer=nameProducer;
        this.description=description;
    }

    public int getIdProducer() {
        return idProducer;
    }

    public String getNameProducer() {
        return nameProducer;
    }

    public String getDescription() {
        return description;
    }

    public void setIdProducer(int idProducer) {
        this.idProducer = idProducer;
    }

    public void setNameProducer(String nameProducer) {
        this.nameProducer = nameProducer;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
