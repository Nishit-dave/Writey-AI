package com.app.writy.firebase;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {
    public String uuid;
    public String brand;
    public String modelno;
    public String version;
    public String android_id;
    public int coin;
    public User() {
    }
    public User(String uuid, String brand, String modelno, String version, String android_id, int coin) {
        this.uuid = uuid;
        this.brand = brand;
        this.modelno = modelno;
        this.version = version;
        this.android_id = android_id;
        this.coin = coin;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("coin", getCoin());
        return result;
    }
}
