import {Component, OnInit, Input} from '@angular/core';

import { AbstractCreateController } from 'src/app/zynerator/controller/AbstractCreateController';

import {TauxRetardTvaService} from 'src/app/controller/service/TauxRetardTva.service';
import {TauxRetardTvaDto} from 'src/app/controller/model/TauxRetardTva.model';
import {TauxRetardTvaCriteria} from 'src/app/controller/criteria/TauxRetardTvaCriteria.model';
@Component({
  selector: 'app-taux-retard-tva-create-admin',
  templateUrl: './taux-retard-tva-create-admin.component.html'
})
export class TauxRetardTvaCreateAdminComponent extends AbstractCreateController<TauxRetardTvaDto, TauxRetardTvaCriteria, TauxRetardTvaService>  implements OnInit {




    constructor( private tauxRetardTvaService: TauxRetardTvaService ) {
        super(tauxRetardTvaService);
    }
    ngOnInit(): void {

    }






    get tauxRetardTva(): TauxRetardTvaDto {

        return this.tauxRetardTvaService.tauxRetardTva;
    }

    set tauxRetardTva(value: TauxRetardTvaDto) {
        this.tauxRetardTvaService.tauxRetardTva = value;
    }

    get tauxRetardTvas(): Array<TauxRetardTvaDto> {


        return this.tauxRetardTvaService.tauxRetardTvas;;
    }

    set tauxRetardTvas(value: Array<TauxRetardTvaDto>) {
        this.tauxRetardTvaService.tauxRetardTvas = value;
    }


    public setValidation(value: boolean){
    }



    public validateForm(): void{
        this.errorMessages = new Array<string>();
    }










}
