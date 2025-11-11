import { Component, OnInit, Renderer2, RendererFactory2, inject } from '@angular/core';
import { filter, map, mergeMap } from 'rxjs/operators';
import { Router, RouterOutlet, NavigationEnd, ActivatedRoute } from '@angular/router';
import { LangChangeEvent, TranslateService } from '@ngx-translate/core';
import dayjs from 'dayjs/esm';

import { AccountService } from 'app/core/auth/account.service';
import { AppPageTitleStrategy } from 'app/app-page-title-strategy';

// ✅ FooterComponent: export nombrado (coincide con tu footer.component.ts)
import { FooterComponent } from '../footer/footer.component';

// ⚠️ PageRibbonComponent suele ser default export en JHipster 8
// Si en tu archivo tiene "export default class ...", usá default:
import PageRibbonComponent from '../profiles/page-ribbon.component';
// Si NO es default (tiene "export class ..."), entonces:
// import { PageRibbonComponent } from '../profiles/page-ribbon.component';

@Component({
  selector: 'jhi-main',
  standalone: true,
  templateUrl: './main.component.html',
  providers: [AppPageTitleStrategy],
  imports: [RouterOutlet, FooterComponent, PageRibbonComponent],
})
export default class MainComponent implements OnInit {
  private readonly renderer: Renderer2;

  private readonly router = inject(Router);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly appPageTitleStrategy = inject(AppPageTitleStrategy);
  private readonly accountService = inject(AccountService);
  private readonly translateService = inject(TranslateService);
  private readonly rootRenderer = inject(RendererFactory2);

  constructor() {
    this.renderer = this.rootRenderer.createRenderer(document.querySelector('html'), null);
  }

  ngOnInit(): void {
    this.accountService.identity().subscribe();

    this.translateService.onLangChange.subscribe((langChangeEvent: LangChangeEvent) => {
      this.appPageTitleStrategy.updateTitle(this.router.routerState.snapshot);
      dayjs.locale(langChangeEvent.lang);
      this.renderer.setAttribute(document.querySelector('html'), 'lang', langChangeEvent.lang);
    });

    // Cambiar el título dinámicamente al navegar
    this.router.events
      .pipe(
        filter(event => event instanceof NavigationEnd),
        map(() => this.activatedRoute),
        map(route => {
          while (route.firstChild) route = route.firstChild;
          return route;
        }),
        mergeMap(route => route.data),
      )
      .subscribe(data => {
        const titleKey = data['pageTitle'];
        if (titleKey) {
          // Traducción instantánea
          document.title = this.translateService.instant(titleKey);
        } else {
          document.title = 'Mi Aplicación'; // Título por defecto
        }
      });

    this.translateService.onLangChange.subscribe(() => {
      const route = this.activatedRoute;
      let activeRoute = route;
      while (activeRoute.firstChild) activeRoute = activeRoute.firstChild;
      const titleKey = activeRoute.snapshot.data['pageTitle'];
      if (titleKey) {
        document.title = this.translateService.instant(titleKey);
      }
    });
  }
}
