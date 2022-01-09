import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, shareReplay } from 'rxjs';
import { Config } from '../model/config';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  private CONFIG_PATH = "assets/config.json";
  private config: Config;

  constructor(private http: HttpClient) {
    this.http.get<Config>(this.CONFIG_PATH).subscribe(cofnig => {
      this.config = cofnig;
    })
  }

  public getConfig(): Config {
    return this.config;
  }
}
