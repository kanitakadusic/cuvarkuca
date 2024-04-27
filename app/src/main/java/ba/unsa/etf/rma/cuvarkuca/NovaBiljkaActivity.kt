package ba.unsa.etf.rma.cuvarkuca

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class NovaBiljkaActivity : AppCompatActivity() {
    private lateinit var benefitLV: ListView
    private lateinit var benefitLA: MultipleListAdapter<MedicinskaKorist>

    private lateinit var tasteLV: ListView
    private lateinit var tasteLA: SingleListAdapter<ProfilOkusaBiljke>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_biljka)

        // LIST VIEW - BENEFITS

        benefitLV = findViewById(R.id.medicinskaKoristLV)
        benefitLA = MultipleListAdapter(this, MedicinskaKorist.entries)
        benefitLV.adapter = benefitLA

        // LIST VIEW - TASTE

        tasteLV = findViewById(R.id.profilOkusaLV)
        tasteLA = SingleListAdapter(this, ProfilOkusaBiljke.entries)
        tasteLV.adapter = tasteLA
    }
}