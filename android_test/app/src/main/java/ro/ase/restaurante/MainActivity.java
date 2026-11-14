package ro.ase.restaurante;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private int noRestaurante = 0;
    private ArrayList<Restaurant> listaRestaurante;
    private RecyclerView rvRestaurante;
    private FloatingActionButton fabAddRestaurant;
    private RestaurantAdapter adapter;
    private ActivityResultLauncher<Intent> addRestaurantLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
        Intent data = result.getData();
        if(result.getResultCode() == RESULT_OK && data != null){
            Restaurant restaurant = IntentCompat.getSerializableExtra(data, "restaurant", Restaurant.class);
            int position = data.getIntExtra("position", -1);

            if(restaurant == null)
                return;

            if(position >= 0){
                listaRestaurante.set(position, restaurant);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "S-a actualizat", Toast.LENGTH_SHORT).show();
            }
            else{
                listaRestaurante.add(restaurant);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "S-a adaugat", Toast.LENGTH_SHORT).show();
                noRestaurante++;
            }

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListener();
        listaRestaurante = new ArrayList<Restaurant>();
        for(int i = 0; i < 3; i++){
            listaRestaurante.add(new Restaurant("nume_" + i, "bucatarie_" + i, "000.0000.0000", (float)(1.2 + i), false));
        }

        adapter = new RestaurantAdapter(listaRestaurante);
        rvRestaurante.setLayoutManager(new LinearLayoutManager(this));
        rvRestaurante.setAdapter(adapter);
    }

    void initViews(){
        rvRestaurante = findViewById(R.id.rvRestaurant);
        fabAddRestaurant = findViewById(R.id.fabAdauga);
    }

    public void setupListener(){
        fabAddRestaurant.setOnClickListener(v->deschideFormular());
    }

    public void editeazaFormulare(AdapterView parent, int position){
        Intent intent = new Intent(this, AddRestaurantActivity.class);
        Restaurant r = listaRestaurante.get(position);
        intent.putExtra("restaurant", r);
        intent.putExtra("position", position);
        addRestaurantLauncher.launch(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_adauga) {
            deschideFormular();
        } else if (id == R.id.item_numar_restaurante) {
            Toast.makeText(this, listaRestaurante.size() + "", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.item_despre) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .show();
        }
        return true;
    }

    public void deschideFormular(){
        Intent intent = new Intent(this, AddRestaurantActivity.class);
        addRestaurantLauncher.launch(intent);
    }
}