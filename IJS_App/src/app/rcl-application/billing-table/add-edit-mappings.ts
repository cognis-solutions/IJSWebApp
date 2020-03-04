export class TableMappingData {
    private mappingObj: any = {
        mtOrLaden: 'mtLadenOptions',
        rateBasis: 'rateBasisOptions',
        rateStatus: 'rateStatusOptions',
        eqCatq: 'eQCatgOptions',
        term: 'termOptions',
        calcMethod: 'calcMethodOptions',
        eqType: 'eQTypeOptions',
        uom: 'uomOptions',
        impOrExp: 'impExpOptions',
        splHandling: 'splHandlingOptions'
    }
    private mtLadenOptions: any = [
        {
            label: 'MT',
            value: 'MT',
        }, {
            label: 'LADEN',
            value: 'LADEN',
        }
    ];
    private rateBasisOptions: any = [
        {
            label: 'Size/Type',
            value: 'S',
        }, {
            label: 'BKG/BL',
            value: 'B'
        },
        {
            label: 'Lump Sum',
            value: 'L'
        }
    ];
    private eQCatgOptions: any = [
        {
            label: 'Chassis/trailer',
            value: 'S',
        }, {
            label: 'Box',
            value: 'B',
        }
    ];
    private rateStatusOptions: any = [
        {
            label: 'Open',
            value: 'O',
        }, {
            label: 'Pending',
            value: 'P',
        },
        {
            label: 'Approved',
            value: 'A',
        },
        {
            label: 'Rejected',
            value: 'R',
        }
    ]
    eQTypeOptions: any = [
        {
            label: '**',
            value: 'ALL',
        }, {
            label: 'GP',
            value: 'GP',
        }, {
            label: 'HC',
            value: 'HC',
        }, {
            label: 'TK',
            value: 'TK',
        }
    ];
    private termOptions: any = [];
    private calcMethodOptions: any = [
        {
            label: 'Tier*UOM',
            value: '1',
        }, {
            label: 'Fix*UOM',
            value: '2',
        }, {
            label: 'Tier Amount',
            value: '3',
        }, {
            label: 'Fix Amount',
            value: '4',
        }
    ];
    private uomOptions: any = [
        {
            label: 'Kilo',
            value: 'K',
        }, {
            label: 'Ton',
            value: 'T',
        }
    ];
    private impExpOptions: any = [
        {
            label: 'All',
            value: "**",
        }, {
            label: 'IMP',
            value: 'IMP',
        }, {
            label: 'EXP',
            value: 'EXP',
        }
    ];
    private splHandlingOptions: any = [
        
        {
            label: 'Normal',
            value: "N",
        }, 
        {
            label: 'Reefer DG',
            value: 'RDG',
        },
        {
            label: 'OOG DG',
            value: 'ODG',
        },
        {
            label: 'RF',
            value: 'RF',
        },{
            label: 'OOG',
            value: '0G',
        },{
            label: 'DG',
            value: 'D1',
        }, {
            label: 'Door Ajar',
            value: 'DA',
        }, {
            label: 'Open Door',
            value: 'OD',
        }, {
            label: 'BBK',
            value: 'BBK',
        }
    ];
    
    dataMappingMethod(rowData) {        
        let keyList = Object.keys(rowData); 
        let rowData2;       
        for(let i = 0; i< keyList.length; i++) {
             if(this.mappingObj[keyList[i]]) {
                rowData = this.mapping(keyList[i], rowData[keyList[i]], rowData);
             }
        }
        return rowData;     
    }
    mapping(propName, propText, rowData){        
        let propOptions =  this.mappingObj[propName];           
        for(let i=0; i< this[propOptions].length ; i++) {
            if(this[propOptions][i]['label'] == propText) {
                rowData[propName] = this[propOptions][i]['value'];
            }
        }           
        //let rowdata1 = rowData;
        return rowData;
    }
}
