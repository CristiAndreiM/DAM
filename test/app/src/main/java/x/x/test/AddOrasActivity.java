package x.x.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;

public class AddOrasActivity extends AppCompatActivity {

    public static final String EXTRA_ORAS = "nume";
    public static final String EXTRA_POSITION = "pozitie";

    private int positie = -1;
    private EditText etOras;
    private EditText etPopulatie;
    private EditText etNumar;
    private Button btnSave;
    private Button btnGoleste;
    private Spinner spRegiune;
    private CheckBox cbResedinta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_oras);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
        Oras orasPrimit = IntentCompat.getSerializableExtra(getIntent(), EXTRA_ORAS, Oras.class);
        if(orasPrimit != null) {
            positie = getIntent().getIntExtra(EXTRA_POSITION, -1);
            precompleteaza(orasPrimit);
            setTitle(R.string.titlu_editeaza);
            btnSave.setText(R.string.btn_actualizeaza);
        }else{
            setTitle(R.string.titlu_adauga);
        }
    }

    private void precompleteaza(Oras oras) {
    etOras.setText(oras.getNume());
    etPopulatie.setText(String.valueOf((long) oras.getPopulatie()));
    etNumar.setText(oras.getTelefonPrimarie());
    cbResedinta.setChecked(oras.isResedinta());

    String[] regiuni = getResources().getStringArray(R.array.regiuni);
    int index = Arrays.asList(regiuni).indexOf(oras.getRegiune());
    if(index >= 0){
        spRegiune.setSelection(index);
    }
}
    private void initViews() {
        etOras = findViewById(R.id.etOras);
        etPopulatie = findViewById(R.id.etPopulatie);
        etNumar = findViewById(R.id.etNrPrimarie);
        spRegiune = findViewById(R.id.spRegiune);
        cbResedinta = findViewById(R.id.cbResedinta);
        btnSave = findViewById(R.id.btnSave);
        btnGoleste = findViewById(R.id.btnGoleste);
    }

    private void setupListeners() {
        btnSave.setOnClickListener(v -> adaugaOras());
        btnGoleste.setOnClickListener(v -> goleste());
    }

    private void goleste() {
        etOras.setText("");
        etPopulatie.setText("");
        etNumar.setText("");

        etOras.setError(null);
        etPopulatie.setError(null);
        etNumar.setError(null);

        spRegiune.setSelection(0);
        cbResedinta.setChecked(false);
        etOras.requestFocus();
    }

    private void adaugaOras() {
        String nume = etOras.getText().toString().trim();
        String pop = etPopulatie.getText().toString().trim();
        String numar = etNumar.getText().toString().trim();

        // 1. Validare nume: minim 3 caractere
        if (nume.length() < 3) {
            etOras.setError("Numele trebuie să aibă minim 3 caractere");
            etOras.requestFocus();
            return;
        }

        // 2. Validare populație: completată
        if (pop.isEmpty()) {
            etPopulatie.setError("Introdu populația");
            etPopulatie.requestFocus();
            return;
        }

        // 3. Validare populație: număr valid
        double populatie;
        try {
            populatie = Double.parseDouble(pop);
        } catch (NumberFormatException e) {
            etPopulatie.setError("Populația trebuie să fie un număr");
            etPopulatie.requestFocus();
            return;
        }

        // 4. Validare populație: mai mare ca 0
        if (populatie <= 0) {
            etPopulatie.setError("Populația trebuie să fie mai mare ca 0");
            etPopulatie.requestFocus();
            return;
        }

        // 5. Validare număr primărie: minim 10 cifre
        if (numar.length() < 10) {
            etNumar.setError("Numărul trebuie să aibă 10 cifre");
            etNumar.requestFocus();
            return;
        }

        // Totul este valid -> creăm obiectul și îl trimitem înapoi
        String regiune = spRegiune.getSelectedItem().toString();
        boolean esteResedinta = cbResedinta.isChecked();

        Oras oras = new Oras(nume, populatie, regiune, esteResedinta, numar);

        String mesajConfirmare = oras.getNume() + ", " + oras.getPopulatie() + " locuitori, "
                + regiune + (esteResedinta ? ", reședință de județ" : "");
        Toast.makeText(this, mesajConfirmare, Toast.LENGTH_LONG).show();

        Intent rezultat = new Intent();
        rezultat.putExtra(EXTRA_ORAS, oras);
        rezultat.putExtra(EXTRA_POSITION, positie);
        setResult(RESULT_OK, rezultat);
        finish();
    }
}
