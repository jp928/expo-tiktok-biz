// Reexport the native module. On web, it will be resolved to ExpoTiktokBizModule.web.ts
// and on native platforms to ExpoTiktokBizModule.ts
export { default } from './ExpoTiktokBizModule';
export { default as ExpoTiktokBizView } from './ExpoTiktokBizView';
export * from  './ExpoTiktokBiz.types';
