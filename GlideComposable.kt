import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File

@Composable
fun GlideImage(
    modifier: Modifier = Modifier,
    data: Any,
    glideModifier: (RequestBuilder<Drawable>) -> RequestBuilder<Drawable> = {
        it
    },
    placeHolderDrawable: Drawable? = null,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit,
) {
    if (LocalInspectionMode.current) {
        Image(
            painter = painterResource(id = android.R.drawable.ic_media_play),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
        return
    }
    BoxWithConstraints(modifier = modifier) {
        val state = remember(placeHolderDrawable) {
            mutableStateOf<ImageBitmap?>(null)
        }
        val context = LocalContext.current
        DisposableEffect(data, modifier, glideModifier, placeHolderDrawable) {
            val glide = Glide.with(context)
            var builder = when (data) {
                is Int -> {
                    glide.load(data)
                }
                is Uri -> {
                    glide.load(data)
                }
                is File -> {
                    glide.load(data)
                }
                is Drawable -> {
                    glide.load(data)
                }
                is ByteArray -> {
                    glide.load(data)
                }
                is Bitmap -> {
                    glide.load(data)
                }
                is String -> {
                    glide.load(data)
                }
                else -> {
                    glide.load(data)
                }
            }
            builder = builder.placeholder(placeHolderDrawable)
            builder = glideModifier(builder)
            val request = builder.into(object: CustomTarget<Drawable>(
                constraints.maxWidth,
                constraints.maxHeight
            ) {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    state.value = resource.toBitmap().asImageBitmap()
                }

                override fun onLoadStarted(placeholder: Drawable?) {
                    if (placeholder != null) {
                        state.value = placeholder.toBitmap().asImageBitmap()
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    if (placeholder != null) {
                        state.value = placeholder.toBitmap().asImageBitmap()
                    }
                }
            }).request!!
            onDispose {
                request.clear()
            }
        }
        val currentBitmap = state.value
        if (currentBitmap != null) {
            Image(
                modifier = modifier,
                contentDescription = contentDescription,
                painter = BitmapPainter(currentBitmap),
                contentScale = contentScale,
            )
        }
    }
}
