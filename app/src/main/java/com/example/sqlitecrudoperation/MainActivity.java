package com.example.sqlitecrudoperation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    ArrayList<model> list;
    RecyclerView recyclerView;

    myAdapter adapter;
    model modelclass;
    Button buttonAdd,buttonView,buttonUpdate,buttonDelete;
    EditText editTextName,editTextSurname,editTextMarks,editTextId;


    String strGetID,str_GetName,str_GetSurName,str_GetMarks;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=new ArrayList<>();
        recyclerView=findViewById(R.id.Rview);


        buttonAdd=findViewById(R.id.btn_add);
        buttonView=findViewById(R.id.btn_view);
        buttonUpdate=findViewById(R.id.btn_update);
        buttonDelete=findViewById(R.id.btn_Delete);
        editTextName=findViewById(R.id.et_Name);
        editTextSurname=findViewById(R.id.et_SurName);
        editTextMarks=findViewById(R.id.et_Marks);
        editTextId=findViewById(R.id.et_id);


        strGetID = getIntent().getStringExtra("id");
        str_GetName = getIntent().getStringExtra("name");
        str_GetSurName = getIntent().getStringExtra("surname");
        str_GetMarks = getIntent().getStringExtra("marks");


        editTextId.setText(strGetID);
        editTextId.setEnabled(false);
        editTextName.setText(str_GetName);
        editTextSurname.setText(str_GetSurName);
        editTextMarks.setText(str_GetMarks);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        myDb = new DatabaseHelper(this);

        myDb.getWritableDatabase();

        ViewData();



        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







               boolean IsInserted= myDb.insertData(editTextId.getText().toString(),editTextName.getText().toString(),
                        editTextSurname.getText().toString(),Integer.parseInt(editTextMarks.getText().toString()));



               if(IsInserted==true)
               {
                   Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                   ViewData();
                   recyclerView.scrollToPosition(list.size()-1);

               }
               else {


                   Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
               }

            }
        });













        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                boolean isUpdate = myDb.UpdateData(editTextId.getText().toString(),editTextName.getText().toString()
                , editTextSurname.getText().toString(),editTextMarks.getText().toString());


                if(isUpdate==true)
                {
                    Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_SHORT).show();
                    editTextId.setText("");
                    editTextName.setText("");
                    editTextSurname.setText("");
                    editTextMarks.setText("");
                    ViewData();


                }

                else {

                    Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                }

            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               Integer deletedRow =  myDb.deleteData(editTextId.getText().toString());

               if(deletedRow>0)
               {
                   Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                   ViewData();
               }
               else
               {
                   Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_SHORT).show();
               }


            }
        });





    }


    public void showMessage(String title, String Message)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();




    }



    public void ViewData()
    {
        Cursor cursor = myDb.getAllData();


        list.clear();

        if(cursor.getCount() == 0 )
        {
            showMessage("Error","No Data Found");

            return;
        }
        while (cursor.moveToNext())
        {

            modelclass= new model(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));


            list.add(modelclass);
            adapter = new myAdapter(list,MainActivity.this);
            recyclerView.setAdapter(adapter);

        }
    }



}

