package com.example.allergendetector;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogViewHolder> {
  private List<PopularBlog.Blog> blogList;
  public BlogAdapter(List<PopularBlog.Blog> blogList){
      this.blogList = blogList;
  }

    @NonNull
    @Override
    public BlogAdapter.BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.item_blog,parent, false);
      return new BlogViewHolder(itemView);
    }

    public void onBindViewHolder(BlogViewHolder holder,int position){
      PopularBlog.Blog blog = blogList.get(position);
      holder.bind(blog);
    }
    public int getItemCount(){
      return blogList.size();
    }
    static class BlogViewHolder extends RecyclerView.ViewHolder{
      private TextView headlineTextView;

      BlogViewHolder(View itemView){
          super(itemView);
          headlineTextView = itemView.findViewById(R.id.title_text_view);

      }
      void bind(final PopularBlog.Blog blog){
        headlineTextView.setText(blog.getHeadline());
        itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            //handles click event on the blog item
            String blogLink = blog.getLink();
            //opens the blog link in a web browser
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(blogLink));
            itemView.getContext().startActivity(intent);



          }
        });

      }

    }
}
