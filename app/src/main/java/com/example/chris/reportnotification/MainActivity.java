package com.example.chris.reportnotification;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import service.BaseApiService;
import utilities.Link;
import utilities.RequestPermissionHandler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private AlertDialog alert;
    private BaseApiService mApiService;
    private ProgressDialog pDialog;
    private RequestPermissionHandler mRequestPermissionHandler;
    private List<String> listPermissionsNeeded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mRequestPermissionHandler = new RequestPermissionHandler();
            checkAndRequestPermissions();
            openPermission();
        }
        mApiService         = Link.getAPIService();
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        FirebaseMessaging.getInstance().subscribeToTopic("ADMIN");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            // Handle the camera action
        } else if (id == R.id.report_ekspedisi) {

        } else if (id == R.id.logout) {

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
	kode cust vendor & check all
	tipe pembayaran (Cash, Credit, DP)
	status pembayaran (Lunas, Bayar Sebagian, Blm Bayar)

lap pengeluaran
	tgl from - to,
	kode cust vendor & check all
	tipe pembayaran (Cash, Credit, DP)
	*/