package ba.unsa.etf.rma.cuvarkuca

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Description
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntentInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    private fun withBitmap(expected: Bitmap) =
        object : BoundedMatcher<View, ImageView>(ImageView::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText(expected.toString())
            }

            override fun matchesSafely(item: ImageView?): Boolean {
                val bitmap = (item?.drawable as BitmapDrawable).bitmap
                return bitmap.sameAs(expected)
            }
        }

    // Zaista nemam ideju zasto klik na dugme samo u ovom testu pravi problem.

    @Test
    fun takePicture() {
        onView(withId(R.id.novaBiljkaBtn)).perform(click())

        Intents.init()

        val testBitmap: Bitmap = BitmapFactory.decodeResource(
            InstrumentationRegistry.getInstrumentation().targetContext.resources,
            R.drawable.tulips
        )

        val intent = Intent()
        val bundle = Bundle()
        bundle.putParcelable("data", testBitmap)
        intent.putExtras(bundle)

        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, intent)
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result)

        onView(withId(R.id.uslikajBiljkuBtn)).perform(scrollTo()).perform(click())

        intended(hasAction(MediaStore.ACTION_IMAGE_CAPTURE))
        onView(withId(R.id.slikaIV)).check(matches(withBitmap(testBitmap)))

        Intents.release()
    }
}