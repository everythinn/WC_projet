import { Component } from '@angular/core';

@Component({
  selector: 'app-maps',
  imports: [],
  templateUrl: './maps.component.html',
  styleUrl: './maps.component.scss'
})
export class MapsComponent {

  isClicked : Boolean = false;

    mapToggle() : void {
      if (this.isClicked == false) {
          this.isClicked = true;
      } else if (this.isClicked == true) {
          this.isClicked = false;
      }
  }

}
