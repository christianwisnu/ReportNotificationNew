package service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import utilities.JSONResponse;

/**
 * Created by christian on 14/02/18.
 */

public interface BaseApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String pasw);

    @FormUrlEncoded
    @POST("listVendor.php")
    Call<JSONResponse> getListVendor(@Field("jenis") String jenis);

    @FormUrlEncoded
    @POST("listCustomer.php")
    Call<JSONResponse> getListCust(@Field("jenis") String jenis);

    @FormUrlEncoded
    @POST("listKapal.php")
    Call<JSONResponse> getListKapal(@Field("pemilik") String idPemilik);

    @FormUrlEncoded
    @POST("listJnsKendaraan.php")
    Call<JSONResponse> getListJnsKndr(@Field("pemilik") String idPemilik);
}