package me.liuyun.bjutlgn.db

import android.content.Context
import android.util.Log

import me.liuyun.bjutlgn.entity.User

class UserManager(context: Context) {

    private val dbHelper: DBHelper = DBHelper(context)

    fun insertUser(user: User): Boolean {
        try {
            val position = (dbHelper.getUserDao()!!.queryRaw(dbHelper.getUserDao()!!.queryBuilder().selectRaw("MAX(position)").prepareStatementString()).firstResult[0] ?: "-1").toInt()
            user.position = position + 1
            dbHelper.getUserDao()!!.createOrUpdate(user)
            return true
        } catch (e: Exception) {
            Log.e(TAG, "insertUser", e)
        }

        return false
    }

    fun updateUser(user: User): Boolean {
        try {
            dbHelper.getUserDao()!!.update(user)
            return true
        } catch (e: Exception) {
            Log.e(TAG, "updateUser", e)
        }

        return false
    }

    fun deleteUser(id: Int): Boolean {
        try {
            dbHelper.getUserDao()!!.deleteById(id)
            return true
        } catch (e: Exception) {
            Log.e(TAG, "deleteUser", e)
        }

        return false
    }

    val allUsers: MutableList<User>
        get() {
            try {
                return dbHelper.getUserDao()!!.queryBuilder().orderBy("position", true).query()
            } catch (e: Exception) {
                Log.e(TAG, "getAllUsers", e)
            }
            return mutableListOf()
        }

    companion object {
        private val TAG = "UserManager"
    }

}
