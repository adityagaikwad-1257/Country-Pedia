package com.adi.countrypedia.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adi.countrypedia.R;
import com.adi.countrypedia.adapters.CountryAdapter;
import com.adi.countrypedia.db.Converters;
import com.adi.countrypedia.models.Country;
import com.adi.countrypedia.receivers.ConnectivityReceiver;
import com.adi.countrypedia.utils.Utils;
import com.adi.countrypedia.viewmodels.CountryViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "aditya";
    CountryViewModel viewModel;
    private final ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
    CountryAdapter countryAdapter;
    RecyclerView recyclerView;
    List<Country> countryList;
    SearchView searchView;

    TextView errorTextView;
    ImageView errorImage;
    ImageView deleteErrorImg;
    ProgressBar progressBar;

    private boolean deleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(CountryViewModel.class);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        errorTextView = findViewById(R.id.error_text_veiw);
        errorTextView.setVisibility(View.GONE);
        errorImage = findViewById(R.id.error_image);
        errorImage.setVisibility(View.GONE);
        deleteErrorImg = findViewById(R.id.delete_error_image);
        deleteErrorImg.setVisibility(View.GONE);

        countryAdapter = new CountryAdapter(this);
        recyclerView.setAdapter(countryAdapter);
        countryList = new ArrayList<>();

        viewModel.getCountryList().observe(this, countries -> {
                    Log.d(TAG, "Database changed " + countries.size());
                    countryList = countries;
                    countryAdapter.submitList(countries);
                    if (countries.size() > 0) {
                        Toast.makeText(MainActivity.this, "Data refreshed", Toast.LENGTH_SHORT).show();
                        updateError("", 0, View.GONE);
                    }else if (!deleted){
                        Toast.makeText(MainActivity.this, "couldn't connect", Toast.LENGTH_SHORT).show();
                        String error = "Couldn't connect! please check your internet connection";
                        updateError(error, R.drawable.ic_no_network, View.VISIBLE);
                    }
                    progressBar.setVisibility(View.GONE);
        }
        );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            viewModel.deleteAll();
            deleted = true;
            deleteDataUpdate();
        }
        if (item.getItemId() == R.id.refresh){
            if (Utils.isOnline(this)) {
                refresh();
            }else {
                Log.d(TAG, "onOptionsItemSelected: ");
                updateError("Couldn't connect! please check your internet connection", R.drawable.ic_no_network, View.VISIBLE);
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.search_icon);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint("name capital language region");

        searchView.setOnCloseListener(() -> {
            countryAdapter.submitList(countryList);
            recyclerView.smoothScrollToPosition(0);
            if (!deleted)
                updateError("", 0, View.GONE);
            return false;
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                countryAdapter.submitList(filter(newText.trim().toLowerCase()));
                recyclerView.smoothScrollToPosition(0);
                return false;
            }
        });

        return true;
    }

    public List<Country> filter(String s){
        if (s.trim().matches("")){
            if (!deleted)
                updateError("", 0, View.GONE);
            return countryList;
        }

        List<Country> searchList = new ArrayList<>();
        for (Country country: countryList){
            if (country.getName().getCommon().toLowerCase().contains(s))
                searchList.add(country);
            else if (Converters.getCapital(country.getCapital()).toLowerCase().contains(s))
                searchList.add(country);
            else if (Converters.getFromHash(country.getLanguages()).toLowerCase().contains(s))
                searchList.add(country);
            else if (country.getSubregion().toLowerCase().contains(s))
                searchList.add(country);
        }

        if (searchList.size() == 0 && !deleted)
            updateError("unable to find a search", R.drawable.ic_exclaimation, View.VISIBLE);
        else if (!deleted)
            updateError("", 0, View.GONE);

        return searchList;
    }

    private void updateError(String errorMsg, int errorImageRes, int visibility){
        deleteErrorImg.setVisibility(View.GONE);
        errorImage.setVisibility(visibility);
        errorImage.setImageResource(errorImageRes);
        errorTextView.setVisibility(visibility);
        errorTextView.setText(errorMsg);
    }

    public void deleteDataUpdate(){
        errorImage.setVisibility(View.GONE);
        deleteErrorImg.setVisibility(View.VISIBLE);
        deleteErrorImg.setImageResource(R.drawable.ic_refresh);
        String error = "No data available, Refresh";
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(error);
    }

    public void refresh(){
        deleteErrorImg.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        connectivityReceiver.onReceive(this, new Intent());
        deleted = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(connectivityReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectivityReceiver);
    }
}