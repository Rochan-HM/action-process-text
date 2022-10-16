"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const config_plugins_1 = require("@expo/config-plugins");
/**
 *
 * Add the following to the manifest:
  <activity
      android:name=".ActionProcessTextCopyActivity"
      android:exported="true"
      android:label="Copy Selection"
      android:launchMode="singleTop"
      android:theme="@style/Theme.AppCompat.Transparent.NoActionBar">
      <intent-filter>
          <action android:name="android.intent.action.PROCESS_TEXT" />
          <data android:mimeType="text/plain" />
          <category android:name="android.intent.category.DEFAULT" />
      </intent-filter>
  </activity>
 */
const COPY_ACTIVITY_NAME = "com.rnactionprocesstext.ActionProcessTextCopyActivity";
const COPY_ACTIVITY_LABEL = "Copy Selection";
const PASTE_ACTIVITY_NAME = "com.rnactionprocesstext.ActionProcessTextPasteActivity";
const PASTE_ACTIVITY_LABEL = "Paste Selection";
const LAUNCH_MODE = "singleTop";
const ANDROID_THEME = "@style/Theme.AppCompat.Transparent.NoActionBar";
const modifyAndroidManifest = (config, { activityName, activityLabel, activityLaunchMode, androidTheme }) => {
    return config_plugins_1.withAndroidManifest(config, (config) => {
        const mainApplication = config_plugins_1.AndroidConfig.Manifest.getMainApplicationOrThrow(config.modResults);
        const newTextProcessorActivity = {
            $: {
                "android:name": activityName,
                "android:label": activityLabel,
                "android:exported": "true",
                "android:launchMode": activityLaunchMode,
                "android:theme": androidTheme,
            },
            "intent-filter": [
                {
                    action: [
                        {
                            $: {
                                "android:name": "android.intent.action.PROCESS_TEXT",
                            },
                        },
                    ],
                    category: [
                        {
                            $: {
                                "android:name": "android.intent.category.DEFAULT",
                            },
                        },
                    ],
                    data: [
                        {
                            $: {
                                "android:mimeType": "text/plain",
                            },
                        },
                    ],
                },
            ],
        };
        if (!mainApplication.activity) {
            mainApplication.activity = [];
            mainApplication.activity.push(newTextProcessorActivity);
            return config;
        }
        const oldActivity = mainApplication.activity.findIndex((activity) => activity.$.name === activityName);
        if (oldActivity === -1) {
            mainApplication.activity.push(newTextProcessorActivity);
        }
        else {
            mainApplication.activity.splice(oldActivity, 1, newTextProcessorActivity);
        }
        return config;
    });
};
const withAndroidProcessTextActivity = (config) => {
    [
        { name: PASTE_ACTIVITY_NAME, label: PASTE_ACTIVITY_LABEL },
        { name: COPY_ACTIVITY_NAME, label: COPY_ACTIVITY_LABEL },
    ].forEach(({ name, label }) => {
        config = modifyAndroidManifest(config, {
            activityName: name,
            activityLabel: label,
            activityLaunchMode: LAUNCH_MODE,
            androidTheme: ANDROID_THEME,
        });
    });
    return config;
};
exports.default = config_plugins_1.createRunOncePlugin(withAndroidProcessTextActivity, "withAndroidProcessTextActivity");
