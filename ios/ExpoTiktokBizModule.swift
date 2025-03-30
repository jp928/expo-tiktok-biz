import ExpoModulesCore

public class ExpoTiktokBizModule: Module {
  // Each module class must implement the definition function. The definition consists of components
  // that describes the module's functionality and behavior.
  // See https://docs.expo.dev/modules/module-api for more details about available components.
  public func definition() -> ModuleDefinition {
    // Sets the name of the module that JavaScript code will use to refer to the module. Takes a string as an argument.
    // Can be inferred from module's class name, but it's recommended to set it explicitly for clarity.
    // The module will be accessible from `requireNativeModule('ExpoTiktokBiz')` in JavaScript.
    Name("ExpoTiktokBiz")


    AsyncFunction("myAsyncFunction") { (appId: String, tiktokAppId: String, Promise: Promise) in
      let config = TikTokConfig(appId: appId, tiktokAppId: tiktokAppId)
      TikTokBusiness.initializeSdk(config: config) { success, error in
        if success {
          promise.resolve(true)
        } else {
          promise.reject(false)
        }
      }
    }.runOnQueue(.main)


    AsyncFunction("trackEvent") { (eventName: String, properties: [String: Any], Promise: Promise) in
      TikTokBusiness.trackEvent(eventName, properties: properties) { success, error in
        if success {
          promise.resolve(true)
        } else {
          promise.reject(false)
        }
      }
    }.runOnQueue(.main)
  }
}
