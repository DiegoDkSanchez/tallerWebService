package sv.ub.com.taller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Grafica extends AppCompatActivity {

    List<Concepto> lista;
    ArrayList<BarEntry> entries;
    BarChart barChart;

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
        setContentView(R.layout.activity_grafica);

        barChart = (BarChart) findViewById(R.id.grafica);
        entries = new ArrayList<>();
        lista = new ArrayList<>();

        EndPointInterface apiService = retrofit.create(EndPointInterface.class);
        Call<List<Concepto>> callConceptos = apiService.getConceptos();
        callConceptos.enqueue(new Callback<List<Concepto>>() {
            @Override
            public void onResponse(Call<List<Concepto>> call, Response<List<Concepto>> response) {
                Log.d("UDBlISTA", response.body().toString()+"");
                lista = response.body();

                BarDataSet bardataset = new BarDataSet(entries, "Celdas");
                ArrayList<String> labels = new ArrayList<String>();
                for(int x =0; x < lista.size(); x++)
                {
                    entries.add(new BarEntry((float) lista.get(x).getValue(), x));
                    labels.add(lista.get(x).getName());

                }
                BarData data = new BarData(labels, bardataset);
                barChart.setData(data); // set the data and list of lables into chart

                barChart.setDescription("Gatos por Conceptos");  // set the description

                List<Integer> colores = new ArrayList<>();
                colores.add(0xFF009689);
                colores.add(0xFF007C72);
                bardataset.setColors(colores);

                barChart.animateY(5000);
            }

            @Override
            public void onFailure(Call<List<Concepto>> call, Throwable t) {
                Log.d("UDB", t.getMessage());
            }
        });



    }
}
