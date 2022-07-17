import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  productURL = 'http://localhost:8080/product'

  constructor(private httpClient: HttpClient) { }

  public productsList(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.productURL)
  }

  public productDetailById(id: number): Observable<Product> {
    return this.httpClient.get<Product>(this.productURL + `/findById/${id}`)
  }

  public productDetailByName(name: string): Observable<Product> {
    return this.httpClient.get<Product>(this.productURL + `/findByName/${name}`)
  }

  public save(product: Product): Observable<any> {
    return this.httpClient.post<any>(this.productURL, product)
  }

  public update(id: number, product: Product): Observable<any> {
    return this.httpClient.put<any>(this.productURL + `/${id}`, product);
  }

  public delete(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.productURL + `/${id}`);
  }
}
