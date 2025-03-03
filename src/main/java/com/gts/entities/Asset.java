package com.gts.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Asset {

    private final String assetId;
    private String name;
    private int quantity;
    private double price;

    public Asset(String assetId, String name, int quantity, double price) {
        this.assetId = assetId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public double getValue() {
        return quantity * price;
    }

    public void updateQuantity(int amount) {
        this.quantity += amount;
    }
}
