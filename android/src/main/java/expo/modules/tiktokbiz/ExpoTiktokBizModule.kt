package expo.modules.tiktokbiz

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import java.net.URL
import expo.modules.kotlin.Promise
import expo.modules.kotlin.exception.CodedException
import expo.modules.kotlin.functions.Queues
import com.bytedance.android.tiktok.TikTokBusinessSdk
class ExpoTiktokBizModule : Module() {
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
    AsyncFunction("initialize") { context: Context, appId: String, secret: String, promise: Promise ->
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


    AsyncFunction("trackEvent") {eventName: String?, properties: ReadableMap?, promise: Promise ->
      val eventInfo = TTBaseEvent.newBuilder(eventName)
      val iterator = properties?.keySetIterator()
      while (iterator != null && iterator.hasNextKey()) {
          val key = iterator.nextKey()
          when (properties.getType(key)) {
              ReadableType.Boolean -> {
                  val value = properties.getBoolean(key)
                  eventInfo.addProperty(key, value)
              }
              ReadableType.Number -> {
                  val value = properties.getDouble(key)
                  eventInfo.addProperty(key, value)
              }
              ReadableType.String -> {
                  val value = properties.getString(key)
                  eventInfo.addProperty(key, value)
              }
              ReadableType.Null -> {
                  eventInfo.addProperty(key, null)
              }
              ReadableType.Map -> {
                  // Handle map if needed
              }
              ReadableType.Array -> {
                  // Handle array if needed
              }
          }
      }
      try {
        TikTokBusinessSdk.trackTTEvent(eventInfo.build())
        TikTokBusinessSdk.flush();
        promise?.resolve(true)
    } catch (e: Exception) {
      promise?.reject("TIKTOK_TRACK_ERROR", e.message, e)
    }
    }.runOnQueue(Queues.MAIN)
  }
}
