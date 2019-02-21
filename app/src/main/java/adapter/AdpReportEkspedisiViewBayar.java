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

import model.ReportEkspedisiItemBayarModel;

public class AdpReportEkspedisiViewBayar extends ArrayAdapter<ReportEkspedisiItemBayarModel> {

    private List<ReportEkspedisiItemBayarModel> columnslist;
    private LayoutInflater vi;
    private int Resource;
    private ViewHolder holder;
    private Context context;
    private DateFormat df2 = new SimpleDateFormat("dd MMMM yyyy");
    private NumberFormat rupiah	= NumberFormat.getNumberInstance(new Locale("in", "ID"));

    public AdpReportEkspedisiViewBayar(Context context, int resource, List<ReportEkspedisiItemBayarModel> objects) {
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
            holder.TvIdBayar    = 	 (TextView)v.findViewById(R.id.txtColRpEkspedisiViewIdBayar);
            holder.TvTglBayar	    =	 (TextView)v.findViewById(R.id.txtColRpEkspedisiViewTglBayar);
            holder.TvTotal    = 	 (TextView)v.findViewById(R.id.txtColRpEkspedisiViewTotalBayar);
            holder.TvKetBayar	    =	 (TextView)v.findViewById(R.id.txtColRpEkspedisiViewKetBayar);
            holder.TvNo	    =	 (TextView)v.findViewById(R.id.txtColRpEkspedisiViewNoBayar);
            v.setTag(holder);
        }else{
            holder 	= (ViewHolder)v.getTag();
        }

        holder.TvNo.setText(String.valueOf(position+1)+".");
        holder.TvIdBayar.setText(columnslist.get(position).getIdBayar());
        Date tglTrans = null;
        try{
            tglTrans = new SimpleDateFormat("dd-MM-yyyy").parse(columnslist.get(position).getTglBayar());
        }catch (Exception e) {}
        holder.TvTglBayar.setText(df2.format(tglTrans));
        holder.TvTotal.setText("Rp. "+rupiah.format(columnslist.get(position).getGrandtotal()));
        holder.TvKetBayar.setText("Ket: "+columnslist.get(position).getKeterangan());

        return v;
    }

    static class ViewHolder{
        private TextView TvNo;
        private TextView TvIdBayar;
        private TextView TvTglBayar;
        private TextView TvTotal;
        private TextView TvKetBayar;
    }
}