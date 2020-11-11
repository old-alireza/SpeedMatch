package alireza.sn.matchspeed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RankListFragment extends Fragment {
    RecyclerView recyclerView;
    MyAdapter adapter;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rank_list,container,false);
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        configure();
    }

    private void configure() {
        List<User> userList = MyPreference.getInstance(getActivity()).getList();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyAdapter(userList);
        recyclerView.setAdapter(adapter);
    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerview_rank_list);
    }
}