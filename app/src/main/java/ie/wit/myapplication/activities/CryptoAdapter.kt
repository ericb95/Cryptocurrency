package ie.wit.myapplication.activities

import ie.wit.myapplication.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ie.wit.myapplication.models.CryptoModel
import java.util.*

//Adapter to get the data stored in CryptoModel and set the data of the recyclerview displaying the cryptocurrency cards

class CryptoAdapter(private val context: Context, courseModelArrayList: ArrayList<CryptoModel>) :
    RecyclerView.Adapter<CryptoAdapter.Viewholder>() {
    private val courseModelArrayList: ArrayList<CryptoModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        // inflating the layout for each item of recycler view.
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        // set the data to textview and imageview of each card layout
        val model: CryptoModel = courseModelArrayList[position]
        holder.cryptoName.text = model.crypto_name
        holder.cryptoDesc.text = model.crypto_desc
        holder.cryptoImage.setImageResource(model.crypto_image)
    }

    override fun getItemCount(): Int {
        // return number of cards in recycler view
        return courseModelArrayList.size
    }

    // View holder class for initializing of views
    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cryptoImage: ImageView
        val cryptoName: TextView
        val cryptoDesc: TextView

        init {
            cryptoImage = itemView.findViewById(R.id.cryptoImage)
            cryptoName = itemView.findViewById(R.id.cryptoName)
            cryptoDesc= itemView.findViewById(R.id.cryptoDesc)
        }
    }

    // Constructor
    init {
        this.courseModelArrayList = courseModelArrayList
    }
}