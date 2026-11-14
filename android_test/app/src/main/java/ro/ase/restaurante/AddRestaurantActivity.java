package ro.ase.restaurante;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.Arrays;

public class AddRestaurantActivity extends AppCompatActivity {
    EditText etNume;
    EditText etPhone;
    EditText etRating;
    Spinner spBucatarie;
    CheckBox cbLivrare;
    Button btnSalveaza;
    int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_restaurant);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        setupListener();

        Restaurant restaurantPrimit = IntentCompat.getSerializableExtra(getIntent(), "restaurant", Restaurant.class);
        position = getIntent().getIntExtra("position", -1);
        if(restaurantPrimit != null){

            precompleteaza(restaurantPrimit);
            setTitle(R.string.titlu_editeaza);
            btnSalveaza.setText(R.string.btn_actualizeaza);
        }
        else
            setTitle(R.string.titlu_adauga);
    }

    void initViews(){
        etNume = findViewById(R.id.etNume);
        etPhone = findViewById(R.id.etPhone);
        etRating = findViewById(R.id.etRating);
        spBucatarie = findViewById(R.id.spBucatarie);
        cbLivrare = findViewById(R.id.cbLivrare);
        btnSalveaza = findViewById(R.id.btnSalveaza);
    }

    public void setupListener(){
        btnSalveaza.setOnClickListener(v->adaugaRestaurant());
    }
    public void adaugaRestaurant(){
        String nume = etNume.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String rt = etRating.getText().toString().trim();
        float rating = Float.parseFloat(rt);
        String bucatarie = spBucatarie.getSelectedItem().toString();
        boolean livreaza = cbLivrare.isChecked();

        Restaurant r = new Restaurant(nume, bucatarie, phone, rating, livreaza);
        Intent rezultat = new Intent();
        rezultat.putExtra("restaurant", r);
        rezultat.putExtra("position", position);
        setResult(RESULT_OK, rezultat);
        finish();
    }

    public void precompleteaza(Restaurant restaurant){
        etNume.setText(restaurant.getNume());
        etPhone.setText(restaurant.getTelefon());
        etRating.setText(restaurant.getRating()+"");
        cbLivrare.setChecked(restaurant.isLivrare());

        String[] bucatarie = getResources().getStringArray(R.array.spBucatarie);
        int index = Arrays.asList(bucatarie).indexOf(restaurant.getBucatarie());
        if(index >= 0)
            spBucatarie.setSelection(index);
    }
}