package x.x.angajati;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Angajat> listaAngajati = new ArrayList<>();
    AngajatAdapter adapter;
    RecyclerView rvAngajati;

    ActivityResultLauncher<Intent> angajatLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result ->{
                Intent data = result.getData();
                if(result.getResultCode() == RESULT_OK && data != null){
                    Angajat a = IntentCompat.getSerializableExtra(data, "angajat", Angajat.class);
                    if(a == null)
                        return;
                    listaAngajati.add(a);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "S-a primit", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this, "Nu s-a primit", Toast.LENGTH_SHORT).show();
            }
    );
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

        rvAngajati = findViewById(R.id.rvAngajati);

        listaAngajati.add(new Angajat("nume", "departament", "telefon", 2, true));
        adapter = new AngajatAdapter(listaAngajati);
        rvAngajati.setLayoutManager(new LinearLayoutManager(this));
        rvAngajati.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.itemAdauga){
            Intent intent = new Intent(this, AddAngajatActivity.class);
            angajatLauncher.launch(intent);
            return true;
        }
        else
            if(id == R.id.itemSterge){
                listaAngajati.clear();
                adapter.notifyDataSetChanged();
            }
        return false;
    }
}