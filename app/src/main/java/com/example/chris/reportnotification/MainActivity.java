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
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import service.BaseApiService;
import utilities.Link;
import utilities.PrefUtil;
import utilities.RequestPermissionHandler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        FirebaseMessaging.getInstance().subscribeToTopic("ADMIN");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Report App");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        invalidateOptionsMenu();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        txtEmail = (TextView)header.findViewById(R.id.txtNavHeaderEmail);
        txtNama = (TextView)header.findViewById(R.id.txtNavHeaderNama);

        txtEmail.setText(email);
        txtNama.setText(username);
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.report_pengeluaran) {

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
}
/*list vendor
list kapal
list jenis kendaraan

lap ekspedisi
	tgl from - to,
	kode cust vendor & check all (WHERE vendor/cust/all)
	tipe pembayaran (Cash, Credit, DP)
	status pembayaran (Lunas, Bayar Sebagian, Blm Bayar)

lap pengeluaran
	tgl from - to,
	kode cust vendor & check all (WHERE vendor/cust/all)
	tipe pembayaran (Cash, Credit, DP)
	*/