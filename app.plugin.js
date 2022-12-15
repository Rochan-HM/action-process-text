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
const COPY_ACTIVITY_NAME = 'com.rnactionprocesstext.ActionProcessTextCopyActivity';
const COPY_ACTIVITY_LABEL = 'Copy Selection';
const PASTE_ACTIVITY_NAME = 'com.rnactionprocesstext.ActionProcessTextPasteActivity';
const PASTE_ACTIVITY_LABEL = 'Paste Selection';
const LAUNCH_MODE = 'singleTop';
const ANDROID_THEME = '@style/Theme.Transparent';
const modifyAndroidStyles = (config) => {
    /*
    <style name="Theme.Transparent" parent="@style/Theme.AppCompat.Transparent.NoActionBar">
      <item name="android:windowIsTranslucent">true</item>
      <item name="android:windowBackground">@android:color/transparent</item>
      <item name="android:windowContentOverlay">@null</item>
      <item name="android:windowNoTitle">true</item>
      <item name="android:windowIsFloating">true</item>
      <item name="android:backgroundDimEnabled">false</item>
    </style>
    */
    return (0, config_plugins_1.withAndroidStyles)(config, (config) => {
        config.modResults = {
            ...config.modResults,
            resources: {
                ...config.modResults.resources,
                style: [
                    ...(config.modResults.resources.style || []),
                    {
                        $: {
                            name: 'Theme.Transparent',
                            parent: '@style/Theme.AppCompat.Transparent.NoActionBar',
                        },
                        item: [
                            {
                                $: {
                                    name: 'android:windowIsTranslucent',
                                },
                                _: 'true',
                            },
                            {
                                $: {
                                    name: 'android:windowBackground',
                                },
                                _: '@android:color/transparent',
                            },
                            {
                                $: {
                                    name: 'android:windowContentOverlay',
                                },
                                _: '@null',
                            },
                            {
                                $: {
                                    name: 'android:windowNoTitle',
                                },
                                _: 'true',
                            },
                            {
                                $: {
                                    name: 'android:windowIsFloating',
                                },
                                _: 'true',
                            },
                            {
                                $: {
                                    name: 'android:backgroundDimEnabled',
                                },
                                _: 'false',
                            },
                        ],
                    },
                ],
            },
        };
        return config;
    });
};
const modifyAndroidManifest = (config, { activityName, activityLabel, activityLaunchMode, androidTheme }) => {
    return (0, config_plugins_1.withAndroidManifest)(config, (config) => {
        const mainApplication = config_plugins_1.AndroidConfig.Manifest.getMainApplicationOrThrow(config.modResults);
        const newTextProcessorActivity = {
            '$': {
                'android:name': activityName,
                'android:label': activityLabel,
                'android:exported': 'true',
                'android:launchMode': activityLaunchMode,
                'android:theme': androidTheme,
            },
            'intent-filter': [
                {
                    action: [
                        {
                            $: {
                                'android:name': 'android.intent.action.PROCESS_TEXT',
                            },
                        },
                    ],
                    category: [
                        {
                            $: {
                                'android:name': 'android.intent.category.DEFAULT',
                            },
                        },
                    ],
                    data: [
                        {
                            $: {
                                'android:mimeType': 'text/plain',
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
    config = modifyAndroidStyles(config);
    return config;
};
const config = {
    name: 'rn-action-process-text',
    slug: 'rn-action-process-text',
};
// export default withAndroidProcessTextActivity(config);
exports.default = (0, config_plugins_1.createRunOncePlugin)(withAndroidProcessTextActivity, config.name);
