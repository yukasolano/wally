import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { DadosMercado } from './dados-mercado';
import { DatePipe, DecimalPipe } from '@angular/common';

@Component({
    selector: 'app-dados-mercado',
    templateUrl: './dados-mercado.component.html',
    styleUrls: ['./dados-mercado.component.scss']
  })
  export class DadosMercadoComponent implements OnInit {

    dadosMercado: DadosMercado;

    constructor(
      private http: HttpClient ) {
    }


    httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    ngOnInit() {
    }

    onAtualiza() {
        this.http.get(`${environment.baseUrl}dados-mercado/atualiza`).subscribe(
        resp => {
            console.log('sucesooo', resp);

        },
        error => {
            console.log('errrou', error);
        });
    }

    onLimpa() {
        this.http.get(`${environment.baseUrl}dados-mercado/limpar`).subscribe(
        resp => {
            console.log('sucesooo', resp);

        },
        error => {
            console.log('errrou', error);
        });
    }

    onBusca() {
        this.http.get<DadosMercado>(`${environment.baseUrl}dados-mercado/busca`).subscribe(
        resp => {
            console.log('sucesooo', resp);
            this.dadosMercado = resp;
        },
        error => {
            console.log('errrou', error);
        });
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
