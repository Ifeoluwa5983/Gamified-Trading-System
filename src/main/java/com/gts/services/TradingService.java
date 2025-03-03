package com.gts.services;

import com.gts.entities.Asset;
import com.gts.entities.User;
import com.gts.exception.TradingException;

import java.util.*;
import java.util.stream.Collectors;

public class TradingService {
    private final Map<String, User> users;

    public TradingService() {
        this.users = new HashMap<>();
    }

    public User createUser(String username) {
        User user = new User(UUID.randomUUID().toString(), username);
        users.put(user.getUserId(), user);
        return user;
    }

    public void addAssetToPortfolio(String userId, String assetId, String name, int quantity, double price) {
        User user = users.get(userId);
        if (user != null) {
            user.getPortfolio().addAsset(new Asset(assetId, name, quantity, price));
        }
    }

    public void removeAssetFromPortfolio(String userId, String assetId) {
        User user = users.get(userId);
        if (user != null) {
            user.getPortfolio().removeAsset(assetId);
        }
    }

    public User getUser(String userId) {
        return users.get(userId);
    }

    public boolean buyAsset(String userId, String assetId, int quantity) {
        User user = users.get(userId);
        if (user != null) {
            Asset asset = user.getPortfolio().getAsset(assetId);
            double totalCost = asset.getPrice() * quantity;

            if (user.getGemCount() >= totalCost) {
                user.getPortfolio().updateAssetQuantity(assetId, quantity);
                user.setTradeCount(user.getTradeCount() + 1);
                awardTradeGems(user);
                updateRanks();
                return true;
            }
        }
        return false;
    }

    public boolean sellAsset(String userId, String assetId, int quantity) {
        User user = users.get(userId);
        if (user != null) {
            Asset asset = user.getPortfolio().getAsset(assetId);
            if (asset != null && asset.getQuantity() >= quantity) {
                user.getPortfolio().updateAssetQuantity(assetId, -quantity);
                awardTradeGems(user);
                user.setGemCount(user.getGemCount() + 1);
                updateRanks();
                return true;
            }
        }
        return false;
    }

    public boolean tradeAsset(String sellerId, String buyerId, String assetId, int quantity) {
        return sellAsset(sellerId, assetId, quantity) && buyAsset(buyerId, assetId, quantity);
    }

    public List<User> getLeaderboard() {
        return users.values().stream()
                .sorted(Comparator.comparingDouble(User::getGemCount).reversed())
                .collect(Collectors.toList());
    }

    private void awardTradeGems(User user) {
        user.earnGems(1);

        int tradeCount = user.getTradeCount();
        if (tradeCount == 5) {
            user.earnGems(5);
        } else if (tradeCount == 10) {
            user.earnGems(10);
        }
    }

    public List<User> getTopUsers(int topN) {
        return users.values().stream()
                .sorted(Comparator.comparingDouble(User::getGemCount).reversed())
                .limit(topN)
                .toList();
    }

//    public int getTradingStreak(String userId) {
//        User user = users.get(userId);
//        return user != null ? user.getTradeStreak() : 0;
//    }
//
//    public double getAssetPrice(String assetId) {
//        return assetPrices.getOrDefault(assetId, 0.0);
//    }
//
//    public String getPortfolioInsights() {
//        User topUser = users.values().stream()
//                .max(Comparator.comparingDouble(u -> u.getPortfolio().getPortfolioValue()))
//                .orElse(null);
//
//        String mostTradedAsset = users.values().stream()
//                .flatMap(user -> user.getPortfolio().getAssets().stream())
//                .collect(Collectors.groupingBy(Asset::getId, Collectors.counting()))
//                .entrySet().stream()
//                .max(Map.Entry.comparingByValue())
//                .map(Map.Entry::getKey)
//                .orElse("None");
//
//        return "Top User: " + (topUser != null ? topUser.getUsername() : "None") +
//                ", Most Traded Asset: " + mostTradedAsset;
//    }

    public void updateRanks() {
        List<User> sortedUsers = getTopUsers(users.size());
        for (int i = 0; i < sortedUsers.size(); i++) {
            sortedUsers.get(i).setRank(i + 1);
        }
    }
}