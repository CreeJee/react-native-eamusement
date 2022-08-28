import { NativeModules, Platform } from 'react-native';

import type {
  EamusementCardConvertModule,
  EamusementHcefModule,
} from './types';

const LINKING_ERROR =
  `The package 'react-native-eamusement' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const PLATFORM_ERROR = `The package 'react-native-eamusement' is not supported for ${Platform.OS}`;

const wrapSafeLinking = <T extends object>(obj?: T): T => {
  const isSupportPlatform = Platform.OS === 'android';
  const isLink = !!obj;
  return isLink && isSupportPlatform
    ? obj
    : new Proxy<T>({} as T, {
        get() {
          throw new Error(isSupportPlatform ? LINKING_ERROR : PLATFORM_ERROR);
        },
      });
};

export const EamusementHcef = wrapSafeLinking(
  NativeModules.EamusementHcef as EamusementHcefModule
);
export const EamusementCardConvert = wrapSafeLinking(
  NativeModules.EamusementCardConvert as EamusementCardConvertModule
);
