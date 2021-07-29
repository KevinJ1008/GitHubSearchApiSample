package com.kevin1008.githubsearchapisample.epoxy

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.imageview.ShapeableImageView
import com.kevin1008.basecore.base.KotlinEpoxyHolder
import com.kevin1008.githubsearchapisample.R
import com.kevin1008.imageloader.loadImage

@EpoxyModelClass
abstract class SearchResultItemEpoxyModel : EpoxyModelWithHolder<SearchResultItemEpoxyModel.ViewHolder>() {

    @EpoxyAttribute
    var imageUrl: String? = null

    @EpoxyAttribute
    var userType: String? = null

    @EpoxyAttribute
    var userName: String? = null

    override fun getDefaultLayout(): Int {
        return R.layout.item_search_result
    }

    override fun bind(holder: ViewHolder) {
        super.bind(holder)
        holder.avatar.loadImage(imageUrl, R.drawable.default_avatar)
        holder.type.text = userType
        holder.name.text = userName
    }

    class ViewHolder : KotlinEpoxyHolder() {
        val avatar by bind<ShapeableImageView>(R.id.img_avatar)
        val type by bind<TextView>(R.id.text_type)
        val name by bind<TextView>(R.id.text_name)
    }
}