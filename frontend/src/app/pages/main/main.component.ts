import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink} from '@angular/router';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [RouterLink, MatButtonModule],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss'
})
export class MainComponent {
}
