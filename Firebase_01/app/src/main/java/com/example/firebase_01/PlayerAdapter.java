package com.example.firebase_01;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private List<Player> playerList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(Player player);
        void onDeleteClick(Player player);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PlayerAdapter(List<Player> playerList) {
        this.playerList = playerList;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = playerList.get(position);
        // Sử dụng getString để lấy chuỗi từ resources và định dạng nó
        holder.tvPlayerId.setText(holder.itemView.getContext().getString(R.string.player_id_format, player.getId()));
        holder.tvPlayerName.setText(holder.itemView.getContext().getString(R.string.player_name_format, player.getName()));
        holder.tvPlayerEmail.setText(holder.itemView.getContext().getString(R.string.player_email_format, player.getEmail()));
        holder.tvPlayerPhone.setText(holder.itemView.getContext().getString(R.string.player_phone_format, player.getPhone()));

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(player);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(player);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlayerId, tvPlayerName, tvPlayerEmail, tvPlayerPhone;
        Button btnEdit, btnDelete;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlayerId = itemView.findViewById(R.id.tvPlayerId);
            tvPlayerName = itemView.findViewById(R.id.tvPlayerName);
            tvPlayerEmail = itemView.findViewById(R.id.tvPlayerEmail);
            tvPlayerPhone = itemView.findViewById(R.id.tvPlayerPhone);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updatePlayerList(List<Player> newList) {
        this.playerList = newList;
        notifyDataSetChanged();
    }
}