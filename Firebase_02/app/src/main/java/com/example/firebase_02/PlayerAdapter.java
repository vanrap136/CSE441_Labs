package com.example.firebase_02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.core.view.View;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private final List<Player> players;
    private final Context context;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public PlayerAdapter(Context context, List<Player> players) {
        this.context = context;
        this.players = players;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("RestrictedApi") android.view.View v = LayoutInflater.from(context).inflate(R.layout.item_player, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Player p = players.get(position);
        holder.txtName.setText(p.username);
        holder.txtCode.setText(p.member_code);
        holder.txtHometown.setText("Quê quán: " + p.hometown);

        holder.btnDelete.setOnClickListener(v -> db.collection("players").document(p.member_code).delete()
                .addOnSuccessListener(unused -> Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show()));

        holder.btnEdit.setOnClickListener(v -> {
            // Gọi Dialog cập nhật (bạn sẽ tạo tiếp theo)
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtCode, txtHometown;
        Button btnEdit, btnDelete;

        public ViewHolder(@SuppressLint("RestrictedApi") @NonNull View itemView) {
            super(itemView);
            try {
                itemView.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                itemView.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                itemView.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                itemView.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                itemView.wait(R.id.btnDelete);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public ViewHolder(android.view.View v) {
            super();
        }
    }
}

