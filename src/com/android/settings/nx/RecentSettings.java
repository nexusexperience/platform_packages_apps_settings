/*
 * Copyright (C) 2014 Nexus Experience Project
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

package com.android.settings.nx;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class RecentSettings extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {
    private static final String TAG = "RecentSettings";

    private static final String KEY_CLEAR_ALL_RECENTS_ENABLED = "clear_all_recents_enabled";

    private SwitchPreference mClearAllRecents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.recent_settings);
        PreferenceScreen prefs = getPreferenceScreen();
        final ContentResolver resolver = getActivity().getContentResolver();

        mClearAllRecents = (SwitchPreference) findPreference(KEY_CLEAR_ALL_RECENTS_ENABLED);
        mClearAllRecents.setPersistent(false);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateClearAllFAB();
    }

    private void updateClearAllFAB() {
        boolean enabled = Settings.System.getInt(getContentResolver(),
                Settings.System.CLEAR_ALL_RECENTS_ENABLED, 0) == 1;
        mClearAllRecents.setChecked(enabled);
    }

    private boolean recentsFAB() {
        return Settings.System.getBoolean(getActivity().getContentResolver(),
               Settings.System.CLEAR_ALL_RECENTS_ENABLED, false);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mClearAllRecents) {
            Settings.System.putInt(resolver, Settings.System.CLEAR_ALL_RECENTS_ENABLED,
                    mClearAllRecents.isChecked() ? 1 : 0);
        } else {
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }
        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference pref, Object value) {
        return true;
    }

}
