package ba.unsa.etf.rma.cuvarkuca

import android.os.Bundle
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var plantRV: RecyclerView
    private lateinit var plantLA: PlantListAdapter
    private var plantList = biljke

    private lateinit var focusS: Spinner
    private lateinit var focusSA: FocusSpinnerAdapter
    private var focusList = focuses

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        plantRV = findViewById(R.id.biljkeRV)
        plantRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        plantLA = PlantListAdapter(listOf())
        plantRV.adapter = plantLA
        plantLA.updatePlantList(plantList)

        focusS = findViewById(R.id.modSpinner)

        focusSA = FocusSpinnerAdapter(this, focusList)
        focusS.adapter = focusSA
    }
}