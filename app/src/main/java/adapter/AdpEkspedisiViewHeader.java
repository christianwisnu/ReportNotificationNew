package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.reportnotification.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.ReportEkspedisiHeaderModel;

public class AdpEkspedisiViewHeader extends ArrayAdapter<ReportEkspedisiHeaderModel> {

    private List<ReportEkspedisiHeaderModel> columnslist;
    private LayoutInflater vi;
    private int Resource;
    private ViewHolder holder;
    private Context context;
    private DateFormat df2 = new SimpleDateFormat("dd MMMM yyyy");

    public AdpEkspedisiViewHeader(Context context, int resource, List<ReportEkspedisiHeaderModel> objects) {
        super(context, resource,  objects);
        this.context = context;
        vi	=	(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource		= resource;
        columnslist		= objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v	=	convertView;
        if (v == null){
            holder	=	new ViewHolder();
            v= vi.inflate(Resource, null);
            holder.TvId    = 	 (TextView)v.findViewById(R.id.txtColEkspedisiHeaderId);
            holder.TvTgl	    =	 (TextView)v.findViewById(R.id.txtColEkspedisiHeaderTgl);
            holder.TvNamaCust    = 	 (TextView)v.findViewById(R.id.txtColEkspedisiHeadernamaCust);
            holder.TvTipeBayar	    =	 (TextView)v.findViewById(R.id.txtColEkspedisiHeaderTipeBayar);
            holder.TvStatusBayar    = 	 (TextView)v.findViewById(R.id.txtColEkspedisiHeaderStatusBayar);
            v.setTag(holder);
        }else{
            holder 	= (ViewHolder)v.getTag();
        }

        holder.TvId.setText(columnslist.get(position).getId());
        Date tglTrans = null;
        try{
            tglTrans = new SimpleDateFormat("dd-MM-yyyy").parse(columnslist.get(position).getTanggal());
        }catch (Exception e) {}
        holder.TvTgl.setText(df2.format(tglTrans));
        holder.TvNamaCust.setText("Customer: "+columnslist.get(position).getNamaCust());
        holder.TvTipeBayar.setText("Tipe Pembayaran: "+columnslist.get(position).getTipeBayar());
        holder.TvStatusBayar.setText("Status Pembayaran: "+columnslist.get(position).getStatusbayar());
        if(columnslist.get(position).getStatusbayar().equals("Lunas")){
            holder.TvStatusBayar.setTextColor(Color.BLUE);
        }else if(columnslist.get(position).getStatusbayar().equals("Bayar Sebagian")){
            holder.TvStatusBayar.setTextColor(Color.GREEN);
        }else if(columnslist.get(position).getStatusbayar().equals("Belum Bayar")){
            holder.TvStatusBayar.setTextColor(Color.RED);
        }
        return v;
    }

    static class ViewHolder{
        private TextView TvId;
        private TextView TvTgl;
        private TextView TvNamaCust;
        private TextView TvTipeBayar;
        private TextView TvStatusBayar;
    }
}
