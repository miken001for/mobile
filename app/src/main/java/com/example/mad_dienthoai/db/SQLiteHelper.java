package com.example.mad_dienthoai.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mad_dienthoai.model.Producer;
import com.example.mad_dienthoai.model.Product;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="phone_manufacturer";
    public static final int DATABASE_VERSION=1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCER_TABLE="CREATE TABLE producer("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, description TEXT )";
        String CREATE_PRODUCT_TABLE="CREATE TABLE product("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, category TEXT, size INTEGER, rating TEXT, id_producer INTEGER, " +
                "FOREIGN KEY(id_producer)  REFERENCES producer(id) ON DELETE CASCADE)";
        db.execSQL(CREATE_PRODUCER_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<Producer> getAllProducer(){
        ArrayList<Producer> producerList= new ArrayList<>();
        String query = "SELECT * FROM producer";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            Producer producer=new Producer(cursor.getInt(0), cursor.getString(1), cursor.getString(2) );
            producerList.add(producer);
            cursor.moveToNext();
        }
        return producerList;
    }
    public ArrayList<Product> getAllProductInProducer(int id_producer){
        ArrayList<Product> productList= new ArrayList<>();
        String query = "SELECT * FROM product WHERE id_producer = " + id_producer ;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            Product product=new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                    cursor.getString(4),cursor.getInt(5) );
            productList.add(product);
            cursor.moveToNext();
        }
        return productList;
    }
    public void addProducer(Producer producer){
        ContentValues values= new ContentValues();
        values.put("name", producer.getNameProducer());
        values.put("description", producer.getDescription());
        SQLiteDatabase db= this.getWritableDatabase();
        db.insert("producer",null,values);
        db.close();
    }

    public void addProduct(Product product){
        ContentValues values= new ContentValues();
        values.put("name", product.getName());
        values.put("category", product.getCategory());
        values.put("size", product.getSize());
        values.put("rating", product.getRating());
        values.put("id_producer", product.getIdProducer());
        SQLiteDatabase db= this.getWritableDatabase();
        db.insert("product",null,values);
        db.close();
    }
    public Producer getProducer(int id){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.query("producer",null,"id = ?", new String[]{String.valueOf(id)},
                null,null,null);
        if(cursor != null) cursor.moveToFirst();
        Producer producer=new Producer(cursor.getInt(0), cursor.getString(1), cursor.getString(2) );
        return producer;
    }

    public Product getProduct(int id){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.query("producer",null,"id = ?", new String[]{String.valueOf(id)},
                null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        Product product=new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                cursor.getString(4),cursor.getInt(5) );
        return product;
    }

    public void updateProducer(Producer producer){
        ContentValues values= new ContentValues();
        values.put("name", producer.getNameProducer());
        values.put("description", producer.getDescription());
        SQLiteDatabase db= this.getWritableDatabase();
        db.update("producer",values,"id = ?", new String[]{String.valueOf(producer.getIdProducer())});
        db.close();
    }

    public void updateProduct(Product product){
        ContentValues values= new ContentValues();
        values.put("name", product.getName());
        values.put("category", product.getCategory());
        values.put("size", product.getSize());
        values.put("rating", product.getRating());
        SQLiteDatabase db= this.getWritableDatabase();
        db.update("product",values,"id = ?",new String[]{String.valueOf(product.getIdProduct())});
        db.close();
    }

    public void deleteProducer(int id){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete("producer","id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteProduct(int id){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete("product","id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<Product> searchProductInProducerBySize(int idProducer, int size){
        ArrayList<Product> productArrayList = new ArrayList<>();
        String query = "SELECT * FROM product WHERE size > " + size + " AND idProducer =" + idProducer;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            Product product=new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                    cursor.getString(4),cursor.getInt(5) );
            productArrayList.add(product);
            cursor.moveToNext();
        }
        return productArrayList;
    }
}
