package com.example.shoppinglist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class ScanActivity extends AppCompatActivity {

    private CodeScanner codeScanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_layout);
        CodeScannerView codeScannerView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this,codeScannerView);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ScanActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        if(ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED){
            codeScanner.startPreview();
        } else {
            ActivityCompat.requestPermissions(ScanActivity.this,
                    new String[] {Manifest.permission.CAMERA},Utilities.REQUEST_CAMERA_USAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Utilities.REQUEST_CAMERA_USAGE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                codeScanner.startPreview();
            } else {
                Toast.makeText(this, R.string.camera_permission, Toast.LENGTH_LONG).show();
                //getSupportFragmentManager().popBackStack();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }
}
