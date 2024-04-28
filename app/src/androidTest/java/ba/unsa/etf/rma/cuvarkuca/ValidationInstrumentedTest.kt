package ba.unsa.etf.rma.cuvarkuca

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.hasToString
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ValidationInstrumentedTest {
    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun editTextInputLength() {
        val alertTooShort: String = appContext.getString(R.string.too_short)
        val alertTooLong: String = appContext.getString(R.string.too_long)

        val inputs = listOf(R.id.nazivET, R.id.porodicaET, R.id.medicinskoUpozorenjeET)

        onView(withId(R.id.novaBiljkaBtn)).perform(click())

        for (editText in inputs) {
            onView(withId(editText))
                .perform(scrollTo())
                .perform(click())
                .perform(clearText())
                .perform(typeText("1"))
                .check(matches(hasErrorText(alertTooShort)))
                .perform(clearText())
                .perform(typeText("21*******************"))
                .check(matches(hasErrorText(alertTooLong)))
                .perform(clearText(), pressImeActionButton())

            closeSoftKeyboard()
        }
    }

    @Test
    fun noDishDuplicates() {
        onView(withId(R.id.novaBiljkaBtn)).perform(click())

        onView(withId(R.id.jeloET))
            .perform(scrollTo())
            .perform(click())
            .perform(clearText())
            .perform(typeText("Apple"), pressImeActionButton())

        closeSoftKeyboard()

        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo()).perform(click())
        onView(withId(R.id.jelaLV)).perform(scrollTo()).perform(click())

        onData(hasToString("Apple"))
            .inAdapterView(withId(R.id.jelaLV))
            .perform(click())

        onView(withId(R.id.jeloET))
            .perform(scrollTo())
            .perform(click())
            .perform(clearText())
            .perform(typeText("AppLe"), pressImeActionButton())

        closeSoftKeyboard()

        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo()).perform(click())
        onView(withId(R.id.jelaLV)).perform(scrollTo()).perform(click())

        onData(not(hasToString("AppLe")))
            .inAdapterView(withId(R.id.jelaLV))
            .perform(click())
    }

    @Test
    fun addNewPlant() {
        onView(withId(R.id.novaBiljkaBtn)).perform(click())

        onView(withId(R.id.nazivET))
            .perform(scrollTo())
            .perform(click())
            .perform(clearText())
            .perform(typeText("Name"), pressImeActionButton())
        closeSoftKeyboard()

        onView(withId(R.id.porodicaET))
            .perform(scrollTo())
            .perform(click())
            .perform(clearText())
            .perform(typeText("Family"), pressImeActionButton())
        closeSoftKeyboard()

        onView(withId(R.id.medicinskoUpozorenjeET))
            .perform(scrollTo())
            .perform(click())
            .perform(clearText())
            .perform(typeText("Warning"), pressImeActionButton())
        closeSoftKeyboard()

        onView(withId(R.id.jeloET))
            .perform(scrollTo())
            .perform(click())
            .perform(clearText())
            .perform(typeText("Meal"), pressImeActionButton())
        closeSoftKeyboard()

        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo()).perform(click())

        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo()).perform(click())
        onData(anything())
            .inAdapterView(withId(R.id.medicinskaKoristLV))
            .atPosition(2)
            .perform(click())

        onView(withId(R.id.profilOkusaLV)).perform(scrollTo()).perform(click())
        onData(anything())
            .inAdapterView(withId(R.id.profilOkusaLV))
            .atPosition(3)
            .perform(click())

        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo()).perform(click())
        onData(anything())
            .inAdapterView(withId(R.id.klimatskiTipLV))
            .atPosition(1)
            .perform(click())

        onView(withId(R.id.zemljisniTipLV)).perform(scrollTo()).perform(click())
        onData(anything())
            .inAdapterView(withId(R.id.zemljisniTipLV))
            .atPosition(4)
            .perform(click())

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo()).perform(click())

        onView(withId(R.id.biljkeRV)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                allOf(
                    hasDescendant(withText("Name")),
                    hasDescendant(withId(R.id.nazivItem))
                )
            )
        )
    }
}