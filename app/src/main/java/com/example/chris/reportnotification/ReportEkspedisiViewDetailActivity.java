package com.example.chris.reportnotification;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.ParseError;
import com.android.volley.error.ServerError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import adapter.AdpReportEkspedisiViewBayar;
import adapter.AdpReportEkspedisiViewItem;
import model.ReportEkspedisiHeaderModel;
import model.ReportEkspedisiItemBayarModel;
import model.ReportEkspedisiItemModel;
import utilities.AppController;
import utilities.Link;
import utilities.Utils;

public class ReportEkspedisiViewDetailActivity extends AppCompatActivity {

    private ImageView imgBack;
    private TextView txtStatusBayar, txtIdTrans, txtTglTrans, txtNamaCust, txtTipeBayar, txtSubTotal,
            txtDiskon, txtTotal, txtDp, txtGrandTotal;
    private ListView lsvBayar, lsvItem;
    private LinearLayout linBayar;
    private NumberFormat rupiah	= NumberFormat.getNumberInstance(new Locale("in", "ID"));
    private AdpReportEkspedisiViewBayar adapterBayar;
    private AdpReportEkspedisiViewItem adapterItem;
    private String noTrans;
    private ProgressDialog pDialog;
    private SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
    private String getLaporanHarian	="getEkspedisiById.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_ekspedisi_view_header);
        Bundle i = getIntent().getExtras();
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        if (i != null){
            try {
                noTrans =  i.getString("nomor");
            } catch (Exception e) {
                e.getMessage();
            }
        }

        imgBack = (ImageView)findViewById(R.id.imgViewKriteriareport_header_ekspedisi_Back);
        txtStatusBayar = (TextView)findViewById(R.id.txtHeaderStatusPembayaran);
        txtIdTrans = (TextView)findViewById(R.id.txtHeaderIdTrans);
        txtTglTrans = (TextView)findViewById(R.id.txtHeaderTglTrans);
        txtNamaCust = (TextView)findViewById(R.id.txtHeaderCustTrans);
        txtTipeBayar = (TextView)findViewById(R.id.txtHeaderTipebayarTrans);
        lsvItem = (ListView)findViewById(R.id.LsvItemHeaderReport);
        linBayar = (LinearLayout)findViewById(R.id.linHeaderBayar);
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

        getDataEkspedisi(Link.BASE_URL_API+getLaporanHarian, noTrans);
    }

    private void getDataEkspedisi(String Url, final String idTrans){
        pDialog.setMessage("Loading Data....");
        showDialog();
        StringRequest register = new StringRequest(Request.Method.POST, Url, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonrespon = new JSONObject(response);
                    String message = (String) jsonrespon.get("message");
                    if(message.trim().equals("1")){
                        JSONArray JsonHeader = jsonrespon.getJSONArray("header");
                        Object object = JsonHeader.getJSONArray(0).get(0);
                        ReportEkspedisiHeaderModel header 	= new ReportEkspedisiHeaderModel();
                        JSONArray JsonItem = (JSONArray) ((JSONObject) object).get("item");
                        for (int a = 0; a <JsonItem.length(); a++) {
                            Object objItem = JsonItem.getJSONObject(a);
                            ReportEkspedisiItemModel item = new ReportEkspedisiItemModel();
                            item.setIdHeader((String)((JSONObject) objItem).get("ekspedisi_id"));
                            item.setIdDetail((Integer)((JSONObject) objItem).get("ekspedisi_detail_id"));
                            item.setIndex((Integer)((JSONObject) objItem).get("index"));
                            item.setIdKapal((Integer)((JSONObject) objItem).get("kapal_id"));
                            item.setNamaKapal((String)((JSONObject) objItem).get("kapal_name"));
                            item.setIdJenisKendaraan((Integer)((JSONObject) objItem).get("jenis_kendaraan_id"));
                            item.setNamaJenisKendaraan((String)((JSONObject) objItem).get("jenis_kendaraan_name"));
                            item.setPlatNo((String)((JSONObject) objItem).get("plat_nomor"));
                            item.setNamaPemilik((String)((JSONObject) objItem).get("pemilik_nama"));
                            item.setNamaSupir((String)((JSONObject) objItem).get("supir_nama"));
                            item.setHarga(new BigDecimal((String)((JSONObject) objItem).get("harga")));
                            item.setKet((String)((JSONObject) objItem).get("void_keterangan"));
                            header.addItem(item);
                        }

                        JSONArray JsonBayarItem = (JSONArray) ((JSONObject) object).get("itemBayar");
                        for (int a = 0; a <JsonBayarItem.length(); a++) {
                            Object objItemBayar = JsonBayarItem.getJSONObject(a);
                            ReportEkspedisiItemBayarModel itemBayar = new ReportEkspedisiItemBayarModel();
                            itemBayar.setIdBayar((String)((JSONObject) objItemBayar).get("pembayaran_id"));
                            itemBayar.setTglBayar((String)((JSONObject) objItemBayar).get("pembayaran_date"));
                            itemBayar.setIdEkspedisi((String)((JSONObject) objItemBayar).get("ekspedisi_id"));
                            itemBayar.setKeterangan((String)((JSONObject) objItemBayar).get("pembayaran_keterangan"));
                            itemBayar.setGrandtotal(new BigDecimal((String)((JSONObject) objItemBayar).get("grandtotal")));
                            itemBayar.setVoidKet((String)((JSONObject) objItemBayar).get("void_keterangan"));
                            header.addBayarItem(itemBayar);
                        }

                        header.setId((String)((JSONObject) object).get("ekspedisi_id"));
                        Date tglTrans=new SimpleDateFormat("yyyy-MM-dd").parse((String)((JSONObject) object).get("ekspedisi_tanggal"));
                        header.setTanggal((df2.format(tglTrans.getTime())));
                        header.setIdCust((String)((JSONObject) object).get("vendor_customer_id"));
                        header.setNamaCust((String)((JSONObject) object).get("vendor_customer_name"));
                        header.setTipeBayar((String)((JSONObject) object).get("tipe_pembayaran"));
                        header.setJatuhTempo((Integer)((JSONObject) object).get("jatuh_tempo"));
                        header.setStatusbayar((String)((JSONObject) object).get("status_pembayaran"));
                        header.setSubTotal(new BigDecimal((String)((JSONObject) object).get("subtotal")));
                        header.setDiskon(new BigDecimal((String)((JSONObject) object).get("diskon")));
                        header.setTotal(new BigDecimal((String)((JSONObject) object).get("total")));
                        header.setDpNominal(new BigDecimal((String)((JSONObject) object).get("dp_nominal")));
                        header.setGrandTotal(new BigDecimal((String)((JSONObject) object).get("grandtotal")));
                        header.setKeterangan((String)((JSONObject) object).get("void_keterangan"));
                        setData(header);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ReportEkspedisiViewDetailActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    hideDialog();
                }catch (Exception ex){
                    ex.printStackTrace();
                    Toast.makeText(ReportEkspedisiViewDetailActivity.this,ex.getMessage(), Toast.LENGTH_SHORT).show();
                    hideDialog();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    hideDialog();
                    Toast.makeText(ReportEkspedisiViewDetailActivity.this,"Check Koneksi Internet Anda", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    hideDialog();
                    Toast.makeText(ReportEkspedisiViewDetailActivity.this,"AuthFailureError", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    hideDialog();
                    Toast.makeText(ReportEkspedisiViewDetailActivity.this,"Check Server Error", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    hideDialog();
                    Toast.makeText(ReportEkspedisiViewDetailActivity.this,"Check Network Error", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    hideDialog();
                    Toast.makeText(ReportEkspedisiViewDetailActivity.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idTrans", idTrans);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(register);
    }

    private void setData(ReportEkspedisiHeaderModel data){
        txtStatusBayar.setText(data.getStatusbayar());
        if(data.getStatusbayar().equals("Lunas")){
            txtStatusBayar.setTextColor(Color.BLUE);
        }else if(data.getStatusbayar().equals("Belum Bayar")){
            txtStatusBayar.setTextColor(Color.RED);
        }else if(data.getStatusbayar().equals("Bayar Sebagian")){
            txtStatusBayar.setTextColor(Color.MAGENTA);
        }
        txtIdTrans.setText(data.getId());
        txtTglTrans.setText(data.getTanggal());
        txtNamaCust.setText(data.getNamaCust());
        txtTipeBayar.setText(data.getTipeBayar());

        adapterItem		= new AdpReportEkspedisiViewItem(ReportEkspedisiViewDetailActivity.this,
                R.layout.col_rp_ekspedisi_view_item, data.getItemList());
        lsvItem.setAdapter(adapterItem);
        adapterItem.notifyDataSetChanged();

        adapterBayar		= new AdpReportEkspedisiViewBayar(ReportEkspedisiViewDetailActivity.this,
                R.layout.col_rp_ekspedisi_view_bayar, data.getItemBayarList());
        lsvBayar.setAdapter(adapterBayar);
        adapterBayar.notifyDataSetChanged();

        Utils.setListViewHeightBasedOnChildren(lsvItem);
        Utils.setListViewHeightBasedOnChildren(lsvBayar);

        txtSubTotal.setText("Rp. "+rupiah.format(data.getSubTotal()));
        txtDiskon.setText("Rp. "+rupiah.format(data.getDiskon()));
        txtTotal.setText("Rp. "+rupiah.format(data.getTotal()));
        txtDp.setText("Rp. "+rupiah.format(data.getDpNominal()));
        txtGrandTotal.setText("Rp. "+rupiah.format(data.getGrandTotal()));
        hideDialog();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
