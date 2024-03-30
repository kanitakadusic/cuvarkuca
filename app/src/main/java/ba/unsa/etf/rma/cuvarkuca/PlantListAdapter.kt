package ba.unsa.etf.rma.cuvarkuca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlantListAdapter (
    private var plantList: List<Biljka>,
    private val onItemClicked: (plant: Biljka) -> Unit
) : RecyclerView.Adapter<PlantListAdapter.PlantViewHolder>() {
    private var oldFocus: Focus = Focus.MEDICAL
    private var currentFocus: Focus = Focus.MEDICAL

    fun setList(list: List<Biljka>) {
        plantList = list
        notifyDataSetChanged()
    }

    fun setFocus(focus: Focus) {
        oldFocus = currentFocus
        currentFocus = focus
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = plantList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerview_item_plant, parent, false)

        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PlantViewHolder,
        position: Int
    ) {
        holder.manageVisibility()
        holder.bindData(plantList[position])

        holder.itemView.setOnClickListener { onItemClicked(plantList[position]) }
    }

    inner class PlantViewHolder (
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun manageVisibility() {
            itemView.findViewById<View>(oldFocus.id.view).visibility = View.GONE
            itemView.findViewById<View>(currentFocus.id.view).visibility = View.VISIBLE
        }

        fun bindData(plant: Biljka) {
            bindGeneralData(plant)

            when (currentFocus) {
                Focus.MEDICAL -> bindMedicalData(plant)
                Focus.CULINARY -> bindCulinaryData(plant)
                Focus.BOTANICAL -> bindBotanicalData(plant)
            }
        }

        private val image: ImageView = itemView.findViewById(R.id.slikaItem)
        private val title: TextView = itemView.findViewById(R.id.nazivItem)

        fun bindGeneralData(plant: Biljka) {
            // val context: Context = holder.image.context
            // val imageId = context.resources.getIdentifier("tulips", "drawable", context.packageName)
            image.setImageResource(R.drawable.tulips)
            title.text = plant.naziv.trim().split("(").first()
        }

        private val caution: TextView = itemView.findViewById(R.id.upozorenjeItem)
        private val benefits: List<TextView> = listOf<TextView>(
            itemView.findViewById(R.id.korist1Item),
            itemView.findViewById(R.id.korist2Item),
            itemView.findViewById(R.id.korist3Item)
        )

        fun bindMedicalData(plant: Biljka) {
            caution.text = plant.medicinskoUpozorenje

            val benefitList = plant.medicinskeKoristi
            for (i in 0 until minOf(benefits.size, benefitList.size)) {
                benefits[i].text = benefitList[i].opis.trim().split(" ").first().lowercase()
            }
        }

        private val taste: TextView = itemView.findViewById(R.id.profilOkusaItem)
        private val dishes: List<TextView> = listOf<TextView>(
            itemView.findViewById(R.id.jelo1Item),
            itemView.findViewById(R.id.jelo2Item),
            itemView.findViewById(R.id.jelo3Item)
        )

        fun bindCulinaryData(plant: Biljka) {
            taste.text = plant.profilOkusa.opis

            val dishList = plant.jela
            for (i in 0 until minOf(dishes.size, dishList.size)) {
                dishes[i].text = dishList[i].trim().split(" ").first().lowercase()
            }
        }

        private val family: TextView = itemView.findViewById(R.id.porodicaItem)
        private val climate: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        private val soil: TextView = itemView.findViewById(R.id.zemljisniTipItem)

        fun bindBotanicalData(plant: Biljka) {
            family.text = plant.porodica

            if (plant.klimatskiTipovi.isNotEmpty()) {
                climate.text = plant.klimatskiTipovi[0].opis
            }

            if (plant.zemljisniTipovi.isNotEmpty()) {
                soil.text = plant.zemljisniTipovi[0].naziv
            }
        }
    }
}