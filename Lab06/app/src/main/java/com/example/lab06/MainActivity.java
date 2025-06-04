package com.example.lab06;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/** @noinspection deprecation*/
public class MainActivity extends AppCompatActivity {

    EditText editHoten, editCMND, editBosung;
    CheckBox chkdocbao, chkdocsach, chkdoccode;
    RadioButton radtc, radcd, raddh;
    RadioGroup radioGroup;
    Button btnGuithongtin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các view
        editHoten = findViewById(R.id.editHoten);
        editCMND = findViewById(R.id.editCMND);
        editBosung = findViewById(R.id.editBosung);
        chkdocbao = findViewById(R.id.chkdocbao);
        chkdocsach = findViewById(R.id.chkdocsach);
        chkdoccode = findViewById(R.id.chkdoccode);
        radtc = findViewById(R.id.radtc);
        radcd = findViewById(R.id.radcd);
        raddh = findViewById(R.id.raddh);
        radioGroup = findViewById(R.id.radioGroup);
        btnGuithongtin = findViewById(R.id.btnGuithongtin);

        btnGuithongtin.setOnClickListener(v -> doShowInformation());
    }

    private void doShowInformation() {
        String ten = editHoten.getText().toString().trim();
        String cmnd = editCMND.getText().toString().trim();
        String bangCap;
        String soThich = "";
        String thongTinBoSung = editBosung.getText().toString().trim();

        // Kiểm tra tên
        if (ten.isEmpty() || ten.length() < 3) {
            editHoten.requestFocus();
            editHoten.selectAll();
            Toast.makeText(this, "Tên phải >= 3 ký tự", Toast.LENGTH_LONG).show();
            return;
        }

        // Kiểm tra CMND
        if (cmnd.length() != 9 || !cmnd.matches("\\d+")) {
            editCMND.requestFocus();
            editCMND.selectAll();
            Toast.makeText(this, "CMND phải đúng 9 chữ số", Toast.LENGTH_LONG).show();
            return;
        }

        // Lấy thông tin bằng cấp
        int selectedRadioId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioId == R.id.radtc) {
            bangCap = "Trung cấp";
        } else if (selectedRadioId == R.id.radcd) {
            bangCap = "Cao đẳng";
        } else if (selectedRadioId == R.id.raddh) {
            bangCap = "Đại học";
        } else {
            Toast.makeText(this, "Phải chọn bằng cấp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy thông tin sở thích
        int countSoThich = 0;
        if (chkdocbao.isChecked()) {
            soThich += "Đọc báo - ";
            countSoThich++;
        }
        if (chkdocsach.isChecked()) {
            soThich += "Đọc sách - ";
            countSoThich++;
        }
        if (chkdoccode.isChecked()) {
            soThich += "Đọc coding - ";
            countSoThich++;
        }

        if (countSoThich < 1) {
            Toast.makeText(this, "Phải chọn ít nhất 1 sở thích", Toast.LENGTH_SHORT).show();
            return;
        }

        // Loại bỏ dấu "-" cuối chuỗi sở thích nếu có
        soThich = soThich.substring(0, soThich.length() - 3);

        // Tạo và hiển thị AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin cá nhân");
        String message = "Họ tên: " + ten + "\n" +
                "CMND: " + cmnd + "\n" +
                "Bằng cấp: " + bangCap + "\n" +
                "Sở thích: " + soThich + "\n" +
                "Thông tin bổ sung: " + thongTinBoSung;
        builder.setMessage(message);
        builder.setPositiveButton("Đóng", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Question");
        b.setMessage("Are you sure you want to exit?");
        b.setIcon(android.R.drawable.ic_dialog_info);
        b.setPositiveButton("Yes", (dialogInterface, i) -> finish());
        b.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());
        b.create().show();
    }
}