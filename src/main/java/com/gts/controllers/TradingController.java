package com.gts.controllers;

import com.gts.entities.User;
import com.gts.exception.TradingException;
import com.gts.services.TradingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trading")
public class TradingController {
    private final TradingService tradingService;

    public TradingController() {
        this.tradingService = new TradingService();
    }

    @PostMapping("/users")
    public User createUser(@RequestParam String username) {
        return tradingService.createUser(username);
    }

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable String userId) {
        return tradingService.getUser(userId);
    }

    @PostMapping("/users/{userId}/assets")
    public String addAssetToPortfolio(
            @PathVariable String userId,
            @RequestParam String assetId,
            @RequestParam String name,
            @RequestParam int quantity,
            @RequestParam double price) {

        tradingService.addAssetToPortfolio(userId, assetId, name, quantity, price);
        return "Asset added successfully";
    }

    @DeleteMapping("/users/{userId}/assets/{assetId}")
    public String removeAssetFromPortfolio(@PathVariable String userId, @PathVariable String assetId) {
        tradingService.removeAssetFromPortfolio(userId, assetId);
        return "Asset removed successfully";
    }

    @PostMapping("/trade")
    public String tradeAsset(@RequestParam String sellerId, @RequestParam String buyerId, @RequestParam String assetId, @RequestParam int quantity) throws TradingException {
        boolean success = tradingService.tradeAsset(sellerId, buyerId, assetId, quantity);
        return success ? "Trade completed successfully" : "Trade failed - check asset quantity or funds";
    }

    @GetMapping("/leaderboard")
    public List<User> getLeaderboard() {
        return tradingService.getLeaderboard();
    }

//    @GetMapping("/users/{userId}/streak")
//    public int getTradingStreak(@PathVariable String userId) {
//        return tradingService.getTradingStreak(userId);
//    }
//
//    @GetMapping("/assets/{assetId}/price")
//    public double getAssetPrice(@PathVariable String assetId) {
//        return tradingService.getAssetPrice(assetId);
//    }
//
//    @GetMapping("/insights")
//    public String getPortfolioInsights() {
//        return tradingService.getPortfolioInsights();
//    }
}
