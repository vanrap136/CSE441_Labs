package com.example.firebase_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private PlayerAdapter playerAdapter;
    private List<Player> playerList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewPlayers = findViewById(R.id.recyclerViewPlayers);
        recyclerViewPlayers.setLayoutManager(new LinearLayoutManager(this));
        playerList = new ArrayList<>();
        playerAdapter = new PlayerAdapter(playerList);
        recyclerViewPlayers.setAdapter(playerAdapter);

        Button btnAddPlayer = findViewById(R.id.btnAddPlayer);

        mDatabase = FirebaseDatabase.getInstance().getReference("players");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                playerList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Player player = postSnapshot.getValue(Player.class);
                    if (player != null) {
                        player.setId(postSnapshot.getKey());
                        playerList.add(player);
                    }
                }
                playerList.sort(Comparator.comparing(Player::getId));
                playerAdapter.updatePlayerList(playerList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, getString(R.string.toast_load_fail, databaseError.getMessage()), Toast.LENGTH_SHORT).show();
                Log.e("FirebaseError", databaseError.getMessage());
            }
        });

        btnAddPlayer.setOnClickListener(v -> showAddEditPlayerDialog(null));

        playerAdapter.setOnItemClickListener(new PlayerAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Player player) {
                showAddEditPlayerDialog(player);
            }

            @Override
            public void onDeleteClick(Player player) {
                deletePlayer(player);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private String generateNextPlayerId() {
        int maxIdNum = 0;
        for (Player player : playerList) {
            try {
                String id = player.getId();
                if (id != null && id.startsWith("MBR")) {
                    int num = Integer.parseInt(id.substring(3));
                    if (num > maxIdNum) {
                        maxIdNum = num;
                    }
                }
            } catch (NumberFormatException e) {
                // Ignore non-MBRxxx format IDs
            }
        }
        return String.format("MBR%03d", maxIdNum + 1);
    }

    private void showAddEditPlayerDialog(final Player playerToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_add_player, null);
        builder.setView(dialogView);

        final EditText etName = dialogView.findViewById(R.id.etPlayerName);
        final EditText etEmail = dialogView.findViewById(R.id.etPlayerEmail);
        final EditText etPhone = dialogView.findViewById(R.id.etPlayerPhone);

        String dialogTitle = getString(R.string.dialog_add_title);
        String positiveButtonText = getString(R.string.dialog_add_positive);

        if (playerToEdit != null) {
            dialogTitle = getString(R.string.dialog_edit_title);
            positiveButtonText = getString(R.string.dialog_update_positive);
            etName.setText(playerToEdit.getName());
            etEmail.setText(playerToEdit.getEmail());
            etPhone.setText(playerToEdit.getPhone());
        }

        builder.setTitle(dialogTitle);

        builder.setPositiveButton(positiveButtonText, (dialog, which) -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(MainActivity.this, getString(R.string.toast_fill_all_info), Toast.LENGTH_SHORT).show();
                return;
            }

            if (playerToEdit == null) {
                String newId = generateNextPlayerId();
                Player newPlayer = new Player(newId, name, email, phone);
                mDatabase.child(newId).setValue(newPlayer)
                        .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, getString(R.string.toast_add_success), Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(MainActivity.this, getString(R.string.toast_add_fail, e.getMessage()), Toast.LENGTH_SHORT).show());
            } else {
                playerToEdit.setName(name);
                playerToEdit.setEmail(email);
                playerToEdit.setPhone(phone);
                mDatabase.child(playerToEdit.getId()).setValue(playerToEdit)
                        .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, getString(R.string.toast_update_success), Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(MainActivity.this, getString(R.string.toast_update_fail, e.getMessage()), Toast.LENGTH_SHORT).show());
            }
        });

        builder.setNegativeButton(getString(R.string.dialog_negative_button), (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void deletePlayer(Player player) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm_delete_title))
                .setMessage(getString(R.string.confirm_delete_message, player.getName(), player.getId()))
                .setPositiveButton(getString(R.string.confirm_delete_positive), (dialog, which) -> mDatabase.child(player.getId()).removeValue()
                        .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, getString(R.string.toast_delete_success), Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(MainActivity.this, getString(R.string.toast_delete_fail, e.getMessage()), Toast.LENGTH_SHORT).show()))
                .setNegativeButton(getString(R.string.dialog_negative_button), null)
                .show();
    }
}