package ba.unsa.etf.rma.cuvarkuca

import android.view.View
import android.widget.ListView
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Utility {
    companion object {
        fun adjustListViewHeight(listView: ListView) {
            val adapter = listView.adapter ?: return
            var itemHeight = 0

            if (adapter.count != 0) {
                val itemView: View = adapter.getView(0, null, listView)
                itemView.measure(
                    View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.UNSPECIFIED
                )

                itemHeight = itemView.measuredHeight
            }

            val params = listView.layoutParams
            params.height = (itemHeight + listView.dividerHeight) * adapter.count
            listView.layoutParams = params

            listView.requestLayout()
        }

        fun setAndRevertTextColor(
            textView: TextView,
            newColor: Int
        ) {
            val coroutineScope = CoroutineScope(Dispatchers.Main)

            val originalColor = textView.currentTextColor
            textView.setTextColor(newColor)

            coroutineScope.launch {
                delay(1500)
                textView.setTextColor(originalColor)
            }
        }
    }
}