/*
 * Copyright 2016 Egor Andreevici
 * Copyright 2016 Macoscope
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.macoscope.ketchuplunch.blackbox;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import static com.macoscope.ketchuplunch.blackbox.UiAutomatorUtils.General.clickOnText;

/**
 * Based on: https://github.com/Egorand/android-testing-runtime-permissions
 *
 * Helper methods for UiAutomator
 */
public final class UiAutomatorUtils {

    public static final String TEXT_OK = "OK";
    public static final String TEXT_ALLOW = "Allow";
    public static final String TEXT_DENY = "Deny";
    public static final String TEXT_NEVER_ASK_AGAIN = "Never ask again";
    public static final String TEXT_PERMISSIONS = "Permissions";

    /**
     * Helper methods for entering system settings UI and changing permissions
     */
    public static class SystemSettings {

        // Permissions Setup
        public static void openApplicationSettings(Context context) throws UiObjectNotFoundException {
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            Intent settingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(settingsIntent);
        }

        public static void openPermissions(UiDevice device) throws UiObjectNotFoundException {
            UiObject permissions = device.findObject(new UiSelector().text(TEXT_PERMISSIONS));
            permissions.click();
        }

        /**
         * Not usable, it kills application and instrumentation fails
         */
        public static void togglePermissionInSettings(Context context, UiDevice device, String permissionTitle) throws
                UiObjectNotFoundException {
            openApplicationSettings(context);
            openPermissions(device);
            clickOnText(device, permissionTitle);
        }
    }

    public static class Permissions {
        /**
         * Doesn't work for getTargetContext()
         */
        public static boolean checkPermissionIsGranted(Context context, String permission) {
            return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
    }

    public static class General {

        public static void clickOnText(UiDevice device, String text) throws UiObjectNotFoundException {
            UiObject uiObject = device.findObject(new UiSelector().text(text));
            uiObject.click();
        }

        public static void clickOnTextSilent(UiDevice device, String text) throws UiObjectNotFoundException {
            UiObject uiObject = device.findObject(new UiSelector().text(text));
            if (uiObject.exists()) {
                uiObject.click();
            }
        }
    }

    /**
     * Interactions with Runtime permission popup
     */
    public static class RuntimePermissionPopup {
        // Assertions
        public static void assertViewWithTextIsVisible(UiDevice device, String text) {
            UiObject allowButton = device.findObject(new UiSelector().text(text));
            if (!allowButton.exists()) {
                throw new AssertionError("View with text <" + text + "> not found!");
            }
        }

        // Actions
        public static void allowCurrentPermission(UiDevice device) throws UiObjectNotFoundException {
            clickOnText(device, TEXT_ALLOW);
        }

        public static void denyCurrentPermission(UiDevice device) throws UiObjectNotFoundException {
            clickOnText(device, TEXT_DENY);
        }

        public static void denyCurrentPermissionPermanently(UiDevice device) throws UiObjectNotFoundException {
            clickOnText(device, TEXT_NEVER_ASK_AGAIN);
            denyCurrentPermission(device);
        }
    }
}