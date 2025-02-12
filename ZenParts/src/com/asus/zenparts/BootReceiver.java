/*
 * Copyright (C) 2018 The Asus-SDM660 Project
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
 * limitations under the License
 */

package com.asus.zenparts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import android.provider.Settings;
import com.asus.zenparts.preferences.VibratorStrengthPreference;

import com.asus.zenparts.kcal.Utils;
import com.asus.zenparts.ambient.SensorsDozeService;
import com.asus.zenparts.dirac.DiracUtils;


public class BootReceiver extends BroadcastReceiver implements Utils {

    private final String TORCH_1_BRIGHTNESS_PATH = "/sys/devices/soc/800f000.qcom,spmi/spmi-0/" +
            "spmi0-03/800f000.qcom,spmi:qcom,pm660l@3:qcom,leds@d300/leds/led:torch_0/" +
            "max_brightness";
    private final String TORCH_2_BRIGHTNESS_PATH = "/sys/devices/soc/800f000.qcom,spmi/spmi-0/" +
            "spmi0-03/800f000.qcom,spmi:qcom,pm660l@3:qcom,leds@d300/leds/led:torch_1/" +
            "max_brightness";

    public void onReceive(Context context, Intent intent) {
    
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        // Ambient
        context.startService(new Intent(context, SensorsDozeService.class));
        
        //FPS
        boolean enabled = sharedPrefs.getBoolean(DeviceSettings.PREF_KEY_FPS_INFO, false);
        if (enabled) {
            context.startService(new Intent(context, FPSInfoService.class));
        }
        
        // Dirac
        new DiracUtils(context).onBootCompleted();
        
	//MSM Thermal control
	FileUtils.setValue(DeviceSettings.MSM_THERMAL_PATH, 
		Settings.Secure.getInt(context.getContentResolver(),
                	DeviceSettings.PERF_MSM_THERMAL, 0));
        FileUtils.setValue(DeviceSettings.CORE_CONTROL_PATH, 
        	Settings.Secure.getInt(context.getContentResolver(),
                	DeviceSettings.PERF_CORE_CONTROL, 0));
        FileUtils.setValue(DeviceSettings.VDD_RESTRICTION_PATH, 
        	Settings.Secure.getInt(context.getContentResolver(),
                	DeviceSettings.PERF_VDD_RESTRICTION, 0));
        //Torch       
        FileUtils.setValue(TORCH_1_BRIGHTNESS_PATH,
                Settings.Secure.getInt(context.getContentResolver(),
                        DeviceSettings.PREF_TORCH_BRIGHTNESS, 100));
        FileUtils.setValue(TORCH_2_BRIGHTNESS_PATH,
                Settings.Secure.getInt(context.getContentResolver(),
                        DeviceSettings.PREF_TORCH_BRIGHTNESS, 100));
        //BLD
        FileUtils.setValue(DeviceSettings.BACKLIGHT_DIMMER_PATH, 
        	 Settings.Secure.getInt(context.getContentResolver(),
                	 DeviceSettings.PREF_BACKLIGHT_DIMMER, 0));
        
        if (Settings.Secure.getInt(context.getContentResolver(), PREF_ENABLED, 0) == 1) {
            
            FileUtils.setValue(KCAL_ENABLE, Settings.Secure.getInt(context.getContentResolver(),
                    PREF_ENABLED, 0));

            String rgbValue = Settings.Secure.getInt(context.getContentResolver(),
                    PREF_RED, RED_DEFAULT) + " " +
                    Settings.Secure.getInt(context.getContentResolver(), PREF_GREEN,
                            GREEN_DEFAULT) + " " +
                    Settings.Secure.getInt(context.getContentResolver(), PREF_BLUE,
                            BLUE_DEFAULT);

            FileUtils.setValue(KCAL_RGB, rgbValue);
            FileUtils.setValue(KCAL_MIN, Settings.Secure.getInt(context.getContentResolver(),
                    PREF_MINIMUM, MINIMUM_DEFAULT));
            FileUtils.setValue(KCAL_SAT, Settings.Secure.getInt(context.getContentResolver(),
                    PREF_GRAYSCALE, 0) == 1 ? 128 :
                    Settings.Secure.getInt(context.getContentResolver(),
                            PREF_SATURATION, SATURATION_DEFAULT) + SATURATION_OFFSET);
            FileUtils.setValue(KCAL_VAL, Settings.Secure.getInt(context.getContentResolver(),
                    PREF_VALUE, VALUE_DEFAULT) + VALUE_OFFSET);
            FileUtils.setValue(KCAL_CONT, Settings.Secure.getInt(context.getContentResolver(),
                    PREF_CONTRAST, CONTRAST_DEFAULT) + CONTRAST_OFFSET);
            FileUtils.setValue(KCAL_HUE, Settings.Secure.getInt(context.getContentResolver(),
                    PREF_HUE, HUE_DEFAULT));
        VibratorStrengthPreference.restore(context);
        
	}
    }
}

