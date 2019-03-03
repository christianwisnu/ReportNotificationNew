package com.example.chris.reportnotification;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import service.BaseApiService;
import utilities.Link;
import utilities.PrefUtil;
import utilities.RequestPermissionHandler;

public class MainActivity extends AppCompatActivity {//implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private AlertDialog alert;
    private BaseApiService mApiService;
    private ProgressDialog pDialog;
    private RequestPermissionHandler mRequestPermissionHandler;
    private List<String> listPermissionsNeeded;
    private PrefUtil pref;
    private SharedPreferences shared;
    private String userId, username, email;
    private TextView txtEmail, txtNama;
    private String judul, jenisTrans, noTrans;
    private ImageView imgPengeluaran, imgEkspedisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mRequestPermissionHandler = new RequestPermissionHandler();
            checkAndRequestPermissions();
            openPermission();
        }
        pref = new PrefUtil(this);
        mApiService         = Link.getAPIService();
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        try{
            shared  = pref.getUserInfo();
            userId = shared.getString(PrefUtil.ID, null);
            username = shared.getString(PrefUtil.NAME, null);
            email = shared.getString(PrefUtil.EMAIL, null);
        }catch (Exception e){e.getMessage();}

        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().subscribeToTopic("ADMIN");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Report App");
        setSupportActionBar(toolbar);

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        invalidateOptionsMenu();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);*/
        txtEmail = (TextView)findViewById(R.id.txtEmailUserLogin);
        txtNama = (TextView)findViewById(R.id.txtNamaUserLogin);
        imgPengeluaran = (ImageView)findViewById(R.id.imgMenuReportPengeluaran);
        imgEkspedisi = (ImageView) findViewById(R.id.imgMenuReportEkspedisi);

        txtEmail.setText("Email: "+email);
        txtNama.setText("Name: "+username);

        Bundle i = getIntent().getExtras();
        if (i != null){
            try {
                judul =  i.getString("isi");
            } catch (Exception e) {
                e.getMessage();
            }
        }
        if(judul!=null){
            String[] a = judul.split("\\|");
            jenisTrans = a[0];
            noTrans = a[1];
            Intent intent;
            if(jenisTrans.equals("EKSPEDISI")){
                intent = new Intent(this, ReportEkspedisiViewDetailActivity.class);
                intent.putExtra("nomor", noTrans);
                startActivity(intent);
            }else if(jenisTrans.equals("PENGELUARAN")){//
                intent = new Intent(this, ReportPengeluaranViewDetailActivity.class);
                intent.putExtra("nomor", noTrans);
                startActivity(intent);
            }else{
                Toast.makeText(MainActivity.this, "FORMAT NOTIFIKASI SALAH", Toast.LENGTH_LONG).show();
            }
        }

        imgPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ReportPengeluaranActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        imgEkspedisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ReportEkspedisiActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    private void checkAndRequestPermissions() {
        int writeExtStorage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readExtStorage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        listPermissionsNeeded = new ArrayList<>();
        if (writeExtStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (readExtStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void openPermission(){
        if (!listPermissionsNeeded.isEmpty()) {
            mRequestPermissionHandler.requestPermission(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    123, new RequestPermissionHandler.RequestPermissionListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(MainActivity.this, "Request permission success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed() {
                            Toast.makeText(MainActivity.this, "Request permission failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("ADMIN");
            pref.clear();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.report_pengeluaran) {
            Intent i = new Intent(MainActivity.this, ReportPengeluaranActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else if (id == R.id.report_ekspedisi) {
            Intent i = new Intent(MainActivity.this, ReportEkspedisiActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else if (id == R.id.logout) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("ADMIN");
            pref.clear();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    https://www.androidhive.info/2012/10/android-push-notifications-using-google-cloud-messaging-gcm-php-and-mysql/*/
}