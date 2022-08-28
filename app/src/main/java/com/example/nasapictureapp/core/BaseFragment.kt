package com.example.nasapictureapp.core

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nasapictureapp.core.viewmodel.BaseViewModel
import com.example.nasapictureapp.util.CustomProgressDialog


open class BaseFragment : Fragment() {

    val progressBar = CustomProgressDialog()
    private val TAG = "BaseFragment"

    protected fun setupViewModel(vm: BaseViewModel) {
        vm.errorMessage.observe(viewLifecycleOwner) {
            handleErrorMessage(it)
        }
        vm.loading.observe(viewLifecycleOwner) {
//            handleLoading(it)
        }
    }


    protected fun setupDefaultLoader(
        vm: BaseViewModel
    ) {
        vm.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            progressBar.show(requireContext())
        } else {
            progressBar.dismiss()
        }
    }


    private fun handleErrorMessage(error: DataError) {
        when (error) {
            is DataError.Code -> {
                Toast.makeText(
                    context,
                    getString(
                        resources.getIdentifier(
                            "error_code_" + error.errorCode,
                            "string",
                            context?.packageName
                        )
                    ), Toast.LENGTH_SHORT
                ).show()
            }
            is DataError.Message -> {
                Toast.makeText(context, error.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected fun requestPermissionSafely(permission: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || context?.checkSelfPermission(
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}
