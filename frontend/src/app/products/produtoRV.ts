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
            precoMedio: {
                name: 'Preço médio',
                pipe: DecimalPipe,
                args: ['0.2-2']
            },
            cotacao: {
                name: 'Cotação',
                pipe: DecimalPipe,
                args: ['0.2-2']
            },
            valorPresente: {
                name: 'Valor presente',
                pipe: DecimalPipe,
                args: ['0.2-2']
            },
            resultado: {
                name: 'Resultado',
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
