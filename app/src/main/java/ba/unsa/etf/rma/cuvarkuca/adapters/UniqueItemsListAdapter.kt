package ba.unsa.etf.rma.cuvarkuca.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ba.unsa.etf.rma.cuvarkuca.R
import ba.unsa.etf.rma.cuvarkuca.Utility
import com.google.android.material.color.MaterialColors

class UniqueItemsListAdapter(
    private val context: Context,
    private val items: MutableList<String>,
    private val onItemClicked: (dish: String?) -> Unit
) : ArrayAdapter<String>(context, 0, items) {

    init {
        val duplicates = items.groupBy { it.lowercase() }.filterValues { it.size > 1 }.keys
        if (duplicates.isNotEmpty()) throw IllegalArgumentException("Non-unique list")

        if (items.any { it.isEmpty() }) throw IllegalArgumentException("Empty item found")

        items.add(0, "")
    }

    private var selected: Int = -1
    private var duplicate: Int = -1

    fun size() = items.size - 1

    fun add(item: String) {
        duplicate = items.indexOfFirst { it.equals(item, ignoreCase = true) }
        if (duplicate == -1) items.add(item)
        notifyDataSetChanged()
    }

    fun get() = items.subList(1, items.size)

    fun modifySelectedItem(item: String) {
        val position = selected

        if (item.isEmpty()) items.removeAt(position)
        else items[position] = item

        selected = -1
        notifyDataSetChanged()
    }

    private fun onTextClicked(position: Int) {
        if (position == 0) return

        if (selected != position) {
            selected = position
            onItemClicked(items[position])
        } else {
            selected = -1
            onItemClicked(null)
        }

        notifyDataSetChanged()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val itemView = convertView ?: LayoutInflater
            .from(context)
            .inflate(R.layout.listview_item, parent, false)

        val textView: TextView = itemView.findViewById(R.id.listview_item_textview)
        var color: Int =
            if (position != selected) MaterialColors.getColor(textView, com.google.android.material.R.attr.colorOnSurface)
            else MaterialColors.getColor(textView, com.google.android.material.R.attr.colorSecondary)

        textView.text = items[position]
        textView.setTextColor(color)

        if (position == duplicate) {
            color = MaterialColors.getColor(textView, com.google.android.material.R.attr.colorError)
            Utility.setAndBackTextColor(textView, color)
            duplicate = -1
        }

        textView.setOnClickListener { onTextClicked(position) }
        return itemView
    }
}