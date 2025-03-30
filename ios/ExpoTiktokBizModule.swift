import ExpoModulesCore
import TikTokBusinessSDK


public class ExpoTiktokBizModule: Module {
    // Each module class must implement the definition function. The definition consists of components
    // that describes the module's functionality and behavior.
    // See https://docs.expo.dev/modules/module-api for more details about available components.
    public func definition() -> ModuleDefinition {
        // Sets the name of the module that JavaScript code will use to refer to the module. Takes a string as an argument.
        // Can be inferred from module's class name, but it's recommended to set it explicitly for clarity.
        // The module will be accessible from `requireNativeModule('ExpoTiktokBiz')` in JavaScript.
        Name("ExpoTiktokBiz")
        
        
        AsyncFunction("initialize") { (appId: String, tiktokAppId: String, promise: Promise) in
            let config = TikTokConfig(appId: appId, tiktokAppId: tiktokAppId)
            
            TikTokBusiness.initializeSdk(config) { success, error in
                if success {
                    promise.resolve(true)
                } else {
                    promise.reject(error!)
                }
            }
        }.runOnQueue(.main)
        
        
        AsyncFunction("trackEvent") { (eventName: String, properties: [String: Any], promise: Promise) in
            let customEvent = TikTokBaseEvent(name: eventName)
            for (key, value) in properties {
                switch value {
                case let stringValue as String:
                    customEvent.addProperty(withKey: key, value: stringValue)
                case let numberValue as NSNumber:
                    customEvent.addProperty(withKey: key, value: numberValue)
                case let boolValue as Bool:
                    customEvent.addProperty(withKey: key, value: boolValue)
                case let doubleValue as Double:
                    customEvent.addProperty(withKey: key, value: doubleValue)
                case let intValue as Int:
                    customEvent.addProperty(withKey: key, value: intValue)
                case is NSNull:
                    customEvent.addProperty(withKey: key, value: nil)
                default:
                    // For other types, convert to string
                    customEvent.addProperty(withKey: key, value: String(describing: value))
                }
            }
            TikTokBusiness.trackTTEvent(customEvent)
            
        }.runOnQueue(.main)
    }
}
