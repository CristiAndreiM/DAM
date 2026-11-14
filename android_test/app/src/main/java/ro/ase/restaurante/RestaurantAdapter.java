package ro.ase.restaurante;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.Holder> {
    List<Restaurant> lista;

    public RestaurantAdapter(List<Restaurant> lista) {
        this.lista = lista;
    }

    public static class Holder extends RecyclerView.ViewHolder{
        TextView tvNume, tvBucatarie, tvRating, tvLivrare;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvNume = itemView.findViewById(R.id.tvNume);
            tvBucatarie = itemView.findViewById(R.id.tvBucatarie);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvLivrare = itemView.findViewById(R.id.tvLivrare);
        }
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rand = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        return new Holder(rand);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Restaurant r = lista.get(position);
        holder.tvNume.setText(r.getNume());
        holder.tvBucatarie.setText(r.getBucatarie());
        holder.tvRating.setText(String.valueOf(r.getRating()));
        holder.tvLivrare.setVisibility(r.isLivrare() ? View.VISIBLE : View.GONE);

        MainActivity activity = (MainActivity) holder.itemView.getContext();
        holder.itemView.setOnClickListener(v-> {
            int pos =  holder.getBindingAdapterPosition();
            Restaurant restaurant =lista.get(pos);

            if(restaurant == null)
                return;
            Intent intent = new Intent(activity,DetailsActivity.class);
            intent.putExtra("restaurant", restaurant);
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
