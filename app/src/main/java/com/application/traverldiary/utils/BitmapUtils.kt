import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import com.application.traverldiary.application.MyApplication
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream

object BitmapUtils {

    //获取Bitmap的缩略图
    fun getThumbnail(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }
}