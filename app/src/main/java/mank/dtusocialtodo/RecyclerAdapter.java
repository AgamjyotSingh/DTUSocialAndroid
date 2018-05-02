package mank.dtusocialtodo;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Swagam on 23/04/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    static List<TodoList> list = new ArrayList<>();

    RecyclerAdapter(List<TodoList> list){
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.message.setText(list.get(position).getMessage());

        if(list.get(position).isDone() == true) {
            holder.message.setChecked(list.get(position).isDone());
            holder.message.setBackgroundColor(Color.parseColor("#00FF80"));
        }

        System.out.println("STATUS:" + list.get(position).isDone());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox message;


        public MyViewHolder(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.message);


        }
    }

}

