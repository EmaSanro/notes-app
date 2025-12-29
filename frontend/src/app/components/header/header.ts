import { Component } from '@angular/core';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header {
  showNavBar : Boolean = false;

  showNav() {
    this.showNavBar = !this.showNavBar;
  }
  
  ngOnInit() {
    this.change();
    window.addEventListener("resize", this.resizePage);
  }
  
  ngOnDestroy() {
    window.removeEventListener("resize", this.resizePage);
  }
  resizePage() {
    if(window.innerWidth > 720) {
      let nav = document.getElementById("navBar");
      nav?.classList.remove('show');
    }
  }

  clicked = document.getElementsByTagName("a");

  change() {
    for(let click of this.clicked) {
      click.addEventListener("click", () => {
        this.showNavBar = false;
      })
    }
  }
}
