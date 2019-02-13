package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.chris.reportnotification.R;

import java.util.ArrayList;

import model.ListJenisKendaraanModel;

import static android.app.Activity.RESULT_OK;

public class AdpListJenisKendaraan extends RecyclerView.Adapter<AdpListJenisKendaraan.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<ListJenisKendaraanModel> mArrayList;
    private ArrayList<ListJenisKendaraanModel> mFilteredList;

    public AdpListJenisKendaraan(Context contextku, ArrayList<ListJenisKendaraanModel> arrayList) {
        context = contextku;
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_list_jenis_kendaraan, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try{
            viewHolder.tv_kode.setText(mFilteredList.get(i).getId());
            viewHolder.tv_nama.setText(mFilteredList.get(i).getNama());
        }catch(Exception ex){}
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = mArrayList;
                } else {
                    ArrayList<ListJenisKendaraanModel> filteredList = new ArrayList<>();
                    for (ListJenisKendaraanModel entity : mArrayList) {
                        if (entity.getNama().toLowerCase().contains(charString) ) {
                            filteredList.add(entity);
                        }
                    }
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<ListJenisKendaraanModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv_kode,tv_nama;

        public ViewHolder(View view) {
            super(view);
            tv_kode = (TextView)view.findViewById(R.id.txt_view_masterjns_kendaraan_kode);
            tv_nama = (TextView)view.findViewById(R.id.txt_view_masterjns_kendaraan_nama);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("kode", tv_kode.getText().toString());
            intent.putExtra("nama", tv_nama.getText().toString());
            ((Activity)context).setResult(RESULT_OK, intent);
            ((Activity)context).finish();
        }
    }
}