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

    init {
        val duplicates = itemList
            .groupBy { it.lowercase() }
            .filterValues { it.size > 1 }
            .keys

        if (duplicates.isNotEmpty())
            throw IllegalArgumentException("Non-unique list")
    }

    private var selected: Int = -1
    private var duplicate: Int = -1

    fun addItem(item: String) {
        duplicate = itemList.indexOfFirst { it.equals(item, ignoreCase = true) }

        if (duplicate == -1) itemList.add(item)
        notifyDataSetChanged()
    }

    fun getItems(): List<String> {
        if (itemList.isNotEmpty() && itemList[0].isEmpty())
            itemList.removeFirst()

        return itemList
    }

    fun modifySelectedItem(item: String) {
        val position = selected

        if (item.isEmpty()) itemList.removeAt(position)
        else itemList[position] = item

        selected = -1
        notifyDataSetChanged()
    }

    private fun onTextClicked(position: Int) {
        if (itemList[position].isEmpty()) return

        if (selected != position) {
            selected = position
            onItemClicked(itemList[position])
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
        val view = convertView ?: LayoutInflater
            .from(context)
            .inflate(R.layout.listview_item, parent, false)

        val text: TextView = view.findViewById(R.id.listview_item_TEXTVIEW)
        text.text = itemList[position]

        var color: Int =
            if (position != selected) MaterialColors.getColor(text, com.google.android.material.R.attr.colorOnSurface)
            else MaterialColors.getColor(text, com.google.android.material.R.attr.colorSecondary)

        text.setTextColor(color)

        if (position == duplicate) {
            color = MaterialColors.getColor(text, com.google.android.material.R.attr.colorError)
            Utility.setAndRevertTextColor(text, color)
            duplicate = -1
        }

        text.setOnClickListener { onTextClicked(position) }

        return view
    }
}