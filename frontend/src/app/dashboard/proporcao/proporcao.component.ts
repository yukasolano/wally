import { ViewChild, Component, Input } from '@angular/core';
import { MatAccordion } from '@angular/material';

@Component({
    selector: 'app-proporcao',
    templateUrl: 'proporcao.component.html',
    styleUrls: ['proporcao.component.scss'],
})
export class ProporcaoComponent {

    @ViewChild(MatAccordion, {static: false}) accordion: MatAccordion;

    @Input() data = [];


    temDados() {
        return this.data && this.data.length > 0;
    }

    update(data: []) {
this.data = data;
    }
    /*
    data = [
        {
            nome: 'Renda Fixa',
            valor: 10000.10,
            porcentagem: 0.18,
            categorias: [
                {
                    nome: 'CDI',
                    valor: 10.10,
                    porcentagem: 0.18
                },
                {
                    nome: 'IPCA',
                    valor: 10.10,
                    porcentagem: 0.18
                }
            ]
        },
        {
            nome: 'Ação',
            valor: 1000.10,
            porcentagem: 0.05,
            categorias: [
                {
                    nome: 'Seguro',
                    valor: 10.10,
                    porcentagem: 0.18
                },
                {
                    nome: 'Energia',
                    valor: 10.10,
                    porcentagem: 0.18
                }
            ]
        }
    ];
    */



}
