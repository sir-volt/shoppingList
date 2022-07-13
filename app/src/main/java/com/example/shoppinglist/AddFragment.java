package com.example.shoppinglist;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.shoppinglist.DataBase.ItemRepository;
import com.example.shoppinglist.ViewModel.AddViewModel;

import org.slf4j.helpers.Util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddFragment extends Fragment {

    private static final String LOG_TAG = "AddFragment";
    public static final int REQUEST_CODE = 10;

    private ItemRepository itemRepository;
    private ItemEntity itemEntity;
    private String itemName, itemImage;
    private Double itemPrice;
    private EditText itemNameText, itemPriceText;
    AppCompatActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        itemRepository = ItemRepository.getInstance(getActivity().getApplication());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (AppCompatActivity) getActivity();
        if(activity != null){
            Utilities.setUpToolbar(activity, getString(R.string.create_new_item));



            /*
             * aggiungiamo un listener al bottone "take a picture" per far partire un intent
             * di cattura dell'immagine fatta con la camera
             */
            view.findViewById(R.id.capture_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(activity.checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                        invokeCamera(activity);
                    } else {
                        String[] permissionRequested = {Manifest.permission.CAMERA};
                        activity.requestPermissions(permissionRequested, Utilities.REQUEST_CAMERA_USAGE);
                    }

                }
            });
            /*
             * recuperiamo da quest'altra parte invece la nostra imageView per fare sì che quando Fragment
             * (view) viene creato, venga mostrata l'ultima immagine che è stata scattata (può anche essere un drawable)
             */
            ImageView imageView = view.findViewById(R.id.item_promotionImageView);
            AddViewModel addViewModel = new ViewModelProvider(activity).get(AddViewModel.class);

            addViewModel.getImageBitMap().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
                @Override
                public void onChanged(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            });


            itemNameText = view.findViewById(R.id.name_edittext);
            itemPriceText = view.findViewById(R.id.price_edittext);

            /*
             * quando viene cliccato il pulsante "fab_done" l'immagine deve essere salvata
             * per fare questo andiamo a recuperare la bitmap dell'immagine che abbiamo in questo
             * momento nella ImageView e utilizziamo il metodo privato saveImage all'interno
             * di MediaStore API (così l'immagine continua ad esistere anche senza app)
             */
            view.findViewById(R.id.fab_done).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bitmap bitmap = addViewModel.getImageBitMap().getValue();
                    String imageUriString;

                    try{
                        if(bitmap != null){
                            //saveImage(bitmap,activity);
                            imageUriString = String.valueOf(saveImageUri(bitmap, activity));
                        } else{
                            //Se l'immagine è vuota, carico l'URI di un icona
                            imageUriString = "ic_baseline_image_not_supported_24";
                        }
                        if(validateInput(itemNameText, itemPriceText)){
                            itemEntity = new ItemEntity(itemName, itemPrice, imageUriString);
                            addViewModel.addItem(itemEntity);
                            addViewModel.setImageBitMap(null);
                            activity.getSupportFragmentManager().popBackStack();
                        }
                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                    //itemEntity = new ItemEntity(null);

                    //itemRepository.insertItem(null);
                }
            });

            view.findViewById(R.id.scan_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent scanIntent = new Intent(activity, ScanActivity.class);
                    startActivityForResult(scanIntent, REQUEST_CODE);
                }
            });

        } else {
            Log.e(LOG_TAG,"Activity is Null");

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Utilities.REQUEST_CAMERA_USAGE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                invokeCamera(activity);
            } else {
                Toast.makeText(activity, "Unable to invoke camera without permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void invokeCamera(AppCompatActivity activity) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //un if per capire se vi è un activity camera per gestire questo intent
        if(takePictureIntent.resolveActivity(activity.getPackageManager()) != null){
            activity.startActivityForResult(takePictureIntent,Utilities.REQUEST_IMAGE_CAPTURE);
        }
    }

    private void saveImage(Bitmap bitmap, Activity activity) throws FileNotFoundException{
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ITALY).format(new Date());
        String name = "PNG_" + timestamp + ".png";

        ContentResolver contentResolver = activity.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");

        Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Log.d("AddFragment", String.valueOf(imageUri));

        OutputStream outputStream = contentResolver.openOutputStream(imageUri);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        try{
            outputStream.close();
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Salva l'immagine scattata dall'utente nella galleria
     * @param bitmap l'immagine scattata
     * @param activity activity
     * @throws FileNotFoundException se il file non è stato creato
     */
    private Uri saveImageUri(Bitmap bitmap, Activity activity) throws FileNotFoundException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ITALY)
                .format(new Date());
        String name = "JPEG_" + timestamp + ".jpg";

        ContentResolver contentResolver = activity.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");

        Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues);

        Log.d(LOG_TAG, String.valueOf(imageUri));

        OutputStream outputStream = contentResolver.openOutputStream(imageUri);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageUri;

    }

    //todo eliminare
    private Boolean validateInput(EditText itemNameText, EditText itemPriceText){
        if(itemNameText.getText()==null) {
            Toast.makeText(getActivity(), getString(R.string.insert_item_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (itemNameText.getText().toString().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.insert_item_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (itemPriceText.getText()==null){
            //Se la casella di testo è vuota allora metterò 0
            itemPrice = 0.0;
        } else if (itemPriceText.getText().toString().equals("") || !(isNumeric(itemPriceText.getText().toString()))){
            itemPrice = 0.0;
        } else {
            itemPrice = Double.valueOf(itemPriceText.getText().toString());
        }
        itemName = itemNameText.getText().toString();
        Log.d(LOG_TAG, "Item Name: " + itemName + " - Item Price: " + itemPrice);
        return true;
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.findItem(R.id.app_bar_search).setVisible(false);
        menu.findItem(R.id.app_bar_settings).setVisible(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String productResponse;
        if(requestCode == REQUEST_CODE){
            if (resultCode == Utilities.RESULT_OK){
                String productCode = data.getExtras().getString(Utilities.BARCODE_SCAN_RESULT);
                Log.d(LOG_TAG, "Codice trovato: " + productCode);
                productResponse = Utilities.checkProductCode(productCode);
                if(productResponse!=null){
                    Toast.makeText(activity, "Prodotto trovato!",Toast.LENGTH_LONG).show();
                    Log.d(LOG_TAG, "Prodotto: " + productResponse);
                    itemNameText.setText(productResponse);
                } else{
                    Toast.makeText(activity, "Prodotto non trovato",Toast.LENGTH_LONG).show();
                }

            } else if (resultCode == Utilities.RESULT_FAIL){

            }
        }
    }
}
