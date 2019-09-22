package rahim.personal.pathfinder.Activities;

import androidx.appcompat.app.AppCompatActivity;
import rahim.personal.pathfinder.R;
import rahim.personal.pathfinder.Utilities.AppContext;
import rahim.personal.pathfinder.Utilities.Helpers;
import rahim.personal.pathfinder.Views.PathGridView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.nightonke.boommenu.Animation.EaseEnum;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {
    private ALGORITHM selectedAlgorithm;
    private PathGridView pathGridView;
    private BoomMenuButton algorithmMenu;

    enum ALGORITHM {
        A_STAR,
        BFS,
        DFS,
        DIJKSTRA,
        UNKNOWN
    }

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
        selectedAlgorithm = ALGORITHM.UNKNOWN;
        
        initSettingsMenu();
        initAlgorithmMenu();
    }

    private void initAlgorithmMenu(){
        algorithmMenu = findViewById(R.id.algorithmMenu);
        algorithmMenu.setButtonEnum(ButtonEnum.Ham);
        algorithmMenu.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        algorithmMenu.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
        algorithmMenu.setShowMoveEaseEnum(EaseEnum.Linear);
        algorithmMenu.setShowRotateEaseEnum(EaseEnum.Linear);
        algorithmMenu.setShowScaleEaseEnum(EaseEnum.Linear);
        algorithmMenu.setHideMoveEaseEnum(EaseEnum.Linear);
        algorithmMenu.setHideRotateEaseEnum(EaseEnum.Linear);
        algorithmMenu.setHideScaleEaseEnum(EaseEnum.Linear);

        final int COLOR_SELECTED = Helpers.getColor(AppContext.getContext(), R.color.WhiteSmoke);
        final int COLOR_NOT_SELECTED = Helpers.getColor(AppContext.getContext(), R.color.White);

        algorithmMenu.addBuilder(
                new HamButton.Builder()
                        .normalImageRes(R.mipmap.a_star_icon)
                        .normalText("A*")
                        .subNormalText("Heuristic based search")
                        .normalColorRes(R.color.Fav2)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                algorithmMenu.getBoomButtons().forEach(new Consumer<BoomButton>() {
                                    @Override
                                    public void accept(BoomButton x) {
                                        x.getImageView().setColorFilter(COLOR_NOT_SELECTED);
                                    }
                                });
                                algorithmMenu.getBoomButton(index).getImageView().setColorFilter(COLOR_SELECTED);

                                selectedAlgorithm = ALGORITHM.A_STAR;
                                Toast.makeText(MainActivity.this, "A* search selected", Toast.LENGTH_SHORT).show();
                            }
                        }));

        algorithmMenu.addBuilder(
                new HamButton.Builder()
                        .normalImageRes(R.mipmap.bfs_icon)
                        .normalText("Breadth First Search")
                        .subNormalText("All paths simultaneously")
                        .normalColorRes(R.color.Fav3)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                algorithmMenu.getBoomButtons().forEach(new Consumer<BoomButton>() {
                                    @Override
                                    public void accept(BoomButton x) {
                                        x.getImageView().setColorFilter(COLOR_NOT_SELECTED);
                                    }
                                });
                                algorithmMenu.getBoomButton(index).getImageView().setColorFilter(COLOR_SELECTED);

                                selectedAlgorithm = ALGORITHM.BFS;
                                Toast.makeText(MainActivity.this, "Breadth First Search selected", Toast.LENGTH_SHORT).show();
                            }
                        }));

        algorithmMenu.addBuilder(
                new HamButton.Builder()
                        .normalImageRes(R.mipmap.dfs_icon)
                        .normalText("Depth First Search")
                        .subNormalText("Path by path")
                        .normalColorRes(R.color.Fav4)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                algorithmMenu.getBoomButtons().forEach(new Consumer<BoomButton>() {
                                    @Override
                                    public void accept(BoomButton x) {
                                        x.getImageView().setColorFilter(COLOR_NOT_SELECTED);
                                    }
                                });
                                algorithmMenu.getBoomButton(index).getImageView().setColorFilter(COLOR_SELECTED);

                                selectedAlgorithm = ALGORITHM.DFS;
                                Toast.makeText(MainActivity.this, "Depth First Search selected", Toast.LENGTH_SHORT).show();
                            }
                        }));

        algorithmMenu.addBuilder(
                new HamButton.Builder()
                        .normalImageRes(R.mipmap.dijkstra_icon)
                        .normalText("Dijkstra's Greedy Search")
                        .subNormalText("Greedy search technique")
                        .normalColorRes(R.color.Fav6)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                algorithmMenu.getBoomButtons().forEach(new Consumer<BoomButton>() {
                                    @Override
                                    public void accept(BoomButton x) {
                                        x.getImageView().setColorFilter(COLOR_NOT_SELECTED);
                                    }
                                });
                                algorithmMenu.getBoomButton(index).getImageView().setColorFilter(COLOR_SELECTED);

                                selectedAlgorithm = ALGORITHM.DIJKSTRA;
                                Toast.makeText(MainActivity.this, "Dijkstra's search selected", Toast.LENGTH_SHORT).show();
                            }
                        }));
    }

    private void initSettingsMenu(){
        final BoomMenuButton settingsMenu = findViewById(R.id.settingsMenu);
        settingsMenu.setButtonEnum(ButtonEnum.TextInsideCircle);
        settingsMenu.setPiecePlaceEnum(PiecePlaceEnum.DOT_7_1);
        settingsMenu.setButtonPlaceEnum(ButtonPlaceEnum.SC_7_1);
        settingsMenu.setShowMoveEaseEnum(EaseEnum.Linear);
        settingsMenu.setShowRotateEaseEnum(EaseEnum.Linear);
        settingsMenu.setShowScaleEaseEnum(EaseEnum.Linear);
        settingsMenu.setHideMoveEaseEnum(EaseEnum.Linear);
        settingsMenu.setHideRotateEaseEnum(EaseEnum.Linear);
        settingsMenu.setHideScaleEaseEnum(EaseEnum.Linear);

        settingsMenu.setBackgroundResource(R.mipmap.a_star_icon);
        settingsMenu.setForeground(null);
        settingsMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("Small")
                        .textGravity(Gravity.CENTER)
                        .textSize(16)
                        .maxLines(2)
                        .textRect(new Rect(0, 0, 280, 280))
                        .normalColorRes(R.color.colorPrimary)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                pathGridView.setParametersAndRebuild(PathGridView.GRID_SIZE_SMALL);
                            }
                        }));

        settingsMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("Medium")
                        .textGravity(Gravity.CENTER)
                        .textSize(14)
                        .maxLines(2)
                        .textRect(new Rect(0, 0, 280, 280))
                        .normalColorRes(R.color.Fav2)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                pathGridView.setParametersAndRebuild(PathGridView.GRID_SIZE_MEDIUM);
                            }
                        }));

        settingsMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("Large")
                        .textGravity(Gravity.CENTER)
                        .textSize(16)
                        .maxLines(2)
                        .textRect(new Rect(0, 0, 280, 280))
                        .normalColorRes(R.color.Fav3)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                pathGridView.setParametersAndRebuild(PathGridView.GRID_SIZE_LARGE);
                            }
                        }));

        settingsMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("Clear")
                        .textGravity(Gravity.CENTER)
                        .textSize(14)
                        .maxLines(2)
                        .textRect(new Rect(0, 0, 280, 280))
                        .normalColorRes(R.color.Fav4)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                pathGridView.clearBlockages();
                            }
                        }));

        settingsMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("Reset")
                        .textGravity(Gravity.CENTER)
                        .textSize(16)
                        .maxLines(2)
                        .textRect(new Rect(0, 0, 280, 280))
                        .normalColorRes(R.color.Fav6)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                pathGridView.Rebuild();
                                Toast.makeText(MainActivity.this, "Grid has been reset", Toast.LENGTH_SHORT).show();
                            }
                        }));

        settingsMenu.addBuilder(
                new TextInsideCircleButton.Builder()
                        .normalText("Stop")
                        .textGravity(Gravity.CENTER)
                        .textSize(16)
                        .maxLines(2)
                        .textRect(new Rect(0, 0, 280, 280))
                        .normalColorRes(R.color.colorAccent)
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
                        .normalColorRes(R.color.colorAccentDark)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Toast.makeText(MainActivity.this, "Start Search", Toast.LENGTH_SHORT).show();
                            }
                        }));
    }
}
