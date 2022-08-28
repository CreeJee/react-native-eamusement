import * as React from 'react';
import { useEffect } from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { EamusementCardConvert, EamusementHcef } from 'react-native-eamusement';

export default function App() {
  useEffect(() => {
    const init = async () => {
      const uid = await EamusementCardConvert.convertNfcID('67JWR00RUE1E0111');
      const isSid = await EamusementHcef.setSID(uid);
      console.log('bindService', isSid);
      if (isSid) {
        console.log('bootedService', await EamusementHcef.enableService());
      }
    };
    init();
    return () => {
      EamusementHcef.disableService();
    };
  }, []);
  return (
    <View style={styles.container}>
      <Text>Example</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
