package ba.unsa.etf.rma.cuvarkuca

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class NovaBiljkaActivity : AppCompatActivity() {
    private lateinit var imageIV: ImageView

    private lateinit var cameraB: Button
    private lateinit var dishB: Button
    private lateinit var addB: Button

    private lateinit var nameET: EditText
    private lateinit var familyET: EditText
    private lateinit var warningET: EditText
    private lateinit var dishET: EditText

    private lateinit var benefitLV: ListView
    private lateinit var benefitLA: MultipleListAdapter<MedicinskaKorist>

    private lateinit var tasteLV: ListView
    private lateinit var tasteLA: SingleListAdapter<ProfilOkusaBiljke>

    private lateinit var dishLV: ListView
    private lateinit var dishLA: UniqueListAdapter

    private lateinit var climateLV: ListView
    private lateinit var climateLA: MultipleListAdapter<KlimatskiTip>

    private lateinit var soilLV: ListView
    private lateinit var soilLA: MultipleListAdapter<Zemljiste>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_biljka)

        imageIV = findViewById(R.id.slikaIV)

        nameET = findViewById(R.id.nazivET)
        Utility.setTextValidator(this, nameET)

        familyET = findViewById(R.id.porodicaET)
        Utility.setTextValidator(this, familyET)

        warningET = findViewById(R.id.medicinskoUpozorenjeET)
        Utility.setTextValidator(this, warningET)

        dishET = findViewById(R.id.jeloET)

        cameraB = findViewById(R.id.uslikajBiljkuBtn)
        cameraB.setOnClickListener {  }

        dishB = findViewById(R.id.dodajJeloBtn)
        dishB.text = getString(R.string.add_dish)
        dishB.setOnClickListener { onDishButtonClicked() }

        addB = findViewById(R.id.dodajBiljkuBtn)
        addB.setOnClickListener { addNewPlant() }

        benefitLV = findViewById(R.id.medicinskaKoristLV)
        benefitLA = MultipleListAdapter(this, MedicinskaKorist.entries, 1)
        benefitLV.adapter = benefitLA
        Utility.adjustListViewHeight(benefitLV)

        tasteLV = findViewById(R.id.profilOkusaLV)
        tasteLA = SingleListAdapter(this, ProfilOkusaBiljke.entries)
        tasteLV.adapter = tasteLA
        Utility.adjustListViewHeight(tasteLV)

        dishLV = findViewById(R.id.jelaLV)
        dishLA = UniqueListAdapter(this, mutableListOf("")) { dish -> onDishItemClicked(dish) }
        dishLV.adapter = dishLA
        Utility.adjustListViewHeight(dishLV)

        climateLV = findViewById(R.id.klimatskiTipLV)
        climateLA = MultipleListAdapter(this, KlimatskiTip.entries, 1)
        climateLV.adapter = climateLA
        Utility.adjustListViewHeight(climateLV)

        soilLV = findViewById(R.id.zemljisniTipLV)
        soilLA = MultipleListAdapter(this, Zemljiste.entries, 1)
        soilLV.adapter = soilLA
        Utility.adjustListViewHeight(soilLV)
    }

    private fun onDishButtonClicked() {
        if (dishET.text.length == 1 || dishET.text.length > 20) {
            Utility.validateTextLength(this, dishET)
            return
        }

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

    private fun addNewPlant() {
        if (listOf(nameET, familyET, warningET).any {
            it.text.length !in Utility.MIN_TEXT_LENGTH..Utility.MAX_TEXT_LENGTH
        }) {
            Snackbar.make(findViewById(R.id.root), R.string.check_input_fields, Snackbar.LENGTH_LONG).show()
            return
        }

        if (dishLA.size() == 0) {
            Snackbar.make(findViewById(R.id.root), R.string.at_least_one_dish, Snackbar.LENGTH_LONG).show()
            return
        }

        val plant: Biljka = Biljka(
            nameET.text.toString(),
            familyET.text.toString(),
            warningET.text.toString(),
            benefitLA.getSelectedItems(),
            tasteLA.getSelectedItem(),
            dishLA.getItems(),
            climateLA.getSelectedItems(),
            soilLA.getSelectedItems()
        )
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