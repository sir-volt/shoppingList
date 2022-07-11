package com.example.shoppinglist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppinglist.ViewModel.AddViewModel;

import pl.coderion.model.Product;
import pl.coderion.model.ProductResponse;
import pl.coderion.service.OpenFoodFactsWrapper;
import pl.coderion.service.impl.OpenFoodFactsWrapperImpl;

public class FoodFactsTestActivity extends AppCompatActivity {

    private static final String LOG_TAG = "FoodFactsTestActivity";
    private EditText barCodeET;
    private Button button;
    private TextView textViewResult, textViewInput;
    OpenFoodFactsWrapper wrapper;
    ProductResponse productResponse;
    Product product;
    String nesquikCode = "8000550502827";
    String nutella = "8000500357729";
    String the = "8000500267370";
    String testCode =    "737628064502";
    String testCode2 =   "0737628064502";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_facts_test);
        barCodeET = findViewById(R.id.editTextBarcode);
        button = findViewById(R.id.buttonBarcode);
        textViewResult = findViewById(R.id.textViewBarcode);
        textViewInput = findViewById(R.id.textViewBarcodeInput);

        String codice = the;

        wrapper = new OpenFoodFactsWrapperImpl();
        productResponse = wrapper.fetchProductByCode(codice);

        if (!productResponse.isStatus()) {
            Log.d(LOG_TAG,"Status: " + productResponse.getStatusVerbose());
            return;
        }

        product = productResponse.getProduct();
        Log.d(LOG_TAG, "Prodotto cercato " + codice  + ";\nRoba ottenuta: " + product.getProductName()
                + "\nGeneric name: " + product.getGenericName()
                + "\nBrand: " + product.getBrands());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplication(), barCodeET.getText().toString(), Toast.LENGTH_SHORT).show();
                //textViewInput.setText(barCodeET.getText().toString());

            }
        });

    }



}
