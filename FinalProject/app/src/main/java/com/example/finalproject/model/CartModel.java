package com.example.finalproject.model;

import java.util.List;

public class CartModel {

    private int userID;
    private int totalNumber;
    private double totalPrice;
    private List<CartItemModel> itemModels;

    public CartModel() {

    }

    public int getTotalNumber() {
        totalNumber = 0;
        itemModels.forEach(item -> totalNumber += item.getNumber());
        return totalNumber;
    }

    public double getTotalPrice() {
        totalPrice = 0;
        itemModels.forEach(item -> totalPrice += item.getNumber() * item.getPrice());
        return totalPrice;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<CartItemModel> getItemModels() {
        return itemModels;
    }

    public void setItemModels(List<CartItemModel> itemModels) {
        this.itemModels = itemModels;
    }

    public CartModel(int userID, List<CartItemModel> itemModels) {
        this.userID = userID;
        this.itemModels = itemModels;
        getTotalNumber();
        getTotalPrice();
    }

    public void addNewItemToCart(CartItemModel newItem){
        for (CartItemModel item: itemModels) {
            if(item.getId() == newItem.getId()){
                return;
            }
        }
        itemModels.add(newItem);
        totalNumber +=1;
        totalPrice +=newItem.getPrice();
    }
}
