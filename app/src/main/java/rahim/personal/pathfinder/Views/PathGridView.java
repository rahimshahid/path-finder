package rahim.personal.pathfinder.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import rahim.personal.pathfinder.R;
import rahim.personal.pathfinder.Utilities.AppContext;
import rahim.personal.pathfinder.Utilities.Helpers;

public class PathGridView extends View {
    private static final String TAG = "PATH_GRID_VIEW";

    // Constants
    public static int GRID_SIZE_SMALL = 12;
    public static int GRID_SIZE_MEDIUM = 16;
    public static int GRID_SIZE_LARGE = 26;

    // Colors
    //public static int COLOR_BOX_EMPTY = Helpers.getColor(AppContext.getContext(), R.color.White);
    public static int COLOR_BORDER = Helpers.getColor(AppContext.getContext(), R.color.LightestGrey);
    public static int COLOR_BOX_BLOCKED = Helpers.getColor(AppContext.getContext(), R.color.DarkGray);
    public static int COLOR_BOX_QUEUED =  Helpers.getColor(AppContext.getContext(), R.color.LightBlue);
    public static int COLOR_BOX_DISCOVERED =  Helpers.getColor(AppContext.getContext(), R.color.LightGreen);
    public static int COLOR_BOX_START = Helpers.getColor(AppContext.getContext(), R.color.BrightGreen);
    public static int COLOR_BOX_END = Helpers.getColor(AppContext.getContext(), R.color.MatRed);
    public static int COLOR_BOX_PATH = Helpers.getColor(AppContext.getContext(), R.color.BlueViolet);

    // Flags
    private boolean GESTURE_START_BOX_GRABBED = false;
    private boolean GESTURE_END_BOX_GRABBED = false;
    private boolean GESTURE_ERASING_BLOCKAGES = false;
    private Box LAST_BOX_TOUCHED = null;

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
    private int minGridSize = 16;

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

    public void setParametersAndRebuild(int gridSize){
        minGridSize = gridSize;
        Rebuild();
    }

    public void Rebuild(){
        grid = new Grid(minGridSize, getWidth(), getHeight());
        invalidate();
    }

    public void clearBlockages(){
        grid.clearBlockages();
        invalidate();
    }

    private void initialize() {
        grid = new Grid();
        paint = new Paint();
        paint.setColor(COLOR_BORDER);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(getResources().getDisplayMetrics().density * 1);
    }

    final GestureDetector gestureDetector = new GestureDetector(AppContext.getContext(),
            new GestureDetector.SimpleOnGestureListener() {
                public void onLongPress(MotionEvent e) {
                    
                }
    });

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        int eventAction = event.getAction();
        int x = (int)event.getX();
        int y = (int)event.getY();
        Box box = grid.getBoxFromPixelLocation(x, y);
        if (box == null){
            return false;
        }
        // put your code in here to handle the event
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                if (box == LAST_BOX_TOUCHED)
                    break;
                LAST_BOX_TOUCHED = box;

                if (box.state == BOX_STATES.START){
                    GESTURE_START_BOX_GRABBED = true;
                    Helpers.vibrate(1);
                }
                else if (box.state == BOX_STATES.END){
                    GESTURE_END_BOX_GRABBED = true;
                    Helpers.vibrate(1);
                }
                else if (box.state == BOX_STATES.EMPTY) {
                    box.state = BOX_STATES.BLOCKED;
                }
                else if (box.state == BOX_STATES.BLOCKED){
                    GESTURE_ERASING_BLOCKAGES = true;
                    box.state = BOX_STATES.EMPTY;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (box == LAST_BOX_TOUCHED)
                    break;
                LAST_BOX_TOUCHED = box;

                if (GESTURE_START_BOX_GRABBED) {
                    grid.setStartBox(box);
                }
                else if (GESTURE_END_BOX_GRABBED) {
                    grid.setEndBox(box);
                }
                else if (GESTURE_ERASING_BLOCKAGES){
                    if (box.state == BOX_STATES.BLOCKED){
                        box.state = BOX_STATES.EMPTY;
                    }
                }
                else if (box.state == BOX_STATES.EMPTY) {
                    box.state = BOX_STATES.BLOCKED;
                }
                break;

            case MotionEvent.ACTION_UP:
                LAST_BOX_TOUCHED = null;
                GESTURE_START_BOX_GRABBED = false;
                GESTURE_END_BOX_GRABBED = false;
                GESTURE_ERASING_BLOCKAGES = false;
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
        grid = new Grid(minGridSize, xNew, yNew);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        grid.draw(canvas, paint);
    }
}

class Grid{
    private Box [][] Box2dArr;
    private int size_X;
    private int size_Y;

    private Box startBox;
    private Box endBox;
    private PathGridView.BOX_STATES lastStartBoxState = PathGridView.BOX_STATES.EMPTY;
    private PathGridView.BOX_STATES lastEndBoxState = PathGridView.BOX_STATES.EMPTY;

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

        // Set starting and ending blocks
        setStartBox(getBox(Helpers.getRandomNum(0, size_Y - 1), Helpers.getRandomNum(0, size_X - 1)));
        setEndBox(getBox(Helpers.getRandomNum(0, size_Y - 1), Helpers.getRandomNum(0, size_X - 1)));
    }

    public Box getStartBox() {
        return startBox;
    }

    public void setStartBox(Box startBox) {
        if (this.startBox != null)
            this.startBox.state = lastStartBoxState;
        lastStartBoxState = startBox.state;
        startBox.state = PathGridView.BOX_STATES.START;
        this.startBox = startBox;
    }

    public Box getEndBox() {
        return endBox;
    }

    public void setEndBox(Box endBox) {
        if (this.endBox != null)
            this.endBox.state = lastEndBoxState;
        lastEndBoxState = endBox.state;
        endBox.state = PathGridView.BOX_STATES.END;
        this.endBox = endBox;
    }

    Box getBox(int y, int x){
        if (y < size_Y && x < size_X) {
            return Box2dArr[y][x];
        }else{
            throw new IndexOutOfBoundsException("Box with the index Y: " + y + ", X: " + x + "does not exists" );
        }
    }

    Box getBoxFromPixelLocation(int x_location, int y_location){
        for (int i = 0; i < size_Y; i++) {
            for (int j = 0; j < size_X; j++) {
                // Find box that is touched and change its state to blocked
                if (Box2dArr[i][j].rectF.contains(x_location, y_location)) {
                    return Box2dArr[i][j];
                }
            }
        }
        return null;
    }

    void clearBlockages(){
        for (int i = 0; i < size_Y; i++) {
            for (int j = 0; j < size_X; j++) {
                if (Box2dArr[i][j].state == PathGridView.BOX_STATES.BLOCKED) {
                    Box2dArr[i][j].state = PathGridView.BOX_STATES.EMPTY;
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
                //paint.setColor(PathGridView.COLOR_BOX_EMPTY);
                //paint.setStyle(Paint.Style.FILL);
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