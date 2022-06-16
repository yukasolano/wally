import { DynamicPipe } from "./dynamic.pipe"
import { TestBed } from '@angular/core/testing';
import { Injector } from '@angular/core';
import { DecimalPipe, DatePipe, PercentPipe } from '@angular/common';

describe('DynamicPipe', () => {

    let pipe: DynamicPipe;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [DynamicPipe],
            providers: [
                Injector,  
                DecimalPipe,
                DatePipe,
                PercentPipe
            ]
        })

        pipe = new DynamicPipe(TestBed);
    })
    
    it('should transform text in text', () => {
        expect(pipe.transform('text', null, null)).toBe('text');
    })

    it('should transform 2020-12-05 in 05/12/2020', () => {
        expect(pipe.transform('2020-12-05', DatePipe, ['shortDate', ''])).toBe('05/12/2020');
    })

    it('should transform 1234.56 in 1.234,56', () => {
       expect(pipe.transform(1234.56, DecimalPipe, ['0.2-2'])).toBe('1.234,56');
    })

    it('should transform 0.125678 in 12.56%', () => {
        expect(pipe.transform('0.125678', PercentPipe, ['0.2-2'])).toBe('12,57%');
    })
})