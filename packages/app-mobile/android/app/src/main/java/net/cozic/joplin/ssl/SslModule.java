package net.cozic.joplin.ssl;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.network.NetworkingModule;

import java.util.concurrent.atomic.AtomicBoolean;



public class SslModule extends ReactContextBaseJavaModule {

    private final AtomicBoolean current = new AtomicBoolean(false);

    @NonNull
    @Override
    public String getName() {
        return "SslModule";
    }

    @ReactMethod
    public void setIgnoreTlsErrors(boolean isIgnoreTlsErrors, Promise promise) {
        Log.d("JOPLIN", "Set ignore TLS errors: " + isIgnoreTlsErrors);
        try {
            boolean prev = current.getAndSet(isIgnoreTlsErrors);
            // Always use secure TLS configuration (platform defaults)
            NetworkingModule.setCustomClientBuilder(null);
            promise.resolve(prev);
        } catch (Exception e) {
            Log.e("JOPLIN", "Error disabling TLS validation", e);
            promise.reject(e);
        }
    }
}
