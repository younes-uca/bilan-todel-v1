import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class EtatAvoirAchatDto  extends BaseDto{

    public id: number;
    public libelle: string;
    public code: string;
    public style: string;

}
