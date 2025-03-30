import { NativeModule, requireNativeModule } from 'expo';
declare class ExpoTiktokBizModule extends NativeModule {
  initialize(appId: string, tiktokAppId: string): Promise<boolean>;
  trackEvent(
    eventName: string,
    properties: Record<string, any>
  ): Promise<boolean>;
}

// This call loads the native module object from the JSI.
export default requireNativeModule<ExpoTiktokBizModule>('ExpoTiktokBiz');
