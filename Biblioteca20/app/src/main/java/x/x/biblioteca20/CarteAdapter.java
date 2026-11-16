package x.x.biblioteca20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CarteAdapter extends ArrayAdapter<Carte> {

    public CarteAdapter(@NonNull Context context, int resource, @NonNull List<Carte> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rand = convertView;
        if (rand == null) {
            rand = LayoutInflater.from(getContext()).inflate(R.layout.item_carte, parent, false);
        }

        TextView tvNume = rand.findViewById(R.id.tvTitlu);
        TextView tvAutor = rand.findViewById(R.id.tvAutor);
        TextView tvGen = rand.findViewById(R.id.tvGen);
        TextView tvCitita = rand.findViewById(R.id.tvCitita);

        Carte c = getItem(position);
        if (c == null)
            return null;

        tvGen.setText(c.getGen());
        tvAutor.setText(c.getAutor());
        tvNume.setText(c.getTitlu());

        if (c.isCitita()) {
            tvCitita.setVisibility(View.VISIBLE);
            tvCitita.setText("Citita");
        } else {
            tvCitita.setVisibility(View.GONE);
            tvCitita.setText("Necitita");
        }
        return rand;
    }
}
