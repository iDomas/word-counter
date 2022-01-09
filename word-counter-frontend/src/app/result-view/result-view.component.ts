import { AfterContentInit, AfterViewInit, Component, Input, OnInit } from '@angular/core';
import { ResultFile } from '../model/result-file';
import { TableContent } from '../model/table-content';

@Component({
  selector: 'app-result-view',
  templateUrl: './result-view.component.html',
  styleUrls: ['./result-view.component.scss']
})
export class ResultViewComponent implements AfterContentInit {

  @Input()
  resultData: ResultFile;
  tableData: string[] = [];

  displayedColumns: string[] = ['position', 'word', 'count'];
  dataSource: TableContent[];

  constructor() { }

  ngAfterContentInit(): void {
    console.log(this.resultData);

    if (!this.resultData.empty) {
      this.tableData = this.resultData.file.split("\n");
      this.dataSource = this.mapTableContent(this.tableData);
    } else {
      this.tableData = [];
    }

  }

  private mapTableContent(tableData: string[]): TableContent[] {
    return tableData.filter((row, index) => index != 0)
                    .map((row, index) => new TableContent(index + 1, row.split(',')[0], row.split(',')[1]));
  }

}
