package ba.unsa.etf.rma.cuvarkuca

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ba.unsa.etf.rma.cuvarkuca.adapters.MultipleChoiceListAdapter
import ba.unsa.etf.rma.cuvarkuca.adapters.SingleChoiceListAdapter
import ba.unsa.etf.rma.cuvarkuca.adapters.UniqueItemsListAdapter
import ba.unsa.etf.rma.cuvarkuca.models.Biljka
import ba.unsa.etf.rma.cuvarkuca.models.KlimatskiTip
import ba.unsa.etf.rma.cuvarkuca.models.MedicinskaKorist
import ba.unsa.etf.rma.cuvarkuca.models.ProfilOkusaBiljke
import ba.unsa.etf.rma.cuvarkuca.models.Zemljiste
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

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
    private lateinit var benefitMCLA: MultipleChoiceListAdapter<MedicinskaKorist>

    private lateinit var tasteLV: ListView
    private lateinit var tasteSCLA: SingleChoiceListAdapter<ProfilOkusaBiljke>

    private lateinit var dishLV: ListView
    private lateinit var dishUILA: UniqueItemsListAdapter

    private lateinit var climateLV: ListView
    private lateinit var climateMCLA: MultipleChoiceListAdapter<KlimatskiTip>

    private lateinit var soilLV: ListView
    private lateinit var soilMCLA: MultipleChoiceListAdapter<Zemljiste>

    companion object {
        const val CAPTURE_PLANT_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_biljka)

        imageIV = findViewById(R.id.slikaIV)

        nameET = findViewById(R.id.nazivET)
        Utility.setEditTextValidator(this, nameET,
            listOf(
                { Utility.validateInputLength(it) },
                { Utility.validatePlantNameForm(it) }
            )
        )

        familyET = findViewById(R.id.porodicaET)
        Utility.setEditTextValidator(this, familyET,
            listOf { Utility.validateInputLength(it) })

        warningET = findViewById(R.id.medicinskoUpozorenjeET)
        Utility.setEditTextValidator(this, warningET,
            listOf { Utility.validateInputLength(it) })

        dishET = findViewById(R.id.jeloET)
        Utility.setEditTextValidator(this, dishET,
            listOf { Utility.validateInputLength(it) })

        cameraB = findViewById(R.id.uslikajBiljkuBtn)
        cameraB.setOnClickListener { dispatchTakePictureIntent() }

        dishB = findViewById(R.id.dodajJeloBtn)
        dishB.text = getString(R.string.add_dish)
        dishB.setOnClickListener { onAddModifyDishButtonClicked() }

        addB = findViewById(R.id.dodajBiljkuBtn)
        addB.setOnClickListener { onAddPlantButtonClicked() }

        benefitMCLA = MultipleChoiceListAdapter(this, MedicinskaKorist.entries, 1)
        benefitLV = findViewById(R.id.medicinskaKoristLV)
        benefitLV.adapter = benefitMCLA
        Utility.adjustListViewHeight(benefitLV)

        tasteSCLA = SingleChoiceListAdapter(this, ProfilOkusaBiljke.entries)
        tasteLV = findViewById(R.id.profilOkusaLV)
        tasteLV.adapter = tasteSCLA
        Utility.adjustListViewHeight(tasteLV)

        dishUILA = UniqueItemsListAdapter(this, mutableListOf()) { dish -> onDishClicked(dish) }
        dishLV = findViewById(R.id.jelaLV)
        dishLV.adapter = dishUILA
        Utility.adjustListViewHeight(dishLV)

        climateMCLA = MultipleChoiceListAdapter(this, KlimatskiTip.entries, 1)
        climateLV = findViewById(R.id.klimatskiTipLV)
        climateLV.adapter = climateMCLA
        Utility.adjustListViewHeight(climateLV)

        soilMCLA = MultipleChoiceListAdapter(this, Zemljiste.entries, 1)
        soilLV = findViewById(R.id.zemljisniTipLV)
        soilLV.adapter = soilMCLA
        Utility.adjustListViewHeight(soilLV)
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        @Suppress("DEPRECATION")
        startActivityForResult(intent, CAPTURE_PLANT_REQUEST_CODE)
    }

    private fun onAddModifyDishButtonClicked() {
        if (dishET.text.isNotEmpty() && (dishET.text.length !in Utility.MIN_INPUT_LENGTH..Utility.MAX_INPUT_LENGTH)) return

        when (dishB.text.toString()) {
            getString(R.string.add_dish) -> {
                dishUILA.add(dishET.text.toString())
                Utility.adjustListViewHeight(dishLV)
                dishET.text.clear()
            }

            getString(R.string.modify_dish) -> {
                dishUILA.modifySelectedItem(dishET.text.toString())

                if (dishET.text.isEmpty()) Utility.adjustListViewHeight(dishLV)
                else dishET.text.clear()

                dishB.setText(R.string.add_dish)
            }
        }
    }

    private fun onAddPlantButtonClicked() {
        if (listOf(nameET, familyET, warningET).any { it.text.isEmpty() || it.error != null }) {
            Snackbar.make(findViewById(R.id.root), R.string.check_input_fields, Snackbar.LENGTH_LONG).show()
            return
        }

        if (dishUILA.size() == 0) {
            Snackbar.make(findViewById(R.id.root), R.string.at_least_one_dish, Snackbar.LENGTH_LONG).show()
            return
        }

        val plant = Biljka(
            naziv = nameET.text.toString(),
            porodica = familyET.text.toString(),
            medicinskoUpozorenje = warningET.text.toString(),
            medicinskeKoristi = benefitMCLA.getSelectedItems(),
            profilOkusa = tasteSCLA.getSelectedItem(),
            jela = dishUILA.get(),
            klimatskiTipovi = climateMCLA.getSelectedItems(),
            zemljisniTipovi = soilMCLA.getSelectedItems()
        )

        lifecycleScope.launch {
            val intent = Intent()
            intent.putExtra("plant", TrefleDAO.fixData(plant))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun onDishClicked(dish: String?) {
        if (dish != null) {
            dishET.setText(dish)
            dishET.setSelection(dishET.text.length)
            dishB.setText(R.string.modify_dish)
        } else {
            dishET.text.clear()
            dishB.setText(R.string.add_dish)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAPTURE_PLANT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            @Suppress("DEPRECATION")
            val bitmap: Bitmap = data?.extras?.get("data") as Bitmap

            imageIV.setImageBitmap(bitmap)
        }
    }
}