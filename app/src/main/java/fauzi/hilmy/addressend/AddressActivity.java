package fauzi.hilmy.addressend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fauzi.hilmy.addressend.api.ApiClient;
import fauzi.hilmy.addressend.api.ApiInterface;
import fauzi.hilmy.addressend.model.Data;
import fauzi.hilmy.addressend.model.Region;
import fauzi.hilmy.addressend.model.UniqueCode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {
    String stuff;
    @BindView(R.id.spinProv)
    Spinner spinProv;
    @BindView(R.id.spinKab)
    Spinner spinKab;
    @BindView(R.id.spinKec)
    Spinner spinKec;
    @BindView(R.id.spinKel)
    Spinner spinKel;
    @BindView(R.id.btnDone)
    Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        stuff = getIntent().getStringExtra("stuff");
        loadUniqueCode();
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddressActivity.this, stuff + " Sent", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddressActivity.this, MainActivity.class));
            }
        });
    }

    private void loadUniqueCode() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UniqueCode> call = apiService.getUniqueCode();

        call.enqueue(new Callback<UniqueCode>() {
            @Override
            public void onResponse(Call<UniqueCode> call, Response<UniqueCode> response) {
                String code = "MeP7c5ne" + response.body().getUniqueCode();
                loadProvinceList(code);
            }

            @Override
            public void onFailure(Call<UniqueCode> call, Throwable t) {

            }
        });
    }

    private void loadProvinceList(final String code) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Data> call = apiService.getProvinceList(code);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarProvinsi = response.body().getData();

                // masukkan daftar provinsi ke list string
                List<String> provs = new ArrayList<>();
                for (int i = 0; i < daftarProvinsi.size(); i++) {
                    provs.add(daftarProvinsi.get(i).getName());
                }

                final ArrayAdapter<String> adapter = new ArrayAdapter<>(AddressActivity.this,
                        android.R.layout.simple_spinner_item, provs);
                spinProv.setAdapter(adapter);
                spinProv.setPrompt("Pilih Provinsi!");
                spinProv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        long idProv = daftarProvinsi.get(position).getId();
                        loadKabupatenList(code, idProv);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    private void loadKabupatenList(final String code, long idProv) {
        spinKec.setAdapter(null);
        spinKel.setAdapter(null);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Data> call = apiService.getKabupatenList(code, idProv);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarKabupaten = response.body().getData();

                // masukkan daftar kabupaten ke list string
                List<String> kabs = new ArrayList<>();
                for (int i = 0; i < daftarKabupaten.size(); i++) {
                    kabs.add(daftarKabupaten.get(i).getName());
                }

                // masukkan daftar kabupaten ke spinner
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(AddressActivity.this,
                        android.R.layout.simple_spinner_item, kabs);
                spinKab.setAdapter(adapter);
                spinKab.setPrompt("Pilih Kabupaten/Kota!");
                spinKab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        long idKab = daftarKabupaten.get(position).getId();
                        loadKecamatanList(code, idKab);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    private void loadKecamatanList(final String code, long idKab) {
        spinKel.setAdapter(null);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Data> call = apiService.getKecamatanList(code, idKab);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarKecamatan = response.body().getData();

                // masukkan daftar kecamatan ke list string
                List<String> kecs = new ArrayList<>();
                for (int i = 0; i < daftarKecamatan.size(); i++) {
                    kecs.add(daftarKecamatan.get(i).getName());
                }

                // masukkan daftar kecamatan ke spinner
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(AddressActivity.this,
                        android.R.layout.simple_spinner_item, kecs);
                spinKec.setAdapter(adapter);
                spinKec.setPrompt("Pilih Kecamatan!!");
                spinKec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        long idKec = daftarKecamatan.get(position).getId();
                        loadKelurahanList(code, idKec);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    private void loadKelurahanList(String code, long idKec) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Data> call = apiService.getKelurahanList(code, idKec);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarKelurahan = response.body().getData();

                // masukkan daftar kelurahan ke list string
                List<String> kels = new ArrayList<>();
                for (int i = 0; i < daftarKelurahan.size(); i++) {
                    kels.add(daftarKelurahan.get(i).getName());
                }

                // masukkan daftar kelurahan ke spinner
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(AddressActivity.this,
                        android.R.layout.simple_spinner_item, kels);
                spinKel.setAdapter(adapter);
                spinKel.setPrompt("Pilih Kelurahan!!");
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }
}
