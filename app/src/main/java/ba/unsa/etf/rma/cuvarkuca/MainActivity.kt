package ba.unsa.etf.rma.cuvarkuca

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.cuvarkuca.adapters.ColorSpinnerAdapter
import ba.unsa.etf.rma.cuvarkuca.adapters.FocusSpinnerAdapter
import ba.unsa.etf.rma.cuvarkuca.adapters.PlantListAdapter
import ba.unsa.etf.rma.cuvarkuca.models.Biljka
import ba.unsa.etf.rma.cuvarkuca.models.BotanicalFocus
import ba.unsa.etf.rma.cuvarkuca.models.CulinaryFocus
import ba.unsa.etf.rma.cuvarkuca.models.Focus
import ba.unsa.etf.rma.cuvarkuca.models.FocusContext
import ba.unsa.etf.rma.cuvarkuca.models.MedicalFocus
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val focusContext = FocusContext(MedicalFocus)
    private var isFilteredByFlowerColor = false

    private lateinit var focusS: Spinner
    private lateinit var focusFSA: FocusSpinnerAdapter

    private lateinit var resetIB: ImageButton

    private lateinit var inputET: EditText

    private lateinit var colorS: Spinner
    private lateinit var colorCSA: ColorSpinnerAdapter

    private lateinit var searchB: Button

    private lateinit var plantRV: RecyclerView
    private lateinit var plantPLA: PlantListAdapter
    private val plants: MutableList<Biljka> = biljke.toMutableList()

    private lateinit var addFAB: FloatingActionButton

    companion object {
        const val NEW_PLANT_REQUEST_CODE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        focusFSA = FocusSpinnerAdapter(
            this,
            listOf(MedicalFocus, CulinaryFocus, BotanicalFocus)
        )

        focusS = findViewById(R.id.modSpinner)
        focusS.adapter = focusFSA
        focusS.onItemSelectedListener = onFocusSelect()

        resetIB = findViewById(R.id.resetBtn)
        resetIB.setOnClickListener { onResetButtonClicked() }

        inputET = findViewById(R.id.pretragaET)

        colorCSA = ColorSpinnerAdapter(
            this,
            resources.getStringArray(R.array.color_names).toList(),
            listOf(
                R.color.red_400,
                R.color.orange_400,
                R.color.yellow_400,
                R.color.green_400,
                R.color.blue_400,
                R.color.purple_400,
                R.color.brown_400
            )
        )

        colorS = findViewById(R.id.bojaSPIN)
        colorS.adapter = colorCSA

        searchB = findViewById(R.id.brzaPretraga)
        searchB.setOnClickListener { onQuickSearchButtonClicked() }

        plantPLA = PlantListAdapter(
            this,
            focusContext,
            plants
        ) { plant -> onPlantClicked(plant) }

        plantRV = findViewById(R.id.biljkeRV)
        plantRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        plantRV.adapter = plantPLA

        addFAB = findViewById(R.id.novaBiljkaBtn)
        addFAB.setOnClickListener { onAddPlantButtonClicked() }

        manageAppearanceOfInputAndColorFilters()
    }

    private fun onFocusSelect(): OnItemSelectedListener {
        return object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val focus = parent?.getItemAtPosition(position) as Focus
                focusContext.changeFocus(focus)
                manageAppearanceOfInputAndColorFilters()

                if (isFilteredByFlowerColor) {
                    plantPLA.setNewItemsWithoutRefresh(plants)
                    isFilteredByFlowerColor = false
                }

                plantPLA.setFocusContext(focusContext)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun onResetButtonClicked() {
        plantPLA.setNewItems(plants)
        isFilteredByFlowerColor = false
    }

    private fun onQuickSearchButtonClicked() {
        val input = inputET.text.toString()

        if (input.isNotEmpty()) {
            val color = colorS.selectedItem.toString()
            isFilteredByFlowerColor = true

            val scope = CoroutineScope(Job() + Dispatchers.Main)
            val trefle = TrefleDAO(this)

            scope.launch {
                inputET.setText(R.string.loading)
                plantPLA.setNewItems(trefle.getPlantsWithFlowerColor(color, input))
                inputET.text.clear()
            }
        }
    }

    private fun onPlantClicked(plant: Biljka) {
        if (isFilteredByFlowerColor) return

        plantPLA.setFilteredItems(plantPLA.getItems().filter {
            focusContext.getCurrentFocus().areSimilarPlants(it, plant)
        })
    }

    private fun onAddPlantButtonClicked() {
        val intent = Intent(this, NovaBiljkaActivity::class.java)
        @Suppress("DEPRECATION")
        startActivityForResult(intent, NEW_PLANT_REQUEST_CODE)
    }

    private fun manageAppearanceOfInputAndColorFilters() {
        val available: Boolean = focusContext.getCurrentFocus().isFilterableByInputAndColor
        val hint: Int
        val color: Int

        if (available) {
            hint = R.string.search
            color = Utility.getAttrColor(this, com.google.android.material.R.attr.colorPrimary)
        } else {
            hint = R.string.not_available
            color = Utility.getAttrColor(this, com.google.android.material.R.attr.colorOutlineVariant)
            inputET.text.clear()
        }

        Utility.setViewAvailability(inputET, available)
        Utility.setViewAvailability(colorS, available)
        Utility.setViewAvailability(searchB, available)

        inputET.setHint(hint)
        searchB.setTextColor(color)

        colorCSA.setEnabledAppearance(available)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_PLANT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            @Suppress("DEPRECATION")
            val plant: Biljka = data?.getParcelableExtra("plant")!!

            plants.add(plant)
            onResetButtonClicked()
        }
    }
}