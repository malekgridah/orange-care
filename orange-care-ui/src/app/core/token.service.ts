import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { share } from 'rxjs/operators';
import { Token } from './interface';
import {LocalStorageService} from "./storage.service";

function capitalize(str: string) {
  return str.substring(0, 1).toUpperCase() + str.substring(1, str.length).toLowerCase();
}

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  private key = 'TOKEN';
  private token: any;
  private change$ = new BehaviorSubject<Token>(this.store.get(this.key) as Token);

  constructor(private store: LocalStorageService) {}

  set(token: any) {
    this.change$.next(token);
    this.store.set(this.key, token);

    return this;
  }

  get() {
    return this.change$.getValue();
  }

  clear() {
    this.store.remove(this.key);
    this.change$.next({});
  }

  change() {
    return this.change$.pipe(share());
  }

  valid() {
    return !!this.get().access_token;
  }

  get value() {
    const token = this.get();

    return token.access_token || token.token || '';
  }

  get type() {
    const token = this.get();

    return capitalize(token.token_type || 'bearer');
  }
  get id() {
    const token = this.get();
    return token.id;
  }

  headerValue() {
    const value = this.value;

    return value ? [this.type, value].join(' ') : '';
  }
}
