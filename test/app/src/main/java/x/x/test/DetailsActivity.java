package x.x.test;

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
public static String EXTRA_DATA = "oras";
private TextView tvNume;
private TextView tvPopulatie;
private TextView tvRegiune;
private TextView tvResedinta;

private Button btnCauta_pe_Google;
private Button btnDistribuie;
private Button btnSuna;
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
        Oras oras = IntentCompat.getSerializableExtra(getIntent(), EXTRA_DATA, Oras.class);
        if(oras == null){
            finish();
            return;
        }

        tvNume = findViewById(R.id.tvNume);
        tvPopulatie = findViewById(R.id.tvPopulatie);
        tvRegiune = findViewById(R.id.tvRegiune);
        tvResedinta = findViewById(R.id.tvResedinta);
        btnCauta_pe_Google = findViewById(R.id.btnCauta_pe_Google);
        btnDistribuie = findViewById(R.id.btnDistribuie);
        btnSuna = findViewById(R.id.btnSuna);

        tvNume.setText(getString(R.string.det_nume, oras.getNume()));
        tvPopulatie.setText(getString(R.string.det_populatie, (long)oras.getPopulatie()));
        tvRegiune.setText(getString(R.string.det_regiune, oras.getRegiune()));
        if(oras.isResedinta())
        tvResedinta.setText(R.string.det_resedinta_da);
        else
            tvResedinta.setText(R.string.det_resedinta_nu);

        btnCauta_pe_Google.setOnClickListener((v->cautaPeGoogle(oras)));
        btnDistribuie.setOnClickListener((v->distribuie(oras)));
        btnSuna.setOnClickListener((v->suna(oras)));
    }

    private void deschide(Intent intent){
        try{
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(this, "Nu exista aplicatie pentru aceasta actiunr", Toast.LENGTH_SHORT);
        }
    }

    private void cautaPeGoogle(Oras oras){
        Uri link = Uri.parse("https://www.google.com/search?q="+
                Uri.encode(oras.getNume()));
        Intent intent = new Intent(Intent.ACTION_VIEW, link);
        deschide(intent);
    }

    private void distribuie(Oras oras){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, oras.toString());
        deschide(Intent.createChooser(intent, "Distribuie prin"));
    }

    private void suna(Oras oras){
        Uri numar = Uri.parse("tel: " + oras.getTelefonPrimarie());
        Intent intent = new Intent(Intent.ACTION_DIAL, numar);
        deschide(intent);
    }
}