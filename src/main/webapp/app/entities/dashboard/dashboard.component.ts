import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { faChartBar } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'jhi-dashboard',
  templateUrl: './dashboard.component.html',
  standalone: true,
  imports: [FontAwesomeModule],
})
export class DashboardComponent {
  iframeSrc!: SafeResourceUrl;
  faChartBar = faChartBar;

  constructor(private sanitizer: DomSanitizer) {
    const metabaseUrl = 'http://10.1.90.240:3000/public/dashboard/55439453-50f8-4833-9c04-65cd6eba67de';
    this.iframeSrc = this.sanitizer.bypassSecurityTrustResourceUrl(metabaseUrl);
  }
}
