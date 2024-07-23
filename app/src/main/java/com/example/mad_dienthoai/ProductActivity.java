package com.example.mad_dienthoai;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad_dienthoai.adapter.ProductAdapter;
import com.example.mad_dienthoai.db.SQLiteHelper;
import com.example.mad_dienthoai.model.Producer;
import com.example.mad_dienthoai.model.Product;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    private Producer producer;
    private ProductAdapter productAdapter;
    private ArrayList<Product> listProduct;
    private RecyclerView recyclerViewProduct;
    private Button btAddProduct;
    private TextView tvListProduct;
    private EditText search;
    private SQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        initView();
        getAllProduct();
        btAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductDialogShow();
            }
        });
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                if(action == EditorInfo.IME_ACTION_SEARCH){
                    String searchQuery= search.getText().toString();
                    if(searchQuery.isEmpty()){
                        getAllProduct();
                    }else{
                        try{
                            int size = Integer.parseInt(searchQuery);
                            if( size <=0 ) throw new Exception();
                            searchProduct(size);
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Kích thước không hợp lệ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }
    private void initView() {
        recyclerViewProduct=findViewById(R.id.recyclerViewProduct);
        btAddProduct=findViewById(R.id.btAddProduct);
        tvListProduct=findViewById(R.id.tvListProduct);
        search=findViewById(R.id.search);

        db = new SQLiteHelper(this);
        producer =(Producer) getIntent().getSerializableExtra("data");
        tvListProduct.setText("Danh Sách Sản Phẩm: " + producer.getNameProducer());

        productAdapter = new ProductAdapter(new Resume() {
            @Override
            public void onUpdateClick(int id) {
                updateProductDialogShow(id);
            }

            @Override
            public void onDeleteClick(int id) {
                deleteProduct(id);
            }

            @Override
            public void onDetailClick(int index) {

            }
        });
        recyclerViewProduct.setAdapter(productAdapter);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this));
    }
    private void getAllProduct() {
        listProduct= db.getAllProductInProducer(producer.getIdProducer());
        productAdapter.setData(listProduct);
    }
    private void addProduct(Product product){
        db.addProduct(product);
        getAllProduct();
        search.setText("");
    }
    private void updateProduct(Product product){
        db.updateProduct(product);
        getAllProduct();
        search.setText("");
    }
    private void deleteProduct(int id){
        db.deleteProduct(id);
        getAllProduct();
    }
    private void searchProduct(int size){
        listProduct=db.searchProductInProducerBySize(producer.getIdProducer(), size);
        productAdapter.setData(listProduct);
    }
    private void addProductDialogShow() {
        View view = LayoutInflater.from(this).inflate(R.layout.product_add,null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        TextView tvThemProduct = (TextView) view.findViewById(R.id.tvThemProduct);
        EditText nameProduct = (EditText) view.findViewById(R.id.editNameProduct);
        EditText categoryProduct = (EditText) view.findViewById(R.id.editCategoryProduct);
        EditText sizeProduct = (EditText) view.findViewById(R.id.editSizeProduct);
        EditText rateProduct = (EditText) view.findViewById(R.id.editRateProduct);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            String name = nameProduct.getText().toString();
                            String category = categoryProduct.getText().toString();
                            double size = Double.parseDouble(sizeProduct.getText().toString());
                            String rate = rateProduct.getText().toString();
                            if(!name.isEmpty() && !category.isEmpty() && size >0 && !rate.isEmpty()){
                                addProduct(new Product(0,name,category, size ,rate, producer.getIdProducer()));
                            }else{
                                Toast.makeText(getApplicationContext() ,"Nhập chưa đủ" , Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext() ,"Nhập các trường không hợp lệ" , Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void updateProductDialogShow( int idProduct) {
        View view = LayoutInflater.from(this).inflate(R.layout.product_update,null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        Product product = db.getProduct(idProduct);
        EditText nameProduct = (EditText) view.findViewById(R.id.editNameProduct);
        nameProduct.setText(product.getName());
        EditText categoryProduct = (EditText) view.findViewById(R.id.editCategoryProduct);
        categoryProduct.setText(product.getCategory());
        EditText sizeProduct = (EditText) view.findViewById(R.id.editSizeProduct);
        sizeProduct.setText(product.getSize() + "");
        EditText rateProduct = (EditText) view.findViewById(R.id.editRateProduct);
        rateProduct.setText(product.getRating());

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = nameProduct.getText().toString();
                        String category = categoryProduct.getText().toString();
                        double size = Double.parseDouble(sizeProduct.getText().toString());
                        String rate = rateProduct.getText().toString();

                        if(!name.isEmpty() && !category.isEmpty() && size > 0 && !rate.isEmpty()){
                            product.setName(name);
                            product.setCategory(category);
                            product.setSize(size);
                            product.setRating(rate);
                            updateProduct(product);
                        }else{
                            Toast.makeText(getApplicationContext() ,"Nhập chưa đủ" , Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}