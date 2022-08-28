export interface EamusementHcefModule {
  setSID(sid: string): Promise<boolean>;
  enableService(): Promise<boolean>;
  disableService(): Promise<boolean>;
  // constant
  support: boolean;
  enabled: boolean;
  HCEF_FATAL_ERROR: 'FATAL_ERROR';
  HCEF_UID_HEX_ERROR: 'UID_HEX_ERROR';
  HCEF_UID_LENGTH_ERROR: 'UID_LENGTH_ERROR';
  HCEF_UID_PREFIX_ERROR: 'UID_PREFIX_ERROR';
}
export interface EamusementCardConvertModule {
  convertKonamiID(nfcID: string): Promise<string>;
  convertNfcID(konamiCard: string): Promise<string>;
}
