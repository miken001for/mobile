package com.example.mad_dienthoai.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad_dienthoai.R;
import com.example.mad_dienthoai.Resume;
import com.example.mad_dienthoai.model.Producer;
import com.example.mad_dienthoai.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<Product> productArrayList;
    private TextView tvNameProduct,tvHsx,tvSizeProduct,tvRating;
    private Button btDeleteProduct, btUpdateProduct;
    private Resume resume;

    public ProductAdapter(Resume resume){
        this.productArrayList= new ArrayList();
        this.resume=resume;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View view) {
            super(view);
            tvNameProduct=view.findViewById(R.id.tvNameProduct);
            tvHsx=view.findViewById(R.id.tvHsx);
            tvSizeProduct=view.findViewById(R.id.tvSizeProduct);
            tvRating=view.findViewById(R.id.tvRating);
            btDeleteProduct=view.findViewById(R.id.btDeleteProduct);
            btUpdateProduct=view.findViewById(R.id.btUpdateProduct);
        }
        public void binData(Product product){
            tvNameProduct.setText("Tên sản phẩm: " + product.getName());
            tvHsx.setText("Hãng sản xuất: " + product.getCategory());
            tvSizeProduct.setText(("Kích thước: " + product.getSize()));
            tvRating.setText("Đánh giá: " + product.getRating());
            btDeleteProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resume.onDeleteClick(product.getIdProduct());
                }
            });
            btUpdateProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resume.onUpdateClick(product.getIdProduct());
                }
            });
        }
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.producer_item, viewGroup,false);
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder viewHolder, int position) {
        viewHolder.binData(productArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
    public void setData(ArrayList<Product> data){
        this.productArrayList = data;
        notifyDataSetChanged();
    }

}
