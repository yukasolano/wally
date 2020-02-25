import { DatePipe, DecimalPipe } from '@angular/common';

export class Extrato {
    tipoInvestimento: string;
    tipoMovimento: string;
    data: string;
    codigo: string;
    quantidade: number;
    valorUnitario: number;

    getTableInfo() {
        return {
            tipoInvestimento: {
                name: 'Tipo investimento',
            },
            tipoMovimento: {
                name: 'Tipo movimento',
            },
            data: {
                name: 'Data',
                pipe: DatePipe,
                args: ['shortDate', '']
            },
            codigo: {
                name: 'Código',
            },
            quantidade: {
                name: 'Quantidade',
                pipe: DecimalPipe,
                args: ['0.0-0']
            },
            valorUnitario: {
                name: 'Valor unitário',
                pipe: DecimalPipe,
                args: ['0.2-2']
            },
        };
    }
}
