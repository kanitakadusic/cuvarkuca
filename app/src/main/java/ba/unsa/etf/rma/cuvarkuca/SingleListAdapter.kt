package ba.unsa.etf.rma.cuvarkuca

import android.content.Context

class SingleListAdapter<T : EnumString>(
    context: Context,
    enumList: List<T>
) : ChoiceListAdapter<T>(
    context,
    enumList,
    Pair(R.drawable.ic_radio_checked, R.drawable.ic_radio_unchecked)
) {

    private var selected: Int = 0

    fun getSelectedItem(): T = super.enumList[selected]

    override fun toggleSelection(position: Int) {
        selected = position
        super.notifyDataSetChanged()
    }

    override fun isSelectedItem(position: Int): Boolean = selected == position
}