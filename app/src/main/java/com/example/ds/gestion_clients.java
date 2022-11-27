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
import android.widget.Toast;

public class gestion_clients extends AppCompatActivity {

    ImageButton _btnRecherche,btnmodifier,btn_delete;
    EditText nom,tel,fax,adresse,contact,telcontact,txt_client;
    Cursor cur;
    SQLiteDatabase DB;
    Button btn_ajout,btn_ajoutC,btn_R;
    String S_Nom="";
    String IDCl="";
    String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_clients);

        nom=(EditText) findViewById(R.id.Z_nom);
        adresse=(EditText)findViewById(R.id.Z_adresse);
        tel=(EditText)findViewById(R.id.Z_tel);
        fax=(EditText)findViewById(R.id.Z_fax);
        contact=(EditText)findViewById(R.id.Z_contact);
        telcontact=(EditText)findViewById(R.id.Z_telcontact);
        _btnRecherche = (ImageButton) findViewById(R.id.btnRecherche);
        txt_client=(EditText) findViewById(R.id.txtclient);
        btn_ajout=(Button) findViewById(R.id.btnajout);
        btn_ajoutC=(Button) findViewById(R.id.btnajoutC);
        btnmodifier=(ImageButton) findViewById(R.id.btnmodifier);
        btn_R=(Button) findViewById(R.id.btnc);
        btn_delete=(ImageButton) findViewById(R.id.btndelete);


        DB = openOrCreateDatabase("BDAssurance.db", Context.MODE_PRIVATE, null);


        //Recherche client
        _btnRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cur = DB.rawQuery("select * from clients where nom like ?", new String[]{"%" + txt_client.getText().toString() + "%"});
                query = "SELECT * FROM clients" + " Where nom='" + txt_client.getText().toString()+ "'";
                // Créer un curseur pour récupérer le résultat de la requête select
                cur = DB.rawQuery(query, null);
                try {
                    cur.moveToFirst();
                    IDCl=cur.getString(0);
                    S_Nom=cur.getString(1);
                    nom.setText(S_Nom);
                    adresse.setText(cur.getString(2));
                    tel.setText(cur.getString(3));
                    fax.setText(cur.getString(4));
                    contact.setText(cur.getString(5));
                    telcontact.setText(cur.getString(6));

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucun résultat.",Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i = new Intent();
                    i.putExtra("id", IDCl);
                    i.putExtra("nomclient", S_Nom);
                    i.putExtra("adresse", adresse.getText().toString());
                    setResult(4, i);
                    finish();


            }
        });

        //Aller à l'interface addclient pour ajouter nouveau client
        btn_ajoutC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nouveau=new Intent(gestion_clients.this,addclient.class);
                startActivity(nouveau);
                finish();
            }
        });

        //Aller à l'interface Recherche_client
        btn_R.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recherche=new Intent(gestion_clients.this,Recherche_client.class);
                startActivity(recherche);
                finish();
            }
        });

        //Modifier les informations d'un client
        btnmodifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nom,adresse,tel,fax,contact ,telcontact
                DB.execSQL("update clients set nom=?, adresse=?,tel=?, fax=?, contact=?, telcontact=? where ID=?;", new String[] {nom.getText().toString(), adresse.getText().toString(),tel.getText().toString(),fax.getText().toString(),contact.getText().toString(),telcontact.getText().toString(),IDCl});
                AfficheMessage("Succès", "Information Modifié");
            }
        });

        //Supprimer les informations d'un client
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.execSQL("DELETE FROM clients WHERE ID =?;",new String[] {IDCl});
                AfficheMessage("Succès", "Information détruite");
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