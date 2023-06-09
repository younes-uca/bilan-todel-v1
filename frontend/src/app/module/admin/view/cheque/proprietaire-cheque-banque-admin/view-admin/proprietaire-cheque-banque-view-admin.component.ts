import {Component, OnInit} from '@angular/core';


import {AbstractViewController} from 'src/app/zynerator/controller/AbstractViewController';
import { environment } from 'src/environments/environment';

import {ProprietaireChequeBanqueService} from 'src/app/controller/service/ProprietaireChequeBanque.service';
import {ProprietaireChequeBanqueDto} from 'src/app/controller/model/ProprietaireChequeBanque.model';
import {ProprietaireChequeBanqueCriteria} from 'src/app/controller/criteria/ProprietaireChequeBanqueCriteria.model';

import {ProprietaireChequeDto} from 'src/app/controller/model/ProprietaireCheque.model';
import {ProprietaireChequeService} from 'src/app/controller/service/ProprietaireCheque.service';
import {BanqueDto} from 'src/app/controller/model/Banque.model';
import {BanqueService} from 'src/app/controller/service/Banque.service';
@Component({
  selector: 'app-proprietaire-cheque-banque-view-admin',
  templateUrl: './proprietaire-cheque-banque-view-admin.component.html'
})
export class ProprietaireChequeBanqueViewAdminComponent extends AbstractViewController<ProprietaireChequeBanqueDto, ProprietaireChequeBanqueCriteria, ProprietaireChequeBanqueService> implements OnInit {


    constructor(private proprietaireChequeBanqueService: ProprietaireChequeBanqueService, private proprietaireChequeService: ProprietaireChequeService, private banqueService: BanqueService){
        super(proprietaireChequeBanqueService);
    }

    ngOnInit(): void {
        this.proprietaireCheque = new ProprietaireChequeDto();
        this.proprietaireChequeService.findAll().subscribe((data) => this.proprietaireCheques = data);
        this.banque = new BanqueDto();
        this.banqueService.findAll().subscribe((data) => this.banques = data);
    }


    get banque(): BanqueDto {
       return this.banqueService.item;
    }
    set banque(value: BanqueDto) {
        this.banqueService.item = value;
    }
    get banques():Array<BanqueDto> {
       return this.banqueService.items;
    }
    set banques(value: Array<BanqueDto>) {
        this.banqueService.items = value;
    }
    get proprietaireCheque(): ProprietaireChequeDto {
       return this.proprietaireChequeService.item;
    }
    set proprietaireCheque(value: ProprietaireChequeDto) {
        this.proprietaireChequeService.item = value;
    }
    get proprietaireCheques():Array<ProprietaireChequeDto> {
       return this.proprietaireChequeService.items;
    }
    set proprietaireCheques(value: Array<ProprietaireChequeDto>) {
        this.proprietaireChequeService.items = value;
    }


}
