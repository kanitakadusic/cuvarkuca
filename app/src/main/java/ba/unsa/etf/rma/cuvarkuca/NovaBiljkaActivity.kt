package ba.unsa.etf.rma.cuvarkuca

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NovaBiljkaActivity : AppCompatActivity() {
    private lateinit var benefitLV: ListView
    private lateinit var benefitLA: MultipleListAdapter<MedicinskaKorist>

    private lateinit var tasteLV: ListView
    private lateinit var tasteLA: SingleListAdapter<ProfilOkusaBiljke>

    private lateinit var dishLV: ListView
    private lateinit var dishLA: StringListAdapter

    private lateinit var climateLV: ListView
    private lateinit var climateLA: MultipleListAdapter<KlimatskiTip>

    private lateinit var soilLV: ListView
    private lateinit var soilLA: MultipleListAdapter<Zemljiste>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_biljka)

        // LIST VIEW - BENEFITS

        benefitLV = findViewById(R.id.medicinskaKoristLV)
        benefitLA = MultipleListAdapter(this, MedicinskaKorist.entries)
        benefitLV.adapter = benefitLA
        Utility.manageHeight(benefitLV)

        // LIST VIEW - TASTE

        tasteLV = findViewById(R.id.profilOkusaLV)
        tasteLA = SingleListAdapter(this, ProfilOkusaBiljke.entries)
        tasteLV.adapter = tasteLA
        Utility.manageHeight(tasteLV)

        // LIST VIEW - DISHES

        dishLV = findViewById(R.id.jelaLV)
        dishLA = StringListAdapter(this, listOf()) { dish -> onDishClicked(dish) }
        dishLV.adapter = dishLA

        Utility.manageHeight(dishLV)

        // LIST VIEW - CLIMATES

        climateLV = findViewById(R.id.klimatskiTipLV)
        climateLA = MultipleListAdapter(this, KlimatskiTip.entries)
        climateLV.adapter = climateLA
        Utility.manageHeight(climateLV)

        // LIST VIEW - SOILS

        soilLV = findViewById(R.id.zemljisniTipLV)
        soilLA = MultipleListAdapter(this, Zemljiste.entries)
        soilLV.adapter = soilLA
        Utility.manageHeight(soilLV)
    }

    private fun onDishClicked(dish: String) {
        Toast.makeText(this, dish, Toast.LENGTH_SHORT).show()
    }
}