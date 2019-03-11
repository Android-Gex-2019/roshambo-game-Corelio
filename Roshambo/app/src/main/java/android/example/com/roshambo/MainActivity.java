/**
 * Rochambo Game
 * Author: Marco Corsini
 * Assignment 2
 * Date: march 11, 2019
 *
 * Adding comments and using small methods ;)
 */

package android.example.com.roshambo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        //load saved state
        if(savedInstanceState != null){
            rochambo = (Rochambo)savedInstanceState.getSerializable("rochambo");
            displayResult();
        }
    }

    //Play method - player choose a play
    public void play(View view) {
        //Record the play
        recordPlayerPlay(view.getTag().toString());
        //Call helper method to display results
        displayResult();
        //Call helper method to animate the result
        animate();
    }

    //Helper method to animate the result
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

    //Method to convert the image tag to a rochambo move
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

    //Helper method to display the result
    private void displayResult(){
        changeImage(rochambo.getPlayerMove(), playerImg);
        changeImage(rochambo.getGameMove(), compImg);
        changeResult(rochambo.winLoseOrDraw());
    }

    //Helper method to display the text result of a play
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

    //Helper method to display the image result of a play
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

    //Save state
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("rochambo", rochambo);
    }
}
