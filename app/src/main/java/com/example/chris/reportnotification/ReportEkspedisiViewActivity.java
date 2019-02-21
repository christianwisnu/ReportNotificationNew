package com.example.chris.reportnotification;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import adapter.AdpReportEkspedisiViewBayar;
import adapter.AdpReportEkspedisiViewItem;
import model.ReportEkspedisiHeaderModel;
import utilities.Utils;

public class ReportEkspedisiViewActivity extends AppCompatActivity {

    private ImageView imgBack;
    private TextView txtStatusBayar, txtIdTrans, txtTglTrans, txtNamaCust, txtTipeBayar, txtSubTotal,
    txtDiskon, txtTotal, txtDp, txtGrandTotal;
    private ListView lsvBayar, lsvItem;
    private ReportEkspedisiHeaderModel headerModel;
    private LinearLayout linBayar;
    private FrameLayout frmBayar;
    private NumberFormat rupiah	= NumberFormat.getNumberInstance(new Locale("in", "ID"));
    private AdpReportEkspedisiViewBayar adapterBayar;
    private AdpReportEkspedisiViewItem adapterItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_ekspedisi_view_header);
        Intent i = getIntent();
        headerModel = (ReportEkspedisiHeaderModel) i.getSerializableExtra("headerModel");
        imgBack = (ImageView)findViewById(R.id.imgViewKriteriareport_header_ekspedisi_Back);
        txtStatusBayar = (TextView)findViewById(R.id.txtHeaderStatusPembayaran);
        txtIdTrans = (TextView)findViewById(R.id.txtHeaderIdTrans);
        txtTglTrans = (TextView)findViewById(R.id.txtHeaderTglTrans);
        txtNamaCust = (TextView)findViewById(R.id.txtHeaderCustTrans);
        txtTipeBayar = (TextView)findViewById(R.id.txtHeaderTipebayarTrans);
        lsvItem = (ListView)findViewById(R.id.LsvItemHeaderReport);
        linBayar = (LinearLayout)findViewById(R.id.linHeaderBayar);
        //frmBayar = (FrameLayout)findViewById(R.id.frmHeaderItemBayar);
        lsvBayar = (ListView)findViewById(R.id.LsvItemBayarHeaderReport);
        txtSubTotal = (TextView)findViewById(R.id.txtHeaderSubTotalTrans);
        txtDiskon = (TextView)findViewById(R.id.txtHeaderDiskonTrans);
        txtTotal = (TextView)findViewById(R.id.txtHeaderTotalTrans);
        txtDp = (TextView)findViewById(R.id.txtHeaderDPTrans);
        txtGrandTotal = (TextView)findViewById(R.id.txtHeaderGtotTrans);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtStatusBayar.setText(headerModel.getStatusbayar());
        if(headerModel.getStatusbayar().equals("Lunas")){
            txtStatusBayar.setTextColor(Color.BLUE);
        }else if(headerModel.getStatusbayar().equals("Belum Bayar")){
            txtStatusBayar.setTextColor(Color.RED);
        }else if(headerModel.getStatusbayar().equals("Bayar Sebagian")){
            txtStatusBayar.setTextColor(Color.MAGENTA);
        }
        txtIdTrans.setText(headerModel.getId());
        txtTglTrans.setText(headerModel.getTanggal());
        txtNamaCust.setText(headerModel.getNamaCust());
        txtTipeBayar.setText(headerModel.getTipeBayar());

        adapterItem		= new AdpReportEkspedisiViewItem(ReportEkspedisiViewActivity.this,
                R.layout.col_rp_ekspedisi_view_item, headerModel.getItemList());
        lsvItem.setAdapter(adapterItem);
        adapterItem.notifyDataSetChanged();

        adapterBayar		= new AdpReportEkspedisiViewBayar(ReportEkspedisiViewActivity.this,
                R.layout.col_rp_ekspedisi_view_bayar, headerModel.getItemBayarList());
        lsvBayar.setAdapter(adapterBayar);
        adapterBayar.notifyDataSetChanged();

        Utils.setListViewHeightBasedOnChildren(lsvItem);
        Utils.setListViewHeightBasedOnChildren(lsvBayar);

        txtSubTotal.setText("Rp. "+rupiah.format(headerModel.getSubTotal()));
        txtDiskon.setText("Rp. "+rupiah.format(headerModel.getDiskon()));
        txtTotal.setText("Rp. "+rupiah.format(headerModel.getTotal()));
        txtDp.setText("Rp. "+rupiah.format(headerModel.getDpNominal()));
        txtGrandTotal.setText("Rp. "+rupiah.format(headerModel.getGrandTotal()));
    }
}
