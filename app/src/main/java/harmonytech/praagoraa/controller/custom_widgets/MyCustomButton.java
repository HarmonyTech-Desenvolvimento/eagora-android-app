package harmonytech.praagoraa.controller.custom_widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MyCustomButton extends android.support.v7.widget.AppCompatButton {

    public MyCustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/CircularStd-Book.otf"));
    }
}
