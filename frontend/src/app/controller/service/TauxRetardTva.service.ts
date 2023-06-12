import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';

import * as moment from 'moment';

import { RoleService } from 'src/app/zynerator/security/Role.service';
import {environment} from 'src/environments/environment';
import {PaginatedList} from 'src/app/zynerator/dto/PaginatedList.model';
import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';

import {TauxRetardTvaDto} from '../model/TauxRetardTva.model';
import {TauxRetardTvaCriteria} from '../criteria/TauxRetardTvaCriteria.model';
import {AbstractService} from 'src/app/zynerator/service/AbstractService';


@Injectable({
  providedIn: 'root'
})
export class TauxRetardTvaService extends AbstractService<TauxRetardTvaDto, TauxRetardTvaCriteria> {
    private _tauxRetardTva: TauxRetardTvaDto;
    private _tauxRetardTvas: Array<TauxRetardTvaDto>;
     constructor(private http: HttpClient, private roleService: RoleService) {
        super();
        this.setHttp(http);
        this.setApi(environment.apiUrl + 'admin/tauxRetardTva/');
    }


    public constrcutDto(): TauxRetardTvaDto {
        return new TauxRetardTvaDto();
    }

    public constrcutCriteria(): TauxRetardTvaCriteria {
        return new TauxRetardTvaCriteria();
    }
    get tauxRetardTva(): TauxRetardTvaDto {
        if (this._tauxRetardTva == null) {
            this._tauxRetardTva = new TauxRetardTvaDto();
        }
        return this._tauxRetardTva;
    }

    set tauxRetardTva(value: TauxRetardTvaDto) {
        this._tauxRetardTva = value;
    }

    get tauxRetardTvas(): Array<TauxRetardTvaDto> {
        if (this._tauxRetardTvas == null) {
            this._tauxRetardTvas = new Array<TauxRetardTvaDto>();
        }

        return this._tauxRetardTvas;
    }

    set tauxRetardTvas(value: Array<TauxRetardTvaDto>) {
        this._tauxRetardTvas = value;
    }
}
