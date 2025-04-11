package com.example.carwashdriver_android.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermisosUtils {

        public static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

        public static boolean tienePermisosUbicacion(Context context) {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        }

        public static void solicitarPermisosUbicacion(Activity activity) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

        public static void manejarResultadoPermisos(int requestCode, @NonNull int[] grantResults, Runnable onPermisoConcedido) {
            if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onPermisoConcedido.run(); // Acción a ejecutar si se conceden los permisos
                }
            }
        }
}
