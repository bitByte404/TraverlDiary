import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import com.application.traverldiary.application.MyApplication
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream

object BitmapUtils {

    //将bitmap设置给ImageView
//    fun setBitmapToImageView(imageView: ImageView, bitmap: Bitmap) {
//        val context = MyApplication.getContext()
//        // 将Bitmap转为Url
//        val bytes = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
//        val imageUri = Uri.parse(path)
//
//        // 通过Glide将Bitmap设置给imageView
//        Glide.with(context)
//            .load(imageUri)
//            .into(imageView)
//    }


    fun getThumbnail(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }
}