package com.app.cosmeticapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private ImageView imageResult;
    private TextView textResultType;

    private static final Map<String, String> typeToImageMap = new HashMap<>();
    static {
        typeToImageMap.put("Oily", "https://res.cloudinary.com/dwghye8ir/image/upload/v1750582459/Oily_Skin_Result_joqnge.jpg");
        typeToImageMap.put("Dry", "https://res.cloudinary.com/dwghye8ir/image/upload/v1750582458/Dry_Skin_Result_qsrymx.jpg");
        typeToImageMap.put("Normal", "https://res.cloudinary.com/dwghye8ir/image/upload/v1750582459/Normal_Skin_Result_xhsxxr.jpg");
        typeToImageMap.put("Combination", "https://res.cloudinary.com/dwghye8ir/image/upload/v1750582458/Combination_Skin_Result_hao8ds.jpg");
        typeToImageMap.put("Sensitive", "https://res.cloudinary.com/dwghye8ir/image/upload/v1750582464/Sensitive_Skin_Result_gds3bz.jpg");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        imageResult = findViewById(R.id.imageResult);
        textResultType = findViewById(R.id.textResultType);

        String skinType = getIntent().getStringExtra("skinType");
        if (skinType != null && typeToImageMap.containsKey(skinType)) {
            textResultType.setText(skinType + " Skin");
            Glide.with(this).load(typeToImageMap.get(skinType)).into(imageResult);
        } else {
            textResultType.setText("Skin Type Not Identified");
        }
    }
}
