package io.dotcoding.software.restaurante.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.dotcoding.software.restaurante.R
import io.dotcoding.software.restaurante.models.Venue
import io.dotcoding.software.restaurante.utils.toPx

class VenuesAdapter(val context: Context):RecyclerView.Adapter<VenuesAdapter.ViewHolder>() {
    var venues: ArrayList<Venue> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_venue, parent, false))

    override fun getItemCount() = venues.size



    fun setItems(venues: ArrayList<Venue>){
        this.venues.clear()
        this.venues.addAll(venues)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val venue = venues[position]
        holder.name.text = "name: ${venue.name}"
        holder.location.text = "Lat: ${venue.location?.lat} \n Lng: ${venue.location?.lng}"
        holder.distance.text = "Distance: ${venue.location?.distance}"
        holder.web.text = "Web: ${venue.url}"
        holder.isOpen.text = "isOpen ${venue.hours?.isOpen.toString()}"

        var imageUrl: String? = null


        venue.photos?.groups?.forEach {
            it.items?.forEach {
                imageUrl = it.prefix+"400x400"+it.suffix
            }
        }

        if(imageUrl != null){
            Picasso.get().load(Uri.parse(imageUrl)).into(holder.image)
        }

    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.itemVenueName)
        val image: ImageView = itemView.findViewById(R.id.itemVenueImage)
        val location: TextView = itemView.findViewById(R.id.itemVenueLocation)
        var distance: TextView = itemView.findViewById(R.id.itemVenueDistance)
        var web: TextView = itemView.findViewById(R.id.itemVenueWebTextView)
        var isOpen: TextView = itemView.findViewById(R.id.itemVenueIsOpen)

    }
}