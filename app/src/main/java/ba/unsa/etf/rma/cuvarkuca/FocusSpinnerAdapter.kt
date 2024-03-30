package ba.unsa.etf.rma.cuvarkuca

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class FocusSpinnerAdapter (
    context: Context,
    focusList: List<Focus>
) : ArrayAdapter<Focus>(context, 0, focusList) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view: View = convertView ?: LayoutInflater
            .from(context)
            .inflate(R.layout.spinner_view_focus, parent, false)

        val textView: TextView = view.findViewById(R.id.spinner_view_focus_TEXTVIEW_select)
        val item: Focus? = getItem(position)

        if (item != null) {
            textView.setText(R.string.select_the_focus)
            textView.setCompoundDrawablesWithIntrinsicBounds(item.id.solidIcon, 0, 0, 0)
        }

        return view
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view: View = convertView ?: LayoutInflater
            .from(context)
            .inflate(R.layout.spinner_item_focus, parent, false)

        val textView: TextView = view.findViewById(R.id.spinner_item_focus_TEXTVIEW_focus)
        val item: Focus? = getItem(position)

        if (item != null) {
            textView.setText(item.id.name)
            textView.setCompoundDrawablesWithIntrinsicBounds(item.id.outlineIcon, 0, 0, 0)
        }

        return view
    }
}