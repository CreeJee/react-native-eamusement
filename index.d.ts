declare module 'react-native-eamusement' {
    export namespace EamusementHcef {
        export function setSID(sid:string): Promise<void>
        export function enableService(): Promise<void> 
        export function disableService(): Promise<void>
        export const support: boolean;
        export const enabled: boolean;
        
    }
    export namespace EamusementCardConvert {
        export function convertSID(sid:string): Promise<void>
    }
}