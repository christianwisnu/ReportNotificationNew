package list;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chris.reportnotification.R;

import java.util.ArrayList;
import java.util.Arrays;

import adapter.AdpListCustomer;
import model.ListCustomerModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.BaseApiService;
import utilities.JSONResponse;
import utilities.Link;

public class ListCustomer extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<ListCustomerModel> mArrayList;
    private ArrayList<ListCustomerModel> mFilteredList;
    private AdpListCustomer mAdapter;
    private ProgressDialog progress;
    private BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_cust);
        Intent i = getIntent();
        mApiService         = Link.getAPIService();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarListCust);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("List Customer");
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();
        initViews();
        loadJSON();
    }

    private void initViews(){
        mRecyclerView = (RecyclerView)findViewById(R.id.card_recycler_view_list_cust);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void loadJSON(){
        mApiService.getListCust("Customer").enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getListcust()));
                if(mArrayList.isEmpty()){
                    Toast.makeText(ListCustomer.this, "Tidak Ada Data!", Toast.LENGTH_LONG).show();
                }
                mAdapter = new AdpListCustomer(ListCustomer.this, mArrayList);
                mRecyclerView.setAdapter(mAdapter);
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                Toast.makeText(ListCustomer.this, "Jaringan Error!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search.expandActionView();
        searchView.requestFocus();
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
