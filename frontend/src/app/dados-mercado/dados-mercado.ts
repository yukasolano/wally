import { DatePipe, DecimalPipe } from '@angular/common';

export class DadosMercado {
    dados: Serie[] =  [];
}

export class Serie {
    nome: string;
    valores = [];
}


