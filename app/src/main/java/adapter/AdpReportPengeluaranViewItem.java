package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chris.reportnotification.R;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.ReportPengeluaranItemModel;

public class AdpReportPengeluaranViewItem extends ArrayAdapter<ReportPengeluaranItemModel> {

    private List<ReportPengeluaranItemModel> columnslist;
    private LayoutInflater vi;
    private int Resource;
    private ViewHolder holder;
    private Context context;
    private DateFormat df2 = new SimpleDateFormat("dd MMMM yyyy");
    private NumberFormat rupiah	= NumberFormat.getNumberInstance(new Locale("in", "ID"));

    public AdpReportPengeluaranViewItem(Context context, int resource, List<ReportPengeluaranItemModel> objects) {
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
            holder.TvNo = (TextView)v.findViewById(R.id.txtColRpPengeluaranViewNoItem);
            holder.TvTglShipping    = 	 (TextView)v.findViewById(R.id.txtColRpPengeluaranViewTglShipItem);
            holder.TvBukti	    =	 (TextView)v.findViewById(R.id.txtColRpPengeluaranViewBuktiItem);
            holder.TvHarga	    =	 (TextView)v.findViewById(R.id.txtColRpPengeluaranViewHargaItem);
            v.setTag(holder);
        }else{
            holder 	= (ViewHolder)v.getTag();
        }
        holder.TvNo.setText(String.valueOf(position+1)+".");
        Date tglTrans = null;
        try{
            tglTrans = new SimpleDateFormat("dd-MM-yyyy").parse(columnslist.get(position).getTglShipping());
        }catch (Exception e) {}
        holder.TvTglShipping.setText(df2.format(tglTrans));
        holder.TvBukti.setText(columnslist.get(position).getBuktiBayar());
        holder.TvHarga.setText("Rp. "+rupiah.format(columnslist.get(position).getHarga()));
        return v;
    }

    static class ViewHolder{
        private TextView TvNo;
        private TextView TvTglShipping;
        private TextView TvBukti;
        private TextView TvHarga;
    }
}
