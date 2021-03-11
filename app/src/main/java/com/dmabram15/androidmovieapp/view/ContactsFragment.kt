package com.dmabram15.androidmovieapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dmabram15.androidmoviesapp.databinding.ContactsFragmentBinding
import android.Manifest
import androidx.appcompat.app.AlertDialog
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.ContactsContract
import com.dmabram15.androidmoviesapp.R
import com.dmabram15.androidmoviesapp.databinding.ContactsCardBinding

const val REQUEST_CODE = 15

class ContactsFragment : Fragment() {

    private lateinit var binding: ContactsFragmentBinding

    companion object {
        fun newInstance() = ContactsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ContactsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun checkPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    getContacts()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    AlertDialog.Builder(it)
                        .setTitle(getString(R.string.contact_permittion_dialog_title))
                        .setMessage(getString(R.string.contact_permittion_dialog_message))
                        .setPositiveButton(getString(R.string.contact_permittion_dialog_allow_button_text)) { _, _ ->
                            requestPermission()
                        }
                        .create()
                        .show()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts()
                } else {
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("Access to contacts")
                            .setMessage("Need to show contacts in app")
                            .setNegativeButton("DISMISS") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                    }
                }
                return
            }
        }
    }

    private fun getContacts() {
        context?.let {
            val contentResolver = it.contentResolver
            val cursorWithContacts: Cursor? = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.PhoneLookup.DISPLAY_NAME + " ASC"
            )

            cursorWithContacts?.let { cursor ->
                for (i in 1 until cursor.count) {
                    cursor.moveToPosition(i)
                    val name = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    )
                    val id = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID)
                    )
                    val phone = getPhoneNumber(id)
                    addView(name, phone)
                }
            }
            cursorWithContacts?.close()
        }
    }

    private fun getPhoneNumber(id : String?) : String? {
        val phoneCursor = context?.contentResolver?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone._ID + " = " + id,
            null,
            null
        )

        phoneCursor?.let {
            if (phoneCursor.count > 0) {
                var phone : String?
                for (i in 0 until phoneCursor.count) {
                    phoneCursor.moveToPosition(i)
                    phone = phoneCursor.getString(
                        phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    )
                    if (phone != null) return phone
                }
            }
            phoneCursor.close()
        }
        return null
    }

    private fun addView(name: String?, phone: String?) {
        val contactCardViewBinding = ContactsCardBinding.inflate(layoutInflater)
        val cardView = contactCardViewBinding.root

        name?.let {
            contactCardViewBinding.nameContactTextView.text = it
        }
        phone.let {
            contactCardViewBinding.phoneNumberContactsTextView.text = it ?: getString(R.string.without_phone_number)
        }

        binding.contractsContainerLinearLayout.addView(cardView)
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)
    }
}