package net.ariflaksito.mystudents.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.ariflaksito.mystudents.ListStudentsActivity;
import net.ariflaksito.mystudents.R;
import net.ariflaksito.mystudents.UpdateActivity;
import net.ariflaksito.mystudents.UpdateKeluarActivity;
import net.ariflaksito.mystudents.db.DbHelper;
import net.ariflaksito.mystudents.model.Chasflow;


import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private ArrayList<Chasflow> listStudents = new ArrayList<>();
    private Activity activity;
    private DbHelper dbHelper;

    private static final int LAYOUT_ONE = 0;
    private static final int LAYOUT_TWO = 1;

    @Override
    public int getItemViewType(int position)
    {
        if(position==0)
            return LAYOUT_ONE;
        else
            return LAYOUT_TWO;
    }


    public StudentAdapter(Activity activity) {
        this.activity = activity;
        dbHelper = new DbHelper(activity);
    }

    public ArrayList<Chasflow> getListStudents() {
        return listStudents;
    }

    public void setListStudents(ArrayList<Chasflow> listNotes) {
        if (listNotes.size() > 0) {
            this.listStudents.clear();

        }
        this.listStudents.addAll(listNotes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        StudentViewHolder viewHolder = null;
        
        if (viewType == LAYOUT_ONE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
            viewHolder = new StudentViewHolderOne(view);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_update, parent, false);
            viewHolder = new StudentViewHolderTwo(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        if (holder.getItemViewType() == LAYOUT_ONE) {
            holder.tvNominal.setText(listStudents.get(position).getNominal());
            holder.tvKeterangan.setText(listStudents.get(position).getKeterangan());
            holder.tvTanggal.setText(listStudents.get(position).getTanggal());
            holder.btnEdit.setOnClickListener((View v) -> {
                Intent intent = new Intent(activity, UpdateActivity.class);
                intent.putExtra("user", (Serializable) listStudents.get(position));
                activity.startActivity(intent);

            });


            holder.btnDelete.setOnClickListener((View v) -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle("Konfirmasi hapus");
                builder.setMessage("Apakah yakin akan dihapus?");

                builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteChasflow(listStudents.get(position).getId());
                        Toast.makeText(activity, "Hapus berhasil!", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(activity, ListStudentsActivity.class);
                        activity.startActivity(myIntent);
                        activity.finish();
                    }
                });

                builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            });
        }else {
            holder.tvKeluar.setText(listStudents.get(position).getKeluar());
            holder.tvKeterangan.setText(listStudents.get(position).getKeterangan());
            holder.tvTanggal.setText(listStudents.get(position).getTanggal());
            holder.btnEdit.setOnClickListener((View v) -> {
                Intent intent = new Intent(activity, UpdateKeluarActivity.class);
                intent.putExtra("user", (Serializable) listStudents.get(position));
                activity.startActivity(intent);

            });


            holder.btnDelete.setOnClickListener((View v) -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle("Konfirmasi hapus");
                builder.setMessage("Apakah yakin akan dihapus?");

                builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteChasflow(listStudents.get(position).getId());
                        Toast.makeText(activity, "Hapus berhasil!", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(activity, ListStudentsActivity.class);
                        activity.startActivity(myIntent);
                        activity.finish();
                    }
                });

                builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            });
        }
    }

    @Override
    public int getItemCount() {
        return
                listStudents.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        final TextView tvNominal, tvKeterangan, tvTanggal , tvKeluar;
        final ImageView ivPhoto;
        final Button btnEdit, btnDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.photo);
            tvNominal = itemView.findViewById(R.id.tv_item_nim);
            tvKeterangan = itemView.findViewById(R.id.tv_item_name);
            tvTanggal = itemView.findViewById(R.id.tv_item_tanggal);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            tvKeluar = itemView.findViewById(R.id.tv_item_keluar);
        }
    }

    private class StudentViewHolderOne extends StudentViewHolder {

        public ImageView name;
        public StudentViewHolderOne(View itemView) {
            super(itemView);
            name =(ImageView)itemView.findViewById(R.id.photo);
        }
    }

    private class StudentViewHolderTwo extends StudentViewHolder {
        public ImageView name;
        public StudentViewHolderTwo(View itemView) {
            super(itemView);
            name =(ImageView)itemView.findViewById(R.id.photo);
        }
    }

//    private class StudentViewHolderOne extends RecyclerView.ViewHolder {
//        public ImageView name;
//
//        public StudentViewHolderOne(View itemView) {
//            super(ImageView);
//            name =(ImageView)itemView.findViewById(R.id.photo);
//        }
//    }

//
//    private class StudentViewHolderTwo extends RecyclerView.ViewHolder {
//        public ImageView name;
//
//        public StudentViewHolderTwo(View itemView) {
//            super(ImageView);
//            name =(ImageView)itemView.findViewById(R.id.photo);
//        }
//    }
}
