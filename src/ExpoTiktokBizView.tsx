import { requireNativeView } from 'expo';
import * as React from 'react';

import { ExpoTiktokBizViewProps } from './ExpoTiktokBiz.types';

const NativeView: React.ComponentType<ExpoTiktokBizViewProps> =
  requireNativeView('ExpoTiktokBiz');

export default function ExpoTiktokBizView(props: ExpoTiktokBizViewProps) {
  return <NativeView {...props} />;
}
