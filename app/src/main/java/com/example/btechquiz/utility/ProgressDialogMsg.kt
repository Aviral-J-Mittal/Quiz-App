package com.example.btechquiz.utility

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import com.example.btechquiz.databinding.ProgressDialogBinding

class ProgressDialogMsg( myActivity: Activity) {
    var activity:Activity?=null
    var dialog:AlertDialog?=null
    var mProgressDialogBinding:ProgressDialogBinding?=null

    init {
        activity=myActivity
        mProgressDialogBinding= ProgressDialogBinding.inflate(activity!!.layoutInflater)
    }
    fun startLoadingDialog(message:String)
    {
        val builder=AlertDialog.Builder(activity)
        mProgressDialogBinding!!.progressMsg.text=message
        builder.setView(mProgressDialogBinding!!.root)
        builder.setCancelable(false)
        dialog=builder.create()
        dialog!!.show()


    }
    fun dismissDialog()
    {
        dialog!!.dismiss()
    }
}