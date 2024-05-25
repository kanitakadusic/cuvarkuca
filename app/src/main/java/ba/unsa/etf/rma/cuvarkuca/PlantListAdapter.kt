package ba.unsa.etf.rma.cuvarkuca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlantListAdapter (
    var items: List<Biljka>,
    private val onItemClicked: (plant: Biljka) -> Unit,
    private var focusContext: FocusContext
) : RecyclerView.Adapter<PlantListAdapter.PlantViewHolder>() {
    private var focusHasChanged: Boolean = false

    fun setFocusContext(focusContext: FocusContext) {
        this.focusContext = focusContext
        focusHasChanged = true
        notifyDataSetChanged()
        focusHasChanged = false
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerview_item_plant, parent, false)

        return PlantViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: PlantViewHolder,
        position: Int
    ) {
        if (!focusHasChanged) {
            val plant = items[position]

            holder.image.setImageResource(R.drawable.tulips)
            holder.title.text = plant.naziv

            holder.caution.text = plant.medicinskoUpozorenje
            val benefitList = plant.medicinskeKoristi
            for (i in 0 until minOf(holder.benefits.size, benefitList.size))
                holder.benefits[i].text = benefitList[i].description

            holder.taste.text = plant.profilOkusa.description
            val dishList = plant.jela
            for (i in 0 until minOf(holder.dishes.size, dishList.size))
                holder.dishes[i].text = dishList[i]

            holder.family.text = plant.porodica
            if (plant.klimatskiTipovi.isNotEmpty())
                holder.climate.text = plant.klimatskiTipovi[0].description
            if (plant.zemljisniTipovi.isNotEmpty())
                holder.soil.text = plant.zemljisniTipovi[0].description
        }

        holder.itemView.findViewById<View>(focusContext.getPreviousFocus().view).visibility = View.GONE
        holder.itemView.findViewById<View>(focusContext.getCurrentFocus().view).visibility = View.VISIBLE

        holder.itemView.setOnClickListener { onItemClicked(items[position]) }
    }

    inner class PlantViewHolder (
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.slikaItem)
        val title: TextView = itemView.findViewById(R.id.nazivItem)

        val caution: TextView = itemView.findViewById(R.id.upozorenjeItem)
        val benefits: List<TextView> = listOf<TextView>(
            itemView.findViewById(R.id.korist1Item),
            itemView.findViewById(R.id.korist2Item),
            itemView.findViewById(R.id.korist3Item)
        )

        val taste: TextView = itemView.findViewById(R.id.profilOkusaItem)
        val dishes: List<TextView> = listOf<TextView>(
            itemView.findViewById(R.id.jelo1Item),
            itemView.findViewById(R.id.jelo2Item),
            itemView.findViewById(R.id.jelo3Item)
        )

        val family: TextView = itemView.findViewById(R.id.porodicaItem)
        val climate: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        val soil: TextView = itemView.findViewById(R.id.zemljisniTipItem)
    }
}