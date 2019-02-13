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

import model.ListCustomerModel;

import static android.app.Activity.RESULT_OK;

public class AdpListCustomer extends RecyclerView.Adapter<AdpListCustomer.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<ListCustomerModel> mArrayList;
    private ArrayList<ListCustomerModel> mFilteredList;

    public AdpListCustomer(Context contextku, ArrayList<ListCustomerModel> arrayList) {
        context = contextku;
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_list_cust, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try{
            viewHolder.tv_kode.setText(mFilteredList.get(i).getIdCust());
            viewHolder.tv_nama.setText(mFilteredList.get(i).getNamaCust());
            viewHolder.tv_alamat.setText(mFilteredList.get(i).getAlamatCust());
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
                    ArrayList<ListCustomerModel> filteredList = new ArrayList<>();
                    for (ListCustomerModel entity : mArrayList) {
                        if (entity.getNamaCust().toLowerCase().contains(charString) ) {
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
                mFilteredList = (ArrayList<ListCustomerModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv_kode,tv_nama,tv_alamat;

        public ViewHolder(View view) {
            super(view);
            tv_kode = (TextView)view.findViewById(R.id.txt_view_mastercust_kode);
            tv_nama = (TextView)view.findViewById(R.id.txt_view_mastercust_nama);
            tv_alamat = (TextView)view.findViewById(R.id.txt_view_mastercust_alamat);
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
