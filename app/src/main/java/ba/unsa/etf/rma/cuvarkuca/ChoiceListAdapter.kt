package ba.unsa.etf.rma.cuvarkuca

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

abstract class ChoiceListAdapter<T : EnumString>(
    context: Context,
    protected val enumList: List<T>,
    private val icons: Pair<Int, Int>
) : ArrayAdapter<T>(context, 0, enumList) {

    protected abstract fun toggleSelection(position: Int)
    protected abstract fun isSelectedItem(position: Int): Boolean

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = convertView ?: LayoutInflater
            .from(context)
            .inflate(R.layout.listview_item, parent, false)

        val text: TextView = view.findViewById(R.id.listview_item_TEXTVIEW)
        text.text = enumList[position].string

        val drawable = if (isSelectedItem(position)) icons.first else icons.second
        text.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0)

        text.setOnClickListener {
            toggleSelection(position)
        }

        return view
    }
}