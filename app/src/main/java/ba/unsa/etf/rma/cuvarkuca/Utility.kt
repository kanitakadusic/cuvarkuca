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

    fun getAttrColor(
        context: Context,
        attr: Int
    ): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attr, typedValue, true)
        return typedValue.data
    }

    fun setViewAvailability(
        view: View,
        available: Boolean
    ) {
        view.isFocusable = available
        view.isFocusableInTouchMode = available
        view.isClickable = available
        view.isEnabled = available
    }

    fun adjustListViewHeight(
        listView: ListView
    ) {
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

    fun validatePlantNameForm(
        input: String
    ): Int? {
        val regex = Regex("^[a-zA-ZčćđšžČĆĐŠŽ]+( [a-zA-ZčćđšžČĆĐŠŽ]+)* \\([a-zA-Z.]+( [a-zA-Z.]+)*\\)$")
        if (input.isNotEmpty() && !regex.matches(input)) return R.string.invalid_name
        return null
    }

    const val MIN_INPUT_LENGTH = 2
    const val MAX_INPUT_LENGTH = 40

    fun validateInputLength(
        input: String
    ): Int? {
        if (input.isNotEmpty() && input.length < MIN_INPUT_LENGTH) return R.string.too_short
        else if (input.length > MAX_INPUT_LENGTH) return R.string.too_long
        return null
    }

    fun setEditTextValidator(
        context: Context,
        editText: EditText,
        validations: List<(String) -> Int?>
    ) {
        val icon = ContextCompat.getDrawable(context, R.drawable.ic_error_filled)
        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                for (validation in validations)
                    when (val stringResource = validation(editText.text.toString())) {
                        is Int -> editText.setError(context.getString(stringResource), icon)
                    }
            }
        })
    }
}