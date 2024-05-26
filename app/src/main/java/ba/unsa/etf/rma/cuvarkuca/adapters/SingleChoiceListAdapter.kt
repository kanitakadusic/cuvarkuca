package ba.unsa.etf.rma.cuvarkuca.adapters

import android.content.Context
import ba.unsa.etf.rma.cuvarkuca.R
import ba.unsa.etf.rma.cuvarkuca.models.EnumWithDescription

class SingleChoiceListAdapter<T : EnumWithDescription>(
    context: Context,
    items: List<T>
) : ChoiceListAdapter<T>(
    context,
    items,
    Pair(R.drawable.ic_radio_checked, R.drawable.ic_radio_unchecked)
) {
    private var selected: Int = 0

    fun getSelectedItem(): T = super.items[selected]

    override fun toggleSelection(position: Int) {
        selected = position
        super.notifyDataSetChanged()
    }

    override fun isSelectedItem(position: Int): Boolean = selected == position
}