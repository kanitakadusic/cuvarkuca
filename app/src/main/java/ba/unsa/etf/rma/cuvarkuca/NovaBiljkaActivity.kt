package ba.unsa.etf.rma.cuvarkuca

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class NovaBiljkaActivity : AppCompatActivity() {
    private lateinit var dishET: EditText

    private lateinit var dishB: Button

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

        dishET = findViewById(R.id.jeloET)

        dishB = findViewById(R.id.dodajJeloBtn)
        dishB.text = getString(R.string.add_dish)
        dishB.setOnClickListener { onDishButtonClicked() }

        benefitLV = findViewById(R.id.medicinskaKoristLV)
        benefitLA = MultipleListAdapter(this, MedicinskaKorist.entries)
        benefitLV.adapter = benefitLA
        Utility.adjustListViewHeight(benefitLV)

        tasteLV = findViewById(R.id.profilOkusaLV)
        tasteLA = SingleListAdapter(this, ProfilOkusaBiljke.entries)
        tasteLV.adapter = tasteLA
        Utility.adjustListViewHeight(tasteLV)

        dishLV = findViewById(R.id.jelaLV)
        dishLA = StringListAdapter(this, mutableListOf()) { dish -> onDishItemClicked(dish) }
        dishLV.adapter = dishLA
        Utility.adjustListViewHeight(dishLV)

        climateLV = findViewById(R.id.klimatskiTipLV)
        climateLA = MultipleListAdapter(this, KlimatskiTip.entries)
        climateLV.adapter = climateLA
        Utility.adjustListViewHeight(climateLV)

        soilLV = findViewById(R.id.zemljisniTipLV)
        soilLA = MultipleListAdapter(this, Zemljiste.entries)
        soilLV.adapter = soilLA
        Utility.adjustListViewHeight(soilLV)
    }

    private fun onDishButtonClicked() {
        when (dishB.text.toString()) {
            getString(R.string.add_dish) -> {
                dishLA.addItem(dishET.text.toString())
                Utility.adjustListViewHeight(dishLV)
                dishET.text.clear()
            }

            getString(R.string.modify_dish) -> {
                dishLA.modifySelectedItem(dishET.text.toString())

                if (dishET.text.isEmpty()) Utility.adjustListViewHeight(dishLV)
                else dishET.text.clear()

                dishB.setText(R.string.add_dish)
            }
        }
    }

    private fun onDishItemClicked(dish: String?) {
        if (dish != null) {
            dishET.setText(dish)
            dishET.setSelection(dishET.text.length)
            dishB.setText(R.string.modify_dish)
        } else {
            dishET.text.clear()
            dishB.setText(R.string.add_dish)
        }
    }
}