package alireza.sn.matchspeed;

import java.util.ArrayList;
import java.util.List;

class RankList {
    private List<User> list;

    public RankList (){
        this.list = new ArrayList<>();
    }

    public void addToList(User user) {
        list.add(user);
    }

    public List<User> getList() {
        return list;
    }
}
