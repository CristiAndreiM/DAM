package x.x.biblioteca20;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import x.x.biblioteca20.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<Carte> lista = new ArrayList<>();
    private CarteAdapter adapter;

    private ActivityResultLauncher<Intent> addCarteLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
        Intent data = result.getData();
        if(result.getResultCode() == RESULT_OK && data != null){
            Carte c = IntentCompat.getSerializableExtra(data, AddCarteActivity.ADD_CARTE_KEY, Carte.class);
            int pos = data.getIntExtra("position", -1);
            if(c != null && pos != -1)
            lista.set(pos, c);
            else
                if(c != null)
                    lista.add(c);
            adapter.notifyDataSetChanged();
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        populeazaListaCarti();
        initializareListView();
        setClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void setClick() {
        binding.lvCarti.setOnItemClickListener((parent, view, position, id) -> {
            Carte c = lista.get(position);
            deschideDetails(c);
        });

        binding.lvCarti.setOnItemLongClickListener((parent, view, position, id) -> {
            setAlertDialogLvCarti(position);
            return true;
        });
    }

    public void setAlertDialogLvCarti(int position) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.alertDialogLv)
                .setPositiveButton(R.string.alert_editeaza, (dialog, which) -> {
                    Intent result = new Intent(this, AddCarteActivity.class);
                    Carte c = lista.get(position);
                    result.putExtra(AddCarteActivity.ADD_CARTE_KEY, c);
                    result.putExtra("position", position);
                    addCarteLauncher.launch(result);
                    Toast.makeText(this, "Editeaza", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.alert_sterge, (dialog, which) -> {
                    lista.remove(position);
                    adapter.notifyDataSetChanged();
                    binding.tvTotal.setText(getString(R.string.tvCarti, lista.size()));
                })
                .setNeutralButton(R.string.alert_Anuleaza, null)
                .show();
    }

    public void deschideDetails(Carte c) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_CARTE, c);
        startActivity(intent);
    }

    public void initializareListView() {
        adapter = new CarteAdapter(this, 0, lista);
        binding.lvCarti.setAdapter(adapter);
        binding.tvTotal.setText(getString(R.string.tvCarti, lista.size()));
    }

    public void populeazaListaCarti() {
        Carte carte1 = new Carte("Moromeții", "Marin Preda", "Roman realist", 1955, 45, true);
        Carte carte2 = new Carte("Dune", "Frank Herbert", "Science Fiction", 1965, 65, true);
        Carte carte3 = new Carte("Ion", "Liviu Rebreanu", "Roman social", 1920, 40, false);
        Carte carte4 = new Carte("1984", "George Orwell", "Distopie", 1949, 50, true);
        Carte carte5 = new Carte("Micul Prinț", "Antoine de Saint-Exupéry", "Ficțiune", 1943, 35, false);

        lista.add(carte1);
        lista.add(carte2);
        lista.add(carte3);
        lista.add(carte4);
        lista.add(carte5);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Adauga) {
            Intent intent = new Intent(this, AddCarteActivity.class);
            addCarteLauncher.launch(intent);
        }
        else
            if(id == R.id.item_sorteaza_dupa_titlu){
             Collections.sort(lista, Comparator.comparing(Carte::getTitlu));
             adapter.notifyDataSetChanged();
            }
        else
            if(id == R.id.item_Sortează_după_preț_descrescător)
            {
                Collections.sort(lista, Comparator.comparing(Carte::getPret).reversed());
                adapter.notifyDataSetChanged();
            }
            else
                if(id == R.id.item_Statistici){
                    String mesaj = "Număr de cărți: " + lista.size() + "\n" +
                            "Citite: " + carti_citite() + "\n" +
                            "Preț mediu: " + pret_mediu();

                    new AlertDialog.Builder(this)
                            .setTitle("Statistici")
                            .setMessage(mesaj)
                            .setPositiveButton("OK", null)
                            .show();


                }
                else
                    if(id == R.id.item_Raport)
                    {
                      Intent intent = new Intent(this, RaportActivity.class);
                      intent.putExtra("carte", new ArrayList<>(lista));
                      startActivity(intent);
                    }
        return super.onOptionsItemSelected(item);
    }

    public float pret_mediu(){
        float pret_mediu = 0;
        for(Carte c : lista)
            pret_mediu += c.getPret();
        return pret_mediu/ lista.size();
    }
    public int carti_citite(){
        int cont = 0;
        for(Carte c : lista){
            if(c.isCitita())
                cont++;
        }
        return cont;
    }
}