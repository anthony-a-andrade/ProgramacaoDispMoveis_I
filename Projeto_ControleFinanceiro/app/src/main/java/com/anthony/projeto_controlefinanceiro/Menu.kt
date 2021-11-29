package com.anthony.projeto_controlefinanceiro

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import org.jetbrains.anko.startActivity

class Menu(val activity: Activity, val home: LinearLayout, val add: LinearLayout, val list: LinearLayout, val btn: ImageView, val ignore: LinearLayout) {
    private var isExpanded = false

    fun enable() {
        isExpanded = false

        btn.setOnClickListener {
            isExpanded = !isExpanded

            home.visibility = getVisibility(isExpanded)
            add.visibility = getVisibility(isExpanded)
            list.visibility = getVisibility(isExpanded)
            btn.setImageResource(if (isExpanded) R.drawable.img_retrair else R.drawable.img_expandir)
        }

        ignore.background = activity.getDrawable(R.drawable.bg_border)
        home.setOnClickListener { select(home) }
        add.setOnClickListener { select(add) }
        list.setOnClickListener { select(list) }
    }

    private fun select(panel: LinearLayout) {
        if (panel.id != ignore.id)
        {
            activity.finish()

            when (panel.id) {
                home.id -> activity.startActivity<HomeActivity>()
                add.id -> activity.startActivity<AddActivity>()
                list.id -> activity.startActivity<ListActivity>()
            }
        }
    }

    private fun getVisibility(isExpanded: Boolean) : Int  {
        return if (isExpanded) View.VISIBLE else View.GONE
    }
}