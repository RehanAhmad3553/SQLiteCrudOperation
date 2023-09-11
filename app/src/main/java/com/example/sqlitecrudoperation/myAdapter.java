package com.example.sqlitecrudoperation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitecrudoperation.databinding.ItemListBinding;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {

    ArrayList<model> mylist;
    Context context;
    DatabaseHelper myDb;

    public myAdapter(ArrayList<model> mylist, Context context) {
        this.mylist = mylist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.tvRcOne.setText(mylist.get(position).id);
        holder.binding.tvRcTwo.setText(mylist.get(position).name);
        holder.binding.tvRcThree.setText(mylist.get(position).surname);
        holder.binding.tvRcFour.setText(mylist.get(position).getMarks());


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                 AlertDialog.Builder alert = new AlertDialog.Builder(context);

                                 alert.setTitle("Message");
                                 alert.setMessage("Are you sure to delete this item?");

                                 alert.setCancelable(false);

                                 alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {

                                         myDb = new DatabaseHelper(context);

                                         myDb.getWritableDatabase();

                                         Integer deletedRow =  myDb.deleteData(mylist.get(position).getId());

                                         if(deletedRow>0)
                                         {
                                             Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show();

                                             Intent intent = new Intent(context,MainActivity.class);
                                             context.startActivity(intent);
                                             ((Activity)context).finish();

                                         }
                                         else
                                         {
                                             Toast.makeText(context, "Data not Deleted", Toast.LENGTH_SHORT).show();
                                         }




                                     }
                                 }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {

                                        Toast.makeText(context, "No Clicked", Toast.LENGTH_SHORT).show();

                                     }
                                 });

                                 alert.show();

                return false;
            }
        });


        holder.itemView.setOnClickListener(v -> {


          Intent intent = new Intent(context,MainActivity.class);
          intent.putExtra("id",mylist.get(position).getId());
          intent.putExtra("name",mylist.get(position).getName());
          intent.putExtra("surname",mylist.get(position).getSurname());
          intent.putExtra("marks",mylist.get(position).getMarks());
          context.startActivity(intent);
          ((Activity)context).finish();



        });




    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ItemListBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemListBinding.bind(itemView);
        }
    }
}
