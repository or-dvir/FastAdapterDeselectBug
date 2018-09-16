package com.sample.fastadapterdeselectbug

import android.support.v7.widget.RecyclerView
import android.view.View
import com.mikepenz.fastadapter.commons.utils.FastAdapterUIUtils
import com.mikepenz.fastadapter.expandable.items.ModelAbstractExpandableItem
import kotlinx.android.synthetic.main.list_item_episode.view.*

class EpisodeAdapterItem(episode: EpisodeEntity, var autoExpand: Boolean = true)
    : ModelAbstractExpandableItem<EpisodeEntity, EpisodeAdapterItem, EpisodeAdapterItem.ViewHolder, EpisodeAdapterSubItem>(episode)
{
    override fun getType() = R.id.fastadapter_id_Episode
    override fun getLayoutRes() = R.layout.list_item_episode
    override fun getViewHolder(v: View) = ViewHolder(v)
    override fun isAutoExpanding() = autoExpand
    override fun isSelectable() = true

    override fun bindView(holder: ViewHolder, payloads: MutableList<Any>)
    {
        super.bindView(holder, payloads)

        val view = holder.view
        val context = view.context

        //replacement method requires API 23 (app min is 19)
        @Suppress("DEPRECATION")
        view.background = FastAdapterUIUtils
                .getSelectableBackground(context,
                                         context.resources.getColor(R.color.colorPrimary),
                                         true)

        view.listItem_episode_tv.text = model.text
    }

    override fun unbindView(holder: ViewHolder)
    {
        super.unbindView(holder)
        holder.view.listItem_episode_tv.text = ""
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}