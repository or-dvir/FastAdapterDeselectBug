package com.sample.fastadapterdeselectbug

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import com.mikepenz.fastadapter.expandable.items.AbstractExpandableItem
import kotlinx.android.synthetic.main.list_item_episode_sub.view.*

class EpisodeAdapterSubItem(private val text: String)
    : AbstractExpandableItem<EpisodeAdapterItem, EpisodeAdapterSubItem.ViewHolder, EpisodeAdapterSubItem>()
{
    override fun getType() = R.id.fastadapter_id_Episode_SubItem
    override fun getLayoutRes() = R.layout.list_item_episode_sub
    override fun getViewHolder(v: View) = ViewHolder(v)
    override fun isSelectable() = false
    //disabling the item so clicking it will not trigger anything
    override fun isEnabled() = false

    override fun bindView(holder: ViewHolder, payloads: MutableList<Any>)
    {
        super.bindView(holder, payloads)
        @Suppress("DEPRECATION") //the replacement method requires API 24
        holder.view.listItem_episode_sub_tv_description.text = text
    }

    override fun unbindView(holder: ViewHolder)
    {
        super.unbindView(holder)
        holder.view.listItem_episode_sub_tv_description.text = ""
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}