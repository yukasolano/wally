import { Component, OnInit, Input } from '@angular/core';
import { DecimalPipe, PercentPipe } from '@angular/common';
import { MatTableDataSource } from '@angular/material/table';

@Component({
    selector: 'app-summary',
    templateUrl: './summary.component.html',
    styleUrls: ['./summary.component.scss']
  })
  export class SummaryComponent implements OnInit {

    @Input() titulo;

    public patrimonioTotal = 0.0;
    displayedColumns: string[];
    dataSource = new MatTableDataSource();

    constructor() {}

    ngOnInit() {
        this.displayedColumns = Object.keys(this.getTableInfo());
    }

    update(variacao, patrimonioTotal) {
        this.patrimonioTotal = patrimonioTotal;
        this.dataSource = new MatTableDataSource([
            {nome: 'Mensal', absoluto: variacao.mensalAbsoluto, percentual: variacao.mensalPorcentagem },
            {nome: 'Anual', absoluto: variacao.anualAbsoluto, percentual: variacao.anualPorcentagem }
        ]);
    }

    getTableInfo() {
        return {
            nome: {
                name: '',
            },
            absoluto: {
                name: 'Variação',
                pipe: DecimalPipe,
                args: ['0.2-2']
            },
            percentual: {
                name: 'Rentabilidade',
                pipe: PercentPipe,
                args: ['0.2-2']
            },
        };
    }

    temDados() {
        return true;
    }
}
