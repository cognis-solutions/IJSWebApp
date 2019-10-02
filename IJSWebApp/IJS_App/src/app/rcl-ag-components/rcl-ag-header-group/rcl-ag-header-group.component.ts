import {Component} from "@angular/core";
import {IHeaderGroupParams} from "ag-grid/main";
import {IHeaderGroupAngularComp} from "ag-grid-angular/main";

@Component({
  selector: 'app-rcl-ag-header-group', 
  templateUrl: './rcl-ag-header-group.component.html',
  styleUrls: ['./rcl-ag-header-group.component.scss']
})
export class RclAgHeaderGroupComponent implements IHeaderGroupAngularComp {
    public params: IHeaderGroupParams;
    public expanded: boolean;

    agInit(params: IHeaderGroupParams): void {
        this.params = params;
        this.params.columnGroup.getOriginalColumnGroup().addEventListener('expandedChanged', this.onExpandChanged.bind(this));
    }

    ngOnDestroy() {
        console.log(`Destroying HeaderComponent`);
    }


    expandOrCollapse() {
        this.params.setExpanded(!this.expanded);
    };

    onExpandChanged() {
        this.expanded = this.params.columnGroup.getOriginalColumnGroup().isExpanded()
    }
}