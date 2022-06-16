import { DecimalPipe, DatePipe, PercentPipe } from '@angular/common';

export class ProdutoRF {
    codigo: string;
    instituicao: string;
    tipoInvestimento: string;
    tipoRentabilidade: string;
    dtVencimento: string;
    dtAplicacao: string;
    valorAplicado: number;
    taxa: number;
    valorPresente: number;
    rentabilidadeLiquida: number;
    taxaAnualLiquida: number;
    taxaMensalLiquida: number;


    getTableInfo() {
        return {
            instituicao: {
                name: 'Instituição',
            },
            tipoInvestimento: {
                name: 'Tipo de investimento',
            },
            tipoRentabilidade: {
                name: 'Tipo de rentabilidade',
            },
            dtAplicacao: {
                name: 'Data de aplicação',
                pipe: DatePipe,
                args: ['shortDate', '']
            },
            dtVencimento: {
                name: 'Data de vencimento',
                pipe: DatePipe,
                args: ['shortDate', '']
            },
            valorAplicado: {
                name: 'Valor aplicado',
                pipe: DecimalPipe,
                args: ['0.2-2']
            },
            valorPresente: {
                name: 'Accrual',
                pipe: DecimalPipe,
                args: ['0.2-2']
            },
            rentabilidadeLiquida: {
                name: 'Rentabilidade líquida',
                pipe: PercentPipe,
                args: ['0.2-2']
            },
            taxaMensalLiquida: {
                name: 'Taxa a.m.',
                pipe: PercentPipe,
                args: ['0.2-2']
            },
            taxaAnualLiquida: {
                name: 'Taxa a.a.',
                pipe: PercentPipe,
                args: ['0.2-2']
            },
        };
    }
}
