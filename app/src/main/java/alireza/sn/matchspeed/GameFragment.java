package alireza.sn.matchspeed;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameFragment extends Fragment {
    private CountDownTimer countDownTimer;
    private AnimatorSet setAnimation;


    final private int[] countdownArray =
            {R.drawable.number_three, R.drawable.nubmer_two, R.drawable.number_one};

    final private int[] shapeResources =
            {R.drawable.circle_gray, R.drawable.circle_green, R.drawable.circle_red, R.drawable.circle_yellow
                    , R.drawable.triangle_gray, R.drawable.triangle_green, R.drawable.triangle_red, R.drawable.triangle_yellow
                    , R.drawable.square_gray, R.drawable.square_green, R.drawable.square_red, R.drawable.square_yellow};

    // 5 => circle , 6 => triangle , 7 => square
    final private int[] shapesArray =
            {5, 5, 5, 5
                    , 6, 6, 6, 6
                    , 7, 7, 7, 7};

    // 1 => gray  2 => green  3 => red  2 => yellow

    final private int[] colorsArray = {1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4};

    private TextView textTime;
    private Button colorButton;
    private Button bothButton;
    private Button nonButton;
    private Button shapeButton;
    private LinearLayout layoutButtons;
    private ImageView countdownImg;
    private ImageView shapes;
    private ImageView scoreImg;

    private MyImage myImage;

    private int indexImages = 0;
    private static int randomNum;
    private int score = 0;
    private boolean createObj = true;

    private String playerName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getArgs();
        return inflater.inflate(R.layout.game_fragment, container, false);
    }

    private void getArgs() {
        playerName = getArguments().getString("playerName", null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        configuration();
        createShape();
        addToObject();
        setImage();
        startGame();
    }

    private void addToObject() {

        if (createObj) {
            myImage = new MyImage(shapesArray[randomNum], colorsArray[randomNum]);
            createObj = false;
        }
    }

    private void startGame() {
        layoutButtons.setVisibility(View.GONE);
        textTime.setVisibility(View.INVISIBLE);
        animations();

        countDownTimer = new CountDownTimer(64000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int untilFinished = (int) (millisUntilFinished / 1000);
                textTime.setText("time remained :" + untilFinished);
            }

            @Override
            public void onFinish() {
                UpgradeScore();
                MessageFinishDialog m = new MessageFinishDialog(getActivity(), score);
                m.show();
                m.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                onDestroy();
                getActivity().getSupportFragmentManager().beginTransaction().remove(GameFragment.this).commit();
                return;
            }
        };
        countDownTimer.start();
    }

    private void UpgradeScore() {
        List<User> list = MyPreference.getInstance(getActivity()).getList();
        User user = new User();
        user.setPlayerName(playerName);
        user.setScore(score);
        list.add(user);
        MyPreference.getInstance(getActivity()).putList(list);
    }

    private void updatePictures() {
        ObjectAnimator outputShape = ObjectAnimator.ofFloat(shapes, "TranslationX", 0, 2000);
        outputShape.setDuration(500).start();

        ObjectAnimator inputShape = ObjectAnimator.ofFloat(shapes, "TranslationX", -2000, 0);
        inputShape.setStartDelay(500);
        inputShape.setDuration(500).start();
        addToObject();
    }

    private void configuration() {
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myImage.getColor() == colorsArray[randomNum] && myImage.getImage() != shapesArray[randomNum]) {
                    nextImage();
                    score++;
                    correctIcon();
                } else {
                    nextImage();
                    incorrectIcon();
                }

            }
        });
        nonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myImage.getImage() != shapesArray[randomNum] && myImage.getColor() != colorsArray[randomNum]) {
                    nextImage();
                    score++;
                    correctIcon();
                } else {
                    nextImage();
                    incorrectIcon();
                }
            }
        });
        bothButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myImage.getColor() == colorsArray[randomNum] &&
                        myImage.getImage() == shapesArray[randomNum]) {
                    nextImage();
                    score++;
                    correctIcon();
                } else {
                    nextImage();
                    incorrectIcon();
                }

            }
        });
        shapeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myImage.getImage() == shapesArray[randomNum] && myImage.getColor() != colorsArray[randomNum]) {
                    nextImage();
                    score++;
                    correctIcon();
                } else {
                    nextImage();
                    incorrectIcon();
                }

            }
        });
    }

    private void incorrectIcon() {
        scoreImg.setAlpha(1f);
        scoreImg.setImageResource(R.drawable.incorrect);
        scoreImg.animate().alpha(0).setDuration(500).start();
    }

    private void correctIcon() {
        scoreImg.setAlpha(1f);
        scoreImg.setImageResource(R.drawable.correct);
        scoreImg.animate().alpha(0).setDuration(500).start();

    }

    private void nextImage() {
        createObj = true;
        updatePictures();
        createShape();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                setImage();
            }
        }, 500);
    }

    private void setImage() {
        shapes.setImageResource(shapeResources[randomNum]);
    }

    private void findViews(View view) {
        textTime = view.findViewById(R.id.time_remain);
        colorButton = view.findViewById(R.id.color_btn);
        nonButton = view.findViewById(R.id.non_btn);
        bothButton = view.findViewById(R.id.both_btn);
        shapeButton = view.findViewById(R.id.shape_btn);
        layoutButtons = view.findViewById(R.id.layout_buttons);
        countdownImg = view.findViewById(R.id.img_countdown);
        shapes = view.findViewById(R.id.shapes);
        scoreImg = view.findViewById(R.id.correct_incorrect);
    }

    private void animations() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(countdownImg, "ScaleX", 1f, 6f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(countdownImg, "ScaleY", 1f, 6f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(countdownImg, "Alpha", 1, 0);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(countdownImg, "rotation", 1, 0);

        setAnimation = new AnimatorSet();
        setAnimation.playTogether(scaleX, scaleY, alpha, rotation);
        setAnimation.setDuration(1000);

        setAnimation.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (indexImages == 3) {
                    layoutButtons.setVisibility(View.VISIBLE);
                    textTime.setVisibility(View.VISIBLE);
                    createShape();
                    updatePictures();
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setImage();
                        }
                    }, 500);
                    return;
                }
                animations();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

                countdownImg.setImageResource(countdownArray[indexImages]);
                indexImages++;
            }
        });
        setAnimation.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
            countDownTimer.cancel();

        if (setAnimation != null)
            setAnimation.cancel();
    }

    private void createShape() {
        Random random = new Random();
        randomNum = random.nextInt(shapeResources.length);

    }

}
