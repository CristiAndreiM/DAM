package x.x.angajati;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AngajatAdapter extends RecyclerView.Adapter<AngajatAdapter.AngajatViewHolder> {
    private List<Angajat> lista;

    public AngajatAdapter(List<Angajat> lista) {
        this.lista = lista;
    }

    public static class AngajatViewHolder extends RecyclerView.ViewHolder {
        TextView tvNume, tvDepartament, tvVechime;

        public AngajatViewHolder(View v){
            super(v);
            tvNume = v.findViewById(R.id.tvNume);
            tvDepartament = v.findViewById(R.id.tvDepartament);
            tvVechime = v.findViewById(R.id.tvVechime);
        }
    }

    @NonNull
    @Override
    public AngajatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View rand = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_angajat, parent, false);
       return new AngajatViewHolder(rand);
    }

    @Override
    public void onBindViewHolder(@NonNull AngajatViewHolder holder, int position) {
        Angajat angajat = lista.get(position);
        holder.tvNume.setText(angajat.getNume());
        holder.tvDepartament.setText(angajat.getDepartament());
        holder.tvVechime.setText(String.valueOf(angajat.getVechi()));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
