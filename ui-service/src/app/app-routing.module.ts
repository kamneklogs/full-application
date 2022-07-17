import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DetailProductComponent } from './product/detail-product/detail-product.component';
import { EditProductComponent } from './product/edit-product/edit-product.component';
import { ListProductComponent } from './product/list-product/list-product.component';
import { NewProductComponent } from './product/new-product/new-product.component';

const routes: Routes = [
  { path: '', component: ListProductComponent },
  { path: 'detail/:id', component: DetailProductComponent },
  { path: 'new', component: NewProductComponent },
  { path: 'edit/:id', component: EditProductComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' } // Redirect to root component
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
