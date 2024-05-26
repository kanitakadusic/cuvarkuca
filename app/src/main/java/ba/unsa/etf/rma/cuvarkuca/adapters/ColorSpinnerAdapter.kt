package ba.unsa.etf.rma.cuvarkuca.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import ba.unsa.etf.rma.cuvarkuca.Utility
import com.google.android.material.R
import java.security.InvalidParameterException

class ColorSpinnerAdapter (
    private val context: Context,
    private val items: List<String>,
    private val colors: List<Int>
) : ArrayAdapter<String>(context, 0, items) {

    private var isEnabledAppearance: Boolean = true
    private val colorOnDisabled: Int = Utility.getAttrColor(context, R.attr.colorOutlineVariant)

    init {
        if (items.size != colors.size)
            throw InvalidParameterException("Invalid list size")
    }

    fun setEnabledAppearance(enabled: Boolean) {
        isEnabledAppearance = enabled
        notifyDataSetChanged()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val itemView: View = convertView ?: LayoutInflater
            .from(context)
            .inflate(ba.unsa.etf.rma.cuvarkuca.R.layout.spinner_title, parent, false)

        val textView: TextView = itemView.findViewById(ba.unsa.etf.rma.cuvarkuca.R.id.spinner_title_textview)

        val icon = ContextCompat.getDrawable(context,
            ba.unsa.etf.rma.cuvarkuca.R.drawable.ic_check_filled
        )
        icon?.setTint(
            if (!isEnabledAppearance) colorOnDisabled
            else ContextCompat.getColor(context, colors[position])
        )

        textView.text = ""
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)

        return itemView
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val itemView: View = convertView ?: LayoutInflater
            .from(context)
            .inflate(ba.unsa.etf.rma.cuvarkuca.R.layout.spinner_item, parent, false)

        val textView: TextView = itemView.findViewById(ba.unsa.etf.rma.cuvarkuca.R.id.spinner_item_textview)

        val icon = ContextCompat.getDrawable(context,
            ba.unsa.etf.rma.cuvarkuca.R.drawable.ic_circle_filled
        )
        icon?.setTint(ContextCompat.getColor(context, colors[position]))

        textView.text = items[position]
        textView.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)

        return itemView
    }
}