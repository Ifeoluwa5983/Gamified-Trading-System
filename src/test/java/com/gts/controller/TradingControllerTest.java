package com.gts.controller;

import com.gts.controllers.TradingController;
import com.gts.entities.User;
import com.gts.services.TradingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradingController.class)
public class TradingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TradingService tradingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setUserName("Ifeoluwa");

        when(tradingService.createUser(anyString())).thenReturn(user);

        mockMvc.perform(post("/api/trading/users")
                        .param("username", "Ifeoluwa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Ifeoluwa"));
    }

    @Test
    public void testGetUser() throws Exception {
        User user = new User();
        user.setUserId("1");
        user.setUserName("Ifeoluwa");

        when(tradingService.getUser(anyString())).thenReturn(user);

        mockMvc.perform(get("/api/trading/users/1"))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.userName").value("Ifeoluwa"));
    }

    @Test
    public void testAddAssetToPortfolio() throws Exception {
        mockMvc.perform(post("/api/trading/users/1/assets")
                        .param("assetId", "asset1")
                        .param("name", "Asset Name")
                        .param("quantity", "10")
                        .param("price", "100.0"))
                .andExpect(status().isOk())
                .andExpect(content().string("Asset added successfully"));
    }

    @Test
    public void testRemoveAssetFromPortfolio() throws Exception {
        mockMvc.perform(delete("/api/trading/users/1/assets/asset1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Asset removed successfully"));
    }

    @Test
    public void testTradeAsset() throws Exception {
        when(tradingService.tradeAsset(anyString(), anyString(), anyString(), anyInt())).thenReturn(true);

        mockMvc.perform(post("/api/trading/trade")
                        .param("sellerId", "1")
                        .param("buyerId", "2")
                        .param("assetId", "asset1")
                        .param("quantity", "5"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetLeaderboard() throws Exception {
        when(tradingService.getLeaderboard()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/trading/leaderboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
