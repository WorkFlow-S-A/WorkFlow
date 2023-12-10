package com.example.workflow

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.workflow.ui.theme.WorkFlowTheme
import com.example.workflow.ui.WorkFlowApp
import com.example.workflow.utils.bluetooth.BluetoothPermissionListener
import com.example.workflow.utils.bluetooth.BluetoothService
import com.example.workflow.utils.bluetooth.BluetoothServiceHolder
import com.example.workflow.utils.bluetooth.BluetoothUtils
import com.example.workflow.utils.bluetooth.BluetoothUtils.REQUEST_ENABLE_BLUETOOTH
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity(), BluetoothPermissionListener {
    var bluetoothService: BluetoothService? = null

    private val bluetoothServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BluetoothService.LocalBinder
            bluetoothService = binder.getService()
            Log.d("MainActivity", "Servicio Bluetooth conectado")
            bluetoothService?.setPermissionListener(this@MainActivity)

            Log.d("Bluetooth", "$bluetoothService")
            BluetoothServiceHolder.bluetoothService = bluetoothService
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // Manejar la desconexión del servicio
            bluetoothService = null
            Log.d("Bluetooth", "Se ha desconectado del servicio Bluetooth")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkFlowTheme {
                WorkFlowApp()
            }
        }
        val serviceIntent = Intent(this, BluetoothService::class.java)
        startService(serviceIntent)

        // Vincular el servicio Bluetooth
        bindService(serviceIntent, bluetoothServiceConnection, Context.BIND_AUTO_CREATE)

    }


    override fun onStart() {
        super.onStart()
        val serviceIntent = Intent(this, BluetoothService::class.java)
        bindService(serviceIntent, bluetoothServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(bluetoothServiceConnection)
    }

    override fun requestBluetoothPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_SCAN),
            REQUEST_ENABLE_BLUETOOTH
        )
    }

    override fun onPermissionGranted() {
        val serviceIntent = Intent(this, BluetoothService::class.java)
    }

    override fun onPermissionDenied() {
        val serviceIntent = Intent(this, BluetoothService::class.java)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WorkFlowTheme {
        WorkFlowApp()
    }
}




