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
    private var stringList: List<String>,
    private val onItemClicked: (dish: String) -> Unit
) : ArrayAdapter<String>(context, 0, stringList) {

    private var selected: Int? = null

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = convertView ?: LayoutInflater
            .from(context)
            .inflate(R.layout.listview_item, parent, false)

        val text: TextView = view.findViewById(R.id.listview_item_TEXTVIEW)
        text.text = stringList[position]

        val color: Int =
            if (position != selected) MaterialColors.getColor(text, com.google.android.material.R.attr.colorOnSurface)
            else MaterialColors.getColor(text, com.google.android.material.R.attr.colorSecondary)

        text.setTextColor(color)

        text.setOnClickListener {
            onItemClicked(stringList[position])

            selected = if (selected != position) position else null
            notifyDataSetChanged()
        }

        return view
    }
}