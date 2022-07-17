import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/service/product.service';

@Component({
  selector: 'app-detail-product',
  templateUrl: './detail-product.component.html',
  styleUrls: ['./detail-product.component.css']
})
export class DetailProductComponent implements OnInit {

  currentProduct: Product;

  constructor(private productService: ProductService,
    private activateRoute: ActivatedRoute,
    private toastrService: ToastrService,
    private router: Router) { }

  ngOnInit(): void {
    const id = this.activateRoute.snapshot.params["id"];
    this.productService.productDetailById(id).subscribe(
      {
        next: (v) => {
          this.currentProduct = v;
        },
        error: (e) => {
          this.toastrService.error(e.error.message, 'Fail', {
            timeOut: 3000,
            positionClass: 'toast-top-center'
          });
          this.router.navigate(['/']);
        }
      }
    );
  }

  protected goBack(): void {
    this.router.navigate(['/']);
  }
}
