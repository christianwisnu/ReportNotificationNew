package com.example.chris.reportnotification;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import adapter.AdpEkspedisiViewHeader;
import list.ListCustomer;
import model.ReportEkspedisiHeaderModel;
import model.ReportEkspedisiItemBayarModel;
import model.ReportEkspedisiItemModel;
import model.ReportEkspedisiModel;
import utilities.AppController;
import utilities.Link;

public class ReportEkspedisiActivity extends AppCompatActivity {

    private EditText edTglFrom, edTglTo, edCust;
    private ImageView imgBack;
    private Spinner spTipebayar;
    private Button btnProses;
    private CheckBox ckAllCust;
    private ListView lsvData;
    private TextView txtStatus;
    private boolean allCust = false;
    private Date tglFrom, tglTo;
    private Calendar dateAndTime = Calendar.getInstance();
    private SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private ProgressDialog pDialog;
    private ReportEkspedisiModel model2;
    private String idCust;
    private int RESULT_CUST = 2;
    private AdpEkspedisiViewHeader adapter;
    private ArrayList<ReportEkspedisiHeaderModel> columnlist= new ArrayList<ReportEkspedisiHeaderModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_ekspedisi_kriteria);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        edTglFrom = (EditText)findViewById(R.id.eAddViewKriteriareport_eksepedisi_tglfrom);
        edTglTo = (EditText)findViewById(R.id.eAddViewKriteriareport_eksepedisi_tglto);
        edCust = (EditText)findViewById(R.id.eAddViewKriteriareport_eksepedisi_cust);
        imgBack = (ImageView) findViewById(R.id.imgViewKriteriareport_ekspedisi_Back);
        spTipebayar = (Spinner)findViewById(R.id.spViewKriteriareport_eksepedisi_cust);
        btnProses = (Button)findViewById(R.id.btnViewKriteriaEkspedisiProses);
        ckAllCust = (CheckBox)findViewById(R.id.ckAllCust);
        lsvData = (ListView)findViewById(R.id.LsvReportKriteria);
        txtStatus = (TextView)findViewById(R.id.TvStatusReportKriteria);
        model2 = new ReportEkspedisiModel();

        edTglFrom.setFocusable(false);
        edTglTo.setFocusable(false);
        edCust.setFocusable(false);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!allCust){
                    edCust.setEnabled(false);
                    hideKeyboard(v);
                    pilihCust();
                    edCust.setEnabled(true);
                }
            }
        });

        edCust.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(!allCust){
                        edCust.setEnabled(false);
                        hideKeyboard(v);
                        pilihCust();
                        edCust.setEnabled(true);
                    }
                }
            }
        });

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validatetanggalFrom() && validatetanggalTo() && validatePeriodeTgl() && validateCust()){
                    getData(Link.BASE_URL_API+"getModel2.php", ckAllCust.isChecked()?"%":idCust,
                            String.valueOf(spTipebayar.getSelectedItem()).equals("Semua")?"%":String.valueOf(spTipebayar.getSelectedItem()),
                            df.format(tglFrom), df.format(tglTo));
                }
            }
        });

        lsvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReportEkspedisiHeaderModel headerModel = columnlist.get(position);
                Intent i = new Intent(ReportEkspedisiActivity.this, ReportEkspedisiViewActivity.class);
                i.putExtra("headerModel", headerModel);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        ckAllCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allCust){
                    allCust=false;
                    edCust.setEnabled(true);
                }else{
                    allCust=true;
                    edCust.setText(null);
                    edCust.setEnabled(false);
                }
            }
        });

        edTglFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edTglFrom.setEnabled(false);
                hideKeyboard(v);
                settingTanggalFrom();
                edTglFrom.setEnabled(true);
            }
        });

        edTglFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edTglFrom.setEnabled(false);
                    hideKeyboard(v);
                    settingTanggalFrom();
                    edTglFrom.setEnabled(true);
                }
            }
        });

        edTglTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edTglTo.setEnabled(false);
                hideKeyboard(v);
                settingTanggalTo();
                edTglTo.setEnabled(true);
            }
        });

        edTglTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edTglTo.setEnabled(false);
                    hideKeyboard(v);
                    settingTanggalTo();
                    edTglTo.setEnabled(true);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_CUST) {
            if(resultCode == RESULT_OK) {
                idCust = data.getStringExtra("kode");
                edCust.setText(data.getStringExtra("nama"));
            }
        }
    }

    private void pilihCust(){
        Intent i = new Intent(ReportEkspedisiActivity.this, ListCustomer.class);
        startActivityForResult(i, RESULT_CUST);
    }

    private void getData(String Url, final String customer, final String tipebayar,
                         final String stglFrom, final String stglTo){
        model2 = new ReportEkspedisiModel();
        columnlist= new ArrayList<ReportEkspedisiHeaderModel>();
        pDialog.setMessage("Loading....");
        showDialog();
        StringRequest register = new StringRequest(Request.Method.POST, Url, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonrespon = new JSONObject(response);
                    String message = (String) jsonrespon.get("message");
                    if(message.trim().equals("1")){
                        if(jsonrespon.getJSONArray("header").isNull(0)){
                            Toast.makeText(ReportEkspedisiActivity.this,"TIDAK ADA DATA", Toast.LENGTH_LONG).show();
                        }else{
                            JSONArray JsonHeader = jsonrespon.getJSONArray("header");
                            for (int i = 0; i <JsonHeader.getJSONArray(0).length(); i++) {
                                Object object = JsonHeader.getJSONArray(0).get(i);
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
                                columnlist.add(header);
                            }
                            adapter		= new AdpEkspedisiViewHeader(ReportEkspedisiActivity.this, R.layout.col_ekspedisi_header, columnlist);
                            lsvData.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                        hideDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ReportEkspedisiActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    hideDialog();
                }catch (Exception ex){
                    ex.printStackTrace();
                    Toast.makeText(ReportEkspedisiActivity.this,ex.getMessage(), Toast.LENGTH_SHORT).show();
                    hideDialog();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    hideDialog();
                    Toast.makeText(ReportEkspedisiActivity.this,"Check Koneksi Internet Anda", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    hideDialog();
                    Toast.makeText(ReportEkspedisiActivity.this,"AuthFailureError", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    hideDialog();
                    Toast.makeText(ReportEkspedisiActivity.this,"Check Server Error", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    hideDialog();
                    Toast.makeText(ReportEkspedisiActivity.this,"Check Network Error", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    hideDialog();
                    Toast.makeText(ReportEkspedisiActivity.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("customer", customer);
                params.put("tglFrom", stglFrom);
                params.put("tglTo", stglTo);
                params.put("tipebayar", tipebayar);
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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void settingTanggalFrom() {
        new DatePickerDialog(ReportEkspedisiActivity.this, dFrom, dateAndTime.get(Calendar.YEAR),dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    private DatePickerDialog.OnDateSetListener dFrom =new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, month);
            dateAndTime.set(Calendar.DAY_OF_MONTH, day);
            updatelabel("FROM");
        }
    };

    private void settingTanggalTo() {
        new DatePickerDialog(ReportEkspedisiActivity.this, dTo, dateAndTime.get(Calendar.YEAR),dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    private DatePickerDialog.OnDateSetListener dTo =new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, month);
            dateAndTime.set(Calendar.DAY_OF_MONTH, day);
            updatelabel("TO");
        }
    };

    private void updatelabel(String tanggal){
        if(tanggal.equals("FROM")){
            edTglFrom.setText(df2.format(dateAndTime.getTime()));
            tglFrom = dateAndTime.getTime();
        }else{
            edTglTo.setText(df2.format(dateAndTime.getTime()));
            tglTo = dateAndTime.getTime();
        }
    }

    private boolean validateCust() {
        boolean value;
        if (edCust.getText().toString().isEmpty() && !ckAllCust.isChecked()){
            value=false;
            Toast.makeText(ReportEkspedisiActivity.this,"Customer harap dipilih!", Toast.LENGTH_LONG).show();
        } else {
            value=true;
        }
        return value;
    }

    private boolean validatePeriodeTgl() {
        boolean value;
        if (tglFrom.compareTo(tglTo)>0){
            value=false;
            Toast.makeText(ReportEkspedisiActivity.this,"Format Tanggal salah", Toast.LENGTH_LONG).show();
        } else {
            value=true;
        }
        return value;
    }

    private boolean validatetanggalFrom() {
        boolean value;
        if (edTglFrom.getText().toString().isEmpty()){
            value=false;
            Toast.makeText(ReportEkspedisiActivity.this,"Tanggal Awal harus diisi !", Toast.LENGTH_LONG).show();
        } else {
            value=true;
        }
        return value;
    }

    private boolean validatetanggalTo() {
        boolean value;
        if (edTglTo.getText().toString().isEmpty()){
            value=false;
            Toast.makeText(ReportEkspedisiActivity.this,"Tanggal Akhir harus diisi !", Toast.LENGTH_LONG).show();
        } else {
            value=true;
        }
        return value;
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
