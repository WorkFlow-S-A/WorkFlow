package com.example.workflow.utils.bluetooth

import android.Manifest
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat

class BluetoothService : Service() {

    private val binder = LocalBinder()
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var permissionListener: BluetoothPermissionListener? = null

    inner class LocalBinder : Binder() {
        fun getService(): BluetoothService {
            return this@BluetoothService
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        initializeBluetooth()
        return START_STICKY
    }

    fun initializeBluetooth() {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        if (bluetoothAdapter == null) {
            Log.d(TAG, "Bluetooth not supported")
            return
        }

        if (!bluetoothAdapter!!.isEnabled) {
            Log.d(TAG, "Bluetooth is enabled")
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            enableBtIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            // Verificar permiso ACCESS_FINE_LOCATION antes de iniciar la actividad
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                permissionListener?.requestBluetoothPermission()
            } else {
                // Si ya se tiene el permiso, iniciar la actividad para habilitar Bluetooth
                Log.d(TAG, "Permission granted")
                startActivity(enableBtIntent)
            }
        }
    }

    fun setPermissionListener(listener: BluetoothPermissionListener) {
        permissionListener = listener
    }

    fun removePermissionListener() {
        permissionListener = null
    }

    // Métodos para operaciones Bluetooth
    fun startDiscovery() {
        Log.d("Bluetooth", "Comienzo de startDiscovery")
        // Verificar permiso ACCESS_FINE_LOCATION antes de iniciar la actividad
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            permissionListener?.requestBluetoothPermission()
        } else {
            bluetoothAdapter?.startDiscovery()
            Log.d("Bluetooth", "Se tienen los permisos en startDiscovery")
            Handler(Looper.getMainLooper()).postDelayed({
                stopDiscovery()
            }, 10000)
            val filter = IntentFilter()
            filter.addAction(BluetoothDevice.ACTION_FOUND)
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            registerReceiver(bluetoothReceiver, filter)
        }
    }

    private val bluetoothReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        Log.d("bluetoothReceiver", "$device")
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Log.d("bluetoothReceiver", "Búsqueda de dispositivos Bluetooth finalizada")
                }
            }
        }
    }

    fun stopDiscovery() {
        // Verificar permiso ACCESS_FINE_LOCATION antes de iniciar la actividad
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            permissionListener?.requestBluetoothPermission()
        } else {
            bluetoothAdapter?.cancelDiscovery()
        }
    }

    fun connectToDevice(device: BluetoothDevice) {
        // TODO: implementar la lógica para conectarse a un dispositivo Bluetooth específico
    }

    companion object {
        private const val TAG = "BluetoothService"
    }
}
