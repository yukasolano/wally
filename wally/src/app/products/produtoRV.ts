import { DecimalPipe, PercentPipe } from '@angular/common';

export class ProdutoRV {
    codigo: string;
    quantidade: number;
    precoTotal: number;
    cotacao: number;
    rentabilidadeDividendo: number;

    getTableInfo() {
        return {
            codigo: {
                name: 'Código',
            },
            quantidade: {
                name: 'Quantidade',
                pipe: DecimalPipe,
                args: ['0.0-0']
            },
            precoTotal: {
                name: 'Preço Total',
                pipe: DecimalPipe,
                args: ['0.2-2']
            },
            cotacao: {
                name: 'Cotação',
                pipe: DecimalPipe,
                args: ['0.2-2']
            },
            rentabilidadeDividendo: {
                name: 'Rentabilidade',
                pipe: PercentPipe,
                args: ['0.2-2']
            },
        };
    }
}
