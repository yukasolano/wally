import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';
import { catchError } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material';


@Injectable({
    providedIn: 'root'
})
export class HttpService {

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    constructor(private http: HttpClient, private snackBar: MatSnackBar) {}

    public get<T>(endpoint: string): Observable<T>  {
        return this.http.get<T>(`${environment.baseUrl}${endpoint}`, this.httpOptions).pipe(catchError(this.handleError<T>()));
    }

    public post(endpoint: string, body: {} = null, posExecution = null): void {
        this.callPost(endpoint, body, posExecution, this.httpOptions);
    }

    public postFile(endpoint: string, body: {} = null, posExecution = null): void {
        this.callPost(endpoint, body, posExecution);
    }

    private callPost(endpoint: string, body: {} = null, posExecution = null, httpOptions = {}) {
        this.http.post<any>(`${environment.baseUrl}${endpoint}`, body, httpOptions).subscribe(
            resp => {
                if (posExecution) { posExecution(); }
                this.showSuccess(resp.message);
            },
            error => {
                this.customerrorHandler(error);
            });
    }

    private handleError<T>(result?: T) {
        return (error: any): Observable<T> => {
          this.customerrorHandler(error);
          return of(result as T);
        };
    }

    private customerrorHandler(response) {
        console.log('Ocorreu um erro:', response);
        if (response.error) {
            this.showError(response.error.message);
        }
    }

    private showError(message: string) {
        this.snackBar.open(message, ' X ', { duration: 5000, panelClass: ['error-snackbar'] });
    }

    private showSuccess(message: string) {
        this.snackBar.open(message, ' X ', { duration: 5000, panelClass: ['success-snackbar'] });
    }

}
