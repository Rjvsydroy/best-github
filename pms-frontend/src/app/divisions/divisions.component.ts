import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DivisionService } from './service/division.service';
import { Division } from './model/division';

@Component({
  selector: 'app-divisions',
  templateUrl: './divisions.component.html',
  styleUrls: ['./divisions.component.css']
})
export class DivisionsComponent implements OnInit, OnDestroy {
  public loading = true;
  public divisions: Division[] = [];
  public error: any = null;

  private subscriptions: Subscription[] = [];

  constructor(private divisionsService: DivisionService) { }

  ngOnInit(): void {
    this.subscriptions.push(this.divisionsService.getDivisions().subscribe(divisions => {
      this.divisions = divisions;
    }, error => {
      this.error = error;
    }, () => {
      this.loading = false;
    }));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }
}
