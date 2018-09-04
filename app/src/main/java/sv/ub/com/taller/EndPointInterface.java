package sv.ub.com.taller;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by DK-Ragnar on 2/9/2018.
 */

public interface EndPointInterface {

    String baseUrl = "http://54.212.221.115:8080/serviciorest-1.0/rest/";

    @GET("categorias/")
    Call<List<Categoria>> getCategorias();

    @GET("categorias/{id}")
    Call<Categoria> getCategoria(@Path("id") int id);

    @GET("conceptos/")
    Call<List<Concepto>> getConceptos();

    @FormUrlEncoded
    @POST("conceptos")
    Call<ResponseBody> addConcepto(
            @Field("name") String name,
            @Field("value") double value,
            @Field("category_id") int category_id);

    @FormUrlEncoded
    @POST("conceptos/{id}")
    Call<ResponseBody> addConcepto(@Path("id") int id,
           @Field("name") String name,
           @Field("value") double value,
           @Field("category_id") int category_id,
           @Field("category") Categoria categoria);


}
