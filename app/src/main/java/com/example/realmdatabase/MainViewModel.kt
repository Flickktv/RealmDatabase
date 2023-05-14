package com.example.realmdatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.RealmModel
import io.realm.kotlin.createObject
import io.realm.kotlin.delete
import java.util.UUID


class MainViewModel : ViewModel() {
    private var realm: Realm = Realm.getDefaultInstance()

    val allContacts: LiveData<List<Contact>>
        get() = getAllContacts()

    fun addContact(name: String, surname: String, number: String) {
        realm.executeTransaction {
            val model = it.createObject<Contact>(UUID.randomUUID().toString()).apply {
                this.name = name
                this.surname = surname
                this.number = number
            }

            it.insertOrUpdate(model)
        }
    }

    fun updateContact() {
        realm.executeTransaction {
            val model = it.copyFromRealm(Contact()).apply {
                this.name = name
                this.surname = surname
                this.number = number
            }
            it.insertOrUpdate(model)
        }
    }

    fun deleteContact(position: Int) {
        realm.executeTransaction {
            val model = realm.where(Contact::class.java).findAll()
            model.deleteFromRealm(position)
            it.insertOrUpdate(model)
        }
    }

    private fun getAllContacts(): MutableLiveData<List<Contact>> {
        val list = MutableLiveData<List<Contact>>()
        val allContacts = realm.where(Contact::class.java).findAll()
        list.value = allContacts.subList(0, allContacts.size)
        return list
    }

}