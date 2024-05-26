package ba.unsa.etf.rma.cuvarkuca.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ba.unsa.etf.rma.cuvarkuca.models.Focus
import ba.unsa.etf.rma.cuvarkuca.R

class FocusSpinnerAdapter (
    context: Context,
    items: List<Focus>
) : ArrayAdapter<Focus>(context, 0, items) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val itemView: View = convertView ?: LayoutInflater
            .from(context)
            .inflate(R.layout.spinner_title, parent, false)

        val textView: TextView = itemView.findViewById(R.id.spinner_title_textview)
        val focus: Focus = getItem(position)!!

        textView.setText(R.string.select_focus)
        textView.setCompoundDrawablesWithIntrinsicBounds(focus.iconSolid, 0, 0, 0)

        return itemView
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val itemView: View = convertView ?: LayoutInflater
            .from(context)
            .inflate(R.layout.spinner_item, parent, false)

        val textView: TextView = itemView.findViewById(R.id.spinner_item_textview)
        val focus: Focus = getItem(position)!!

        textView.setText(focus.name)
        textView.setCompoundDrawablesWithIntrinsicBounds(focus.iconOutline, 0, 0, 0)

        return itemView
    }
}