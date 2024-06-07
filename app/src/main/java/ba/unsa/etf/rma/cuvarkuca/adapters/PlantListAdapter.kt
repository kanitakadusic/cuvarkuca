package ba.unsa.etf.rma.cuvarkuca.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.cuvarkuca.R
import ba.unsa.etf.rma.cuvarkuca.TrefleDAO
import ba.unsa.etf.rma.cuvarkuca.models.Biljka
import ba.unsa.etf.rma.cuvarkuca.models.FocusContext
import ba.unsa.etf.rma.cuvarkuca.services.BiljkaDatabase
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlantListAdapter (
    private var focusContext: FocusContext,
    private var items: MutableList<Biljka>,
    private val onItemClicked: (plant: Biljka) -> Unit
) : RecyclerView.Adapter<PlantListAdapter.PlantViewHolder>() {

    private var focusHasChanged: Boolean = false

    fun setFocusContext(
        focusContext: FocusContext
    ) {
        this.focusContext = focusContext
        focusHasChanged = true
        notifyDataSetChanged()
        focusHasChanged = false
    }

    private val bitmaps: MutableMap<String, Bitmap> = mutableMapOf()

    fun getItems() = items

    fun setNewItems(
        newPlants: List<Biljka>
    ) {
        items = newPlants.toMutableList()
        bitmaps.clear()
        notifyDataSetChanged()
    }

    fun setNewItemsWithoutRefresh(
        newPlants: List<Biljka>
    ) {
        items = newPlants.toMutableList()
        bitmaps.clear()
    }

    fun setFilteredItems(
        filteredPlants: List<Biljka>
    ) {
        items = filteredPlants.toMutableList()
        notifyDataSetChanged()
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
        if (!focusHasChanged) holder.bind(items[position])

        holder.itemView.findViewById<View>(focusContext.getPreviousFocus().view).visibility = View.GONE
        holder.itemView.findViewById<View>(focusContext.getCurrentFocus().view).visibility = View.VISIBLE

        holder.itemView.setOnClickListener { onItemClicked(items[position]) }
    }

    inner class PlantViewHolder (
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val image: ImageView = itemView.findViewById(R.id.slikaItem)
        private val title: TextView = itemView.findViewById(R.id.nazivItem)

        private val caution: TextView = itemView.findViewById(R.id.upozorenjeItem)
        private val benefits: List<TextView> = listOf<TextView>(
            itemView.findViewById(R.id.korist1Item),
            itemView.findViewById(R.id.korist2Item),
            itemView.findViewById(R.id.korist3Item)
        )

        private val taste: TextView = itemView.findViewById(R.id.profilOkusaItem)
        private val dishes: List<TextView> = listOf<TextView>(
            itemView.findViewById(R.id.jelo1Item),
            itemView.findViewById(R.id.jelo2Item),
            itemView.findViewById(R.id.jelo3Item)
        )

        private val family: TextView = itemView.findViewById(R.id.porodicaItem)
        private val climate: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        private val soil: TextView = itemView.findViewById(R.id.zemljisniTipItem)

        fun bind(
            plant: Biljka
        ) {
            val scope = CoroutineScope(Job() + Dispatchers.Main)

            scope.launch {
                val bitmap = bitmaps.getOrPut(plant.naziv) {
                    val trefleBitmap = TrefleDAO.getImage(plant)
                    Log.i("*_bind", "Image fetched: " + plant.naziv)

                    if (trefleBitmap != null && plant.id != 0L) {
                        val room = BiljkaDatabase.getInstance()
                        room?.plantDao()?.addImage(plant.id, trefleBitmap)
                    }

                    trefleBitmap ?: BitmapFactory
                        .decodeResource(image.context.resources, R.drawable.default_plant_image)
                }

                Glide.with(image.context)
                    .load(bitmap)
                    .placeholder(R.drawable.default_plant_image)
                    .error(R.drawable.default_plant_image)
                    .fallback(R.drawable.default_plant_image)
                    .centerCrop()
                    .into(image)
            }

            title.text = plant.naziv

            caution.text = plant.medicinskoUpozorenje
            val benefitList = plant.medicinskeKoristi
            for (i in 0 until minOf(benefits.size, benefitList.size))
                benefits[i].text = benefitList[i].description

            taste.text = plant.profilOkusa.description
            val dishList = plant.jela
            for (i in 0 until minOf(dishes.size, dishList.size))
                dishes[i].text = dishList[i]

            family.text = plant.porodica
            if (plant.klimatskiTipovi.isNotEmpty())
                climate.text = plant.klimatskiTipovi[0].description
            if (plant.zemljisniTipovi.isNotEmpty())
                soil.text = plant.zemljisniTipovi[0].description
        }
    }
}