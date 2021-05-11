package es.javierserrano.beersearch.app.view.custom

import android.content.Context
import android.util.AttributeSet
import com.bumptech.glide.Glide

class GlideImageView : androidx.appcompat.widget.AppCompatImageView {
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context) : super(context)

    fun setImageURL(imageURL: String?) {
        imageURL?.let {
            Glide.with(this).load(imageURL).into(this)
        }
    }
}