import * as React from 'react';

import { ExpoTiktokBizViewProps } from './ExpoTiktokBiz.types';

export default function ExpoTiktokBizView(props: ExpoTiktokBizViewProps) {
  return (
    <div>
      <iframe
        style={{ flex: 1 }}
        src={props.url}
        onLoad={() => props.onLoad({ nativeEvent: { url: props.url } })}
      />
    </div>
  );
}
