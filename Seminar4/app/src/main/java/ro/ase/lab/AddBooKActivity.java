package ro.ase.lab;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ro.ase.lab.databinding.ActivityAddBookBinding;

public class AddBooKActivity extends AppCompatActivity {

    private ActivityAddBookBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBookBinding.inflate(getLayoutInflater());
        setContentView((binding.getRoot()));
        binding.btnSave.setOnClickListener(v -> save());
        ;
    }

    private void save(){
        String titlu = binding.etTitlu.getText().toString().trim();
        String yearStr = binding.etAn.getText().toString().trim();

        if(titlu.isEmpty() || yearStr.isEmpty()){
            Toast.makeText(this, R.string.err_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        String gens = binding.spGen.getSelectedItem().toString();

        int year = Integer.parseInt(yearStr);
        if(year < 2000 || year > 2005){
            Toast.makeText(this, R.string.err_an_aparitie, Toast.LENGTH_SHORT).show();
            return;
        }
        Carte carte = new Carte(titlu, gens, year);
        Intent data = new Intent();
        data.putExtra("carte", carte);

        setResult(RESULT_OK, data);
        finish();
    }
}
