import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { file } from 'jszip';
import { Observable } from 'rxjs';
import { ConfigService } from './config.service';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  private FILE_ENDPOINT = '/file';

  constructor(private http: HttpClient,
    private configService: ConfigService) { }

  public postFiles(files: File[]): Observable<any> {
    let boundary = 0;
    for (let iterator of files) {
      boundary += iterator.size;
    }
    let formData = new FormData();
    formData.append('files', new Blob(files));

    console.log(formData);
    return this.http.post<any>(
        this.configService.getConfig().apiHost + this.FILE_ENDPOINT,
        formData,
        {responseType: 'blob' as 'json'}
      );
  }

}
