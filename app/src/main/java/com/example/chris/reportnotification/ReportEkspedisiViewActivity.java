package com.example.chris.reportnotification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import model.ReportEkspedisiModel;

public class ReportEkspedisiViewActivity extends AppCompatActivity {

    private ImageView imgBack;
    private ListView lsvdata;
    private ReportEkspedisiModel model2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_ekspedisi_view_header);
        model2 = new ReportEkspedisiModel();
        Intent i = getIntent();
        model2 = (ReportEkspedisiModel) i.getSerializableExtra("model");

        imgBack = (ImageView)findViewById(R.id.imgViewKriteriareport_header_ekspedisi_Back);
        lsvdata = (ListView)findViewById(R.id.LsvReportEkspedisiHeader);

        /*adapter		= new AdpTransaksi2(ReportEkspedisiViewActivity.this, R.layout.col_transaksi2, model2.getHeaderList());
        lsvdata.setAdapter(adapter);*/

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lsvdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
