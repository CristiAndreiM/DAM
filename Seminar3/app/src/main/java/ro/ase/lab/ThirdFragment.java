package ro.ase.lab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class ThirdFragment extends Fragment {

    public ThirdFragment() {
        super(R.layout.fragment_third);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvGreeting = view.findViewById(R.id.tvGreeting);
        TextView tvNumberA = view.findViewById(R.id.tvSuma);
        TextView tvNumberB = view.findViewById(R.id.tvProdus);
        Button btnBack = view.findViewById(R.id.btnBack);

        String name = requireArguments().getString("name");
        String numberA = requireArguments().getString("numberA");
        String numberB = requireArguments().getString("numberB");
        double a = Double.parseDouble(numberA);
        double b = Double.parseDouble(numberB);
        double suma = a + b;
        double produs = a * b;

        tvGreeting.setText(getString(R.string.greeting, name));
        tvNumberA.setText(getString(R.string.suma, String.valueOf(suma)));
        tvNumberB.setText(getString(R.string.produs, String.valueOf(produs)));

        btnBack.setOnClickListener(v ->
                getParentFragmentManager().popBackStack());
    }
    }
