import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { faChartBar } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'jhi-dashboard',
  templateUrl: './dashboard.component.html',
  standalone: true,
  imports: [FontAwesomeModule],
})
export class DashboardComponent implements OnInit {
  iframeUrl: SafeResourceUrl | undefined;
  faChartBar = faChartBar;

  constructor(
    private http: HttpClient,
    private sanitizer: DomSanitizer,
  ) {}

  ngOnInit(): void {
    this.http.get<{ url: string }>('/api/metabase-url').subscribe(res => {
      this.iframeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(res.url);
    });
  }
}
