package ba.unsa.etf.rma.cuvarkuca.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ba.unsa.etf.rma.cuvarkuca.R
import ba.unsa.etf.rma.cuvarkuca.models.EnumWithDescription

abstract class ChoiceListAdapter<T : EnumWithDescription>(
    context: Context,
    protected val items: List<T>,
    private val icons: Pair<Int, Int>
) : ArrayAdapter<T>(context, 0, items) {

    protected abstract fun toggleSelection(position: Int)
    protected abstract fun isSelectedItem(position: Int): Boolean

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val itemView = convertView ?: LayoutInflater
            .from(context)
            .inflate(R.layout.listview_item, parent, false)

        val textView: TextView = itemView.findViewById(R.id.listview_item_textview)
        val drawable: Int = if (isSelectedItem(position)) icons.first else icons.second

        textView.text = items[position].description
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0)

        textView.setOnClickListener { toggleSelection(position) }
        return itemView
    }
}