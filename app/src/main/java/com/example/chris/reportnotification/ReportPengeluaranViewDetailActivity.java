package com.example.chris.reportnotification;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import adapter.AdpReportPengeluaranViewItem;
import model.ReportPengeluaranHeaderModel;
import model.ReportPengeluaranItemModel;
import utilities.AppController;
import utilities.Link;
import utilities.Utils;

public class ReportPengeluaranViewDetailActivity extends AppCompatActivity {

    private ImageView imgBack;
    private TextView txtIdTrans, txtTglTrans, txtNamaVendor, txtTipeBayar, txtSubTotal,
            txtDiskon, txtTotal, txtDp, txtGrandTotal;
    private ListView  lsvItem;
    private NumberFormat rupiah	= NumberFormat.getNumberInstance(new Locale("in", "ID"));
    private AdpReportPengeluaranViewItem adapterItem;
    private String noTrans;
    private ProgressDialog pDialog;
    private SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
    private String getLaporanHarian	="getReportPengeluaranById.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_pengeluaran_view_header);
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
                        ReportPengeluaranHeaderModel header = new ReportPengeluaranHeaderModel();
                        JSONArray JsonHeader = jsonrespon.getJSONArray("header");
                        Object object = JsonHeader.getJSONArray(0).get(0);
                        JSONArray JsonItem = (JSONArray) ((JSONObject) object).get("item");
                        for (int a = 0; a <JsonItem.length(); a++) {
                            Object objItem = JsonItem.getJSONObject(a);
                            ReportPengeluaranItemModel item = new ReportPengeluaranItemModel();
                            item.setIdHeader((String)((JSONObject) objItem).get("pengeluaran_id"));
                            item.setIndex((Integer)((JSONObject) objItem).get("index"));
                            item.setTglShipping((String) ((JSONObject) objItem).get("shipping_date"));
                            item.setBuktiBayar((String) ((JSONObject) objItem).get("bukti_pembayaran"));
                            item.setHarga(new BigDecimal((String)((JSONObject) objItem).get("harga")));
                            item.setKet((String)((JSONObject) objItem).get("void_keterangan"));
                            header.addItem(item);
                        }

                        header.setId((String)((JSONObject) object).get("pengeluaran_id"));
                        Date tglTrans=new SimpleDateFormat("yyyy-MM-dd").parse((String)((JSONObject) object).get("pengeluaran_date"));
                        header.setTanggal((df2.format(tglTrans.getTime())));
                        header.setIdVendor((String)((JSONObject) object).get("vendor_customer_id"));
                        header.setNamaVendor((String)((JSONObject) object).get("vendor_customer_name"));
                        header.setTipeBayar((String)((JSONObject) object).get("tipe_pembayaran"));
                        header.setJatuhTempo((Integer)((JSONObject) object).get("jatuh_tempo"));
                        header.setSubTotal(new BigDecimal((String)((JSONObject) object).get("subtotal")));
                        header.setDiskon(new BigDecimal((String)((JSONObject) object).get("discount")));
                        header.setTotal(new BigDecimal((String)((JSONObject) object).get("total")));
                        header.setDpNominal(new BigDecimal((String)((JSONObject) object).get("dp_nominal")));
                        header.setGrandTotal(new BigDecimal((String)((JSONObject) object).get("grandtotal")));
                        header.setKeteranganHeader((String)((JSONObject) object).get("void_keterangan"));
                        setData(header);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ReportPengeluaranViewDetailActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    hideDialog();
                }catch (Exception ex){
                    ex.printStackTrace();
                    Toast.makeText(ReportPengeluaranViewDetailActivity.this,ex.getMessage(), Toast.LENGTH_SHORT).show();
                    hideDialog();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    hideDialog();
                    Toast.makeText(ReportPengeluaranViewDetailActivity.this,"Check Koneksi Internet Anda", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    hideDialog();
                    Toast.makeText(ReportPengeluaranViewDetailActivity.this,"AuthFailureError", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    hideDialog();
                    Toast.makeText(ReportPengeluaranViewDetailActivity.this,"Check Server Error", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    hideDialog();
                    Toast.makeText(ReportPengeluaranViewDetailActivity.this,"Check Network Error", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    hideDialog();
                    Toast.makeText(ReportPengeluaranViewDetailActivity.this,error.toString(), Toast.LENGTH_LONG).show();
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

    private void setData(ReportPengeluaranHeaderModel data){
        txtIdTrans.setText(data.getId());
        txtTglTrans.setText(data.getTanggal());
        txtNamaVendor.setText(data.getNamaVendor());
        txtTipeBayar.setText(data.getTipeBayar());

        adapterItem		= new AdpReportPengeluaranViewItem(ReportPengeluaranViewDetailActivity.this,
                R.layout.col_rp_pengeluaran_view_item, data.getItemList());
        lsvItem.setAdapter(adapterItem);
        adapterItem.notifyDataSetChanged();

        Utils.setListViewHeightBasedOnChildren(lsvItem);

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
