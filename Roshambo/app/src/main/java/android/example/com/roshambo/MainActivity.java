package android.example.com.roshambo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private ImageView playerImg;
    private ImageView compImg;
    private Rochambo rochambo = new Rochambo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        result.setText("Choose your play");
        playerImg = findViewById(R.id.player_move_img);
        compImg = findViewById(R.id.comp_move_img);
    }

    public void play(View view) {
        recordPlayerPlay(view.getTag().toString());
        displayResult();
        animate();
    }

    private void animate() {
        ObjectAnimator animatorPlayer = ObjectAnimator.ofFloat(playerImg,
                "rotationX", 0f, 360f)
                .setDuration(500);
        ObjectAnimator animatorGame = ObjectAnimator.ofFloat(compImg,
                "rotationY", 0f, 360f)
                .setDuration(500);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorGame, animatorPlayer);
        set.setInterpolator(new AnticipateOvershootInterpolator());
        set.start();
    }

    private void recordPlayerPlay(String play){
        switch (play){
            case "ROCK":
                rochambo.playerMakesMove(Rochambo.ROCK);
                break;
            case "PAPER":
                rochambo.playerMakesMove(Rochambo.PAPER);
                break;
            case "SCISSOR":
                rochambo.playerMakesMove(Rochambo.SCISSOR);
                break;
            default:
                rochambo.playerMakesMove(Rochambo.NONE);
        }
    }

    private void displayResult(){
        changeImage(rochambo.getPlayerMove(), playerImg);
        changeImage(rochambo.getGameMove(), compImg);
        changeResult(rochambo.winLoseOrDraw());
    }

    private void changeResult(int winLoseOrDraw) {
        switch (winLoseOrDraw){
            case Rochambo.DRAW:
                result.setText(R.string.draw);
                break;
            case Rochambo.WIN:
                result.setText(R.string.win);
                break;
            case Rochambo.LOSE:
                result.setText(R.string.lose);
                break;
            default:
                result.setText(R.string.draw);
        }
    }

    private void changeImage(int play, ImageView img){
        switch (play){
            case Rochambo.ROCK:
                img.setImageResource(R.drawable.rock);
                break;
            case Rochambo.PAPER:
                img.setImageResource(R.drawable.paper);
                break;
            case Rochambo.SCISSOR:
                img.setImageResource(R.drawable.scissors);
                break;
            default:
                img.setImageResource(R.drawable.none);
        }
    }
}
