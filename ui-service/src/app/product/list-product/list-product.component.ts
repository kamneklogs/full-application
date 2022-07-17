import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/service/product.service';

@Component({
  selector: 'app-list-product',
  templateUrl: './list-product.component.html',
  styleUrls: ['./list-product.component.css']
})
export class ListProductComponent implements OnInit {

  products: Product[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  private loadProducts(): void {
    this.productService.productsList().subscribe({
      next: (v) => this.products = v,
      error: (e) => console.error(e),
    }
    );
  }
}
