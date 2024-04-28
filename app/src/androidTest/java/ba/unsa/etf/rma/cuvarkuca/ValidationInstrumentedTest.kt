package ba.unsa.etf.rma.cuvarkuca

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ValidationInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<NovaBiljkaActivity> =
        ActivityScenarioRule(NovaBiljkaActivity::class.java)

    @Test
    fun editTextInputLength() {
        onView(withId(R.id.dodajBiljkuBtn)).check(matches(withText("Dodaj biljku")))
    }
}