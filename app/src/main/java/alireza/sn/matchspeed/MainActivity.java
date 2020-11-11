package alireza.sn.matchspeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button startGameBtn;
    private Button rankListBtn;
    private Button introGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        configure();
    }

    private void configure() {
        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPlayerName();
            }
        });

        rankListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRankListFragment();

            }
        });

        introGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIntroGameFragment();
            }
        });


    }

    private void openIntroGameFragment() {
        IntroGameFragment introGame = new IntroGameFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_container,introGame).addToBackStack(null).commit();
    }

    private void openRankListFragment() {
            RankListFragment rankListFragment = new RankListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_container,rankListFragment)
                    .addToBackStack(null).commit();
    }

    private void getPlayerName() {
        GetNameDialog dialog = new GetNameDialog(MainActivity.this, new SetOnGetNameListener() {

            @Override
            public void onSetName(String name) {
                Bundle bundle = new Bundle();

                bundle.putString("playerName",name);
                GameFragment gameFragment = new GameFragment();
                gameFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().
                        add(R.id.frame_layout_container,gameFragment).
                        addToBackStack(null).commit();
            }
        });
        dialog.show();
    }

    private void findViews() {
        startGameBtn = findViewById(R.id.start_game);
        rankListBtn = findViewById(R.id.rank_list_button);
        introGame = findViewById(R.id.intro_game);

    }
}