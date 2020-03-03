package com.example.navigationcomponentsample.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dharam.githubissues.repository.model.Comments
import com.example.navigationcomponentsample.R
import com.example.navigationcomponentsample.constants.Constants.parseDate

import kotlinx.android.synthetic.main.item_comment_list.view.*

class MyCommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //bind data to itemview
    fun bind(comments: Comments) {
        //set the author name of comment  with first letter in capital format
        itemView.user_name.text = comments.user.login.capitalize()

        //set updated time of issue with refortmat date
        itemView.comment_updated_time.text = parseDate(comments.updated_at)

        // load the author profile image of comment
        Glide.with(itemView)
                .load(comments.user.avatar_url)
                .fitCenter()
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.user_profile)
                .error(R.drawable.user_profile)
                .fallback(R.drawable.user_profile)
                .into(itemView.user_profile_picute)

        //set the comment body
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            itemView.comment_body.setText(android.text.Html.fromHtml(comments.body,
                    android.text.Html.FROM_HTML_MODE_COMPACT))
        else
            itemView.comment_body.setText(android.text.Html.fromHtml(comments.body))

    }

}


class CommentsAdapter(var comments: List<Comments>) : RecyclerView.Adapter<MyCommentHolder>() {

    @Override
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyCommentHolder {
        //inflate the itemview for comments
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_comment_list, parent, false)
        return MyCommentHolder(view)
    }

    //return the item count of comment list
    @Override
    override fun getItemCount(): Int = comments.size

    //function to bind the data to viewholder
    @Override
    override fun onBindViewHolder(myHolder: MyCommentHolder, position: Int) =
            myHolder.bind(comments.get(position))
}


