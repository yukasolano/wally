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

}
