package ba.unsa.etf.rma.cuvarkuca

import android.content.Context

class SingleListAdapter<T : EnumString>(
    context: Context,
    enumList: List<T>
) : ChoiceListAdapter<T>(
    context,
    enumList,
    Pair(R.drawable.radio_checked_24px, R.drawable.radio_unchecked_24px)
) {

    private var selected: Int = 0

    fun getSelectedItem(): Int = selected

    override fun toggleSelection(position: Int) {
        selected = position
        super.notifyDataSetChanged()
    }

    override fun isSelectedItem(position: Int): Boolean = selected == position
}