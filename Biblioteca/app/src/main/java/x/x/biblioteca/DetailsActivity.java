package x.x.biblioteca;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvTitlu, tvAutor, tvGen, tvAn, tvDisponibila;
    private Button btnCauta;
    private Carte c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        afiseazaCarte();

        btnCauta.setOnClickListener(v -> {
            Uri link = Uri.parse("https://www.google.com/search?q=" + Uri.encode(c.getAutor()));
            Intent intent = new Intent(Intent.ACTION_VIEW, link);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, R.string.eroare_browser, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        tvTitlu = findViewById(R.id.tvTitlu);
        tvAutor = findViewById(R.id.tvAutor);
        tvGen = findViewById(R.id.tvGen);
        tvAn = findViewById(R.id.tvAn);
        tvDisponibila = findViewById(R.id.tvDisponibila);
        btnCauta = findViewById(R.id.btnCautaGoogle);
    }

    private void afiseazaCarte() {
        c = IntentCompat.getSerializableExtra(getIntent(), "carte", Carte.class);
        if (c == null) {
            finish();
            return;
        }

        tvTitlu.setText(c.getTitlu());
        tvAutor.setText(getString(R.string.format_autor, c.getAutor()));
        tvGen.setText(getString(R.string.format_gen, c.getGen()));
        tvAn.setText(getString(R.string.format_an, c.getAn_aparitie()));
        tvDisponibila.setText(c.isDisponibila() ? R.string.status_disponibila : R.string.status_indisponibila);
    }
}