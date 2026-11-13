package x.x.test;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrasRecyclerAdapter extends RecyclerView.Adapter<OrasRecyclerAdapter.OrasViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Oras oras, int position);

        void onItemLongClick(Oras oras, int position);
    }

    private final Context context;
    private final List<Oras> orase;
    private OnItemClickListener listener;

    public OrasRecyclerAdapter(Context context, List<Oras> orase) {
        this.context = context;
        this.orase = orase;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_oras, parent, false);
        return new OrasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrasViewHolder holder, int position) {
        Oras o = orase.get(position);

        if (o != null) {
            holder.tvItemNume.setText(o.getNume());
            holder.tvItemDetalii.setText(context.getString(R.string.item_detalii,
                    o.getRegiune(), (long) o.getPopulatie()));

            if (o.isResedinta()) {
                holder.tvItemResedinta.setVisibility(View.VISIBLE);
            } else {
                holder.tvItemResedinta.setVisibility(View.GONE);
            }

            if (o.getPopulatie() > 100000) {
                holder.tvItemNume.setTextColor(ContextCompat.getColor(context, R.color.oras_mare));
            } else {
                holder.tvItemNume.setTextColor(ContextCompat.getColor(context, R.color.oras_mic));
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(o, position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onItemLongClick(o, position);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return orase.size();
    }

    // Clasa care ține minte referințele către view-urile unui rând
    public static class OrasViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemNume, tvItemDetalii, tvItemResedinta;

        public OrasViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemNume = itemView.findViewById(R.id.tvItemNume);
            tvItemDetalii = itemView.findViewById(R.id.tvItemDetalii);
            tvItemResedinta = itemView.findViewById(R.id.tvItemResedinta);
        }
    }
}