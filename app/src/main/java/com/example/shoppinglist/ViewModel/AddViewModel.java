package com.example.shoppinglist.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppinglist.R;
import com.example.shoppinglist.Utilities;

public class AddViewModel extends AndroidViewModel {
    /*
     * imageBitMap è un riferimento all'immagine che abbiamo scattato
     * E' un MutableLiveData perchè i dati sono modificati spesso
     */
    private final MutableLiveData<Bitmap> imageBitMap = new MutableLiveData<>();
    private final Application application;

    public AddViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        initializeBitMap();
    }

    /*
     * Siccome inizialmente non abbiamo una specifica immagine, dobbiamo inizializzare la BitMap
     * con un qualche drawable (in questo caso possiamo usare il drawable Add)
     */
    private void initializeBitMap() {
        /*
         * recupero la risorsa Drawable che vogliamo, poi utilizziamo un metodo che creiamo
         * in Utilities per trasformare in Drawable in Bitmap
         */
        Drawable drawable = ResourcesCompat.getDrawable(application.getResources(),
                R.drawable.ic_baseline_add_24, application.getTheme());
        Bitmap bitmap = Utilities.drawableToBitmap(drawable);

        imageBitMap.setValue(bitmap);
    }

    /*questi due metodi ci servono per settare e restituire la imageBitMap*/
    public void setImageBitMap(Bitmap bitMap) {imageBitMap.setValue(bitMap);}

    public MutableLiveData<Bitmap> getImageBitMap(){
        return imageBitMap;
    }
}
