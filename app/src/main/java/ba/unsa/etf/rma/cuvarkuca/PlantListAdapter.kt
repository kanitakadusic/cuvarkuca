package ba.unsa.etf.rma.cuvarkuca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlantListAdapter (
    private var plantList: List<Biljka>
) : RecyclerView.Adapter<PlantListAdapter.PlantViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerview_item_plant, parent, false)

        return PlantViewHolder(view)
    }

    override fun getItemCount(): Int = plantList.size

    override fun onBindViewHolder(
        holder: PlantViewHolder,
        position: Int
    ) {
        // val context: Context = holder.image.context
        // val imageId = context.resources.getIdentifier("tulips", "drawable", context.packageName)
        val imageId: Int = R.drawable.tulips
        holder.image.setImageResource(imageId)

        holder.title.text = plantList[position].naziv.trim().split("(").first()
        holder.caution.text = plantList[position].medicinskoUpozorenje

        val benefitList = plantList[position].medicinskeKoristi
        val size = minOf(holder.benefits.size, benefitList.size)
        for (i in 0 until size) {
            holder.benefits[i].text = benefitList[i].opis
                .trim().split(" ").first().lowercase()
        }
    }

    fun updatePlantList(
        biljke: List<Biljka>
    ) {
        this.plantList = biljke
        notifyDataSetChanged()
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
    }
}