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
    formRF: FormGroup;
    formRV: FormGroup;

    formMovMain: FormGroup;
    formMovFile: FormGroup;
    formMov: FormGroup;

    constructor(
      private formBuilder: FormBuilder,
      private http: HttpClient ) {

        this.form = this.formBuilder.group({
          categoria: ['RF'],
        });

        this.formRF = this.formBuilder.group({
          instituicao: ['', Validators.required],
          tipoInvestimento: ['', Validators.required],
          tipoRentabilidade: ['', Validators.required],
          dtVencimento: ['', Validators.required],
          dtAplicacao: ['', Validators.required],
          taxa: ['', Validators.required],
          valorAplicado: ['', Validators.required]
        });

        this.formRV = this.formBuilder.group({
          tipoInvestimento: ['', Validators.required],
          codigo: ['', Validators.required],
          data: ['', Validators.required],
          valorUnitario: ['', Validators.required],
          quantidade: ['', Validators.required],
        });

        this.formMovMain = this.formBuilder.group({
          importaArquivo: [false]
        });

        this.formMovFile = this.formBuilder.group({
          arquivo: [''],
        });

        this.formMov = this.formBuilder.group({
          tipoInvestimento: ['', Validators.required],
          codigo: ['', Validators.required],
          data: ['', Validators.required],
          valorUnitario: ['', Validators.required],
          quantidade: ['', Validators.required],
        });
    }


    httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    ngOnInit() {
    }

    formInvalid() {
      return this.form.value.categoria === 'RF' ? this.formRF.invalid : this.formRV.invalid;
    }

    formMovInvalid() {
      if (this.formMovMain.value.importaArquivo ) {
        return this.formMovFile.invalid;
      }
      return this.formMov.invalid;
    }

    onSubmit() {
        if (this.form.value.categoria === 'RF') {

          this.http.post(`${environment.baseUrl}produtos/renda-fixa`, this.formRF.value, this.httpOptions).subscribe(
            resp => {
            console.log('sucesooo', resp);
            this.formRF.reset();
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


    onSubmitMov() {
      if (this.formMovMain.value.importaArquivo) {
        const formData = new FormData();
        formData.append('arquivo', this.formMovFile.value.arquivo._files[0], this.formMovFile.value.arquivo._files[0].name);
        this.http.post(`${environment.baseUrl}produtos/arquivo-movimento`, formData).subscribe(
          resp => {
          console.log('sucesooo', resp);
          this.formMovFile.reset();
        },
          error => {
          console.log('errrou', error);
        });
      } else {
          this.http.post(`${environment.baseUrl}produtos/movimento`, this.formMov.value, this.httpOptions).subscribe(
            resp => {
            console.log('sucesooo', resp);
            this.formMov.reset();
          },
            error => {
            console.log('errrou', error);
          });
      }
    }

    limpar() {
            this.http.post(`${environment.baseUrl}produtos/limpar`, {}, this.httpOptions).subscribe(
              resp => {
              console.log('sucesooo', resp);
            },
              error => {
              console.log('errrou', error);
            });
        }

  }
