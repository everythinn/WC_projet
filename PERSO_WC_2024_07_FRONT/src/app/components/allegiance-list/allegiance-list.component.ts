import { Component } from '@angular/core';

@Component({
  selector: 'app-allegiance-list',
  imports: [],
  templateUrl: './allegiance-list.component.html',
  styleUrl: './allegiance-list.component.scss'
})
export class AllegianceListComponent {

  isSimplified : Boolean = false;

  toggleView() : void {
    const toggleText = document.getElementById("toggle-text");
      if (this.isSimplified == false) {
          this.isSimplified = true;
      } else if (this.isSimplified == true) {
          this.isSimplified = false;
      }
  }

}
