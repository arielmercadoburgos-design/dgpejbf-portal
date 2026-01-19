import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import CryptoJS from 'crypto-js';
import { map, Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';

@Injectable({ providedIn: 'root' })
export class MetabaseService {
  private baseUrl = '/api/metabase'; // usa proxy o la URL base del backend
  // IMPORTANTE: Esta clave debe ser EXACTAMENTE la misma que en application-dev.yml
  private readonly SECRET_KEY = environment.metabaseEncryptKey;
  constructor(private http: HttpClient) {}
  getEmbedUrl(resource: string, id: number): Observable<string> {
    // Tipo de respuesta esperada: un objeto con la propiedad iframeUrlCifrada
    return this.http
      .get<{ iframeUrlCifrada: string }>(
        // Argumento 'url' para el método get()
        `${this.baseUrl}/embed?resource=${resource}&id=${id}`,
      )
      .pipe(
        // Transforma el objeto de respuesta en una URL descifrada (string)
        map(response => {
          const urlDescifrada = this.decrypt(response.iframeUrlCifrada);
          console.warn('URL DESCIFRADA:', urlDescifrada); // Línea de debug, puedes quitarla después
          return urlDescifrada;
        }),
      );
  }

  // Método para descifrar la cadena AES-256 (mismo algoritmo que en Java)
  // metabase.service.ts

  private decrypt(encryptedText: string): string {
    // 1. Convertir la clave secreta Base64 a WordArray
    const key = CryptoJS.enc.Base64.parse(this.SECRET_KEY); // <-- Usa Base64.parse

    // 2. Crear un objeto CipherParams indicando que la entrada cifrada es Base64
    const cipherParams = CryptoJS.lib.CipherParams.create({
      ciphertext: CryptoJS.enc.Base64.parse(encryptedText), // <-- Parsear la entrada como Base64
    });

    // 3. Descifrar usando los modos coincidentes (ECB, PKCS7)
    const decrypted = CryptoJS.AES.decrypt(cipherParams, key, {
      mode: CryptoJS.mode.ECB,
      padding: CryptoJS.pad.Pkcs7,
    });

    // 4. Intentar convertir a UTF-8 (Falla si el descifrado es incorrecto)
    try {
      return decrypted.toString(CryptoJS.enc.Utf8); // LÍNEA 40 (donde ocurre el error)
    } catch (e) {
      // Capturamos el error Malformed UTF-8 data y lo reportamos claramente
      console.error('ERROR CRÍTICO: Descifrado fallido. Clave o modo incorrecto.', e);
      // Lanzar un error para que el componente no use un valor inválido
      throw new Error('La clave de cifrado no coincide o el algoritmo es incorrecto.');
    }
  }
}
