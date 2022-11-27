package com.example.ds;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addclient extends AppCompatActivity {
    EditText nom,tel,fax,adresse,contact,telcontact;
    Button btn_ajout,btn_back;
    SQLiteDatabase DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addclient);
        nom=(EditText) findViewById(R.id.Z_nom);
        adresse=(EditText)findViewById(R.id.Z_adresse);
        tel=(EditText)findViewById(R.id.Z_tel);
        fax=(EditText)findViewById(R.id.Z_fax);
        contact=(EditText)findViewById(R.id.Z_contact);
        telcontact=(EditText)findViewById(R.id.Z_telcontact);
        btn_ajout=(Button) findViewById(R.id.btnajout);
        btn_back=(Button) findViewById(R.id.btnback);

        DB = openOrCreateDatabase("BDAssurance.db", Context.MODE_PRIVATE, null);
        DB.execSQL("CREATE TABLE IF NOT EXISTS clients(ID INTEGER PRIMARY KEY AUTOINCREMENT,nom VARCHAR NOT NULL,adresse VARCHAR NOT NULL,tel INTEGER NOT NULL,fax INTEGER NOT NULL,contact VARCHAR NOT NULL,telcontact INTEGER NOT NULL);");
        btn_ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.execSQL("INSERT INTO clients (nom,adresse,tel,fax,contact ,telcontact) VALUES('" + nom.getText().toString() + "','" + adresse.getText().toString() + "','" + tel.getText().toString() + "','" + fax.getText().toString()  + "','" +  contact.getText().toString() + "','" +telcontact.getText().toString()  +"');");
                AfficheMessage("Succès", "Information ajouter");
                Intent cl=new Intent(addclient.this,gestion_clients.class);
                startActivity(cl);
                finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=new Intent(addclient.this,gestion_clients.class);
                startActivity(back);
                finish();

            }
        });

    }
    public void AfficheMessage(String titre,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titre);
        builder.setMessage(message);
        builder.show();

    }
}