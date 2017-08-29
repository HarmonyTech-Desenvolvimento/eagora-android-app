package harmonytech.praagoraa.controller.custom_widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by islane on 29/08/17.
 */

public class MyCustomSpinner extends android.support.v7.widget.AppCompatSpinner {
    public MyCustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        sTypeFace(context);

    }

    public static Typeface sTypeFace(Context mCnxt) {
        Typeface mtypeface = Typeface.createFromAsset(mCnxt.getAssets(),
                "fonts/CircularStd-Book.otf");
        return mtypeface;
    }
}
