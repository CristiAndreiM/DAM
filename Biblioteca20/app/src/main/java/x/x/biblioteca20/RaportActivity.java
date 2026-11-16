package x.x.biblioteca20;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RaportActivity extends AppCompatActivity {

    List<Carte> lista;
    RecyclerView rvCarte;
    RecyclerViewCarte adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_raport);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvCarte = findViewById(R.id.rvLista);

        // 1. Preluăm lista ca ArrayList (care implementează Serializable)
        lista = IntentCompat.getSerializableExtra(getIntent(), "carte", ArrayList.class);

        // 2. Evităm un posibil NullPointerException dacă nu s-a trimis nimic
        if (lista == null) {
            lista = new ArrayList<>();
        }

        adapter = new RecyclerViewCarte(this, lista);

        // 3. Instanțiem LinearLayoutManager pentru afișare verticală
        rvCarte.setLayoutManager(new LinearLayoutManager(this));
        rvCarte.setAdapter(adapter);
    }
}
