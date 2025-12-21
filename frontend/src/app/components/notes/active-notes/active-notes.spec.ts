import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActiveNotes } from './active-notes';

describe('ActiveNotes', () => {
  let component: ActiveNotes;
  let fixture: ComponentFixture<ActiveNotes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActiveNotes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActiveNotes);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
