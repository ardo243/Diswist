package com.example.diswis.destinasiharusnya

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object WishlistManager {
    private const val PREF_NAME = "wishlist_prefs"
    private const val KEY_WISHLIST_NAMES = "wishlist_names"
    
    private var prefs: SharedPreferences? = null
    private val gson = Gson()
    private var inMemoryWishlistNames: MutableSet<String>? = null
    
    fun init(context: Context) {
        if (prefs == null) {
            prefs = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }
    
    fun addToWishlist(destination: Destination) {
        val names = getWishlistNames()
        if (names.add(destination.name)) {
            saveWishlistNames(names)
        }
    }
    
    fun removeFromWishlist(destinationName: String) {
        val names = getWishlistNames()
        if (names.remove(destinationName)) {
            saveWishlistNames(names)
        }
    }
    
    fun isInWishlist(destinationName: String): Boolean {
        return getWishlistNames().contains(destinationName)
    }
    
    private fun getWishlistNames(): MutableSet<String> {
        inMemoryWishlistNames?.let { return it }
        
        val json = prefs?.getString(KEY_WISHLIST_NAMES, null)
        val names = if (json != null) {
            try {
                val type = object : TypeToken<MutableSet<String>>() {}.type
                gson.fromJson<MutableSet<String>>(json, type) ?: mutableSetOf()
            } catch (e: Exception) {
                mutableSetOf()
            }
        } else {
            // Backup/Migration: check if old format exists
            val oldJson = prefs?.getString("wishlist", null)
            if (oldJson != null) {
                try {
                    val type = object : TypeToken<List<Map<String, Any>>>() {}.type
                    val oldList = gson.fromJson<List<Map<String, Any>>>(oldJson, type)
                    oldList?.mapNotNull { it["name"] as? String }?.toMutableSet() ?: mutableSetOf()
                } catch (e: Exception) {
                    mutableSetOf()
                }
            } else {
                mutableSetOf()
            }
        }
        inMemoryWishlistNames = names
        return names
    }

    fun getWishlist(): List<Destination> {
        val names = getWishlistNames()
        return DestinationData.list.filter { destination -> 
            names.any { it.equals(destination.name, ignoreCase = true) }
        }
    }
    
    private fun saveWishlistNames(names: Set<String>) {
        inMemoryWishlistNames = names.toMutableSet()
        val json = gson.toJson(names)
        prefs?.edit()?.putString(KEY_WISHLIST_NAMES, json)?.apply()
    }
    
    fun clearWishlist() {
        inMemoryWishlistNames = mutableSetOf()
        prefs?.edit()?.remove(KEY_WISHLIST_NAMES)?.remove("wishlist")?.apply()
    }
}
