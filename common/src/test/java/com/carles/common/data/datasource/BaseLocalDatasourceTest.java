package com.carles.common.data.datasource;

import com.carles.common.utils.SharedPreferencesHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.reflect.Whitebox;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BaseLocalDatasourceTest {

    private BaseLocalDatasource datasource;
    @Mock
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Before
    public void setup() {
        datasource = mock(BaseLocalDatasource.class, CALLS_REAL_METHODS);
        Whitebox.setInternalState(datasource, "sharedPreferencesHelper", sharedPreferencesHelper);
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
