package rahim.personal.pathfinder.Activities;

import androidx.appcompat.app.AppCompatActivity;
import rahim.personal.pathfinder.R;
import rahim.personal.pathfinder.Views.PathGridView;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

public class MainActivity extends AppCompatActivity {
    private PathGridView pathGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Set Content
        setContentView(R.layout.activity_main);
        pathGridView = findViewById(R.id.pathGridView);
        initMenus();
    }

    private void initMenus(){
        initAlgorithmMenu();
        initSettingsMenu();
    }

    private void initAlgorithmMenu(){
        BoomMenuButton algorithmMenu = findViewById(R.id.algorithmMenu);
        algorithmMenu.setButtonEnum(ButtonEnum.TextInsideCircle);
        algorithmMenu.setPiecePlaceEnum(PiecePlaceEnum.DOT_4_1);
        algorithmMenu.setButtonPlaceEnum(ButtonPlaceEnum.SC_4_1);

        algorithmMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("A*")
                        .textGravity(Gravity.CENTER)
                        .textSize(20)
                        .textRect(new Rect(0, 0, 280, 280))
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Toast.makeText(MainActivity.this, "A* search selected", Toast.LENGTH_SHORT).show();
                            }
                        }));

        algorithmMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("BFS")
                        .textGravity(Gravity.CENTER)
                        .textSize(16)
                        .textRect(new Rect(0, 0, 280, 280))
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Toast.makeText(MainActivity.this, "Breadth First Search selected", Toast.LENGTH_SHORT).show();
                            }
                        }));

        algorithmMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("DFS")
                        .textGravity(Gravity.CENTER)
                        .textSize(16)
                        .textRect(new Rect(0, 0, 280, 280))
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Toast.makeText(MainActivity.this, "Depth First Search selected", Toast.LENGTH_SHORT).show();
                            }
                        }));

        algorithmMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("Dijkstra")
                        .textGravity(Gravity.CENTER)
                        .textSize(14)
                        .textRect(new Rect(0, 0, 280, 280))
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Toast.makeText(MainActivity.this, "Dijkstra's search selected", Toast.LENGTH_SHORT).show();
                            }
                        }));
    }

    private void initSettingsMenu(){
        BoomMenuButton settingsMenu = findViewById(R.id.settingsMenu);
        settingsMenu.setButtonEnum(ButtonEnum.TextInsideCircle);
        settingsMenu.setPiecePlaceEnum(PiecePlaceEnum.DOT_7_1);
        settingsMenu.setButtonPlaceEnum(ButtonPlaceEnum.SC_7_1);

        settingsMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("Small")
                        .textGravity(Gravity.CENTER)
                        .textSize(16)
                        .maxLines(2)
                        .textRect(new Rect(0, 0, 280, 280))
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Toast.makeText(MainActivity.this, "Small Grid", Toast.LENGTH_SHORT).show();
                            }
                        }));

        settingsMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("Medium")
                        .textGravity(Gravity.CENTER)
                        .textSize(14)
                        .maxLines(2)
                        .textRect(new Rect(0, 0, 280, 280))
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Toast.makeText(MainActivity.this, "Medium Grid", Toast.LENGTH_SHORT).show();
                            }
                        }));

        settingsMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("Large")
                        .textGravity(Gravity.CENTER)
                        .textSize(16)
                        .maxLines(2)
                        .textRect(new Rect(0, 0, 280, 280))
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Toast.makeText(MainActivity.this, "Large Grid", Toast.LENGTH_SHORT).show();
                            }
                        }));

        settingsMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("Unblock")
                        .textGravity(Gravity.CENTER)
                        .textSize(14)
                        .maxLines(2)
                        .textRect(new Rect(0, 0, 280, 280))
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Toast.makeText(MainActivity.this, "Clear Blockages", Toast.LENGTH_SHORT).show();
                            }
                        }));

        settingsMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("Reset")
                        .textGravity(Gravity.CENTER)
                        .textSize(16)
                        .maxLines(2)
                        .textRect(new Rect(0, 0, 280, 280))
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Toast.makeText(MainActivity.this, "Reset Grid", Toast.LENGTH_SHORT).show();
                            }
                        }));

        settingsMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("Stop")
                        .textGravity(Gravity.CENTER)
                        .textSize(16)
                        .maxLines(2)
                        .textRect(new Rect(0, 0, 280, 280))
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Toast.makeText(MainActivity.this, "Stop Search", Toast.LENGTH_SHORT).show();
                            }
                        }));

        settingsMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("Start")
                        .textGravity(Gravity.CENTER)
                        .textSize(16)
                        .maxLines(2)
                        .textRect(new Rect(0, 0, 280, 280))
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Toast.makeText(MainActivity.this, "Start Search", Toast.LENGTH_SHORT).show();
                            }
                        }));
    }
}
