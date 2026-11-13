package x.x.test;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DespreActivity extends AppCompatActivity {
    public static final String EXTRA_NR_ORASE = "nr_orase";
    public static final String EXTRA_DATE_ORAS = "date_oras";
    private TextView tvNume;
    private TextView tvOrase;
   private TextView tvVersiune;
   private Button btnInapoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_despre);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvVersiune = findViewById(R.id.tvVersiune);
        tvNume = findViewById(R.id.tvNume);
        tvOrase = findViewById(R.id.tvOrase);
        btnInapoi = findViewById(R.id.btnInapoi);

        int nrOrase = getIntent().getIntExtra(EXTRA_NR_ORASE, 0);
       // Oras oras = IntentCompat.getSerializableExtra(getIntent(), EXTRA_DATE_ORAS, Oras.class);

        String nume = getIntent().getStringExtra(EXTRA_DATE_ORAS);
        tvNume.setText(nume);
        tvOrase.setText(getString(R.string.tvNumar, nrOrase));
        try{
            String versiune = getPackageManager()
                    .getPackageInfo(getPackageName(), 0)
                    .versionName;
            tvVersiune.setText(getString(R.string.tvVersiune, versiune));
        } catch (PackageManager.NameNotFoundException e) {
            tvVersiune.setText("Versiune necunoscuta");
        }

        btnInapoi.setOnClickListener((v->{
            finish();
        }));
    }
}