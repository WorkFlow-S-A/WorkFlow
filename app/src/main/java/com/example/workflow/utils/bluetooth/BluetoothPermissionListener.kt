package com.example.workflow.utils.bluetooth

interface BluetoothPermissionListener {
    fun requestBluetoothPermission()
    fun onPermissionGranted()
    fun onPermissionDenied()
}