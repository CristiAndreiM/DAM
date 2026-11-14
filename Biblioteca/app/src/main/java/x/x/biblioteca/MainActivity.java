package x.x.biblioteca;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvCarte;
    private ArrayAdapter<Carte> adapter;
    private List<Carte> listaCarti = new ArrayList<>();
    private Button btnAdd;

    private final ActivityResultLauncher<Intent> addCarteLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                if (result.getResultCode() == RESULT_OK && data != null) {
                    Carte c = IntentCompat.getSerializableExtra(data, "carte", Carte.class);
                    if (c == null) {
                        return;
                    }
                    listaCarti.add(c);
                    adapter.notifyDataSetChanged();
                }
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

        initViews();

        for (int i = 0; i < 3; i++) {
            listaCarti.add(new Carte("Titlu " + i, "Autor " + i, "Gen " + i, 2020 + i, false));
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCarti);
        lvCarte.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddCarteActivity.class);
            addCarteLauncher.launch(intent);
        });

        lvCarte.setOnItemClickListener((parent, view, position, id) -> {
            Carte c = adapter.getItem(position);
            if (c != null) {
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.putExtra("carte", c);
                startActivity(intent);
            }
        });

        lvCarte.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_stergere_titlu)
                    .setMessage(R.string.dialog_stergere_mesaj)
                    .setPositiveButton(R.string.da, (dialog, which) -> {
                        listaCarti.remove(position);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.nu, null)
                    .show();
            return true;
        });
    }

    private void initViews() {
        lvCarte = findViewById(R.id.lvCarte);
        btnAdd = findViewById(R.id.btnAdd);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemTotalCarti) {
            Toast.makeText(this, getString(R.string.toast_total_carti, listaCarti.size()), Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.itemTotalCartiDisponibile) {
            int count = 0;
            for (Carte c : listaCarti) {
                if (c.isDisponibila()) {
                    count++;
                }
            }
            Toast.makeText(this, getString(R.string.toast_total_disponibile, count), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}