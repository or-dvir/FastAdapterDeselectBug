package com.sample.fastadapterdeselectbug

import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapterExtension
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.expandable.ExpandableExtension
import com.mikepenz.fastadapter.select.SelectExtension

//NOTE: these are taken from the FastAdapters` GitHub issues page at:
//https://github.com/mikepenz/FastAdapter/issues/660
inline fun <reified T : IAdapterExtension<Item>, Item : IItem<*, *>> FastAdapter<Item>.getExtension(): T? =
        getExtension(T::class.java)

inline val <Item : IItem<*, *>> FastAdapter<Item>.selected: List<Item>
    get() = getExtension<SelectExtension<Item>, Item>()?.selectedItems.orEmpty().toList()

fun <Item : IItem<*, *>> FastAdapter<Item>.collapseAll() =
        getExtension<ExpandableExtension<Item>, Item>()?.collapse()