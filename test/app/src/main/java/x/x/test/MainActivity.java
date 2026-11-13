package x.x.test;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

    private static String TAG = "CicluViata";
    private static String KEY_LISTA = "lista_orase";

    private int noOrase = 0;
    private TextView tvNumar;
    private Button btnInc;
    private RecyclerView rvOrase;
    private FloatingActionButton fabAdauga;

    private OrasRecyclerAdapter adapter;
    private ArrayList<Oras> listaOrase;

    private final ActivityResultLauncher<Intent> addOrasLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                Intent data = result.getData();
                if (result.getResultCode() == RESULT_OK && data != null) {
                    Oras oras = IntentCompat.getSerializableExtra(data, AddOrasActivity.EXTRA_ORAS, Oras.class);
                    int positie = data.getIntExtra(AddOrasActivity.EXTRA_POSITION, -1);
                    if (oras == null) {
                        return;
                    }

                    if (positie >= 0) {
                        listaOrase.set(positie, oras);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Ai actualizat: " + oras.getNume(), LENGTH_SHORT).show();
                    } else {
                        listaOrase.add(oras);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Ai adăugat: " + oras.getNume(), LENGTH_SHORT).show();
                    }

                    noOrase = listaOrase.size();
                    displayNoOrase();
                } else {
                    Toast.makeText(this, "Adăugare anulată", LENGTH_SHORT).show();
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

        Log.d(TAG, "onCreate");
        initViews();
        setupList();
        setupListeners();
    }

    private void initViews() {
        tvNumar = findViewById(R.id.tvNumar);
        btnInc = findViewById(R.id.btnInc);
        rvOrase = findViewById(R.id.rvOrase);
        fabAdauga = findViewById(R.id.fabAdauga);
    }

    private void setupList() {
        listaOrase = new ArrayList<>();
        Oras o1 = new Oras("Bucuresti", 2000000, "Muntenia", true);
        for (int i = 1; i <= 20; i++) {
            double populatie = i * 15000; // 15000, 30000, ..., 300000
            listaOrase.add(new Oras("Oras " + i, populatie, "Moldova", i % 3 == 0, "0712345678"));
        }
        listaOrase.add(o1);

        rvOrase.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrasRecyclerAdapter(this, listaOrase);
        rvOrase.setAdapter(adapter);

        noOrase = listaOrase.size();
        displayNoOrase();
    }

    private void setupListeners() {
        btnInc.setOnClickListener(v -> {
            noOrase++;
            displayNoOrase();
        });

        fabAdauga.setOnClickListener(v -> deschideFormular());

        adapter.setOnItemClickListener(new OrasRecyclerAdapter.OnItemClickListener() {
            // Click scurt -> deschide DetailsActivity
            @Override
            public void onItemClick(Oras oras, int position) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_DATA, oras);
                startActivity(intent);
            }

            // Click lung -> dialog cu Da / Nu / Editează
            @Override
            public void onItemLongClick(Oras oras, int position) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.dialog_delete_item_title)
                        .setPositiveButton(R.string.dialog_yes, (dialog, which) -> {
                            listaOrase.remove(position);
                            adapter.notifyItemRemoved(position);
                            adapter.notifyItemRangeChanged(position, listaOrase.size());
                            noOrase = listaOrase.size();
                            displayNoOrase();
                        })
                        .setNegativeButton(R.string.dialog_no, null)
                        .setNeutralButton(R.string.titlu_editeaza, (dialog, which) -> editeazaOras(oras, position))
                        .show();
            }
        });
    }

    private void deschideFormular() {
        Intent intent = new Intent(this, AddOrasActivity.class);
        addOrasLauncher.launch(intent);
    }

    private void editeazaOras(Oras oras, int position) {
        Intent intent = new Intent(this, AddOrasActivity.class);
        intent.putExtra(AddOrasActivity.EXTRA_ORAS, oras);
        intent.putExtra(AddOrasActivity.EXTRA_POSITION, position);
        addOrasLauncher.launch(intent);
    }

    public void displayNoOrase() {
        tvNumar.setText(getString(R.string.tvNumar, noOrase));
    }

    public void resetNoOrase(View v) {
        listaOrase.clear();
        adapter.notifyDataSetChanged();
        noOrase = 0;
        displayNoOrase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_despre) {
            Intent intent = new Intent(this, DespreActivity.class);
            intent.putExtra(DespreActivity.EXTRA_NR_ORASE, listaOrase.size());
            if (adapter != null && !listaOrase.isEmpty()) {
                Oras o = listaOrase.get(listaOrase.size() - 1);
                intent.putExtra(DespreActivity.EXTRA_DATE_ORAS, o);
            } else {
                intent.putExtra(DespreActivity.EXTRA_DATE_ORAS, "lista goala");
            }
            startActivity(intent);
            return true;

        } else if (id == R.id.no_Orase) {
            Toast.makeText(this, String.valueOf(noOrase), LENGTH_SHORT).show();
            return true;

        } else if (id == R.id.menu_sterg) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_delete_all_title)
                    .setMessage(R.string.dialog_delete_all_message)
                    .setPositiveButton(R.string.dialog_yes, (dialog, which) -> {
                        listaOrase.clear();
                        noOrase = 0;
                        adapter.notifyDataSetChanged();
                        displayNoOrase();
                    })
                    .setNegativeButton(R.string.dialog_no, null)
                    .show();
            return true;

        } else if (id == R.id.menu_adauga) {
            deschideFormular();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // ---------- Ciclul de viață ----------

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_LISTA, listaOrase);
    }
}