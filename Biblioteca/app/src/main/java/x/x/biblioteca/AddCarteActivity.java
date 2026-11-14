package x.x.biblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddCarteActivity extends AppCompatActivity {

    private EditText etNume, etAutor, etAn;
    private Spinner spGen;
    private CheckBox cbDisponibila;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_carte);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        btnSave.setOnClickListener(v -> save());
    }

    private void initViews() {
        etNume = findViewById(R.id.etNume);
        etAutor = findViewById(R.id.etAutor);
        etAn = findViewById(R.id.etAn);
        spGen = findViewById(R.id.spGen);
        cbDisponibila = findViewById(R.id.cbDisponibila);
        btnSave = findViewById(R.id.btnSave);
    }

    private void save() {
        String titlu = etNume.getText().toString().trim();
        String autor = etAutor.getText().toString().trim();
        String anText = etAn.getText().toString().trim();

        if (titlu.isEmpty()) {
            etNume.setError(getString(R.string.eroare_titlu_gol));
            etNume.requestFocus();
            return;
        }

        if (autor.isEmpty()) {
            etAutor.setError(getString(R.string.eroare_autor_gol));
            etAutor.requestFocus();
            return;
        }

        if (anText.isEmpty()) {
            etAn.setError(getString(R.string.eroare_an_invalid));
            etAn.requestFocus();
            return;
        }

        int anAparitie = Integer.parseInt(anText);
        if (anAparitie < 2000) {
            etAn.setError(getString(R.string.eroare_an_invalid));
            etAn.requestFocus();
            return;
        }

        String gen = spGen.getSelectedItem().toString();
        boolean disponibila = cbDisponibila.isChecked();

        Carte c = new Carte(titlu, autor, gen, anAparitie, disponibila);

        Intent intent = new Intent();
        intent.putExtra("carte", c);
        setResult(RESULT_OK, intent);
        finish();
    }
}