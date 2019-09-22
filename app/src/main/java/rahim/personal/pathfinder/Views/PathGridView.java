package rahim.personal.pathfinder.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import rahim.personal.pathfinder.R;
import rahim.personal.pathfinder.Utilities.AppContext;
import rahim.personal.pathfinder.Utilities.Helpers;

public class PathGridView extends View {
    private static final String TAG = "PATH_GRID_VIEW";

    // Constants
    public static int MIN_GRID_SIZE = 16;
    // Colors
    public static int COLOR_BORDER = Helpers.getColor(AppContext.getContext(), R.color.LightestGrey);
    public static int COLOR_BOX_EMPTY = Helpers.getColor(AppContext.getContext(), R.color.White);
    public static int COLOR_BOX_BLOCKED = Helpers.getColor(AppContext.getContext(), R.color.DarkGray);
    public static int COLOR_BOX_QUEUED =  Helpers.getColor(AppContext.getContext(), R.color.LightBlue);
    public static int COLOR_BOX_DISCOVERED =  Helpers.getColor(AppContext.getContext(), R.color.LightGreen);
    public static int COLOR_BOX_START = Helpers.getColor(AppContext.getContext(), R.color.BrightGreen);
    public static int COLOR_BOX_END = Helpers.getColor(AppContext.getContext(), R.color.MatRed);
    public static int COLOR_BOX_PATH = Helpers.getColor(AppContext.getContext(), R.color.BlueViolet);

    // Box States
    public enum BOX_STATES{
        EMPTY,
        START,
        END,
        BLOCKED,
        QUEUED,
        DISCOVERED,
        PATH
    }

    // Members
    private Paint paint;
    private Grid grid;

    public PathGridView(Context context) {
        super(context);
        initialize();
    }

    public PathGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public PathGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        grid = new Grid();
        paint = new Paint();
        paint.setColor(COLOR_BORDER);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(getResources().getDisplayMetrics().density * 1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int eventAction = event.getAction();
        int x = (int)event.getX();
        int y = (int)event.getY();

        // put your code in here to handle the event
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                grid.OnUserTap(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                grid.OnUserTap(x, y);
                break;
        }
        // tell the View to redraw the Canvas
        invalidate();
        // tell the View that we handled the event
        return true;
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        // Rebuild grid when size is changed
        grid = new Grid(MIN_GRID_SIZE, xNew, yNew);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        grid.draw(canvas, paint);
    }
}

class Grid{
    private int size_X;
    private int size_Y;
    private Box [][] Box2dArr;

    Grid(){
        size_X = 0;
        size_Y = 0;
    }

    Grid(int minGridSize, int viewWidth, int viewHeight){
        // Calculate size: Fix either X or Y, then calculate the other dimension
        // based on view aspect ratio to get square boxes
        if (viewHeight > viewWidth) {
            // PORTRAIT
            size_X = minGridSize;
            double aspectRatio = (double) viewHeight / (double) viewWidth;
            size_Y = (int) Math.ceil(aspectRatio * (double) size_X);
        } else{
            // LANDSCAPE
            size_Y = minGridSize;
            double aspectRatio = (double) viewWidth / (double) viewHeight;
            size_X = (int) Math.ceil(aspectRatio * (double) size_Y);
        }

        float BOX_WIDTH = viewWidth / size_X;
        float BOX_HEIGHT = viewHeight / size_Y;

        // Initialize Grid
        Box2dArr = new Box[size_Y][size_X];
        for (int i = 0; i < size_Y; i++){
            for (int j = 0; j < size_X; j++){
                float top = i * BOX_HEIGHT;
                float left = j * BOX_WIDTH;
                float bottom = (i + 1)  * BOX_HEIGHT;
                float right = (j + 1)  * BOX_WIDTH;
                Box2dArr[i][j] = new Box(top, left, bottom, right); // Init Boxes
            }
        }
    }

    Box getBox(int y, int x){
        if (y < size_Y && x < size_X) {
            return Box2dArr[y][x];
        }else{
            throw new IndexOutOfBoundsException("Box with the index Y: " + y + ", X: " + x + "does not exists" );
        }
    }

    void OnUserTap(int x_location, int y_location){
        for (int i = 0; i < size_Y; i++){
            for (int j = 0; j < size_X; j++){
                // Find box that is touched and change its state to blocked
                if (Box2dArr[i][j].rectF.contains(x_location, y_location)){
                    Box2dArr[i][j].state = PathGridView.BOX_STATES.BLOCKED;
                }
            }
        }
    }

    void draw(Canvas canvas, Paint paint){
        for (int i = 0; i < size_Y; i++){
            for (int j = 0; j < size_X; j++){
                Box2dArr[i][j].draw(canvas, paint);
            }
        }
    }
}

class Box{
    RectF rectF;
    PathGridView.BOX_STATES state;

    Box(float top, float left, float bottom, float right){
        rectF = new RectF(left, top, right, bottom);
        state = PathGridView.BOX_STATES.EMPTY;
    }

    void draw(Canvas canvas, Paint paint){
        int previousPaintColor = paint.getColor();

        switch (state){
            case EMPTY:
                paint.setColor(PathGridView.COLOR_BOX_EMPTY);
                paint.setStyle(Paint.Style.FILL);
                break;
            case BLOCKED:
                paint.setColor(PathGridView.COLOR_BOX_BLOCKED);
                paint.setStyle(Paint.Style.FILL);
                break;
            case START:
                paint.setColor(PathGridView.COLOR_BOX_START);
                paint.setStyle(Paint.Style.FILL);
                break;
            case END:
                paint.setColor(PathGridView.COLOR_BOX_END);
                paint.setStyle(Paint.Style.FILL);
                break;
            case DISCOVERED:
                paint.setColor(PathGridView.COLOR_BOX_DISCOVERED);
                paint.setStyle(Paint.Style.FILL);
                break;
            case QUEUED:
                paint.setColor(PathGridView.COLOR_BOX_QUEUED);
                paint.setStyle(Paint.Style.FILL);
                break;
            case PATH:
                paint.setColor(PathGridView.COLOR_BOX_PATH);
                paint.setStyle(Paint.Style.FILL);
                break;
        }
        canvas.drawRect(rectF, paint); // Draw Filled Rectangle
        paint.setColor(previousPaintColor); // Reset Paint
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectF, paint); // Draw Border
    }
}