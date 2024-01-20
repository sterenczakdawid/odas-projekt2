import { Component } from '@angular/core';
import { Router , RouterLink} from '@angular/router';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss'
})
export class MainComponent {
}
