import { Component, OnInit } from '@angular/core';
import { DatePipe, DecimalPipe } from '@angular/common';

import { DadosMercado } from './dados-mercado';
import { HttpService } from '../services/http.service';


@Component({
    selector: 'app-dados-mercado',
    templateUrl: './dados-mercado.component.html',
    styleUrls: ['./dados-mercado.component.scss']
  })
  export class DadosMercadoComponent implements OnInit {

    dadosMercado: DadosMercado;

    constructor(private httpService: HttpService ) {}

    ngOnInit() {
    }

    onAtualiza() {
        this.httpService.post('dados-mercado/atualiza', {});
    }

    onLimpa() {
        this.httpService.post('dados-mercado/limpa', {});
    }

    onBusca() {
        this.httpService.get<DadosMercado>('dados-mercado/busca').subscribe(resp => this.dadosMercado = resp);
    }

    getTableInfo() {
        return {
            data: {
                name: 'Data',
                pipe: DatePipe,
                args: ['shortDate', '']
            },
            valor: {
                name: 'Valor',
                pipe: DecimalPipe,
                args: ['0.2-2']
            },
        };
    }
  }
