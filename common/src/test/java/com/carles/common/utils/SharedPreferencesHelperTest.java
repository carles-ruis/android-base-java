package com.carles.common.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PreferenceManager.class)
public class SharedPreferencesHelperTest {

    private SharedPreferencesHelper sharedPreferencesHelper;
    @Mock
    private Context context;
    @Mock
    private SharedPreferences sharedPreferences;
    @Mock
    private SharedPreferences.Editor editor;

    @Before
    public void setup() {
        mockStatic(PreferenceManager.class);
        when(PreferenceManager.getDefaultSharedPreferences(context)).thenReturn(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(editor);
        sharedPreferencesHelper = new SharedPreferencesHelper(context);
    }

    @Test
    public void getCacheExpirationTime_verifyCall() {
        sharedPreferencesHelper.getCacheExpirationTime("someclass", "1");
        verify(sharedPreferences).getLong(eq("expiration_time_prefix_someclass1"), eq(0l));
    }

    @Test
    public void setCacheExpirationTime_verifyCall() {
        when(editor.putLong(anyString(), anyLong())).thenReturn(editor);
        sharedPreferencesHelper.setCacheExpirationTime("someclass", "1", 99l);
        verify(editor).putLong(eq("expiration_time_prefix_someclass1"), eq(99l));
    }
}
