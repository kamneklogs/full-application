import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { ToastrService } from 'ngx-toastr';
import { ProductService } from 'src/app/service/product.service';

@Component({
  selector: 'app-list-product',
  templateUrl: './list-product.component.html',
  styleUrls: ['./list-product.component.css']
})
export class ListProductComponent implements OnInit {

  products: Product[] = [];

  constructor(private productService: ProductService,
    private toastr: ToastrService) { }

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

  protected deleteProduct(id: number): void {
    this.productService.delete(id).subscribe({
      next: (v) => {
        this.toastr.success('Product deleted successfully', 'OK!', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.loadProducts();
      },
      error: (e) => {
        this.toastr.error(e.error.message, 'Fail', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
      },
    });
  }
}
