package ba.unsa.etf.rma.cuvarkuca

import android.content.Context

class MultipleListAdapter<T : EnumString>(
    context: Context,
    enumList: List<T>,
    private val atLeast: Int = 0
) : ChoiceListAdapter<T>(
    context,
    enumList,
    Pair(R.drawable.ic_checkbox_checked, R.drawable.ic_checkbox_unchecked)
) {

    private var selected: HashSet<Int> = HashSet()

    init {
        selected = hashSetOf()

        for (i in 0 until atLeast)
            selected.add(i)
    }

    fun getSelectedItems(): List<T> {
        return selected.map { position ->
            super.enumList[position]
        }
    }

    override fun toggleSelection(position: Int) {
        if (selected.contains(position) && selected.size > atLeast) selected.remove(position)
        else selected.add(position)

        super.notifyDataSetChanged()
    }

    override fun isSelectedItem(position: Int): Boolean = selected.contains(position)
}