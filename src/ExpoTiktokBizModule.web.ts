import { registerWebModule, NativeModule } from 'expo';

import { ExpoTiktokBizModuleEvents } from './ExpoTiktokBiz.types';

class ExpoTiktokBizModule extends NativeModule<ExpoTiktokBizModuleEvents> {
  PI = Math.PI;
  async setValueAsync(value: string): Promise<void> {
    this.emit('onChange', { value });
  }
  hello() {
    return 'Hello world! 👋';
  }
}

export default registerWebModule(ExpoTiktokBizModule);
