package com.carles.common.data.datasource;

import com.carles.common.utils.SharedPreferencesHelper;

public abstract class BaseLocalDatasource {

    protected static final long EXPIRE_TIME = 1000 * 60;
    protected final SharedPreferencesHelper sharedPreferencesHelper;

    public BaseLocalDatasource() {
        this.sharedPreferencesHelper = SharedPreferencesHelper.getInstance();
    }

    protected long calculateCacheExpirationTime() {
        return System.currentTimeMillis() + EXPIRE_TIME;
    }

    protected boolean isExpired(String className, String itemId) {
        return sharedPreferencesHelper.getCacheExpirationTime(className, itemId) < System.currentTimeMillis();
    }
}