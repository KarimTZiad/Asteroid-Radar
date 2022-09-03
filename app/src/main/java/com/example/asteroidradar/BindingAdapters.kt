package com.example.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)

    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("asteroidIconHazardous")
fun bindImageViewHazardousIconContentDesc(imageView: ImageView, hazardous: Boolean){
    val context = imageView.context
    if(hazardous){
        imageView.contentDescription = context.getString(R.string.potentially_hazardous_asteroid_icon)
    }
    else{
        imageView.contentDescription = context.getString(R.string.not_hazardous_asteroid_icon)
    }
}

@BindingAdapter("asteroidImageHazardous")
fun bindImageViewHazardousImageContentDesc(imageView: ImageView, hazardous: Boolean){
    val context = imageView.context
    if(hazardous){
        imageView.contentDescription = context.getString(R.string.potentially_hazardous_asteroid_image)
    }
    else{
        imageView.contentDescription = context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("picOfDayContentDesc")
fun bindImageViewPictureOfDayContentDesc(imageView: ImageView, title: String){
    val context = imageView.context
    imageView.contentDescription = context.getString(R.string.nasa_picture_of_day_content_description_format, title)
}