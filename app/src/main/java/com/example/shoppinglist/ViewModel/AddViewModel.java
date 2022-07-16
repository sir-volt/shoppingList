package com.example.shoppinglist.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppinglist.DataBase.ItemRepository;
import com.example.shoppinglist.ItemEntity;
import com.example.shoppinglist.R;
import com.example.shoppinglist.Utilities;

public class AddViewModel extends AndroidViewModel {
    /*
     * imageBitMap è un riferimento all'immagine che abbiamo scattato
     * E' un MutableLiveData perchè i dati sono modificati spesso
     */
    private final MutableLiveData<Bitmap> imageBitMap = new MutableLiveData<>();
    private final Application application;
    private final ItemRepository repository;

    public AddViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = ItemRepository.getInstance(application);
    }

    /* NON CI SERVE PIU' PERCHÈ ABBIAMO UN IMMAGINE SPECIFICATA NEL LAYOUT
     * Siccome inizialmente non abbiamo una specifica immagine, dobbiamo inizializzare la BitMap
     * con un qualche drawable (in questo caso possiamo usare il drawable Add)
     */
    private void initializeBitMap() {
        /*
         * recupero la risorsa Drawable che vogliamo, poi utilizziamo un metodo che creiamo
         * in Utilities per trasformare in Drawable in Bitmap
         */
        Drawable drawable = ResourcesCompat.getDrawable(application.getResources(),
                R.drawable.ic_baseline_image_white_24, application.getTheme());
        Bitmap bitmap = Utilities.drawableToBitmap(drawable);

        imageBitMap.setValue(bitmap);
    }

    /*questi due metodi ci servono per settare e restituire la imageBitMap*/
    public void setImageBitMap(Bitmap bitMap) {imageBitMap.setValue(bitMap);}

    public MutableLiveData<Bitmap> getImageBitMap(){
        return imageBitMap;
    }

    public void addItem(ItemEntity itemEntity){
        repository.addItemToDatabase(itemEntity);
    }
}
