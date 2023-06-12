import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';

import * as moment from 'moment';

import { RoleService } from 'src/app/zynerator/security/Role.service';
import {environment} from 'src/environments/environment';
import {PaginatedList} from 'src/app/zynerator/dto/PaginatedList.model';
import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';

import {OperationComptableDto} from '../model/OperationComptable.model';
import {OperationComptableCriteria} from '../criteria/OperationComptableCriteria.model';
import {AbstractService} from 'src/app/zynerator/service/AbstractService';

import {SocieteDto} from '../model/Societe.model';
import {CompteComptableDto} from '../model/CompteComptable.model';
import {TypeOperationComptableDto} from '../model/TypeOperationComptable.model';
import {EtatOperationComptableDto} from '../model/EtatOperationComptable.model';

@Injectable({
  providedIn: 'root'
})
export class OperationComptableService extends AbstractService<OperationComptableDto, OperationComptableCriteria> {
     constructor(private http: HttpClient, private roleService: RoleService) {
        super();
        this.setHttp(http);
        this.setApi(environment.apiUrl + 'admin/operationComptable/');
    }
    private _operationComptable : OperationComptableDto;
    private _operationComptables : Array<OperationComptableDto>;

    public constrcutDto(): OperationComptableDto {
        return new OperationComptableDto();
    }

    saveToDatabase(file: File): Observable<Array<OperationComptableDto>> {
        const formData: FormData = new FormData();
        formData.append('file', file, file.name);
        console.log('daz');
        return this.http.post<Array<OperationComptableDto>>(this.API + 'hh', formData);
    }
    public constrcutCriteria(): OperationComptableCriteria {
        return new OperationComptableCriteria();
    }

    get operationComptable(): OperationComptableDto {
        return this._operationComptable;
    }

    set operationComptable(value: OperationComptableDto) {
        this._operationComptable = value;
    }

    get operationComptables(): Array<OperationComptableDto> {
        return this._operationComptables;
    }

    set operationComptables(value: Array<OperationComptableDto>) {
        this._operationComptables = value;
    }
}
