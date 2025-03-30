import { NativeModule, requireNativeModule } from 'expo';

import { ExpoTiktokBizModuleEvents } from './ExpoTiktokBiz.types';

declare class ExpoTiktokBizModule extends NativeModule<ExpoTiktokBizModuleEvents> {
  PI: number;
  hello(): string;
  setValueAsync(value: string): Promise<void>;
}

// This call loads the native module object from the JSI.
export default requireNativeModule<ExpoTiktokBizModule>('ExpoTiktokBiz');
