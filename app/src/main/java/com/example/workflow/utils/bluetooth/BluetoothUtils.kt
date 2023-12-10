package com.example.workflow.utils.bluetooth

object BluetoothUtils {
    const val REQUEST_ENABLE_BLUETOOTH = 1
    const val MAC_ADDRESS = "00:11:22:33:44:55"
}

object BluetoothServiceHolder {
    var bluetoothService: BluetoothService? = null
}