package com.carles.common.data.datasource;

import com.carles.common.utils.SharedPreferencesHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BaseLocalDatasourceTest {

    private BaseLocalDatasource datasource;
    @Mock
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Before
    public void setup() {
        datasource = new BaseLocalDatasource() {};
        datasource.sharedPreferencesHelper = sharedPreferencesHelper;
    }

    @Test
    public void calculateCacheExpirationTime_shouldReturnFutureTime() {
        long cacheExpirationTime = datasource.calculateCacheExpirationTime();
        assertTrue(cacheExpirationTime > System.currentTimeMillis());
    }

    @Test
    public void isExpired_shouldCheckSharedPreferences() {
        datasource.isExpired("someclass", "someid");
        verify(sharedPreferencesHelper).getCacheExpirationTime("someclass", "someid");
    }

}
