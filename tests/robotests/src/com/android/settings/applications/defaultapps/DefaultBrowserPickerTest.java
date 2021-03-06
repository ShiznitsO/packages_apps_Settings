/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.applications.defaultapps;


import android.app.Activity;
import android.content.Context;
import android.os.UserManager;

import com.android.settings.testutils.SettingsRobolectricTestRunner;
import com.android.settings.TestConfig;
import com.android.settings.applications.PackageManagerWrapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SettingsRobolectricTestRunner.class)
@Config(manifest = TestConfig.MANIFEST_PATH, sdk = TestConfig.SDK_VERSION)
public class DefaultBrowserPickerTest {

    private static final String TEST_APP_KEY = "";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Activity mActivity;
    @Mock
    private UserManager mUserManager;
    @Mock
    private PackageManagerWrapper mPackageManager;

    private DefaultBrowserPicker mPicker;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mActivity.getSystemService(Context.USER_SERVICE)).thenReturn(mUserManager);

        mPicker = new DefaultBrowserPicker();
        mPicker.onAttach((Context) mActivity);

        ReflectionHelpers.setField(mPicker, "mPm", mPackageManager);
    }

    @Test
    public void setDefaultAppKey_shouldUpdateDefaultBrowser() {
        mPicker.setDefaultKey(TEST_APP_KEY);
        verify(mPackageManager)
                .setDefaultBrowserPackageNameAsUser(eq(TEST_APP_KEY), anyInt());
    }

    @Test
    public void getDefaultAppKey_shouldReturnDefaultBrowser() {
        mPicker.getDefaultKey();
        verify(mPackageManager)
                .getDefaultBrowserPackageNameAsUser(anyInt());
    }
}
