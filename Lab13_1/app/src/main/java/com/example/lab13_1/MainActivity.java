package com.example.lab13_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    MultiAutoCompleteTextView multiAutoCompleteTextView;
    String[] provinces = {"Hà Nội", "Huế", "Hà Giang", "Hà Nam Ninh", "Bắc Kạn", "Lạng Sơn",
            "Điện Biên", "Lai Châu", "Sơn La", "Yên Bái", "Hòa Bình", "Thái Nguyên",
            "Phú Thọ", "Tuyên Quang", "Cao Bằng", "Bắc Giang", "Quảng Ninh", "Hải Phòng",
            "Thái Bình", "Nam Định", "Ninh Bình", "Hà Tĩnh", "Quảng Bình", "Quảng Trị",
            "Thừa Thiên Huế", "Đà Nẵng", "Quảng Nam", "Quảng Ngãi", "Bình Định", "Phú Yên",
            "Khánh Hòa", "Gia Lai", "Kon Tum", "Đắk Lắk", "Đắk Nông", "Lâm Đồng",
            "Bình Phước", "Bình Dương", "Đồng Nai", "Tây Ninh", "Bà Rịa - Vũng Tàu",
            "Hồ Chí Minh", "Long An", "Tiền Giang", "Bến Tre", "Trà Vinh", "Vĩnh Long",
            "Đồng Tháp", "An Giang", "Kiên Giang", "Cần Thơ", "Hậu Giang", "Sóc Trăng",
            "Bạc Liêu", "Cà Mau"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTextView = findViewById(R.id.autoComplete);
        multiAutoCompleteTextView = findViewById(R.id.multiAutoComplete);

        // ArrayAdapter for AutoCompleteTextView
        ArrayAdapter<String> adapterAutoComplete = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, provinces);
        autoCompleteTextView.setAdapter(adapterAutoComplete);

        // ArrayAdapter for MultiAutoCompleteTextView
        ArrayAdapter<String> adapterMultiAutoComplete = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, provinces);
        multiAutoCompleteTextView.setAdapter(adapterMultiAutoComplete);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}