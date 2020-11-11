package alireza.sn.matchspeed;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleableRes;

class MessageFinishDialog extends Dialog {
    private int score;
    private TextView textScore;

    public MessageFinishDialog( Context context,int score) {
        super(context);
        this.score = score;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_finish);
        findView();
        init();
    }



    private void init() {
        textScore.setText("your score : "+score);
    }

    private void findView() {
        textScore = findViewById(R.id.textVIew_score_dialog);
    }
}
