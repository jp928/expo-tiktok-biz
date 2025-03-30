package expo.modules.tiktokbiz

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.kotlin.Promise
import expo.modules.kotlin.exception.CodedException
import expo.modules.kotlin.functions.Queues
import android.content.Context
import com.tiktok.TikTokBusinessSdk
import com.tiktok.appevents.base.TTBaseEvent

class ExpoTiktokBizModule : Module() {

  private val context: Context
        get() = appContext.reactContext ?: throw Exception("React context not found")
  // Each module class must implement the definition function. The definition consists of components
  // that describes the module's functionality and behavior.
  // See https://docs.expo.dev/modules/module-api for more details about available components.
  override fun definition() = ModuleDefinition {
    // Sets the name of the module that JavaScript code will use to refer to the module. Takes a string as an argument.
    // Can be inferred from module's class name, but it's recommended to set it explicitly for clarity.
    // The module will be accessible from `requireNativeModule('ExpoTiktokBiz')` in JavaScript.
    Name("ExpoTiktokBiz")

    // Defines a JavaScript function that always returns a Promise and whose native code
    // is by default dispatched on the different thread than the JavaScript runtime runs on.
    AsyncFunction("initialize") { appId: String, secret: String, promise: Promise ->
      // Send an event to JavaScript.
      val ttConfig = TikTokBusinessSdk.TTConfig(context)
            .setAppId(appId)
            .setTTAppId(secret)

      TikTokBusinessSdk.initializeSdk(
        ttConfig
      )

      TikTokBusinessSdk.startTrack()

      promise.resolve(true)
    }.runOnQueue(Queues.MAIN)


    AsyncFunction("trackEvent") {eventName: String?, properties: Map<String, Any>?, promise: Promise ->
      try {
        val eventInfo = TTBaseEvent.newBuilder(eventName)
        // Handle properties
        properties?.forEach { (key, value) ->
            when (value) {
                is String -> eventInfo.addProperty(key, value)
                is Double -> eventInfo.addProperty(key, value)
                is Int -> eventInfo.addProperty(key, value)
                is Boolean -> eventInfo.addProperty(key, value)
                is Float -> eventInfo.addProperty(key, value)
                is Long -> eventInfo.addProperty(key, value)
                null -> eventInfo.addProperty(key, null)
                // For complex objects, convert to string
                else -> eventInfo.addProperty(key, value.toString())
            }
        }

        TikTokBusinessSdk.trackTTEvent(eventInfo.build())
        TikTokBusinessSdk.flush();
        promise?.resolve(true)
    } catch (e: Exception) {
      promise?.reject("TIKTOK_TRACK_ERROR", e.message, e)
    }
    }.runOnQueue(Queues.MAIN)
  }
}
