package harmonytech.praagoraa.controller.custom_widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;

public class MyCustomTextInputLayout extends TextInputLayout {

    public MyCustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/CircularStd-Book.otf"));
    }
}
