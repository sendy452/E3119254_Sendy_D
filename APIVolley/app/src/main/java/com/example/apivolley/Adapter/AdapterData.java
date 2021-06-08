package com.example.apivolley.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apivolley.InsertData;
import com.example.apivolley.Model.ModelData;
import com.example.apivolley.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    private final List<ModelData> mItems;
    private final Context context;

    /**
     * Deklarasi untuk menampung banyak data
     */
    public AdapterData(Context context, List<ModelData> items) {
        this.mItems = items;
        this.context = context;
    }

    /**
     * Deklarasi layout_row yang menampung tiap data
     */
    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    /**
     * Deklarasi tiap variabel yang menampung data sesuai dengan data di database
     */
    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        ModelData md = mItems.get(position);
        holder.tvusername.setText(md.getUsername());
        holder.tvgrup.setText(md.getGrup());
        holder.tvnama.setText(md.getNama());
        holder.tvpassword.setText(md.getPassword());
        holder.md = md;
    }

    /**
     * Menghitung banyaknya data yang diambil
     */
    @Override
    public int getItemCount() {
        return mItems.size();
    }


    /**
     * Membuat class HolderData turunan dari RecyclerView.ViewHolder
     */
    class HolderData extends RecyclerView.ViewHolder {
        TextView tvusername, tvgrup, tvnama, tvpassword;
        ModelData md;

        /**
         * Deklarasikan setiap widget yang ada di layout_row
         */
        public HolderData(View view) {
            super(view);

            tvusername = view.findViewById(R.id.username);
            tvgrup = view.findViewById(R.id.grup);
            tvnama = view.findViewById(R.id.nama);
            tvpassword = view.findViewById(R.id.password);

            view.setOnClickListener(new View.OnClickListener() {
                /** Apabila salah satu data di klik maka akan menampilkan detail data*/
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, InsertData.class);
                    update.putExtra("update", 1);
                    update.putExtra("username", md.getUsername());
                    update.putExtra("grup", md.getGrup());
                    update.putExtra("nama", md.getNama());
                    update.putExtra("password", md.getPassword());

                    context.startActivity(update);
                }
            });
        }
    }
}