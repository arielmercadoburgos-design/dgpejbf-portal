import { Component } from '@angular/core';
import { TranslateDirective } from 'app/shared/language';
@Component({
  selector: 'jhi-footer',
  standalone: true,
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss'],
})
export class FooterComponent {
  currentYear = new Date().getFullYear();
  version = '1.0.0';
}
