import { DatePipe, DecimalPipe, PercentPipe } from '@angular/common';

export class Extrato {
    tipoInvestimento: string;
    tipoMovimento: string;
    tipoRentabilidade: string;
    data: string;
    codigo: string;
    quantidade: number;
    valor: number;
    instituicao: string;
    taxa: number;
    vencimento: string;
    corretora: string;

    getTableInfo() {
        return {
            tipoInvestimento: {
                name: 'Tipo investimento',
            },
            tipoMovimento: {
                name: 'Tipo movimento',
            },
            codigo: {
                name: 'Código',
            },
            instituicao: {
                name: 'Instituição',
            },
            tipoRentabilidade: {
                name: 'Tipo de rentabilidade',
            },
            vencimento: {
                name: 'Vencimento',
                pipe: DatePipe,
                args: ['shortDate', '']
            },
            taxa: {
                name: 'Taxa',
                pipe: PercentPipe,
                args: ['0.2-2'],
            },
            data: {
                name: 'Data',
                pipe: DatePipe,
                args: ['shortDate', '']
            },
            quantidade: {
                name: 'Quantidade',
                pipe: DecimalPipe,
                args: ['0.0-0']
            },
            valor: {
                name: 'Valor unitário',
                pipe: DecimalPipe,
                args: ['0.2-2']
            },
            corretora: {
                name: 'Corretora',
            }
        };
    }
}
