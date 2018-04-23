package mank.dtusocialtodo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Swagam on 23/04/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<TodoList> list = new ArrayList<>();

    RecyclerAdapter(List<TodoList> list){
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.message.setText(list.get(position).getMessage());

        holder.message.setChecked(list.get(position).isDone());

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

