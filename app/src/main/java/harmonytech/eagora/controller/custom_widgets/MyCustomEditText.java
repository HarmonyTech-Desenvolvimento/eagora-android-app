package harmonytech.eagora.controller.custom_widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MyCustomEditText extends android.support.v7.widget.AppCompatEditText {
    public MyCustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/CircularStd-Book.otf"));
    }
}
