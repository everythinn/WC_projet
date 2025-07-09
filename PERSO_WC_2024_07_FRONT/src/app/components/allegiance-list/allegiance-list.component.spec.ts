import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllegianceListComponent } from './allegiance-list.component';

describe('AllegianceListComponent', () => {
  let component: AllegianceListComponent;
  let fixture: ComponentFixture<AllegianceListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AllegianceListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllegianceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
