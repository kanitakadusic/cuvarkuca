package ba.unsa.etf.rma.cuvarkuca

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var plantRV: RecyclerView
    private lateinit var plantLA: PlantListAdapter
    private var plantList: List<Biljka> = biljke

    private lateinit var focusS: Spinner
    private lateinit var focusSA: FocusSpinnerAdapter
    private var focusList: List<Focus> = enumValues<Focus>().toList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        plantRV = findViewById(R.id.biljkeRV)
        plantRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        plantLA = PlantListAdapter(listOf())
        plantRV.adapter = plantLA
        plantLA.setList(plantList)

        // TODO("onItemSelectedListener for RecyclerView")

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

            override fun onNothingSelected(
                parent: AdapterView<*>?
            ) {
                // should not be implemented, but should be declared
            }
        }
    }
}