// encofrid.pipe.ts
import { Pipe, PipeTransform } from '@angular/core';
import * as CryptoJS from 'crypto-js';

@Pipe({
  name: 'EccodingUriPipe'
})
export class EccodingUriPipe implements PipeTransform {

  private SECRET_KEY = 'your-secret-key';  // Replace with your secret key

  transform(value: string, encode: boolean = true): string {
    return encode ? this.encoFrID(value) : this.decoFrID(value);
  }

  private encoFrID(id: string): string {
    console.log('Original ID:', id);
    const encrypted = CryptoJS.AES.encrypt(id, this.SECRET_KEY).toString();
    console.log('encrypted ID:', encrypted);
    const encoded = encodeURIComponent(btoa(encrypted));
    console.log('Encoded ID:', encoded);
    return encoded;
  }

  private decoFrID(encodedId: string): string {
    console.log('Encoded ID:', encodedId);
    const urlDecoded = decodeURIComponent(encodedId);
    const base64Decoded = atob(urlDecoded);
    const decryptedBytes = CryptoJS.AES.decrypt(base64Decoded, this.SECRET_KEY);
    const decrypted = decryptedBytes.toString(CryptoJS.enc.Utf8);
    console.log('Decoded ID:', decrypted);
    return decrypted;
  }


}
