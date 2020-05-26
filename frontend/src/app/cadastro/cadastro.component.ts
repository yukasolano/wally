import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Extrato } from '../products/extrato';
import { TabelaProdutosComponent } from '../products/tabela-produtos/tabela-produtos.component';
import { HttpService } from '../services/http.service';

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

    extrato = new Extrato();

    @ViewChild('tabelaExtrato', {static: true}) tabelaExtrato: TabelaProdutosComponent;

    constructor(
      private formBuilder: FormBuilder,
      private httpService: HttpService ) {

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
          importaArquivo: []
        });

        this.formMovFile = this.formBuilder.group({
          arquivo: [''],
        });

        this.formMov = this.formBuilder.group({
          tipoMovimento: ['', Validators.required],
          codigo: ['', Validators.required],
          data: ['', Validators.required],
          valorUnitario: ['', Validators.required],
          quantidade: ['', Validators.required],
        });
    }

    ngOnInit() {
      this.updateExtrato();
    }

    updateExtrato = (): void => {
      this.formRF.reset();
      this.formRV.reset();
      this.formMov.reset();
      this.formMovFile.reset();

      this.httpService.get<Extrato>(`produtos/extrato`).subscribe( resp => {
        this.tabelaExtrato.updateData(resp);
      });
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
          this.httpService.post(`produtos/renda-fixa`, this.formRF.value, this.updateExtrato);
        } else {
          this.httpService.post(`produtos/renda-variavel`, this.formRV.value, this.updateExtrato);
        }
    }

    onSubmitMov() {
      if (this.formMovMain.value.importaArquivo) {
        const formData = new FormData();
        formData.append('arquivo', this.formMovFile.value.arquivo._files[0], this.formMovFile.value.arquivo._files[0].name);
        this.httpService.postFile(`produtos/arquivo-movimento`, formData, this.updateExtrato);
      } else {
          this.httpService.post(`produtos/movimento`, this.formMov.value, this.updateExtrato);
      }
    }

    limpar() {
      this.httpService.post(`produtos/limpar`, {}, this.updateExtrato);
    }

  }
