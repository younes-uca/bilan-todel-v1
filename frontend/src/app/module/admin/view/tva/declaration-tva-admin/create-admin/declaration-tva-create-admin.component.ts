import {Component, OnInit, Input} from '@angular/core';

import {AbstractCreateController} from 'src/app/zynerator/controller/AbstractCreateController';

import {DeclarationTvaService} from 'src/app/controller/service/DeclarationTva.service';
import {DeclarationTvaDto} from 'src/app/controller/model/DeclarationTva.model';
import {DeclarationTvaCriteria} from 'src/app/controller/criteria/DeclarationTvaCriteria.model';
import {ComptableTraitantDto} from 'src/app/controller/model/ComptableTraitant.model';
import {ComptableTraitantService} from 'src/app/controller/service/ComptableTraitant.service';
import {SocieteDto} from 'src/app/controller/model/Societe.model';
import {SocieteService} from 'src/app/controller/service/Societe.service';
import {ComptableValidateurDto} from 'src/app/controller/model/ComptableValidateur.model';
import {ComptableValidateurService} from 'src/app/controller/service/ComptableValidateur.service';
import {TauxRetardTvaDto} from 'src/app/controller/model/TauxRetardTva.model';
import {TauxRetardTvaService} from 'src/app/controller/service/TauxRetardTva.service';
import {DeclarationIsDto} from "../../../../../../controller/model/DeclarationIs.model";

@Component({
    selector: 'app-declaration-tva-create-admin',
    templateUrl: './declaration-tva-create-admin.component.html'
})
export class DeclarationTvaCreateAdminComponent extends AbstractCreateController<DeclarationTvaDto, DeclarationTvaCriteria, DeclarationTvaService> implements OnInit {

    trimestres : number[] = [];
    years: number[] = [];
    private simuler : boolean = true;

    private _validDeclarationTvaReference = true;
    private _validSocieteIce = true;
    private _validComptableTraitantCin = true;
    private _validComptableValidateurCin = true;

    constructor(private declarationTvaService: DeclarationTvaService, private comptableTraitantService: ComptableTraitantService, private societeService: SocieteService, private comptableValidateurService: ComptableValidateurService, private tauxRetardTvaService: TauxRetardTvaService) {
        super(declarationTvaService);
    }

    ngOnInit(): void {
        this.societe = new SocieteDto();
        this.societeService.findAll().subscribe((data) => this.societes = data);
        this.tauxRetardTva = new TauxRetardTvaDto();
        this.tauxRetardTvaService.findAll().subscribe((data) => this.tauxRetardTvas = data);
        this.comptableTraitant = new ComptableTraitantDto();
        this.comptableTraitantService.findAll().subscribe((data) => this.comptableTraitants = data);
        this.comptableValidateur = new ComptableValidateurDto();
        this.comptableValidateurService.findAll().subscribe((data) => this.comptableValidateurs = data);
    }


    public save():void{
        this.declarationTvaService.savee(false).subscribe(data =>{
                if(data != null) {
                    this.declarationTvaService.items.push({...data}) ;
                    this.item= this.declarationTvaService.constrcutDto();
                    this.createDialog = false;
                    this.years=[];
                    this.trimestres=[];
                }else this.messageService.add({severity: 'error', summary: 'Erreurs', detail: 'Element existant'});
            }
        )
    }

    public onSimuler(): void {
        if(this.getDateAvailable(this.item.annee,this.item.trimistre)<=new Date()){
            this.simuler = false;
            this.declarationTvaService.savee(true).subscribe(data => {
                this.item = data;
            });
        }else alert("TAXE PAS ENCORE DISPONIBLE");

    }
    public getDateAvailable(annee: number, trimestre: number): Date {
        let date: Date | null = null;

        if (trimestre === 1) {
            date = new Date(annee, 3, 1, 0, 0, 0);
        }
        if (trimestre === 2) {
            date = new Date(annee, 6, 1, 0, 0, 0);
        }
        if (trimestre === 3) {
            date = new Date(annee, 9, 1, 0, 0, 0);
        }
        if (trimestre === 4) {
            date = new Date(annee, 12,1, 0, 0, 0);
        }
        return date as Date;
    }

    public getAnnee() : void{
        if (this.item.societe.dernierTrimestrePayerTva==4){
            this.years=[this.item.societe.dernierAnneePayerTva+1];
        }else {
            this.years=[this.item.societe.dernierAnneePayerTva];
        }
    }

    public getTrimestre() : void{
        if (this.item.societe.dernierTrimestrePayerTva==4){
            this.trimestres=[1];
        }else {
            this.trimestres=[this.item.societe.dernierTrimestrePayerTva+1];
            console.log('khdama')
        }
    }
    public hideCreateDialog() {
        super.hideCreateDialog();
        this.years=[];
        this.trimestres=[];
    }
    public exportPdf(element: DeclarationTvaDto): void{
        this.service.exportPdf(element).subscribe((data: ArrayBuffer) => {
            const blob = new Blob([data], { type: 'application/pdf' });
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.download = 'DeclarationTva.pdf';
            link.setAttribute('target', '_blank'); // open link in new tab
            link.click();
            window.URL.revokeObjectURL(url);
        }, (error) => {
            console.error(error); // handle any errors that occur
        });
    }


    public setValidation(value: boolean) {
        this.validDeclarationTvaReference = value;
    }


    public validateForm(): void {
        this.errorMessages = new Array<string>();
        this.validateDeclarationTvaReference();
    }

    public validateDeclarationTvaReference() {
        if (this.stringUtilService.isEmpty(this.item.reference)) {
            this.errorMessages.push('Reference non valide');
            this.validDeclarationTvaReference = false;
        } else {
            this.validDeclarationTvaReference = true;
        }
    }


    public async openCreateTauxRetardTva(tauxRetardTva: string) {
        const isPermistted = await this.roleService.isPermitted('TauxRetardTva', 'add');
        if (isPermistted) {
            this.tauxRetardTva = new TauxRetardTvaDto();
            this.createTauxRetardTvaDialog = true;
        } else {
            this.messageService.add({
                severity: 'error', summary: 'erreur', detail: 'problème de permission'
            });
        }
    }

    get societe(): SocieteDto {
        return this.societeService.item;
    }

    set societe(value: SocieteDto) {
        this.societeService.item = value;
    }

    get societes(): Array<SocieteDto> {
        return this.societeService.items;
    }

    set societes(value: Array<SocieteDto>) {
        this.societeService.items = value;
    }

    get createSocieteDialog(): boolean {
        return this.societeService.createDialog;
    }

    set createSocieteDialog(value: boolean) {
        this.societeService.createDialog = value;
    }

    get tauxRetardTva(): TauxRetardTvaDto {
        return this.tauxRetardTvaService.item;
    }

    set tauxRetardTva(value: TauxRetardTvaDto) {
        this.tauxRetardTvaService.item = value;
    }

    get tauxRetardTvas(): Array<TauxRetardTvaDto> {
        return this.tauxRetardTvaService.items;
    }

    set tauxRetardTvas(value: Array<TauxRetardTvaDto>) {
        this.tauxRetardTvaService.items = value;
    }

    get createTauxRetardTvaDialog(): boolean {
        return this.tauxRetardTvaService.createDialog;
    }

    set createTauxRetardTvaDialog(value: boolean) {
        this.tauxRetardTvaService.createDialog = value;
    }

    get comptableTraitant(): ComptableTraitantDto {
        return this.comptableTraitantService.item;
    }

    set comptableTraitant(value: ComptableTraitantDto) {
        this.comptableTraitantService.item = value;
    }

    get comptableTraitants(): Array<ComptableTraitantDto> {
        return this.comptableTraitantService.items;
    }

    set comptableTraitants(value: Array<ComptableTraitantDto>) {
        this.comptableTraitantService.items = value;
    }

    get createComptableTraitantDialog(): boolean {
        return this.comptableTraitantService.createDialog;
    }

    set createComptableTraitantDialog(value: boolean) {
        this.comptableTraitantService.createDialog = value;
    }

    get comptableValidateur(): ComptableValidateurDto {
        return this.comptableValidateurService.item;
    }

    set comptableValidateur(value: ComptableValidateurDto) {
        this.comptableValidateurService.item = value;
    }

    get comptableValidateurs(): Array<ComptableValidateurDto> {
        return this.comptableValidateurService.items;
    }

    set comptableValidateurs(value: Array<ComptableValidateurDto>) {
        this.comptableValidateurService.items = value;
    }

    get createComptableValidateurDialog(): boolean {
        return this.comptableValidateurService.createDialog;
    }

    set createComptableValidateurDialog(value: boolean) {
        this.comptableValidateurService.createDialog = value;
    }


    get validDeclarationTvaReference(): boolean {
        return this._validDeclarationTvaReference;
    }

    set validDeclarationTvaReference(value: boolean) {
        this._validDeclarationTvaReference = value;
    }

    get validSocieteIce(): boolean {
        return this._validSocieteIce;
    }

    set validSocieteIce(value: boolean) {
        this._validSocieteIce = value;
    }

    get validComptableTraitantCin(): boolean {
        return this._validComptableTraitantCin;
    }

    set validComptableTraitantCin(value: boolean) {
        this._validComptableTraitantCin = value;
    }

    get validComptableValidateurCin(): boolean {
        return this._validComptableValidateurCin;
    }

    set validComptableValidateurCin(value: boolean) {
        this._validComptableValidateurCin = value;
    }
    get declarationTva(): DeclarationTvaDto {

        return this.declarationTvaService.declarationTva;
    }

    set declarationTva(value: DeclarationTvaDto) {
        this.declarationTvaService.declarationTva = value;
    }

    get declarationTvas(): Array<DeclarationTvaDto> {

        return  this.declarationTvaService.declarationTvas;
    }

    set declarationTvas(value: Array<DeclarationTvaDto>) {
        this.declarationTvaService.declarationTvas = value;
    }


}
