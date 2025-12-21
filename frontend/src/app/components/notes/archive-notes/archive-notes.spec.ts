import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArchiveNotes } from './archive-notes';

describe('ArchiveNotes', () => {
  let component: ArchiveNotes;
  let fixture: ComponentFixture<ArchiveNotes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArchiveNotes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ArchiveNotes);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
