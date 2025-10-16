import { Component } from '@angular/core';
import { CommonModule, NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-maps',
  imports: [CommonModule, NgFor, NgIf],
  templateUrl: './maps.component.html',
  styleUrl: './maps.component.scss'
})
export class MapsComponent {

  showStoneClanSubmaps = false;
  showDayClanSubmaps = false;
  showMirkClanSubmaps = false;
  showDewClanSubmaps = false;
  showSunClanSubmaps = false;
  showMoonClanSubmaps = false;

  stoneClanSubmaps = [
    {
      id: 'stoneclan-territory',
      src: 'assets/maps/stoneClan.png',
      alt: 'StoneClan Territory',
      label: 'Territoire du Clan des Pierres',
      description : 'StoneClan is located to the northwest of the Clearing. Their territory is mostly grassy hills in the east, and coniferous forest and marshes in the west. They border MirkClan in the east, and MoonClan in the south. The borders are marked by a tree line in the west, and marshes in the south. Their camp is located in the forest, near a rock formation. They mainly prey on rodents of all kinds, sometimes birds.'
    },
    {
      id: 'stoneclan-camp',
      src: 'assets/maps/stoneClanCamp.jpg',
      alt: 'StoneClan Camp',
      label: 'Camp du Clan des Pierres',
      description: ''
    }
  ];

  dayClanSubmaps = [
    {
      id: 'dayclan-territory',
      src: 'assets/maps/dayClan.png',
      alt: 'DayClan Territory',
      label: 'Territoire du Clan du Jour',
      description : 'DayClan is located to the east of the Clearing. Their territory is mostly moor, with a lot of hills in the easternmost part of their territory, as well as a lake. They border MirkClan in the north, and DewClan in the west. The borders are marked by a river in the north, and the transition from moor to flat land in the west. Their camp is located near the river. They mainly prey on small rodents and rabbits.'
    },
    {
      id: 'dayclan-camp',
      src: 'assets/maps/dayClanCamp.jpg',
      alt: 'DayClan Camp',
      label: 'Camp du Clan du Jour',
      description: ''
    }
  ];

  mirkClanSubmaps = [
    {
      id: 'mirkclan-territory',
      src: 'assets/maps/mirkClan.png',
      alt: 'MirkClan Territory',
      label: 'Territoire du Clan des Ténèbres',
      description : 'MirkClan is located to the northeast of the Clearing. Their territory is mostly barren hills, with a few patches of deciduous forest. They border StoneClan in the west, and DayClan in the south. The borders are marked by a tree line in the west, and a river in the south. Their camp is located on top of the highest hill in their territory. They typically prey on birds of all kinds and small rodents.'
    },
    {
      id: 'mirkclan-camp',
      src: 'assets/maps/mirkClanCamp.jpg',
      alt: 'MirkClan Camp',
      label: 'Camp du Clan des Ténèbres',
      description: 'The new camp of MirkClan is located partially underground in a thick bush. The stump of an old and partially uprooted tree serves as the leader\'s speaking place, and their den is buried under its roots. The medicine cat\'s den and the nursery are also buried.'
    }
  ];

  dewClanSubmaps = [
    {
      id: 'dewclan-territory',
      src: 'assets/maps/dewClan.png',
      alt: 'DewClan Territory',
      label: 'Territoire du Clan de la Rosée',
      description: 'DewClan is located to the southeast of the Clearing. Their territory is flat land covered with scarce trees, a lot of streams and therefore a few marshes. They border SunClan in the west, and DayClan in the northeast. The borders are marked by the forest in the west, and the transition from moor to flat land in the east. Their camp is located at the meeting point of two rivers. They mainly prey on rodents, occasionally fish and frogs.'
    },
    {
      id: 'dewclan-camp',
      src: 'assets/maps/dewClanCamp.jpg',
      alt: 'DewClan Camp',
      label: 'Camp du Clan de la Rosée',
      description: ''
    }
  ];

  sunClanSubmaps = [
    {
      id: 'sunclan-territory',
      src: 'assets/maps/sunClan.png',
      alt: 'SunClan Territory',
      label: 'Territoire du Clan du Soleil',
      description: 'SunClan is located to the southwest of the Clearing. Their territory is flat land and deciduous forest. They border MoonClan in the west, and DewClan in the east. The borders are marked by a Twoleg nest in the west, and the end of the forest in the east. Their camp is located in the forest, near the river. They mainly prey on fish and small rodents.'
    },
    {
      id: 'sunclan-camp',
      src: 'assets/maps/sunClanCamp.jpg',
      alt: 'sunClan Camp',
      label: 'Camp du Clan du Soleil',
      description: ''
    }
  ];

  moonClanSubmaps = [
    {
      id: 'moonclan-territory',
      src: 'assets/maps/moonClan.png',
      alt: 'MoonClan Territory',
      label: 'Territoire du Clan de la Lune',
      description : 'MoonClan is located to the west of the Clearing. Their territory is covered in marshes and coniferous forest. They border StoneClan in the north, and SunClan in the east. The borders are marked by the end of the marshes in the north, and a Twoleg nest in the east. Their camp is located in the forest, near the river. They prey on rodents, frogs, lizards and all marsh creatures.'
    },
    {
      id: 'moonclan-camp',
      src: 'assets/maps/moonClanCamp.jpg',
      alt: 'MoonClan Camp',
      label: 'Camp du Clan de la Lune',
      description: ''
    }
  ];

  toggleStoneClanSubmaps() : void {
    this.showStoneClanSubmaps = !this.showStoneClanSubmaps;

    if(this.showStoneClanSubmaps) {
      setTimeout(() => {
        const el = document.getElementById('stone_map');
        el?.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }, 0);
    }
  }

  toggleDayClanSubmaps() : void {
    this.showDayClanSubmaps = !this.showDayClanSubmaps;

      if(this.showDayClanSubmaps) {
      setTimeout(() => {
        const el = document.getElementById('day_map');
        el?.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }, 0);
    }
  }

  toggleMirkClanSubmaps() : void {
    this.showMirkClanSubmaps = !this.showMirkClanSubmaps;

      if(this.showMirkClanSubmaps) {
      setTimeout(() => {
        const el = document.getElementById('mirk_map');
        el?.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }, 0);
    }
  }

  toggleDewClanSubmaps() : void {
    this.showDewClanSubmaps = !this.showDewClanSubmaps;

      if(this.showDewClanSubmaps) {
      setTimeout(() => {
        const el = document.getElementById('dew_map');
        el?.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }, 0);
    }
  }

  toggleSunClanSubmaps() : void {
    this.showSunClanSubmaps = !this.showSunClanSubmaps;

      if(this.showSunClanSubmaps) {
      setTimeout(() => {
        const el = document.getElementById('sun_map');
        el?.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }, 0);
    }
  }

  toggleMoonClanSubmaps() : void {
    this.showMoonClanSubmaps = !this.showMoonClanSubmaps;

      if(this.showMoonClanSubmaps) {
      setTimeout(() => {
        const el = document.getElementById('moon_map');
        el?.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }, 0);
    }
  }
}
