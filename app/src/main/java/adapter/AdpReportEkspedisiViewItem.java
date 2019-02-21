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
import java.util.List;
import java.util.Locale;

import model.ReportEkspedisiItemModel;

public class AdpReportEkspedisiViewItem extends ArrayAdapter<ReportEkspedisiItemModel> {

    private List<ReportEkspedisiItemModel> columnslist;
    private LayoutInflater vi;
    private int Resource;
    private ViewHolder holder;
    private Context context;
    private DateFormat df2 = new SimpleDateFormat("dd MMMM yyyy");
    private NumberFormat rupiah	= NumberFormat.getNumberInstance(new Locale("in", "ID"));

    public AdpReportEkspedisiViewItem(Context context, int resource, List<ReportEkspedisiItemModel> objects) {
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
            holder.TvNo = (TextView)v.findViewById(R.id.txtColRpEkspedisiViewNoItem);
            holder.TvNamaKapal    = 	 (TextView)v.findViewById(R.id.txtColRpEkspedisiViewKapalItem);
            holder.TvKendaraan	    =	 (TextView)v.findViewById(R.id.txtColRpEkspedisiViewKendaraanItem);
            holder.TvPlatNo    = 	 (TextView)v.findViewById(R.id.txtColRpEkspedisiViewPlatNoItem);
            holder.TvPemilik	    =	 (TextView)v.findViewById(R.id.txtColRpEkspedisiViewPemilikItem);
            holder.TvSopir    = 	 (TextView)v.findViewById(R.id.txtColRpEkspedisiViewDriverItem);
            holder.TvHarga	    =	 (TextView)v.findViewById(R.id.txtColRpEkspedisiViewHargaItem);
            v.setTag(holder);
        }else{
            holder 	= (ViewHolder)v.getTag();
        }
        holder.TvNo.setText(String.valueOf(position+1)+".");
        holder.TvNamaKapal.setText(columnslist.get(position).getNamaKapal());
        holder.TvKendaraan.setText(columnslist.get(position).getNamaJenisKendaraan());
        holder.TvPlatNo.setText(columnslist.get(position).getPlatNo());
        holder.TvPemilik.setText(columnslist.get(position).getNamaPemilik());
        holder.TvSopir.setText(columnslist.get(position).getNamaSupir());
        holder.TvHarga.setText("Rp. "+rupiah.format(columnslist.get(position).getHarga()));
        return v;
    }

    static class ViewHolder{
        private TextView TvNo;
        private TextView TvNamaKapal;
        private TextView TvKendaraan;
        private TextView TvPlatNo;
        private TextView TvPemilik;
        private TextView TvSopir;
        private TextView TvHarga;
    }
}
