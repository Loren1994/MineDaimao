package util;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by Loren on 2016/4/5.
 */
public class DraweeViewUtils {
    public static void setDraweeView(Uri uri, SimpleDraweeView draweeView) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setAutoRotateEnabled(true)
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setImageRequest(request)
                .build();
        draweeView.setController(controller);
    }
    /**
     * <com.facebook.drawee.view.SimpleDraweeView
     android:id="@+id/background_image"
     android:layout_width="match_parent"
     android:layout_height="match_parent"
     android:scaleType="centerCrop"
     fresco:actualImageScaleType="centerCrop"
     fresco:failureImage="@mipmap/ic_default_avatar"
     fresco:failureImageScaleType="centerCrop"
     fresco:placeholderImageScaleType="focusCrop" />
     */
}
