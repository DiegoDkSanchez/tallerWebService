package sv.ub.com.taller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormAddConcepto extends AppCompatActivity {


    MaterialBetterSpinner spinner;
    List<String> categorias = new ArrayList<>();
    private List<Categoria> listaCategorias;
    private EditText txtName, txtValue;
    private Button save;
    private int selectitem = 1;
    private Concepto parametro;
    private int id;

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://54.212.221.115:8080/serviciorest-1.0/rest/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_add_concepto);

        parametro = (Concepto) getIntent().getSerializableExtra("concepto");

        spinner = (MaterialBetterSpinner) findViewById(R.id.selectCategoria);
        save = (Button) findViewById(R.id.save);
        txtName = (EditText) findViewById(R.id.insertname);
        txtValue = (EditText) findViewById(R.id.insertvalue);

        if(parametro != null){

        }

        final EndPointInterface apiService = retrofit.create(EndPointInterface.class);
        Call<List<Categoria>> callCategorias = apiService.getCategorias();
        callCategorias.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                listaCategorias = response.body();
                for(Categoria tmpCategoria : listaCategorias){
                    categorias.add(tmpCategoria.name);
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Log.d("UDB", t.getMessage());
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, categorias);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectitem = position;
                Log.d("UDB",selectitem+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormAddConcepto.this, MainActivity.class);
                Log.d("UDB", selectitem+"");
                //Categoria tmpCategoria = listaCategorias.get(idcategoria);
                //Categoria tmpCategoria = new Categoria();
               Call<ResponseBody> call = apiService.addConcepto(txtName.getText().toString(),Double.parseDouble(txtValue.getText().toString()),2);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                startActivity(intent);

            }
        });

    }
}
