package ro.ase.lab;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        HorizontalScrollView scrollView = new HorizontalScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ListView listView = new ListView(this);
        TextView tvListaBiblioteci = new TextView(this);
        tvListaBiblioteci.setText(R.string.lista_biblioteci);
        TextView tvRezultat = new TextView(this);


        Biblioteca biblioteca = new Biblioteca(200, "Unirii", "Biblioteca Centrala", 2);
        Biblioteca biblioteca1 = new Biblioteca(300, "Unirii", "Biblioteca Centrala", 3);
        Biblioteca biblioteca2 = new Biblioteca(400, "Unirii", "Biblioteca Centrala", 4);

        BibliotecaDB dbInstance = BibliotecaDB.getInstance(getApplicationContext());
        dbInstance.getBibliotecaDAO().insertBiblioteca(biblioteca);
        dbInstance.getBibliotecaDAO().insertBiblioteca(biblioteca1);
        dbInstance.getBibliotecaDAO().insertBiblioteca(biblioteca2);

        List<Biblioteca> listaBiblioteci = dbInstance.getBibliotecaDAO().getBiblioteci();
        int totalCarti = dbInstance.getBibliotecaDAO().getTotalNrCarti();
        float capacitateMedie = dbInstance.getBibliotecaDAO().getCapacitateMedie();
        tvRezultat.setText(getString(R.string.rezultat_text, totalCarti, capacitateMedie));

        ArrayAdapter<Biblioteca> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaBiblioteci);
        listView.setAdapter(adapter);

        linearLayout.addView(tvListaBiblioteci);

        linearLayout.addView(listView);
        linearLayout.addView(tvRezultat);
        scrollView.addView(linearLayout);

        setContentView(scrollView);
    }
}