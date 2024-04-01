package ba.unsa.etf.rma.cuvarkuca

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var focusS: Spinner
    private lateinit var focusSA: FocusSpinnerAdapter
    private var focusList: List<Focus> = enumValues<Focus>().toList()

    private lateinit var resetIM: ImageButton

    private lateinit var plantRV: RecyclerView
    private lateinit var plantLA: PlantListAdapter
    private var plantList: List<Biljka> = biljke

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // SPINNER - CHANGE FOCUS

        focusS = findViewById(R.id.modSpinner)

        focusSA = FocusSpinnerAdapter(this, focusList)
        focusS.adapter = focusSA

        focusS.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = parent?.getItemAtPosition(position)
                if (item != null && item is Focus) {
                    plantLA.setFocus(item)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // IMAGE BUTTON - RESET FILTER

        resetIM = findViewById(R.id.resetBtn)

        resetIM.setOnClickListener {
            plantLA.setList(plantList)
        }

        // RECYCLER VIEW - LIST PLANTS

        plantRV = findViewById(R.id.biljkeRV)
        plantRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        plantLA = PlantListAdapter(listOf()) { plant -> onItemClicked(plant) }
        plantRV.adapter = plantLA
        plantLA.setList(plantList)
    }

    private fun onItemClicked(reference: Biljka) {
        val item = focusS.selectedItem

        if (item is Focus) {
            val similar: (Biljka, Biljka) -> Boolean = when (item) {
                Focus.MEDICAL -> { x, y -> medicallySimilar(x, y) }
                Focus.CULINARY -> { x, y -> culinarySimilar(x, y) }
                Focus.BOTANICAL -> { x, y -> botanicallySimilar(x, y) }
            }
            val filteredList = mutableListOf<Biljka>()

            for (plant in plantList) {
                if (similar(reference, plant)) {
                    filteredList.add(plant)
                }
            }

            plantLA.setList(filteredList)
        }
    }

    private fun medicallySimilar(x: Biljka, y: Biljka): Boolean {
        return x.medicinskeKoristi.any { it in y.medicinskeKoristi }
    }

    private fun culinarySimilar(x: Biljka, y: Biljka): Boolean {
        return x.profilOkusa == y.profilOkusa
                || x.jela.any { it in y.jela }
    }

    private fun botanicallySimilar(x: Biljka, y: Biljka): Boolean {
        return x.porodica == y.porodica
                && x.klimatskiTipovi.any { it in y.klimatskiTipovi }
                && x.zemljisniTipovi.any { it in y.zemljisniTipovi }
    }
}