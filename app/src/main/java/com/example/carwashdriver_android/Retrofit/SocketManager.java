package com.example.carwashdriver_android.Retrofit;
import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketManager {
    private static Socket mSocket;

    public static void initSocket() {
        if (mSocket == null) {
            try {
                mSocket = IO.socket("http://192.168.88.115:3000");
            } catch (URISyntaxException e) {
                Log.e("SOCKET", "Error al conectar: " + e.getMessage());
            }
        }
    }

    public static Socket getSocket() {
        return mSocket;
    }

    public static void connect() {
        if (mSocket != null && !mSocket.connected()) {
            mSocket.connect();
        }
    }

    public static void disconnect() {
        if (mSocket != null && mSocket.connected()) {
            mSocket.disconnect();
        }
    }

    public static void setListeners() {
        if (mSocket == null) return;

        mSocket.on(Socket.EVENT_CONNECT, args ->
                Log.d("SOCKET", "Conectado al servidor"));

        mSocket.on("mensaje", args -> {
            String msg = (String) args[0];
            Log.d("SOCKET", "Mensaje recibido: " + msg);
        });
    }

    public static void enviarMensaje(String mensaje) {
        if (mSocket != null) {
            mSocket.emit("mensaje", mensaje);
        }
    }

    public static void notifyUserConnected(String userId) {
        if (mSocket != null && mSocket.connected()) {
            mSocket.emit("user_connected", userId); // Asegúrate que el servidor escuche este evento
        }
    }

}