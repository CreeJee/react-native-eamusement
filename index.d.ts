declare module 'react-native-eamusement' {
    export namespace EamusementHcef {
        export function setSID(sid:string): Promise<boolean>
        export function enableService(): Promise<boolean> 
        export function disableService(): Promise<boolean>
        // constant
        export const support: boolean;
        export const enabled: boolean;
        export const HCEF_FATAL_ERROR = "FATAL_ERROR";
        export const HCEF_UID_HEX_ERROR = "UID_HEX_ERROR";
        export const HCEF_UID_LENGTH_ERROR = "UID_LENGTH_ERROR";
        export const HCEF_UID_PREFIX_ERROR = "UID_PREFIX_ERROR";
    }
    export namespace EamusementCardConvert {
        export function convertKonamiID(nfcID:string): Promise<string>
        export function convertNfcID(konamiCard:string): Promise<string>
    }
    }
}