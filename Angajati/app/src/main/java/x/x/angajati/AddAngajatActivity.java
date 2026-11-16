package x.x.angajati;

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
import androidx.recyclerview.widget.ItemTouchUIUtil;

public class AddAngajatActivity extends AppCompatActivity {

    private EditText etNume, etTelefon, etVechi;
    private Spinner spDepartament;
    private CheckBox cbLucreazaRemote;
    private Button btnSave;
    private Angajat a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_angajat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        btnSave.setOnClickListener(v->salveaza());
    }

    public void initViews(){
        etNume = findViewById(R.id.etNume);
        etTelefon = findViewById(R.id.etTelefon);
        etVechi = findViewById(R.id.etVechi);
        spDepartament = findViewById(R.id.spDepartament);
        cbLucreazaRemote = findViewById(R.id.cbLucreazaRemote);
        btnSave = findViewById(R.id.btnSalveaza);
    }

    public void salveaza(){
        String nume = etNume.getText().toString().trim();
        String phone = etTelefon.getText().toString().trim();
        int vechime = Integer.valueOf(etVechi.getText().toString());
        String departament = spDepartament.getSelectedItem().toString().trim();
        boolean lucreazaRemote = cbLucreazaRemote.isChecked();
        a = new Angajat(nume, departament, phone, vechime, lucreazaRemote);

        Intent result = new Intent();
        result.putExtra("angajat", a);
        setResult(RESULT_OK, result);
        finish();
    }
}