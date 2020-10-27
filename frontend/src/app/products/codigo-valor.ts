import { Pipe } from '@angular/core';

export class CodigoValor {
    constructor(private codigo: string, private valor: string, private pipe: Pipe = null, private args: [] = []) {}
}