package com.vektorel.tavsiyekitap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GirdiRecyclerAdapter extends RecyclerView.Adapter<GirdiRecyclerAdapter.PostHolder> {


    private ArrayList<String> userEmailList;
    private ArrayList<String> userkitapadiList;
    private ArrayList<String> userImageList;
    private ArrayList<String> userkitapyorumList;

    public GirdiRecyclerAdapter(ArrayList<String> userEmailList, ArrayList<String> userkitapadiList, ArrayList<String> userImageList, ArrayList<String> userkitapyorumList) {
        this.userEmailList = userEmailList;
        this.userkitapadiList = userkitapadiList;
        this.userImageList = userImageList;
        this.userkitapyorumList = userkitapyorumList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row,parent,false);
        return new PostHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
            holder.kitapadi.setText(userkitapadiList.get(position));
            holder.kitapyorum.setText(userkitapyorumList.get(position));
            holder.email.setText(userEmailList.get(position));
            Picasso.get().load(userImageList.get(position)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {

        return userEmailList.size();
    }


    class PostHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView kitapadi;
        TextView kitapyorum;
        TextView email;


        public PostHolder(@NonNull View itemView) {

            super(itemView);
            imageView=itemView.findViewById(R.id.recyclerview_image);
            kitapadi=itemView.findViewById(R.id.recyclerview_kitapadi);
            kitapyorum=itemView.findViewById(R.id.recyclerview_kitapyorum);
            email=itemView.findViewById(R.id.recyclerview_useremail);
        }
    }





}
