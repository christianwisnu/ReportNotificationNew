package adapter;

import android.content.Context;
import android.content.Intent;
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
    private DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

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

        /*holder.TvNama.setText(columnslist.get(position).getNamaPasien());
        holder.TvNotrans.setText(String.valueOf(columnslist.get(position).getNomorUrut()));*/

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
