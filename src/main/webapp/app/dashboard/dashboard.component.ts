import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'jhi-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  iframeSrc!: SafeResourceUrl;

  constructor(
    private http: HttpClient,
    private sanitizer: DomSanitizer,
  ) {}

  ngOnInit(): void {
    this.http.get('/api/metabase-url', { responseType: 'text', withCredentials: true }).subscribe(res => {
      this.iframeSrc = this.sanitizer.bypassSecurityTrustResourceUrl(res); // la URL que devuelve el backend
    });
  }
}
