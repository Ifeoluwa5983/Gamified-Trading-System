package com.gts.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String userId;
    private String userName;
    private double gemCount;
    private int rank;
    private int tradeCount;
    private Portfolio portfolio;

    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.gemCount = 0;
        this.rank = 0;
        this.portfolio = new Portfolio();
    }

    public User() {
    }

    public void earnGems(double gems) {
        this.gemCount += gems;
    }
}
