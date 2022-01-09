import { Component, OnInit } from '@angular/core';
import * as JSZip from 'jszip';
import { ResultFile } from '../model/result-file';
import { FileService } from '../service/file.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  zip: JSZip;
  resultFiles: ResultFile[] = [];
  targetTxt = [
    'AG.txt',
    'HN.txt',
    'OU.txt',
    'VZ.txt'
  ];
  activeView = -1;
  originalZip: Blob;

  constructor(private fileService: FileService) { }

  ngOnInit(): void {
    this.zip = new JSZip();
  }

  public fileUpload($event: any): void {
    const files: File[] = [];
    for (const iterator of $event.target.files) {
      files.push(iterator);
    }

    this.fileService.postFiles(files).subscribe((response) => {
        const blob = new Blob([response], { type: 'application/octet-stream' });
        this.originalZip = blob;

        this.resultFiles = [];

        this.zip.loadAsync(blob).then(data => {

          for (let key of this.targetTxt) {
            let reader = new FileReader();
            this.readZipFile(data, key, reader);
            reader.onload = () => {
              let file = reader.result ? reader.result.toString() : "ERROR";
              this.resultFiles.push(new ResultFile(file, false))
            }
          }
        });
    });
  }

  private readZipFile(data: any, key: string, reader: FileReader): void {
    console.log(data.files[key]);

    if (data.files[key]) {
      data.files[key].async('blob').then((value: Blob) => {
          reader.readAsText(value);
      });
    } else {
      this.resultFiles.push(new ResultFile("", true));
      console.log('Hello Reached me', this.resultFiles);

    }
  }

  public setActiveView(i: number): void {
    this.activeView = i;
  }

  public download() {
    const url= window.URL.createObjectURL(this.originalZip);
    window.open(url);
  }
}
