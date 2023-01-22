package com.thatnawfal.storyappdicoding.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

class AccountDataStore(private val ctx: Context) {

    suspend fun loginDataSet(_token: String, _name: String, _email: String){
        ctx.accountDataStore.edit {
            it[token] = _token
            it[email] = _email
            it[name] = _name
        }
    }

    suspend fun logoutDataSet(){
        ctx.accountDataStore.edit {
            it[token] = ""
            it[email] = ""
            it[name] = ""
        }
    }


    companion object {
        private const val DATASTORE_NAME = "account_preference"

        private val token = stringPreferencesKey("token_key")
        private val name = stringPreferencesKey("name_key")
        private val email = stringPreferencesKey("email_key")

        private val Context.accountDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}