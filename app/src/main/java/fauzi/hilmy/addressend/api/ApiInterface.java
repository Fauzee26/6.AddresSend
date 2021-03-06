package fauzi.hilmy.addressend.api;

import fauzi.hilmy.addressend.model.Data;
import fauzi.hilmy.addressend.model.UniqueCode;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("poe")
    Call<UniqueCode> getUniqueCode();

    @GET("{code}/m/wilayah/provinsi")
    Call<Data> getProvinceList(@Path("code") String code);

    @GET("{code}/m/wilayah/kabupaten")
    Call<Data> getKabupatenList(@Path("code") String code, @Query("idpropinsi") long idProv);

    @GET("{code}/m/wilayah/kecamatan")
    Call<Data> getKecamatanList(@Path("code") String code, @Query("idkabupaten") long idKab);

    @GET("{code}/m/wilayah/kelurahan")
    Call<Data> getKelurahanList(@Path("code") String code, @Query("idkecamatan") long idKec);
}
