package com.example.crud_operations;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.crud_operations.R;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder>{

    Context context;
    ArrayList<UsersItem> usersItemArrayList;
    DatabaseReference databaseReference;

    public UserRecyclerAdapter(Context context, ArrayList<UsersItem> usersItemArrayList) {
        this.context = context;
        this.usersItemArrayList = usersItemArrayList;
        this.databaseReference= FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.user_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UsersItem users = usersItemArrayList.get(position);

        holder.textName.setText("Name : " + users.getUserName());
        holder.textDes.setText("Des : " + users.getUserDes());
        holder.textDate.setText("Date : " + users.getUserDate());

        holder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogUpdate viewDialogUpdate = new ViewDialogUpdate();
                viewDialogUpdate.showDialog(context,users.getUserID(),users.getUserName(),users.getUserDes(),users.getUserDate());
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogConfirmDelete viewDialogConfirmDelete = new ViewDialogConfirmDelete();
                viewDialogConfirmDelete.showDialog(context,users.getUserID());
            }
        });

    }

    @Override
    public int getItemCount() {

        return usersItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textName;
        TextView textDes;
        TextView textDate;

        Button buttonDelete;
        Button buttonUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textName=itemView.findViewById(R.id.textname);
            textDes=itemView.findViewById(R.id.textdes);
            textDate=itemView.findViewById(R.id.textdate);

            buttonDelete=itemView.findViewById(R.id.deleteBtn);
            buttonUpdate=itemView.findViewById(R.id.updateBtn);
        }
    }

    public class ViewDialogUpdate {
        public void showDialog(Context context, String id, String name, String des, String date){
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_new_user);

            EditText Name = dialog.findViewById(R.id.textname);
            EditText Des = dialog.findViewById(R.id.textdes);
            EditText Pdate = dialog.findViewById(R.id.textdate);

            Name.setText(name);
            Des.setText(des);
            Pdate.setText(date);



            Button addButton = dialog.findViewById(R.id.buttonadd);
            Button cancelButton = dialog.findViewById(R.id.buttonCancel);

            addButton.setText("UPDATE");
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String newName = Name.getText().toString();
                    String newDes = Des.getText().toString();
                    String newDate = Pdate.getText().toString();

                    if (name.isEmpty() || des.isEmpty() || date.isEmpty()) {
                        Toast.makeText(context, "Please Enter All data....", Toast.LENGTH_SHORT).show();
                    } else {

                        if (newName.equals(name) && newDes.equals(des) && newDate.equals(date)) {
                            Toast.makeText(context, "you don't change anything", Toast.LENGTH_SHORT).show();
                        } else {

                            databaseReference.child("USERS").child(id).setValue(new UsersItem(id, newName, newDes, newDate));
                            Toast.makeText(context, "User Updated Successfully", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }

    public class ViewDialogConfirmDelete {
        public void showDialog(Context context, String id){
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.view_dialog_confirm_delete);


            Button buttonDelete = dialog.findViewById(R.id.buttonDelete);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);


            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                            databaseReference.child("USERS").child(id).removeValue();
                            Toast.makeText(context, "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }
}