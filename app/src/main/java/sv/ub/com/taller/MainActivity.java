package sv.ub.com.taller;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<DataModelItem> dataModelItemArrayList;
    private AppCompatButton button;
    private static CustomAdapter adapter;
    private List<Concepto> listaConceptos;
    private List<Categoria> listaCategorias;


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
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        button = findViewById(R.id.verGraficas);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormAddConcepto.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Grafica.class);
                startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.listaCategorias);
        EndPointInterface apiService = retrofit.create(EndPointInterface.class);
        Call<List<Concepto>> callConceptos = apiService.getConceptos();
        callConceptos.enqueue(new Callback<List<Concepto>>() {
            @Override
            public void onResponse(Call<List<Concepto>> call, Response<List<Concepto>> response) {
                Log.d("UDBlISTA", response.body().toString()+"");
                listaConceptos = response.body();
                cargarDatos(listaConceptos);
            }

            @Override
            public void onFailure(Call<List<Concepto>> call, Throwable t) {
                Log.d("UDB", t.getMessage());
            }
        });

        Call<List<Categoria>> callCategorias = apiService.getCategorias();
        callCategorias.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                Log.d("UDB", response.body()+"");
                listaCategorias = response.body();
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Log.d("UDB", t.getMessage());
            }
        });
    }

    private void cargarDatos(List<Concepto> listaConceptos) {
        dataModelItemArrayList = new ArrayList<>();
        for(Concepto concepto: listaConceptos){
            this.dataModelItemArrayList.add(new DataModelItem(concepto.id,concepto.name,concepto.value,concepto.categoria.name));
        }

        adapter = new CustomAdapter(dataModelItemArrayList,getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}
