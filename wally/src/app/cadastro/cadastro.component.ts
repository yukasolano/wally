import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
    selector: 'app-cadastro',
    templateUrl: './cadastro.component.html',
    styleUrls: ['./cadastro.component.scss']
  })
  export class CadastroComponent implements OnInit {

    form: FormGroup;
    formFileRF: FormGroup;
    formRF: FormGroup;
    formRV: FormGroup;
    formFileRV: FormGroup;

    constructor(
      private formBuilder: FormBuilder,
      private http: HttpClient ) {

        this.form = this.formBuilder.group({
          categoria: ['RF'],
          importaArquivo: [false]
        });

        this.formRF = this.formBuilder.group({
          corretora: ['', Validators.required],
          instituicao: ['', Validators.required],
          tipoInvestimento: ['', Validators.required],
          tipoRentabilidade: ['', Validators.required],
          dtVencimento: ['', Validators.required],
          dtAplicacao: ['', Validators.required],
          taxa: ['', Validators.required],
          valorAplicado: ['', Validators.required]
        });

        this.formFileRF = this.formBuilder.group({
          arquivo: [''],
        });

        this.formRV = this.formBuilder.group({
          tipo: ['dividendo', Validators.required],
          codigo: ['', Validators.required],
          data: ['', Validators.required],
          valorUnitario: ['', Validators.required],
          quantidade: ['', Validators.required],
        });

        this.formFileRV = this.formBuilder.group({
          arquivo: [''],
        });
    }


    httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    ngOnInit() {
    }

    formInvalid() {
      if (this.form.value.categoria === 'RF') {
        return this.form.value.importaArquivo ? this.formFileRF.invalid : this.formRF.invalid;
      }
      return this.form.value.importaArquivo ? this.formFileRV.invalid : this.formRV.invalid;
    }
    onSubmit() {
      if (this.form.value.categoria === 'RF') {

        if (this.form.value.importaArquivo) {
          console.log( this.formFileRF.value.arquivo._files[0]);
          const formData = new FormData();
          formData.append('arquivo', this.formFileRF.value.arquivo._files[0], this.formFileRF.value.arquivo._files[0].name);
          this.http.post(`${environment.baseUrl}produtos/arquivo-renda-fixa`, formData).subscribe(
            resp => {
            console.log('sucesooo', resp);
            this.formFileRF.reset();
          },
            error => {
            console.log('errrou', error);
          });
        } else {

          this.http.post(`${environment.baseUrl}produtos/renda-fixa`, this.formRF.value, this.httpOptions).subscribe(
            resp => {
            console.log('sucesooo', resp);
            this.formRF.reset();
          },
            error => {
            console.log('errrou', error);
          });
        }
      } else {

        if (this.form.value.importaArquivo) {
          console.log( this.formFileRV.value.arquivo._files[0]);
          const formData = new FormData();
          formData.append('arquivo', this.formFileRV.value.arquivo._files[0], this.formFileRV.value.arquivo._files[0].name);
          this.http.post(`${environment.baseUrl}produtos/arquivo-renda-variavel`, formData).subscribe(
            resp => {
            console.log('sucesooo', resp);
            this.formFileRV.reset();
          },
            error => {
            console.log('errrou', error);
          });
        } else {

          this.http.post(`${environment.baseUrl}produtos/renda-variavel`, this.formRV.value, this.httpOptions).subscribe(
            resp => {
            console.log('sucesooo', resp);
            this.formRV.reset();
          },
            error => {
            console.log('errrou', error);
          });
        }
      }
    }

  }
