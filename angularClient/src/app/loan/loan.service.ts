import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LOANS_DATA } from './model/mock-loans';
import { Observable, filter, of } from 'rxjs';
import { Pageable } from '../core/model/page/Pageable';
import { LoanPage } from './model/LoanPage';
import { Loan } from './model/Loan';
import { FilterObj } from './model/FilterObj';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  constructor( private http: HttpClient) { }

  getLoans(pageable: Pageable, filterObj: FilterObj): Observable<LoanPage> {
    return this.http.post<LoanPage>('http://localhost:8080/loan', {pageable:pageable, filterDto:filterObj});
  }

  saveLoan(loan: Loan): Observable<void> {
    let url = 'http://localhost:8080/loan';
    if (loan.id != null) url += '/'+loan.id;

    return this.http.put<void>(url, loan);
}

  deleteLoan(idLoan : number): Observable<any> {
    return this.http.delete('http://localhost:8080/loan/'+idLoan);
}  
}
