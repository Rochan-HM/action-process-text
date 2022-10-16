"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.sharedPreferenceName = sharedPreferenceName;
var _reactNative = require("react-native");
const LINKING_ERROR = `The package 'rn-action-process-text' doesn't seem to be linked. Make sure: \n\n` + _reactNative.Platform.select({
  ios: "- You have run 'pod install'\n",
  default: ''
}) + '- You rebuilt the app after installing the package\n' + '- You are not using Expo Go\n';
const RnActionProcessText = _reactNative.NativeModules.RnActionProcessText ? _reactNative.NativeModules.RnActionProcessText : new Proxy({}, {
  get() {
    throw new Error(LINKING_ERROR);
  }
});
function sharedPreferenceName() {
  return RnActionProcessText.SharedPreferenceName();
}
//# sourceMappingURL=index.js.map