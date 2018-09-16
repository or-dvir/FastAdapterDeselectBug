package com.sample.fastadapterdeselectbug

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.view.ActionMode
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.expandable.ExpandableExtension
import com.mikepenz.fastadapter_extensions.ActionModeHelper
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AppCompatActivity()
{
    private lateinit var mAdapterEpisodes: ModelAdapter<EpisodeEntity, EpisodeAdapterItem>
    private lateinit var mAdapterRv: FastAdapter<EpisodeAdapterItem>
    private lateinit var mActionModeHelper: ActionModeHelper<EpisodeAdapterItem>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv.apply {
            addItemDecoration(DividerItemDecoration(this@ActivityMain, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(this@ActivityMain, RecyclerView.VERTICAL, false)
            setupAdapter()
            adapter = mAdapterRv
        }

        mAdapterEpisodes.add(
                EpisodeEntity("item 1"),
                EpisodeEntity("item 2"),
                EpisodeEntity("item 3"),
                EpisodeEntity("item 4"),
                EpisodeEntity("item 5"),
                EpisodeEntity("item 6"),
                EpisodeEntity("item 7"),
                EpisodeEntity("item 8"),
                EpisodeEntity("item 9"),
                EpisodeEntity("item 10"))
    }

    private fun setupAdapter()
    {
        mAdapterEpisodes = ModelAdapter { episode ->
            val subItem = listOf(EpisodeAdapterSubItem("${episode.text} sub item"))
            val finalItem = EpisodeAdapterItem(episode)
            finalItem.withSubItems(subItem)

            finalItem
        }

        mAdapterRv = FastAdapter<EpisodeAdapterItem>().addAdapter(0, mAdapterEpisodes)
                .withSelectable(true)
                .withSelectOnLongClick(true)
                .withMultiSelect(true)
                .withOnPreClickListener { v, adapter, item, position ->
                    //if actionMode is active, it means the the users' clicks
                    //should select another item and NOT expand it
                    item.autoExpand = !mActionModeHelper.isActive

                    mActionModeHelper.onClick(item) ?: false
                }
                .withOnLongClickListener { v, adapter, item, position ->
                    //this is just so selecting an item will not trigger "onClick()"
                    true
                }
                .withOnPreLongClickListener { v, adapter, item, position ->
                    //if we have no actionMode we do not consume the event
                    mActionModeHelper.onLongClick(this, position) != null
                }
                .withSelectionListener { item, selected ->

                    //NOTE:
                    //the "selected" parameter is the state of the item AFTER
                    //the selection/de-selection

                    val numSelected = mAdapterRv.selected.size

                    //when selecting our first item on the list,
                    //collapse all expanded items.
                    //this will make it easier for the user to select more items without having
                    //to scroll past the expanded items (which may be long)
                    if (numSelected == 1)
                        mAdapterRv.collapseAll()

                    //NOTE:
                    //this if-else statement makes it so that
                    //once an item is selected, selecting other items could be done via
                    //a regular click (tap).
                    //once all items are de-selected, re-enable long click
                    //for selecting items

                    if (selected)
                        mAdapterRv.withSelectOnLongClick(false)
                    else if (numSelected == 0)
                        mAdapterRv.withSelectOnLongClick(true)
                }

        mAdapterRv.addExtension(ExpandableExtension<EpisodeAdapterItem>()
                                        .withOnlyOneExpandedItem(true))

        mActionModeHelper = ActionModeHelper(mAdapterRv,
                                             R.menu.context_menu,
                                             object : ActionMode.Callback
                                             {
                                                 override fun onActionItemClicked(mode: ActionMode,
                                                                                  item: MenuItem): Boolean
                                                 {
                                                     mode.finish()
                                                     return true
                                                 }

                                                 override fun onCreateActionMode(mode: ActionMode,
                                                                                 menu: Menu) : Boolean
                                                 {
                                                     return true
                                                 }

                                                 override fun onPrepareActionMode(mode: ActionMode?,
                                                                                  menu: Menu?) : Boolean
                                                 {
                                                     return false
                                                 }

                                                 override fun onDestroyActionMode(mode: ActionMode?)
                                                 {
                                                     /*do nothing*/
                                                 }
                                             })
    }
}
