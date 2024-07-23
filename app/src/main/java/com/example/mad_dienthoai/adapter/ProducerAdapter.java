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

import java.util.ArrayList;

public class ProducerAdapter extends RecyclerView.Adapter<ProducerAdapter.ViewHolder> {
    private ArrayList<Producer> producerArrayList;
    private TextView tvNameProducer, tvDesProducer;
    private Button btDeleteProducer, btUpdateProducer, btViewDetailProducer;
    private Resume resume;

    public ProducerAdapter(Resume resume){
        this.producerArrayList= new ArrayList();
        this.resume=resume;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View view) {
            super(view);
            tvNameProducer=view.findViewById(R.id.tvNameProducer);
            tvDesProducer=view.findViewById(R.id.tvDesProducer);
            btDeleteProducer=view.findViewById(R.id.btDeleteProducer);
            btUpdateProducer=view.findViewById(R.id.btUpdateProducer);
            btViewDetailProducer=view.findViewById(R.id.btViewDetailProducer);
        }

        public void binData(Producer producer, int index){
            tvNameProducer.setText("Tên hãng sản xuất: " + producer.getNameProducer());
            tvDesProducer.setText("Mô tả hãng sản xuất: " + producer.getDescription());
            btDeleteProducer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resume.onDeleteClick(producer.getIdProducer());
                }
            });
            btUpdateProducer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resume.onUpdateClick(producer.getIdProducer());
                }
            });
            btViewDetailProducer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resume.onDetailClick(index);
                }
            });
        }
    }
    @NonNull
    @Override
    public ProducerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.producer_item, viewGroup,false);
        return new ProducerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProducerAdapter.ViewHolder viewHolder, int position) {
        viewHolder.binData(producerArrayList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return producerArrayList.size();
    }
    public void setData(ArrayList<Producer> data){
        this.producerArrayList=data;
        notifyDataSetChanged();
    }

}
