package com.gts.entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Portfolio {
    private Map<String, Asset> assets = new HashMap<>();

    public void addAsset(Asset asset) {
        assets.put(asset.getAssetId(), asset);
    }

    public void removeAsset(String assetId) {
        assets.remove(assetId);
    }

    public Asset getAsset(String assetId) {
        return assets.get(assetId);
    }

    public void updateAssetQuantity(String assetId, int quantity) {
        Asset asset = assets.get(assetId);
        if (asset != null) {
            asset.updateQuantity(quantity);
        }
    }
}