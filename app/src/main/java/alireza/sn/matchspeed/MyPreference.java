package alireza.sn.matchspeed;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

class MyPreference{
    private static MyPreference instance = null;
    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    private MyPreference(Context context){
        sharedPreferences = context.getSharedPreferences("preference",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static MyPreference getInstance(Context context){
        if (instance==null)
            instance = new MyPreference(context);

        return instance;
    }

    public void putList(List<User> userList) {
        Gson gson = new Gson();
        String strUserList = gson.toJson(userList,ArrayList.class);
        editor.putString("list",strUserList);
        editor.apply();
    }

    public ArrayList getList() {
        Gson gson = new Gson();
        String strUserList = sharedPreferences.getString("list",null);

        if (strUserList == null)
            return new ArrayList<>();

        return gson.fromJson(strUserList,new TypeToken<ArrayList<User>>(){}.getType());
    }

}
