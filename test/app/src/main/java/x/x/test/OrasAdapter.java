package x.x.test;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
public class OrasAdapter extends ArrayAdapter<Oras>{
    public OrasAdapter(@NonNull Context context, int resource, @NonNull List<Oras> orase) {
        super(context, resource, orase);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rand = convertView;
        if(rand == null){
            rand = LayoutInflater.from(getContext()).inflate(R.layout.item_oras, parent, false);
        }

        TextView tvItemNume = rand.findViewById(R.id.tvItemNume);
        TextView tvItemDetalii = rand.findViewById(R.id.tvItemDetalii);
        TextView tvItemResedinta = rand.findViewById(R.id.tvItemResedinta);

        Oras o = getItem(position);
        if(o != null) {
            tvItemNume.setText(o.getNume());
            tvItemDetalii.setText(getContext().getString(R.string.item_detalii, o.getRegiune(), (long)o.getPopulatie()));

            if (o.isResedinta()) {
                tvItemResedinta.setVisibility(View.VISIBLE);
            } else
                tvItemResedinta.setVisibility(View.GONE);

            if(o.getPopulatie() > 100000)
                tvItemNume.setTextColor(Color.RED);
            else
                tvItemNume.setTextColor(Color.BLACK);
        }
       return rand;
    }
}

