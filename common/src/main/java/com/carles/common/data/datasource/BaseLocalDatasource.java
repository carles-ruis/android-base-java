package com.carles.common.data.datasource;

import com.carles.common.utils.SharedPreferencesHelper;

import javax.inject.Inject;

public abstract class BaseLocalDatasource {

    private static final long EXPIRE_TIME = 1000 * 60;
    @Inject
    protected SharedPreferencesHelper sharedPreferencesHelper;

    protected long calculateCacheExpirationTime() {
        return System.currentTimeMillis() + EXPIRE_TIME;
    }

    protected boolean isExpired(String className, String itemId) {
        return sharedPreferencesHelper.getCacheExpirationTime(className, itemId) < System.currentTimeMillis();
    }
}