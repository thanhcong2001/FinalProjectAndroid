package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

public class DetailStore extends AppCompatActivity {
    private TextView titleTxt, addressTxt, wifiTxt, descriptionTxt, distanceTxt;
    private Item_mapstore item;
    private ImageView pic;

    DecimalFormat formatter = new DecimalFormat("##,##");


    private Button directionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_store);

        initView();
        setVariable();

        directionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailStore.this, MapsActivity.class);
                intent.putExtra("latitude", item.getLatitude());
                intent.putExtra("longitude", item.getLongtitude());
                startActivity(intent);
            }
        });
    }

    private void setVariable() {
        item = (Item_mapstore) getIntent().getSerializableExtra("object");

        titleTxt.setText(item.getTitle());
        addressTxt.setText(item.getAddress());
        descriptionTxt.setText(item.getDescription());
        distanceTxt.setText(formatter.format(item.getDistance())+ "Km");

        if (item.isWifi()) {
            wifiTxt.setText("Wifi");
        } else {
            wifiTxt.setText("No Wifi");
        }

        int drawableResourceId = getResources().getIdentifier(item.getPic(), "drawable", getPackageName());
        Glide.with(this).load(drawableResourceId).into(pic);
    }

    private void initView() {
        titleTxt = findViewById(R.id.titleTxt);
        addressTxt = findViewById(R.id.addressTxt);
        wifiTxt = findViewById(R.id.wifiTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        distanceTxt = findViewById(R.id.distanceTxt);
        pic = findViewById(R.id.pic);

        directionBtn = findViewById(R.id.directionBtn);
    }


}
