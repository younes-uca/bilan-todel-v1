import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';

import * as moment from 'moment';

import { RoleService } from 'src/app/zynerator/security/Role.service';
import {environment} from 'src/environments/environment';
import {PaginatedList} from 'src/app/zynerator/dto/PaginatedList.model';
import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';

import {CompteComptableDto} from '../model/CompteComptable.model';
import {CompteComptableCriteria} from '../criteria/CompteComptableCriteria.model';
import {AbstractService} from 'src/app/zynerator/service/AbstractService';

import {SousClassComptableDto} from '../model/SousClassComptable.model';

@Injectable({
  providedIn: 'root'
})
export class CompteComptableService extends AbstractService<CompteComptableDto, CompteComptableCriteria> {

    private _compteComptable: CompteComptableDto;
    private _compteComptables: Array<CompteComptableDto>;
     constructor(private http: HttpClient, private roleService: RoleService) {
        super();
        this.setHttp(http);
        this.setApi(environment.apiUrl + 'admin/compteComptable/');
    }
    saveToDatabase(file: File): Observable<Array<CompteComptableDto>> {
        const formData: FormData = new FormData();
        formData.append('file', file, file.name);

        return this.http.post<Array<CompteComptableDto>>(this.API + "upload-compteComptables-data", formData);
    }
    public getCompteComptables(): Observable<Array<CompteComptableDto>> {
        return  this.http.get<Array<CompteComptableDto>>(this.API+"List")
    }

    public constrcutDto(): CompteComptableDto {
        return new CompteComptableDto();
    }

    public constrcutCriteria(): CompteComptableCriteria {
        return new CompteComptableCriteria();
    }
    get compteComptable(): CompteComptableDto {
        if(this._compteComptable==null)
            return this._compteComptable;
    }

    set compteComptable(value: CompteComptableDto) {
        this._compteComptable = value;
    }

    get compteComptables(): Array<CompteComptableDto> {
        if(this._compteComptables==null)
            return this._compteComptables;
    }

    set compteComptables(value: Array<CompteComptableDto>) {
        this._compteComptables = value;
    }
}
