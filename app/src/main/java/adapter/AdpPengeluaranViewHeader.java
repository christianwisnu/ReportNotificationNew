package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chris.reportnotification.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.ReportPengeluaranHeaderModel;

public class AdpPengeluaranViewHeader extends ArrayAdapter<ReportPengeluaranHeaderModel> {

    private List<ReportPengeluaranHeaderModel> columnslist;
    private LayoutInflater vi;
    private int Resource;
    private ViewHolder holder;
    private Context context;
    private DateFormat df2 = new SimpleDateFormat("dd MMMM yyyy");

    public AdpPengeluaranViewHeader(Context context, int resource, List<ReportPengeluaranHeaderModel> objects) {
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
            holder.TvId    = 	 (TextView)v.findViewById(R.id.txtColPengeluaranHeaderId);
            holder.TvTgl	    =	 (TextView)v.findViewById(R.id.txtColPengeluaranHeaderTgl);
            holder.TvNamaVendor    = 	 (TextView)v.findViewById(R.id.txtColPengeluaranHeadernamaVendor);
            holder.TvTipeBayar	    =	 (TextView)v.findViewById(R.id.txtColPengeluaranHeaderTipeBayar);
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
        holder.TvNamaVendor.setText("Vendor: "+columnslist.get(position).getNamaVendor());
        holder.TvTipeBayar.setText("Tipe Pembayaran: "+columnslist.get(position).getTipeBayar());

        return v;
    }

    static class ViewHolder{
        private TextView TvId;
        private TextView TvTgl;
        private TextView TvNamaVendor;
        private TextView TvTipeBayar;
    }


}
