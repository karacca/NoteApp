package com.task.noteapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author karacca
 * @date 13.03.2022
 */

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val description: String,
    val imageUrl: String? = null,
    val createdDate: Long,
    var modifiedDate: Long? = null
) {

    val prettyCreatedDate: String
        get() = SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.getDefault()
        ).format(
            Date(createdDate)
        )

    companion object {

        val Mock = Note(
            title = "Title ${System.currentTimeMillis()}",
            description = "Lorem Ipsum is simply dummy text of the printing and " +
                "typesetting industry. Lorem Ipsum has been the industry's " +
                "standard dummy text ever since the 1500s, when an unknown " +
                "printer took a galley of type and scrambled it to make a type specimen book",
            imageUrl = "https://picsum.photos/600",
            createdDate = System.currentTimeMillis(),
            modifiedDate = System.currentTimeMillis()
        )
    }
}
