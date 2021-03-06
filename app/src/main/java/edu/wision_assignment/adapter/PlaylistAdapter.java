package edu.wision_assignment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.wision_assignment.R;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ReciewViewHolder> {

    private Context context;
    private List<String> data;

    public PlaylistAdapter(Context context, List<String> data) {

        this.context = context;
        this.data = data;
    }


    @Override
    public ReciewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.card, null);
        ReciewViewHolder holder = new ReciewViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReciewViewHolder holder, int position) {
        Picasso.get().load(data.get(position)).into(holder.imageItem);
        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Comment Event Detected", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Video Liked", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class ReciewViewHolder extends RecyclerView.ViewHolder {


        ImageView imageItem;
        Button btnLike, btnComment;

        public ReciewViewHolder(View view) {
            super(view);
            btnLike = view.findViewById(R.id.btnLike);
            btnComment = view.findViewById(R.id.btnComment);
            imageItem = (ImageView) view.findViewById(R.id.imgFeed);

        }

    }

}
