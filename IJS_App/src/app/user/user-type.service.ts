import { Injectable } from '@angular/core';

@Injectable()
export class UserTypeService {

  private appUserType: string;
  constructor() { }

  public setValue(userType){
    this.appUserType = userType;
  }  
  public getValue(){
    return this.appUserType;
  }
  
}
