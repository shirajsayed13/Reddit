package com.shiraj.reddit.data.comment

import android.os.Parcel
import android.os.Parcelable

data class RedditComment(
    val after: String?,
    val before: String?,
    val comments: List<RedditCommentItem>?
) : Parcelable {
    companion object {
        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.Creator<RedditComment> =
            object : Parcelable.Creator<RedditComment> {
                override fun createFromParcel(source: Parcel): RedditComment = RedditComment(source)
                override fun newArray(size: Int): Array<RedditComment?> = arrayOfNulls(size)
            }
    }

    constructor(source: Parcel) : this(
        source.readString(), source.readString(),
        source.createTypedArrayList(RedditCommentItem.CREATOR)!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(after)
        dest?.writeString(before)
        dest?.writeTypedList(comments)
    }
}

data class RedditCommentItem(
    val author: String?,
    val body: String?
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RedditCommentItem> =
            object : Parcelable.Creator<RedditCommentItem> {
                override fun createFromParcel(source: Parcel): RedditCommentItem =
                    RedditCommentItem(source)

                override fun newArray(size: Int): Array<RedditCommentItem?> = arrayOfNulls(size)
            }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(author)
        dest?.writeString(body)
    }
}