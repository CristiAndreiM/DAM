package ro.ase.restaurante;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailsActivity extends AppCompatActivity {

    TextView etNume;
    TextView etPhone;
    TextView etRating;
    TextView spBucatarie;
    TextView cbLivrare;
    Button btnSuna;
    Button btnDistribuie;
    Restaurant restaurant;
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
        precompleteaza();
        setupListener();
    }

   public void initViews(){
        etNume = findViewById(R.id.etNume);
        etPhone = findViewById(R.id.etPhone);
        etRating = findViewById(R.id.etRating);
        spBucatarie = findViewById(R.id.spBucatarie);
        cbLivrare = findViewById(R.id.cbLivrare);
       btnDistribuie = findViewById(R.id.btnDistribuie);
       btnSuna = findViewById(R.id.btnSuna);
    }

    public void precompleteaza(){
        restaurant = IntentCompat.getSerializableExtra(getIntent(), "restaurant", Restaurant.class);
        etNume.setText(restaurant.getNume());
        etPhone.setText(restaurant.getTelefon());
        etRating.setText(restaurant.getRating()+"");
        spBucatarie.setText(restaurant.getBucatarie());
        cbLivrare.setText("Livreaza? " + restaurant.isLivrare());
    }

    public void setupListener(){
        btnSuna.setOnClickListener(v->{
            Uri numar = Uri.parse("tel:" + restaurant.getTelefon());
            Intent intent = new Intent(Intent.ACTION_DIAL, numar);
            startActivity(intent);
        });

        btnDistribuie.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra("restaurant", restaurant.toString());
            startActivity(Intent.createChooser(intent, "Distribuie prin"));
        });
    }
}