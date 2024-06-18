import {Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {AppSettings} from '../../app.settings';

@Component({
  selector: 'app-custom-panel',
  templateUrl: './custom-panel.component.html',
  styleUrls: ['./custom-panel.component.scss']
})
export class CustomPanelComponent implements OnChanges {
@Input() title: string;
@Input() subtitle: string;
@Input() theme: 'primary' | 'critical';
@Input() class: string;
@Input() extended = false;

  open = false;

  color = '#000011';
  appSetting: AppSettings;

  constructor(private host: ElementRef, private appSettings: AppSettings) {
    this.appSetting = appSettings;
  }

  ngOnChanges(changes: SimpleChanges) {
    if ('theme' in changes) {
      this.color = this.theme === 'primary' ? '#000011' : '#110000';
      this.host.nativeElement.style.setProperty(`--color`, this.color);
    }
  }
}
