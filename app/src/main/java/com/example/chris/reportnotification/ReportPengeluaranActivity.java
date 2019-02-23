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

import adapter.AdpPengeluaranViewHeader;
import list.ListVendor;
import model.ReportPengeluaranHeaderModel;
import model.ReportPengeluaranItemModel;
import model.ReportPengeluaranModel;
import utilities.AppController;
import utilities.Link;

public class ReportPengeluaranActivity extends AppCompatActivity {

    private EditText edTglFrom, edTglTo, edVendor;
    private ImageView imgBack;
    private Spinner spTipebayar;
    private Button btnProses;
    private CheckBox ckAllVendor;
    private ListView lsvData;
    private TextView txtStatus;
    private boolean allVendor = false;
    private Date tglFrom, tglTo;
    private Calendar dateAndTime = Calendar.getInstance();
    private SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private ProgressDialog pDialog;
    private ReportPengeluaranModel model2;
    private String idVendor;
    private int RESULT_VENDOR = 2;
    private AdpPengeluaranViewHeader adapter;
    private ArrayList<ReportPengeluaranHeaderModel> columnlist= new ArrayList<ReportPengeluaranHeaderModel>();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_pengeluaran_kriteria);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        edTglFrom = (EditText)findViewById(R.id.eAddViewKriteriareport_pengeluaran_tglfrom);
        edTglTo = (EditText)findViewById(R.id.eAddViewKriteriareport_pengeluaran_tglto);
        edVendor = (EditText)findViewById(R.id.eAddViewKriteriareport_pengeluaran_vendor);
        imgBack = (ImageView) findViewById(R.id.imgViewKriteriareport_pengeluaran_Back);
        spTipebayar = (Spinner)findViewById(R.id.spViewKriteriareport_pengeluaran_vendor);
        btnProses = (Button)findViewById(R.id.btnViewKriteriaPengeluaranProses);
        ckAllVendor = (CheckBox)findViewById(R.id.ckAllVendor);
        lsvData = (ListView)findViewById(R.id.LsvReportPengeluaranKriteria);
        txtStatus = (TextView)findViewById(R.id.TvStatusReportPengeluaranKriteria);
        model2 = new ReportPengeluaranModel();

        edTglFrom.setFocusable(false);
        edTglTo.setFocusable(false);
        edVendor.setFocusable(false);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!allVendor){
                    edVendor.setEnabled(false);
                    hideKeyboard(v);
                    pilihVendor();
                    edVendor.setEnabled(true);
                }
            }
        });

        edVendor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(!allVendor){
                        edVendor.setEnabled(false);
                        hideKeyboard(v);
                        pilihVendor();
                        edVendor.setEnabled(true);
                    }
                }
            }
        });

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validatetanggalFrom() && validatetanggalTo() && validatePeriodeTgl() && validateVendor()){
                    getData(Link.BASE_URL_API+"getReportPengeluaran.php", ckAllVendor.isChecked()?"%":idVendor,
                            String.valueOf(spTipebayar.getSelectedItem()).equals("Semua")?"%":String.valueOf(spTipebayar.getSelectedItem()),
                            df.format(tglFrom), df.format(tglTo));
                }
            }
        });

        lsvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReportPengeluaranHeaderModel headerModel = columnlist.get(position);
                Intent i = new Intent(ReportPengeluaranActivity.this, ReportPengeluaranViewActivity.class);
                i.putExtra("headerModel", headerModel);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        ckAllVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allVendor){
                    allVendor=false;
                    edVendor.setEnabled(true);
                }else{
                    allVendor=true;
                    edVendor.setText(null);
                    edVendor.setEnabled(false);
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

    private void getData(String Url, final String vendor, final String tipebayar,
                         final String stglFrom, final String stglTo){
        model2 = new ReportPengeluaranModel();
        columnlist= new ArrayList<ReportPengeluaranHeaderModel>();
        pDialog.setMessage("Loading....");
        showDialog();
        StringRequest register = new StringRequest(Request.Method.POST, Url, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonrespon = new JSONObject(response);
                    String message = (String) jsonrespon.get("message");
                    if(message.trim().equals("1")){
                        JSONArray JsonHeader = jsonrespon.getJSONArray("header");
                        for (int i = 0; i <JsonHeader.getJSONArray(0).length(); i++) {
                            Object object = JsonHeader.getJSONArray(0).get(i);
                            ReportPengeluaranHeaderModel header 	= new ReportPengeluaranHeaderModel();

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
                            columnlist.add(header);
                        }
                        adapter		= new AdpPengeluaranViewHeader(ReportPengeluaranActivity.this, R.layout.col_pengeluaran_header, columnlist);
                        lsvData.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        hideDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ReportPengeluaranActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    hideDialog();
                }catch (Exception ex){
                    ex.printStackTrace();
                    Toast.makeText(ReportPengeluaranActivity.this,ex.getMessage(), Toast.LENGTH_SHORT).show();
                    hideDialog();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    hideDialog();
                    Toast.makeText(ReportPengeluaranActivity.this,"Check Koneksi Internet Anda", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    hideDialog();
                    Toast.makeText(ReportPengeluaranActivity.this,"AuthFailureError", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    hideDialog();
                    Toast.makeText(ReportPengeluaranActivity.this,"Check Server Error", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    hideDialog();
                    Toast.makeText(ReportPengeluaranActivity.this,"Check Network Error", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    hideDialog();
                    Toast.makeText(ReportPengeluaranActivity.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("vendor", vendor);
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

    private void pilihVendor(){
        Intent i = new Intent(ReportPengeluaranActivity.this, ListVendor.class);
        startActivityForResult(i, RESULT_VENDOR);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void settingTanggalFrom() {
        new DatePickerDialog(ReportPengeluaranActivity.this, dFrom, dateAndTime.get(Calendar.YEAR),dateAndTime.get(Calendar.MONTH),
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
        new DatePickerDialog(ReportPengeluaranActivity.this, dTo, dateAndTime.get(Calendar.YEAR),dateAndTime.get(Calendar.MONTH),
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

    private boolean validateVendor() {
        boolean value;
        if (edVendor.getText().toString().isEmpty() && !ckAllVendor.isChecked()){
            value=false;
            Toast.makeText(ReportPengeluaranActivity.this,"Vendor harap dipilih!", Toast.LENGTH_LONG).show();
        } else {
            value=true;
        }
        return value;
    }

    private boolean validatePeriodeTgl() {
        boolean value;
        if (tglFrom.compareTo(tglTo)>0){
            value=false;
            Toast.makeText(ReportPengeluaranActivity.this,"Format Tanggal salah", Toast.LENGTH_LONG).show();
        } else {
            value=true;
        }
        return value;
    }

    private boolean validatetanggalFrom() {
        boolean value;
        if (edTglFrom.getText().toString().isEmpty()){
            value=false;
            Toast.makeText(ReportPengeluaranActivity.this,"Tanggal Awal harus diisi !", Toast.LENGTH_LONG).show();
        } else {
            value=true;
        }
        return value;
    }

    private boolean validatetanggalTo() {
        boolean value;
        if (edTglTo.getText().toString().isEmpty()){
            value=false;
            Toast.makeText(ReportPengeluaranActivity.this,"Tanggal Akhir harus diisi !", Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_VENDOR) {
            if(resultCode == RESULT_OK) {
                idVendor = data.getStringExtra("kode");
                edVendor.setText(data.getStringExtra("nama"));
            }
        }
    }
}
