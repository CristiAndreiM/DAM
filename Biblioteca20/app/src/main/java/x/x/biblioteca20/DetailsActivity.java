package x.x.biblioteca20;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import x.x.biblioteca20.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;
    public static final String EXTRA_CARTE = "carte";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

       binding = ActivityDetailsBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent data = getIntent();
        Carte c = IntentCompat.getSerializableExtra(data, EXTRA_CARTE, Carte.class);
        if(c == null)
            return;
        setDetails(c);

        binding.btnCauta.setOnClickListener(v->{
            Uri link = Uri.parse("https://www.google.com/search?q=" + Uri.encode(c.getAutor()) + Uri.encode(c.getTitlu()));

            Intent intent = new Intent(Intent.ACTION_VIEW, link);
            startActivity(intent);
        });

        binding.btnDistribuie.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,c.toString());
            startActivity(Intent.createChooser(intent, "distribuie prin: "));
        });
    }
    public void setDetails(Carte c){
        binding.tvNume.setText(c.getTitlu());
        binding.tvAutor.setText(c.getAutor());
        binding.tvGen.setText(c.getGen());
        binding.tvAn.setText(getString(R.string.tvAn, c.getAn()));
        binding.tvPret.setText(getString(R.string.tvPret, c.getPret()));

        if(c.isCitita())
            binding.tvCitita.setText(R.string.tvCitita_da);
        else
            binding. tvCitita.setText(R.string.tvCitita_nu);
    }
}