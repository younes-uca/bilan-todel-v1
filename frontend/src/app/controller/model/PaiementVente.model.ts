import {EtatPaiementVenteDto} from './EtatPaiementVente.model';
import {VenteDto} from './Vente.model';
import {ModePaiementDto} from './ModePaiement.model';
import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class PaiementVenteDto  extends BaseDto{

    public id: number;
    public reference: string;
   public datePaiement: Date;
    public montant: number;
    public description: string;
    public datePaiementMax: string ;
    public datePaiementMin: string ;
    public montantMax: string ;
    public montantMin: string ;
    public vente: VenteDto ;
    public modePaiement: ModePaiementDto ;
    public etatPaiementVente: EtatPaiementVenteDto ;

}
