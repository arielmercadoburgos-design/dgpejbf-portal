import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardPublicoComponent } from './dashboard-publico.component';

describe('DashboardPublicoComponent', () => {
  let component: DashboardPublicoComponent;
  let fixture: ComponentFixture<DashboardPublicoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardPublicoComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardPublicoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
