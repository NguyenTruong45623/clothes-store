package com.example.bt2.until

import android.Manifest
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog

fun showPermissionRationale(context: Context,  requestPermissionsLauncher: ActivityResultLauncher<String>) {
    AlertDialog.Builder(context)
        .setTitle("Yêu cầu quyền")
        .setMessage("Ứng dụng cần quyền này để hoạt động đúng. Vui lòng cấp quyền để tiếp tục.")
        .setPositiveButton("OK") { _, _ ->
            requestPermissionsLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        .setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        .create()
        .show()
}