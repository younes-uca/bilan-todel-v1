import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import {
  trigger,
  state,
  style,
  transition,
  animate,
} from '@angular/animations';
import { AppComponent } from './app.component';
import { AppMainComponent } from './app.main.component';

import { AuthService } from 'src/app/zynerator/security/Auth.service';
import { RoleService } from 'src/app/zynerator/security/Role.service';

@Component({
  selector: 'app-menu',
  templateUrl: './app.menu.component.html',
  animations: [
    trigger('inline', [
      state(
        'hidden',
        style({
          height: '0px',
          overflow: 'hidden',
        })
      ),
      state(
        'visible',
        style({
          height: '*',
        })
      ),
      state(
        'hiddenAnimated',
        style({
          height: '0px',
          overflow: 'hidden',
        })
      ),
      state(
        'visibleAnimated',
        style({
          height: '*',
        })
      ),
      transition(
        'visibleAnimated => hiddenAnimated',
        animate('400ms cubic-bezier(0.86, 0, 0.07, 1)')
      ),
      transition(
        'hiddenAnimated => visibleAnimated',
        animate('400ms cubic-bezier(0.86, 0, 0.07, 1)')
      ),
    ]),
  ],
})
export class AppMenuComponent implements OnInit {
  model: any[];
  modelsuperadmin:any[];
  modelanonymous: any[];
    modeladmin : any[];
  constructor(public app: AppComponent,
   public appMain: AppMainComponent,
   private roleService: RoleService,
   private authService:AuthService,
  private router: Router) {}

  ngOnInit() {


    this.modeladmin =
      [



              {
                label: 'Référentiel Bilan',
                icon: 'pi pi-wallet',
                items:[
                    {
                      label: 'Liste des types d\'opération comptable',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/bilan/type-operation-comptable/list']
                    },
                    {
                      label: 'Liste des sous class comptable',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/bilan/sous-class-comptable/list']
                    },
                    {
                      label: 'Liste des class comptable',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/bilan/class-comptable/list']
                    },
                    {
                      label: 'Liste des états de bilan',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/bilan/etat-bilan/list']
                    },
                    {
                      label: 'Liste des états d\'opération comptable',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/bilan/etat-operation-comptable/list']
                    },
                    {
                      label: 'Liste des comptes comptable',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/bilan/compte-comptable/list']
                    },
                ]
              },
              {
                label: 'Géstion TVA',
                icon: 'pi pi-wallet',
                items:[
                    {
                      label: 'Liste des taux de retard tva',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/tva/taux-retard-tva/list']
                    },
                    {
                      label: 'Liste des déclarations tva',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/tva/declaration-tva/list']
                    },
                ]
              },
              {
                label: 'Géstion IS',
                icon: 'pi pi-wallet',
                items:[
                    {
                      label: 'Liste des déclarations IS',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/is/declaration-is/list']
                    },
                    {
                      label: 'Liste des taux IS',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/is/taux-is/list']
                    },
                ]
              },

              {
                label: 'Géstion Abonné',
                icon: 'pi pi-wallet',
                items:[
                    {
                      label: 'Liste des stores',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/abonne/store/list']
                    },
                    {
                      label: 'Liste des magasins',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/abonne/magasin/list']
                    },
                    {
                      label: 'Liste des abonnés',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/abonne/abonne/list']
                    },
                    {
                      label: 'Liste des sociétés',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/abonne/societe/list']
                    },
                ]
              },
              {
                label: 'Géstion Bilan',
                icon: 'pi pi-wallet',
                items:[
                    {
                      label: 'Liste des bilans',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/bilan/bilan/list']
                    },
                    {
                      label: 'Liste des opérations comptable',
                      icon: 'pi pi-fw pi-plus-circle',
                      routerLink: ['/app/admin/bilan/operation-comptable/list']
                    },
                ]
              },

    ]
        if (this.authService.authenticated) {
      if (this.authService.authenticatedUser.roles) {

        this.authService.authenticatedUser.roles.forEach(role => {
          const roleName: string = this.getRole(role);
          this.roleService._role.next(roleName.toUpperCase());
          eval('this.model = this.model' + this.getRole(role));
        })
      }

    }
  }
  getRole(text){
  const [role, ...rest] = text.split('_');
  return rest.join('').toLowerCase();
}
  onMenuClick(event) {
    this.appMain.onMenuClick(event);
  }
}
