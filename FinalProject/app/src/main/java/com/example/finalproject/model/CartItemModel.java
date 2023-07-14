package com.example.finalproject.model;

public class CartItemModel {
    private int id;
    private int number;
    private String name;
    private int cover;
    private double price;

    public CartItemModel(int id, int number) {
        this.id = id;
        this.number = number;
    }

    public CartItemModel(int id, int number, String name, int cover, double price) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.cover = cover;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getCover() {
        return cover;
    }

    public double getPrice() {
        return price;
    }
}
