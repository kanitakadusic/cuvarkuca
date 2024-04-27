package ba.unsa.etf.rma.cuvarkuca

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.android.material.color.MaterialColors

class StringListAdapter(
    private val context: Context,
    private val itemList: MutableList<String>,
    private val onItemClicked: (dish: String?) -> Unit
) : ArrayAdapter<String>(context, 0, itemList) {

    private var selected: Int? = null

    fun addItem(item: String) {
        itemList.add(item)
        notifyDataSetChanged()
    }

    fun getItems(): List<String> = itemList

    fun modifySelectedItem(item: String) {
        val position = selected ?: return

        if (item.isEmpty()) itemList.removeAt(position)
        else itemList[position] = item

        selected = null
        notifyDataSetChanged()
    }

    private fun onTextClicked(position: Int) {
        if (selected != position) {
            selected = position
            onItemClicked(itemList[position])
        } else {
            selected = null
            onItemClicked(null)
        }

        notifyDataSetChanged()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = convertView ?: LayoutInflater
            .from(context)
            .inflate(R.layout.listview_item, parent, false)

        val text: TextView = view.findViewById(R.id.listview_item_TEXTVIEW)
        text.text = itemList[position]

        val color: Int =
            if (position != selected) MaterialColors.getColor(text, com.google.android.material.R.attr.colorOnSurface)
            else MaterialColors.getColor(text, com.google.android.material.R.attr.colorSecondary)

        text.setTextColor(color)
        text.setOnClickListener { onTextClicked(position) }

        return view
    }
}