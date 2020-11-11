package alireza.sn.matchspeed;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GetNameDialog extends Dialog {
    private SetOnGetNameListener listener;
    private EditText name;
    private Button startBtn;

    public GetNameDialog( Context context,SetOnGetNameListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_get_name);
        findViews();
        init();
    }

    private void init() {

        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String playerName = name.getText().toString();

                if (playerName.isEmpty()){
                    Toast.makeText(getContext(), "this field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onSetName(playerName);
                dismiss();
            }
        });
    }


    private void findViews() {
        name = findViewById(R.id.player_name);
        startBtn = findViewById(R.id.start_game_dialog);
    }
}
