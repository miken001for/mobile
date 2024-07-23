package com.example.mad_dienthoai;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad_dienthoai.adapter.ProducerAdapter;
import com.example.mad_dienthoai.db.SQLiteHelper;
import com.example.mad_dienthoai.model.Producer;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  {
    private RecyclerView recyclerViewProducer;
    private ProducerAdapter producerAdapter;
    private ArrayList<Producer> listProducer;
    private Button btAddProducer;
    private SQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producer_detail);
        initView();
        readDataFromDb();
        btAddProducer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProducerDialogShow();
            }
        });
    }
    private void initView() {
        recyclerViewProducer=findViewById(R.id.recyclerViewProducer);
        btAddProducer=findViewById(R.id.btAddProducer);
        db= new SQLiteHelper(this);
        producerAdapter = new ProducerAdapter( new Resume(){

            @Override
            public void onUpdateClick(int id) {
                updateProducerDialogShow(id);
            }

            @Override
            public void onDeleteClick(int id) {
                deleteProducer(id);
            }

            @Override
            public void onDetailClick(int index) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ProductActivity.class);
                intent.putExtra("data", listProducer.get(index));
                startActivity(intent);
            }
        });
        recyclerViewProducer.setAdapter(producerAdapter);
        recyclerViewProducer.setLayoutManager(new LinearLayoutManager(this));
    }
    private void readDataFromDb() {
        listProducer = db.getAllProducer();
        producerAdapter.setData(listProducer);
    }
    private void addProducer(Producer producer){
        db.addProducer(producer);
        readDataFromDb();
    }

    private void deleteProducer(int id){
        db.deleteProducer(id);
        readDataFromDb();
    }
    private void updateProducer(Producer producer){
        db.updateProducer(producer);
        readDataFromDb();
    }
    private void addProducerDialogShow() {
        View view = LayoutInflater.from(this).inflate(R.layout.producer_add,null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        TextView tvThemProducer = (TextView) view.findViewById(R.id.tvThemProducer);
        EditText nameProducer = (EditText) view.findViewById(R.id.editNameProducer);
        EditText desProducer = (EditText) view.findViewById(R.id.editDesProducer);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = nameProducer.getText().toString();
                        String des = desProducer.getText().toString();
                        if(!name.isEmpty() && !des.isEmpty()){
                            addProducer(new Producer(0,name,des));
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
    private void updateProducerDialogShow( int idProducer) {
        View view = LayoutInflater.from(this).inflate(R.layout.producer_update,null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        Producer producer = db.getProducer(idProducer);
        EditText nameProducer = (EditText) view.findViewById(R.id.editNameProducer);
        nameProducer.setText(producer.getNameProducer());
        EditText desProducer = (EditText) view.findViewById(R.id.editDesProducer);
        desProducer.setText(producer.getDescription());

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = nameProducer.getText().toString();
                        String des = desProducer.getText().toString();
                        if(!name.isEmpty() && !des.isEmpty()){
                            producer.setNameProducer(name);
                            producer.setDescription(des);
                            updateProducer(producer);
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