package com.example.ds;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Recherche_client extends AppCompatActivity {
    Cursor cur;
    SQLiteDatabase DB;
    TextView TV_nom,TV_tel,TV_fax,TV_adresse,TV_contact,TV_telcontact,TV_txt_client;
    EditText ED_ref;
    ImageButton _btnRecherche;
    String query,query1;
    Cursor c,c1;
    String ID_client="";
    Button BTN_client;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_client);
        _btnRecherche=(ImageButton) findViewById(R.id.btnRecherche);
        TV_nom=(TextView) findViewById(R.id.TVnom);
        ED_ref=(EditText) findViewById(R.id.EDref);
        TV_adresse=(TextView) findViewById(R.id.TVadresse);
        TV_tel=(TextView) findViewById(R.id.TVtel);
        TV_fax=(TextView) findViewById(R.id.TVfax);
        TV_contact=(TextView) findViewById(R.id.TVcontact);
        TV_telcontact=(TextView) findViewById(R.id.TVtelcontact);
        BTN_client=(Button) findViewById(R.id.btn_client);

        DB = openOrCreateDatabase("BDAssurance.db", Context.MODE_PRIVATE, null);


        //Recherche un client par reference de son contrat
        _btnRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = "SELECT * FROM contrats" + " Where reference='" + ED_ref.getText().toString()+ "'";
                // Créer un curseur pour récupérer le résultat de la requête select
                c= DB.rawQuery(query, null);
                if(c.moveToFirst()){
                    ID_client=c.getString(5);
                    query1 = "SELECT * FROM clients" + " Where ID='" + ID_client+ "'";
                    // Créer un curseur pour récupérer le résultat de la requête select
                    c1 = DB.rawQuery(query1, null);
                    if(c1.moveToFirst()) {
                        TV_nom.setText("Nom : "+c1.getString(1));
                        TV_adresse.setText("Adresse : "+c1.getString(2));
                        TV_tel.setText("Tel : "+c1.getString(3));
                        TV_fax.setText("Fax : "+c1.getString(4));
                        TV_contact.setText("Contact : "+c1.getString(5));
                        TV_telcontact.setText("Tel contact : "+c1.getString(6));
                    }
                    }

                }

        });

        BTN_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cl=new Intent(Recherche_client.this,gestion_clients.class);
                startActivity(cl);
                finish();

            }
        });


    }

}