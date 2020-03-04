import { Injectable } from '@angular/core';

@Injectable()
export class WindowRefService {

  constructor() { }
  nativeWindow(): any {
    return _window();
  }
}

function _window(): any {
  // return the native window obj
  console.log("this is window object:" + window )
  return window;
}