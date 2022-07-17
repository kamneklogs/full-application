import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/service/product.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {

  productToEdit =new Product("",0);


  constructor(private productService: ProductService,
    private activateRoute: ActivatedRoute,
    private toastrService: ToastrService,
    private router: Router) { }

  ngOnInit(): void {
    const id = this.activateRoute.snapshot.params["id"];
    this.productService.productDetailById(id).subscribe({
      next: (v) => {
        this.productToEdit = v;
        console.log(this.productToEdit);
      },
      error: (e) => {
        this.toastrService.error(e.error.message, 'Fail', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.router.navigate(['/']);
      }
    });
  }

  protected onUpdate(): void {
    const id = this.activateRoute.snapshot.params["id"];
    this.productService.update(id, this.productToEdit).subscribe(
      {
        next: (v) => {
          this.toastrService.success('Product updated successfully', 'OK!', {
            timeOut: 3000,
            positionClass: 'toast-top-center'
          });
          this.router.navigate(['/']);
        },
        error: (e) => {
          this.toastrService.error(e.error.message, 'Fail', {
            timeOut: 3000,
            positionClass: 'toast-top-center'
          });
          this.router.navigate(['/']);
        },
      }
    );
  }
}
