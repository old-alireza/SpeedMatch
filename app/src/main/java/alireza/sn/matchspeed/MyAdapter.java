package alireza.sn.matchspeed;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<User> list;

    public MyAdapter(List<User> list) {
        this.list = list;
        sortingList();
    }

    private void sortingList() {
        Comparator<User> comparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if (o1.getScore()<o2.getScore())
                    return +1;
                else if (o1.getScore()>o2.getScore())
                    return -1;
                else
                return 0;
            }
        };
        Collections.sort(list,comparator);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getPlayerName());
        holder.score.setText(String.valueOf(list.get(position).getScore()));
        holder.number.setText(String.valueOf((position+1)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

   public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView score;
        public TextView number;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            score = itemView.findViewById(R.id.score);
            number = itemView.findViewById(R.id.number);
        }
    }
}
