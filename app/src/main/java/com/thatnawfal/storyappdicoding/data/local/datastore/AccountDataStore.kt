package com.thatnawfal.storyappdicoding.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.thatnawfal.storyappdicoding.data.remote.response.LoginResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountDataStore(private val ctx: Context) {

    suspend fun loginDataSet(_token: String, _name: String, _userid: String){
        ctx.accountDataStore.edit {
            it[token] = _token
            it[userid] = _userid
            it[name] = _name
        }
    }

    suspend fun logoutDataSet(){
        ctx.accountDataStore.edit {
            it[token] = ""
            it[userid] = ""
            it[name] = ""
        }
    }

    fun getData() : Flow<LoginResult> {
        return ctx.accountDataStore.data.map {
            LoginResult(
                it[name] ?: "",
                it[token] ?: "",
                it[userid]
            )
        }
    }


    companion object {
        private const val DATASTORE_NAME = "account_preference"

        private val token = stringPreferencesKey("token_key")
        private val name = stringPreferencesKey("name_key")
        private val userid = stringPreferencesKey("userid_key")

        private val Context.accountDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}