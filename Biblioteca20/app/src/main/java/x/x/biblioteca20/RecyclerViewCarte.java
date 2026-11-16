package x.x.biblioteca20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewCarte extends RecyclerView.Adapter<RecyclerViewCarte.CarteViewHolder> {

    private final Context context;
    private final List<Carte> carte;

    public RecyclerViewCarte(Context context, List<Carte> carte){
        this.context = context;
        this.carte = carte;
    }

    @NonNull
    @Override
    public CarteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rand = LayoutInflater.from(context).inflate(R.layout.item_carte, parent, false);
        return new CarteViewHolder(rand);
    }

    @Override
    public void onBindViewHolder(@NonNull CarteViewHolder holder, int position) {
        Carte c = carte.get(position);
        if(c != null){
            holder.tvTitlu.setText(c.getTitlu());
            holder.tvAutor.setText(c.getAutor());
            holder.tvGen.setText(c.getGen());
            holder.tvGen.setVisibility(View.VISIBLE);
        }
    }

    public static class CarteViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitlu, tvAutor, tvGen, tvCitita;

        public CarteViewHolder(View v){
            super(v);
            tvTitlu = v.findViewById(R.id.tvTitlu);
            tvAutor = v.findViewById(R.id.tvAutor);
            tvGen = v.findViewById(R.id.tvGen);
            tvCitita = v.findViewById(R.id.tvCitita);

        }
    }

    @Override
    public int getItemCount() {
       return carte.size();
    }
}
