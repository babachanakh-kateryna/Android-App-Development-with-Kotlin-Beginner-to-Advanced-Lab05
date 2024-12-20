package com.example.project4.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project4.model.MyImages
import com.example.project4.util.ConvertImage
import com.example.project4.view.UpdateImageActivity

class MyImagesAdapter(val activity: Activity): RecyclerView.Adapter<MyImagesAdapter.MyImagesViewHolder>() {

    var imageList : List<MyImages> = ArrayList()

    fun setImage(images : List<MyImages>){
        this.imageList = images
        notifyDataSetChanged()
    }

    class MyImagesViewHolder(val itemBinding: ImageItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyImagesViewHolder {

        val view = ImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyImagesViewHolder(view)

    }

    override fun getItemCount(): Int {

        return imageList.size

    }

    override fun onBindViewHolder(holder: MyImagesViewHolder, position: Int) {

        val myImage = imageList[position]
        with(holder){

            itemBinding.textViewTitle.text = myImage.imageTitle
            itemBinding.textViewDescription.text = myImage.imageDescription
            val imageAsBitmap = ConvertImage.convertToBitmap(myImage.imageAsString)
            itemBinding.imageView.setImageBitmap(imageAsBitmap)

            itemBinding.cardView.setOnClickListener {
                val intent = Intent(activity, UpdateImageActivity::class.java)
                intent.putExtra("id",myImage.imageId)
                activity.startActivity(intent)
            }

        }

    }

    fun returnItemAtGivenPosition(position: Int) : MyImages{

        return imageList[position]

    }

}