package com.example.firebase_02;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.media.PlayerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    List<Player> players = new ArrayList<>();
    PlayerAdapter adapter;
    RecyclerView recyclerView;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btnAdd);

        adapter = new PlayerAdapter() {
            @Override
            public void play() {

            }

            @Override
            public void pause() {

            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadPlayers();

        btnAdd.setOnClickListener(v -> showDialog());
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm hội viên");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        EditText edtName = new EditText(this);
        edtName.setHint("Tên hội viên");
        layout.addView(edtName);
        // Có thể thêm các trường khác như quê quán, avatar...

        builder.setView(layout);

        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String name = edtName.getText().toString();
            @SuppressLint("DefaultLocale") String code = "MBR" + String.format("%03d", players.size() + 1);
            Player player = new Player(code, name, "", "", "Hà Nội", "TP.HCM", 0, 0);
            db.collection("players").document(code).set(player)
                    .addOnSuccessListener(unused -> Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show());
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }


    private void loadPlayers() {
        db.collection("players").addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(this, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show();
                return;
            }
            players.clear();
            assert value != null;
            for (QueryDocumentSnapshot doc : value) {
                Player p = doc.toObject(Player.class);
                players.add(p);
            }
            adapter.notifyDataSetChanged();
        });
    }
}
