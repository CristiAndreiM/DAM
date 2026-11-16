package x.x.biblioteca20;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import x.x.biblioteca20.databinding.ActivityAddCarteBinding;

public class AddCarteActivity extends AppCompatActivity {
    public static final String ADD_CARTE_KEY = "addCarteKey";
    private ActivityAddCarteBinding binding;
    private int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityAddCarteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        verificaModEditare();

        binding.btnSalveaza.setOnClickListener(v -> Salveaza());
        binding.btnGoleste.setOnClickListener(v -> Goleste());
    }

    private void verificaModEditare() {
        Carte carteDeEditat = (Carte) getIntent().getSerializableExtra(ADD_CARTE_KEY);
         position = getIntent().getIntExtra("position", -1);
        if (carteDeEditat != null && position >= 0) {
            setTitle(R.string.titlu_editeaza);
            binding.btnSalveaza.setText(R.string.btn_actualizeaza);

            binding.etNume.setText(carteDeEditat.getTitlu());
            binding.etAutor.setText(carteDeEditat.getAutor());
            binding.etAn.setText(String.valueOf(carteDeEditat.getAn()));
            binding.etPret.setText(String.valueOf(carteDeEditat.getPret()));
            binding.cbCitita.setChecked(carteDeEditat.isCitita());

            for (int i = 0; i < binding.spGen.getCount(); i++) {
                if (binding.spGen.getItemAtPosition(i).toString().equals(carteDeEditat.getGen())) {
                    binding.spGen.setSelection(i);
                    break;
                }
            }
        } else {
            setTitle(R.string.titlu_adauga);
            binding.btnSalveaza.setText(R.string.btn_salveaza);
        }
    }

    public void Salveaza() {
        if (!isValid()) {
            return;
        }

        String titlu = binding.etNume.getText().toString().trim();
        String autor = binding.etAutor.getText().toString().trim();
        String gen = binding.spGen.getSelectedItem().toString();
        int an = Integer.parseInt(binding.etAn.getText().toString().trim());
        float pret = Float.parseFloat(binding.etPret.getText().toString().trim());
        boolean citita = binding.cbCitita.isChecked();

        Carte c = new Carte(titlu, autor, gen, an, pret, citita);

        Intent result = new Intent();
        result.putExtra(ADD_CARTE_KEY, c);
        result.putExtra("position", position);
        setResult(RESULT_OK, result);
        finish();
    }

    private boolean isValid() {
        if (binding.etNume.getText().toString().trim().isEmpty()) {
            binding.etNume.setError("Introdu titlul!");
            return false;
        }
        if (binding.etAutor.getText().toString().trim().isEmpty()) {
            binding.etAutor.setError("Introdu autorul!");
            return false;
        }
        if (binding.etAn.getText().toString().trim().isEmpty()) {
            binding.etAn.setError("Introdu anul!");
            return false;
        }
        if (binding.etPret.getText().toString().trim().isEmpty()) {
            binding.etPret.setError("Introdu prețul!");
            return false;
        }
        return true;
    }

    public void Goleste() {
        binding.etNume.setText("");
        binding.etAutor.setText("");
        binding.etPret.setText("");
        binding.spGen.setSelection(0);
        binding.cbCitita.setChecked(false);
        binding.etAn.setText("");
    }
}