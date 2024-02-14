import android.content.Context
import com.application.traveldiary.application.BmobApp
import com.application.traveldiary.manager.BmobManager
import com.application.traveldiary.models.Dynamic
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DynamicManager private constructor() {
    private val mBmobManager = BmobManager.getInstance()
    private var mDynamicList = emptyList<Dynamic>()
    private val context = BmobApp.getContext()

    companion object {
        val instance: DynamicManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            DynamicManager()
        }
    }

    // 从指定文件路径获取动态集合
    suspend fun getDynamicFromFile(filename: String): List<Dynamic>? {
        return withContext(Dispatchers.IO) {
            val fileInputStream = context.openFileInput(filename)
            val reader = InputStreamReader(fileInputStream)

            val gson = Gson()
            val type = object : TypeToken<List<Dynamic>>() {}.type
            val dynamics = gson.fromJson<List<Dynamic>>(reader, type)

            reader.close()

            dynamics
        }
    }

    // 将动态集合存入指定路径文件
    suspend fun dynamicsIntoFile(dynamics: List<Dynamic>, filename: String, onEnd: () -> Unit = {}) {
        withContext(Dispatchers.IO) {
            val gson = Gson()
            val json = gson.toJson(dynamics)

            val fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
            val writer = OutputStreamWriter(fileOutputStream)

            writer.write(json)
            writer.close()
            withContext(Dispatchers.Main) {
                onEnd()
            }
        }
    }
}