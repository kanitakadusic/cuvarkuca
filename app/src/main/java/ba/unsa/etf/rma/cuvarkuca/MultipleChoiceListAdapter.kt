package ba.unsa.etf.rma.cuvarkuca

import android.content.Context

class MultipleChoiceListAdapter<T : EnumWithDescription>(
    context: Context,
    items: List<T>,
    private val atLeast: Int = 0
) : ChoiceListAdapter<T>(
    context,
    items,
    Pair(R.drawable.ic_checkbox_checked, R.drawable.ic_checkbox_unchecked)
) {
    private var selected: HashSet<Int> = (0 until atLeast).toHashSet()

    fun getSelectedItems(): List<T> {
        return selected.map { position ->
            super.items[position]
        }
    }

    override fun toggleSelection(position: Int) {
        if (selected.contains(position) && selected.size > atLeast) selected.remove(position)
        else selected.add(position)

        super.notifyDataSetChanged()
    }

    override fun isSelectedItem(position: Int): Boolean = selected.contains(position)
}