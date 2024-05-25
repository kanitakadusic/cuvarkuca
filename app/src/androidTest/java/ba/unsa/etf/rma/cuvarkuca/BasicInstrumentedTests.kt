package ba.unsa.etf.rma.cuvarkuca

// za ispravnu realizaciju testova je potrebno da
// "FocusSpinnerAdapter" prima listu stringova

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.core.Is
import org.hamcrest.number.OrderingComparison.greaterThan
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BasicInstrumentedTests {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun initialPlantsAreDisplayed() {
        activityScenarioRule.scenario.onActivity {
            val recyclerView = it.findViewById<RecyclerView>(R.id.biljkeRV)
            val itemCount = recyclerView.adapter!!.itemCount
            assertThat(itemCount, greaterThan(4))
        }

        val plantNames = listOf(
            "Bosiljak (Ocimum basilicum)",
            "Nana (Mentha spicata)",
            "Kamilica (Matricaria chamomilla)",
            "Ružmarin (Rosmarinus officinalis)",
            "Lavanda (Lavandula angustifolia)"
        )

        for (name in plantNames)
            onView(withId(R.id.biljkeRV)).perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    allOf(
                        hasDescendant(withText(name)),
                        hasDescendant(withId(R.id.nazivItem))
                    )
                )
            )
    }

    @Test
    fun spinnerHasAllFocuses() {
        val focusNames = listOf("Medicin", "Kuha", "Botan")

        for (name in focusNames) {
            onView(withId(R.id.modSpinner)).perform(click())
            onData(allOf(Is(instanceOf(String::class.java)), containsString(name))).perform(click())
        }
    }

    @Test
    fun onFocusChange() {
        onView(withId(R.id.modSpinner)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), containsString("Medicin"))).perform(click())

        onView(withId(R.id.biljkeRV)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                allOf(
                    hasDescendant(withText("Bosiljak (Ocimum basilicum)")),
                    hasDescendant(withText(containsString("Smirenje")))
                )
            )
        )

        onView(withId(R.id.modSpinner)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), containsString("Kuha"))).perform(click())

        onView(withId(R.id.biljkeRV)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                allOf(
                    hasDescendant(withText("Bosiljak (Ocimum basilicum)")),
                    hasDescendant(withText(containsString("Salata")))
                )
            )
        )
    }

    @Test
    fun filteringInMedicalMode() {
        onView(withId(R.id.modSpinner)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), containsString("Medicin"))).perform(click())

        onView(withId(R.id.biljkeRV)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                allOf(
                    hasDescendant(withText("Bosiljak (Ocimum basilicum)")),
                    hasDescendant(withText(containsString("Smirenje")))
                ), click()
            )
        )

        val visibleNames = listOf(
            "Bosiljak (Ocimum basilicum)",
            "Kamilica (Matricaria chamomilla)",
            "Lavanda (Lavandula angustifolia)"
        )

        val invisibleNames = listOf(
            "Nana (Mentha spicata)",
            "Ružmarin (Rosmarinus officinalis)"
        )

        for (name in visibleNames)
            onView(withId(R.id.biljkeRV)).perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    allOf(
                        hasDescendant(withText(name)),
                        hasDescendant(withId(R.id.nazivItem))
                    )
                )
            )

        for (name in invisibleNames) {
            try {
                onView(withId(R.id.biljkeRV)).perform(
                    RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                        allOf(
                            hasDescendant(withText(name)),
                            hasDescendant(withId(R.id.nazivItem))
                        )
                    )
                )

                assert(false) { "Scroll treba pasti, sljedeca biljka se prikazuje nakon filtriranja, a ne treba. Biljka: $name" }
            } catch (e: Exception) {
                assertThat(e.message, containsString("Error performing"))
            }
        }
    }

    @Test
    fun focusesHaveValidIds() {
        onView(withId(R.id.modSpinner)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), containsString("Medicin"))).perform(click())

        val medicalIds = listOf(
            R.id.nazivItem,
            R.id.slikaItem,
            R.id.korist1Item,
            R.id.korist2Item,
            R.id.korist3Item,
            R.id.upozorenjeItem
        )

        for (id in medicalIds)
            onView(withId(R.id.biljkeRV)).perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    allOf(
                        hasDescendant(withText("Bosiljak (Ocimum basilicum)")),
                        hasDescendant(withText(containsString("Smirenje"))),
                        hasDescendant(withId(id))
                    )
                )
            )

        onView(withId(R.id.modSpinner)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), containsString("Kuh"))).perform(click())

        val culinaryIds = listOf(
            R.id.nazivItem,
            R.id.slikaItem,
            R.id.jelo1Item,
            R.id.jelo2Item,
            R.id.jelo3Item,
            R.id.profilOkusaItem
        )

        for (id in culinaryIds)
            onView(withId(R.id.biljkeRV)).perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    allOf(
                        hasDescendant(withText("Bosiljak (Ocimum basilicum)")),
                        hasDescendant(withText(containsString("biljni okus"))),
                        hasDescendant(withId(id))
                    )
                )
            )

        onView(withId(R.id.modSpinner)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), containsString("Botan"))).perform(click())

        val botanicalIds = listOf(
            R.id.nazivItem,
            R.id.slikaItem,
            R.id.porodicaItem,
            R.id.zemljisniTipItem,
            R.id.klimatskiTipItem
        )

        for (id in botanicalIds)
            onView(withId(R.id.biljkeRV)).perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    allOf(
                        hasDescendant(withText("Bosiljak (Ocimum basilicum)")),
                        hasDescendant(withText(containsString("Mediteranska"))),
                        hasDescendant(withId(id))
                    )
                )
            )
    }
}