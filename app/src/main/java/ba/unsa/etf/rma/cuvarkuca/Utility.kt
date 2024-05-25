package ba.unsa.etf.rma.cuvarkuca

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Utility {
    fun getAttrColor(context: Context, attr: Int): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attr, typedValue, true)
        return typedValue.data
    }

    fun setViewAvailability(view: View, available: Boolean) {
        view.isFocusable = available
        view.isFocusableInTouchMode = available
        view.isClickable = available
        view.isEnabled = available
    }

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

    fun setAndBackTextColor(
        textView: TextView,
        newColor: Int
    ) {
        val scope = CoroutineScope(Dispatchers.Main)
        val originalColor = textView.currentTextColor

        scope.launch {
            textView.setTextColor(newColor)
            delay(1500)
            textView.setTextColor(originalColor)
        }
    }

    const val MIN_TEXT_LENGTH = 2
    const val MAX_TEXT_LENGTH = 20

    fun validateTextLength(context: Context, editText: EditText) {
        val icon = ContextCompat.getDrawable(context, R.drawable.ic_error_filled)
        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)

        if (editText.text.length < MIN_TEXT_LENGTH) {
            editText.setError(context.getString(R.string.too_short), icon)
        } else if (editText.text.length > MAX_TEXT_LENGTH) {
            editText.setError(context.getString(R.string.too_long), icon)
        } else {
            editText.error = null
        }
    }

    fun setTextLengthValidator(context: Context, editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) { validateTextLength(context, editText) }
        })
    }
}