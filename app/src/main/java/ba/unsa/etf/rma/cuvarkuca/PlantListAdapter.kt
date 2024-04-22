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

    fun getList(): List<Biljka> = plantList

    fun changeItemsDisplay(focus: Focus) {
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
        holder.image.setImageResource(R.drawable.tulips)
        holder.title.text = plantList[position].naziv

        holder.manageViewVisibility()
        focusData[currentFocus.position].bindData(holder, plantList[position])

        holder.itemView.setOnClickListener { onItemClicked(plantList[position]) }
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

        fun manageViewVisibility() {
            itemView.findViewById<View>(focusData[oldFocus.position].viewBox).visibility = View.GONE
            itemView.findViewById<View>(focusData[currentFocus.position].viewBox).visibility = View.VISIBLE
        }
    }
}