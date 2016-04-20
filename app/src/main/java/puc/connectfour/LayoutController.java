package puc.connectfour;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by Rafael on 18/04/2016.
 */
public class LayoutController {
    private static LayoutController ourInstance = new LayoutController();
    private Context context;

    private Drawable bgEmpty, bgRed, bgYellow;
    private ImageView img1, img2, img3, img4, img5, img6, img7;

    public static LayoutController getInstance(Context context) {
        ourInstance.context = context;
        return ourInstance;
    }

    private LayoutController() {
        // Define os 3 layouts para controle
        bgEmpty = context.getDrawable(R.drawable.bg_circle_empty);
        bgRed = context.getDrawable(R.drawable.bg_circle_red);
        bgYellow = context.getDrawable(R.drawable.bg_circle_yellow);

//        linkScreenElements();
    }
}
