package com.thatnawfal.storyappdicoding.presentation.widgets

import android.content.Context
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class StackRemoteViewFactor(
    private val mContext : Context
) : RemoteViewsService.RemoteViewsFactory {

    private val storiesImageList = ArrayList<Bitmap>()

    override fun onCreate() {
        // Not Yet Implemented
    }

    override fun onDataSetChanged() {

    }

    override fun onDestroy() {
        // Do Nothing
    }

    override fun getCount(): Int = storiesImageList.size

    override fun getViewAt(position: Int): RemoteViews {
        TODO("Not yet implemented")
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(p0: Int): Long = 0

    override fun hasStableIds(): Boolean = true

}