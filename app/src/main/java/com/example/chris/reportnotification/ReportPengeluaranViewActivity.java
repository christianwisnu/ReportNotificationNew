package com.example.chris.reportnotification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import adapter.AdpReportPengeluaranViewItem;
import model.ReportPengeluaranHeaderModel;
import utilities.Utils;

public class ReportPengeluaranViewActivity extends AppCompatActivity {

    private ImageView imgBack;
    private TextView txtIdTrans, txtTglTrans, txtNamaVendor, txtTipeBayar, txtSubTotal,
            txtDiskon, txtTotal, txtDp, txtGrandTotal;
    private ListView lsvItem;
    private ReportPengeluaranHeaderModel headerModel;
    private NumberFormat rupiah	= NumberFormat.getNumberInstance(new Locale("in", "ID"));
    private AdpReportPengeluaranViewItem adapterItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_pengeluaran_view_header);
        Intent i = getIntent();
        headerModel = (ReportPengeluaranHeaderModel) i.getSerializableExtra("headerModel");
        imgBack = (ImageView)findViewById(R.id.imgViewKriteriareport_header_pengeluaran_Back);
        txtIdTrans = (TextView)findViewById(R.id.txtHeaderpengeluaranIdTrans);
        txtTglTrans = (TextView)findViewById(R.id.txtHeaderpengeluaranTglTrans);
        txtNamaVendor = (TextView)findViewById(R.id.txtHeaderpengeluaranVendorTrans);
        txtTipeBayar = (TextView)findViewById(R.id.txtHeaderpengeluaranTipebayarTrans);
        lsvItem = (ListView)findViewById(R.id.LsvItempengeluaranHeaderReport);
        txtSubTotal = (TextView)findViewById(R.id.txtHeaderpengeluaranSubTotalTrans);
        txtDiskon = (TextView)findViewById(R.id.txtHeaderpengeluaranDiskonTrans);
        txtTotal = (TextView)findViewById(R.id.txtHeaderpengeluaranTotalTrans);
        txtDp = (TextView)findViewById(R.id.txtHeaderpengeluaranDPTrans);
        txtGrandTotal = (TextView)findViewById(R.id.txtHeaderpengeluaranGtotTrans);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtIdTrans.setText(headerModel.getId());
        txtTglTrans.setText(headerModel.getTanggal());
        txtNamaVendor.setText(headerModel.getNamaVendor());
        txtTipeBayar.setText(headerModel.getTipeBayar());

        adapterItem		= new AdpReportPengeluaranViewItem(ReportPengeluaranViewActivity.this,
                R.layout.col_rp_pengeluaran_view_item, headerModel.getItemList());
        lsvItem.setAdapter(adapterItem);
        adapterItem.notifyDataSetChanged();

        Utils.setListViewHeightBasedOnChildren(lsvItem);

        txtSubTotal.setText("Rp. "+rupiah.format(headerModel.getSubTotal()));
        txtDiskon.setText("Rp. "+rupiah.format(headerModel.getDiskon()));
        txtTotal.setText("Rp. "+rupiah.format(headerModel.getTotal()));
        txtDp.setText("Rp. "+rupiah.format(headerModel.getDpNominal()));
        txtGrandTotal.setText("Rp. "+rupiah.format(headerModel.getGrandTotal()));
    }
}
