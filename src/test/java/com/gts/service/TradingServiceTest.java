package com.gts.service;

import com.gts.entities.User;
import com.gts.exception.TradingException;
import com.gts.services.TradingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TradingServiceTest {
    private TradingService tradingService;

    @BeforeEach
    public void setUp() {
        tradingService = new TradingService();
    }

    @Test
    public void testCreateUser() {
        User user = tradingService.createUser("Ifeoluwa");
        assertNotNull(user);
        assertEquals("Ifeoluwa", user.getUserName());
    }

    @Test
    public void testAddAssetToPortfolio() {
        User user = tradingService.createUser("Ifeoluwa");
        tradingService.addAssetToPortfolio(user.getUserId(), "asset1", "Asset 1", 10, 100.0);
        assertNotNull(user.getPortfolio().getAsset("asset1"));
    }

    @Test
    public void testRemoveAssetFromPortfolio() {
        User user = tradingService.createUser("Ifeoluwa");
        tradingService.addAssetToPortfolio(user.getUserId(), "asset1", "Asset 1", 10, 100.0);
        tradingService.removeAssetFromPortfolio(user.getUserId(), "asset1");
        assertNull(user.getPortfolio().getAsset("asset1"));
    }

    @Test
    public void testGetUser() {
        User user = tradingService.createUser("Ifeoluwa");
        User retrievedUser = tradingService.getUser(user.getUserId());
        assertEquals(user, retrievedUser);
    }

    @Test
    public void testTradeAsset() {
        User seller = tradingService.createUser("seller");
        User buyer = tradingService.createUser("buyer");
        tradingService.addAssetToPortfolio(seller.getUserId(), "asset1", "Asset 1", 10, 100.0);
        buyer.earnGems(1000.0);

        boolean success = tradingService.tradeAsset(seller.getUserId(), buyer.getUserId(), "asset1", 5);
        assertTrue(success);
        assertEquals(5, seller.getPortfolio().getAsset("asset1").getQuantity());
        assertEquals(5, buyer.getPortfolio().getAsset("asset1").getQuantity());
    }

    @Test
    public void testGetLeaderboard() {
        User user1 = tradingService.createUser("user1");
        User user2 = tradingService.createUser("user2");
        user1.earnGems(500);
        user2.earnGems(1000);

        List<User> leaderboard = tradingService.getLeaderboard();
        assertEquals(2, leaderboard.size());
        assertEquals(user2, leaderboard.get(0));
        assertEquals(user1, leaderboard.get(1));
    }
}
