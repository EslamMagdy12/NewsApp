package com.example.newsapp.news.network.db

import androidx.room.TypeConverter
import com.example.newsapp.news.network.dto.Source

class Converter {
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}