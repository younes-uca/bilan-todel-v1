import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';

import * as moment from 'moment';

import { RoleService } from 'src/app/zynerator/security/Role.service';
import {environment} from 'src/environments/environment';
import {PaginatedList} from 'src/app/zynerator/dto/PaginatedList.model';
import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';

import {EtatOperationComptableDto} from '../model/EtatOperationComptable.model';
import {EtatOperationComptableCriteria} from '../criteria/EtatOperationComptableCriteria.model';
import {AbstractService} from 'src/app/zynerator/service/AbstractService';


@Injectable({
  providedIn: 'root'
})
export class EtatOperationComptableService extends AbstractService<EtatOperationComptableDto, EtatOperationComptableCriteria> {
     constructor(private http: HttpClient, private roleService: RoleService) {
        super();
        this.setHttp(http);
        this.setApi(environment.apiUrl + 'admin/etatOperationComptable/');
    }

    public constrcutDto(): EtatOperationComptableDto {
        return new EtatOperationComptableDto();
    }

    public constrcutCriteria(): EtatOperationComptableCriteria {
        return new EtatOperationComptableCriteria();
    }
}
