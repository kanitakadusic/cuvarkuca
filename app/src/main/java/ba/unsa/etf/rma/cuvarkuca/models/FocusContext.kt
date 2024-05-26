package ba.unsa.etf.rma.cuvarkuca.models

class FocusContext(
    private var currentFocus: Focus
) {
    private var previousFocus: Focus = currentFocus

    fun getCurrentFocus() = currentFocus
    fun getPreviousFocus() = previousFocus

    fun changeFocus(focus: Focus) {
        previousFocus = currentFocus
        currentFocus = focus
    }
}