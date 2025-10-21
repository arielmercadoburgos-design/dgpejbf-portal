import { Component } from '@angular/core';
import { TranslateDirective } from 'app/shared/language';

@Component({
  selector: 'jhi-footer',
  standalone: true,
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss'], // 👈 plural
  imports: [TranslateDirective],
})
export class FooterComponent {
  currentYear: number = new Date().getFullYear();
  version: string = '1.0.0';
}
