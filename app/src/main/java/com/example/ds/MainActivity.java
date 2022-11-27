package com.example.ds;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button BTN_DateD,BTN_DateF,BTN_Client;
    EditText txt_DateD,txt_DateF,txt_client,txt_adresse,edt_ref,txt_red,txt_Cl;
    private int jour,mois,annee;
    ImageButton _btnRecherche,_btnFirst,_btnPrevious,_btnNext,_btnLast,btn_ajout,btnmodifier,btn_delete;
    String DATEE;
    DatePickerDialog.OnDateSetListener dateDialog1,dateDialog2;
    SQLiteDatabase DB;
    LinearLayout layNaviguer, layRecherche;
    Cursor cur,cur1,cur2,cur3,cur4;
    String ID_C ="";
    String ID ="";
    String IDCL="";
    String ID_Contrat="";
    String query2,query1,query3,query4;
    String IDcontrat="";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4){
            String message = data.getStringExtra("nomclient");
            String adresse = data.getStringExtra("adresse");
            IDCL=data.getStringExtra("id");
            txt_Cl.setText(message );
            txt_adresse.setText(adresse);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_DateD=(EditText) findViewById(R.id.txtDateD);
        txt_DateF=(EditText) findViewById(R.id.txtDateF);
        txt_client=(EditText) findViewById(R.id.txtclient);
        edt_ref=(EditText) findViewById(R.id.edtref);
        txt_red=(EditText) findViewById(R.id.txtred);
        txt_adresse=(EditText) findViewById(R.id.txtadresse);
        txt_Cl=(EditText) findViewById(R.id.txtCl);
        BTN_DateD=(Button) findViewById(R.id.BTNDateD);
        BTN_DateF=(Button) findViewById(R.id.BTNDateF);
        BTN_Client=(Button) findViewById(R.id.BTNClient);
        btnmodifier=(ImageButton) findViewById(R.id.btnmodifier);
        _btnFirst = (ImageButton) findViewById(R.id.btnFirst);
        _btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        _btnNext = (ImageButton) findViewById(R.id.btnNext);
        _btnLast = (ImageButton) findViewById(R.id.btnLast);
        _btnRecherche = (ImageButton) findViewById(R.id.btnRecherche);
        btn_ajout=(ImageButton) findViewById(R.id.btnajout);
        layNaviguer = (LinearLayout) findViewById(R.id.layNaviguer);
        btn_delete=(ImageButton) findViewById(R.id.btndelete);

        DB = openOrCreateDatabase("BDAssurance.db", Context.MODE_PRIVATE, null);
        DB.execSQL("CREATE TABLE IF NOT EXISTS contrats (ID INTEGER PRIMARY KEY AUTOINCREMENT,reference VARCHAR NOT NULL, datedebut VARCHAR NOT NULL,datefin VARCHAR NOT NULL,redevence REAL NOT NULL,client_id INTEGER NOT NULL);");

        //Date Debut
        BTN_DateD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar C=Calendar.getInstance();
                jour=C.get(Calendar.DAY_OF_MONTH);
                mois=C.get(Calendar.MONTH);
                annee=C.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateDialog1, annee, mois, jour);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateDialog1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                if (day < 10 && month < 10) {
                    DATEE =   year+ "-" + 0 + month + "-" + 0 +day ;
                    txt_DateD.setText(DATEE);
                } else if (day < 10) {
                    DATEE =  year+ "-" + month + "-" + 0 + day;
                    txt_DateD.setText(DATEE);
                } else if (month < 10) {
                    DATEE = year  + "-" + 0 + month + "-" + day;
                    txt_DateD.setText(DATEE);
                } else {
                    {
                        DATEE = year  + "-" + month + "-" + day;
                        txt_DateD.setText(DATEE);

                    }
                }
            }
        };

        //Date Fin
        BTN_DateF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar C=Calendar.getInstance();
                jour=C.get(Calendar.DAY_OF_MONTH);
                mois=C.get(Calendar.MONTH);
                annee=C.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateDialog2, annee, mois, jour);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        dateDialog2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                if (day < 10 && month < 10) {
                    DATEE =   year+ "-" + 0 + month + "-" + 0 +day ;
                    txt_DateF.setText(DATEE);
                } else if (day < 10) {
                    DATEE =  year+ "-" + month + "-" + 0 + day;
                    txt_DateF.setText(DATEE);
                } else if (month < 10) {
                    DATEE = year  + "-" + 0 + month + "-" + day;
                    txt_DateF.setText(DATEE);
                } else {
                    {
                        DATEE = year  + "-" + month + "-" + day;
                        txt_DateF.setText(DATEE);

                    }
                }
            }
        };


        BTN_Client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,gestion_clients.class);
                startActivityForResult(intent,4);
            }
        });


        //Ajout un nouveau contrat
        btn_ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_client.getText().toString().trim().length() == 0){
                    DB.execSQL("INSERT INTO contrats (reference,datedebut,datefin,redevence,client_id) VALUES('" + edt_ref.getText() + "','" + txt_DateD.getText() + "','" + txt_DateF.getText() + "','" + txt_red.getText() + "','" + IDCL + "');");
                AfficheMessage("Succès", "Information ajouter");
                    videTexte();
            }
                else{
                    DB.execSQL("INSERT INTO contrats (reference,datedebut,datefin,redevence,client_id) VALUES('" + edt_ref.getText() + "','" + txt_DateD.getText() + "','" + txt_DateF.getText() + "','" + txt_red.getText() + "','" + ID_C + "');");
                    AfficheMessage("Succès", "Information ajouter");
                    videTexte();
                }
            }
        });


        //Pour rechercher des contrats d'un client
        _btnRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cur =DB.rawQuery("select * from clients where nom like ?", new String[]{"%" + txt_client.getText().toString() + "%"});
                query3 = "SELECT * FROM clients" + " Where nom='" + txt_client.getText().toString()+ "'";
                // Créer un curseur pour récupérer le résultat de la requête select
                cur = DB.rawQuery(query3, null);
                if(cur.moveToFirst()){
                    txt_adresse.setText(cur.getString(2));
                    txt_Cl.setText(cur.getString(1));
                    ID_C = cur.getString(0);
                    query4 = "SELECT * FROM contrats" + " Where client_id='" + ID_C + "'";
                    // Créer un curseur pour récupérer le résultat de la requête select
                    cur4 = DB.rawQuery(query4, null);

                    try {
                        cur4.moveToFirst();
                        ID = cur4.getString(0);
                        edt_ref.setText(cur4.getString(1));
                        txt_DateD.setText(cur4.getString(2));
                        txt_DateF.setText(cur4.getString(3));
                        txt_red.setText(cur4.getString(4));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "aucun résultat.", Toast.LENGTH_SHORT).show();
                    }

                }

                if(cur.getCount()==0){
                    AfficheMessage("Erreur","Aucune donnée");
                    return ;
                }

            }

        });

        //Modifier un contrat
        btnmodifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_client.getText().toString().trim().length() == 0) {
                    AfficheMessage("Succès", "Saisir nom client");
                    return;
                }
                query2 = "SELECT * FROM contrats" + " Where client_id='" + ID_C + "'";
                // Créer un curseur pour récupérer le résultat de la requête select
                cur2 = DB.rawQuery(query2, null);
                if(cur2.moveToFirst()){
                    IDcontrat=cur2.getString(0);
                    DB.execSQL("update contrats set reference=?, datedebut=?, datefin=?, redevence=? where ID=?;", new String[]{edt_ref.getText().toString(), txt_DateD.getText().toString(), txt_DateF.getText().toString(), txt_red.getText().toString(), IDcontrat});
                    AfficheMessage("Succès", "Information Modifié");
                }
                else
                if(cur2.moveToNext()){
                    IDcontrat=cur2.getString(0);
                    DB.execSQL("update contrats set reference=?, datedebut=?, datefin=?, redevence=? where ID=?;", new String[]{edt_ref.getText().toString(), txt_DateD.getText().toString(), txt_DateF.getText().toString(), txt_red.getText().toString(), IDcontrat});
                    AfficheMessage("Succès", "Information Modifié");
                }



            }
        });

        //supprimer un contrat
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_client.getText().toString().trim().length() == 0){
                    AfficheMessage("Erreur","Selectionner nom client pour pouvoir supprimer son contrat!!!");
                }

                if(cur2.moveToFirst()){
                    IDcontrat=cur2.getString(0);
                    DB.execSQL("DELETE FROM contrats WHERE ID =?;",new String[] {IDcontrat});
                    AfficheMessage("Succès", "Information détruite");
                }
                else
                if(cur2.moveToNext()){
                    IDcontrat=cur2.getString(0);
                    DB.execSQL("DELETE FROM contrats WHERE ID =?;",new String[] {IDcontrat});
                    AfficheMessage("Succès", "Information détruite");
                }

            }
        });


        // Bouton Premier
        _btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                   cur4.moveToFirst();
                        edt_ref.setText(cur4.getString(1));
                        txt_DateD.setText(cur4.getString(2));
                        txt_DateF.setText(cur4.getString(3));
                        txt_red.setText(cur4.getString(4));
                    _btnPrevious.setEnabled(false);
                    _btnNext.setEnabled(true);

                } // fin du try
                catch (Exception se) {
                    Toast.makeText(getApplicationContext(),"aucun contrat n'est existant.",Toast.LENGTH_SHORT).show();

                }
            }
            });


        //Bouton Suivant
        _btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                cur4.moveToNext();
                    edt_ref.setText(cur4.getString(1));
                    txt_DateD.setText(cur4.getString(2));
                    txt_DateF.setText(cur4.getString(3));
                    txt_red.setText(cur4.getString(4));
                    if (cur4.isLast()){
                        _btnNext.setEnabled(false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
        });

        //Bouton precédent
        _btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                cur4.moveToPrevious();
                    edt_ref.setText(cur4.getString(1));
                    txt_DateD.setText(cur4.getString(2));
                    txt_DateF.setText(cur4.getString(3));
                    txt_red.setText(cur4.getString(4));
                        _btnNext.setEnabled(true);
                        if (cur4.isFirst()){
                            _btnPrevious.setEnabled(false);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
        //bouton dernier
        _btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur4.moveToLast();
                    edt_ref.setText(cur4.getString(1));
                    txt_DateD.setText(cur4.getString(2));
                    txt_DateF.setText(cur4.getString(3));
                    txt_red.setText(cur4.getString(4));
                    _btnPrevious.setEnabled(true);
                    _btnNext.setEnabled(false);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucun contrat n'est existant.",Toast.LENGTH_SHORT).show();

                }
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
    public void videTexte()
    {
        edt_ref.setText("");
        txt_DateD.setText("");
        txt_DateF.setText("");
        txt_red.setText("");
        txt_Cl.setText("");
        txt_adresse.setText("");
    }

}