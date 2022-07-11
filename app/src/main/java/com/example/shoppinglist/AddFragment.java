package com.example.shoppinglist;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.shoppinglist.ViewModel.AddViewModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.create_new_item));
        }
        /*
         * aggiungiamo un listener al bottone "take a picture" per far partire un intent
         * di cattura dell'immagine fatta con la camera
         */
        view.findViewById(R.id.capture_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //un if per capire se vi è un activity camera per gestire questo intent
                if(takePictureIntent.resolveActivity(activity.getPackageManager()) != null){
                    activity.startActivityForResult(takePictureIntent,Utilities.REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        /*
         * recuperiamo da quest'altra parte invece la nostra imageView per fare sì che quando Fragment
         * (view) viene creato, venga mostrata l'ultima immagine che è stata scattata (può anche essere un drawable)
         */
        ImageView imageView = view.findViewById(R.id.item_promotionImageView);
        AddViewModel addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(AddViewModel.class);

        addViewModel.getImageBitMap().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });

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
                try{
                    if(bitmap != null){
                        saveImage(bitmap,activity);
                    }
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        });

        view.findViewById(R.id.scan_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scanIntent = new Intent(activity, ScanActivity.class);
                startActivity(scanIntent);
            }
        });

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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.findItem(R.id.app_bar_search).setVisible(false);
    }

}
