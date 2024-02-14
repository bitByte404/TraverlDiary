package com.application.traveldiary.utils

import android.os.Build
import com.application.traveldiary.models.Comment
import com.application.traveldiary.models.Dynamic
import com.application.traveldiary.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.util.Date

// 用于获取社区部分的测试数据
object CommunityTest {
    val pictureList = listOf(
        "https://imgs.wuliner.cn/imgs/demo_pic1.jpg",
        "https://imgs.wuliner.cn/imgs/demo_pic2.jpg",
        "https://imgs.wuliner.cn/imgs/demo_pic3.jpg",
        "https://imgs.wuliner.cn/imgs/demo_pic4.jpg",
        "https://imgs.wuliner.cn/imgs/demo_pic5.png",
        "https://imgs.wuliner.cn/imgs/demo_pic6.jpg",
        "https://imgs.wuliner.cn/imgs/demo_pic7.jpg",
        "https://imgs.wuliner.cn/imgs/demo_pic8.jpg",
        "https://imgs.wuliner.cn/imgs/demo_pic9.png",
        "https://imgs.wuliner.cn/imgs/demo_pic10.jpg",
        "https://imgs.wuliner.cn/imgs/demo_pic11.jpg",
        "https://imgs.wuliner.cn/imgs/demo_pic12.jpg"
    )

    var randomAvatar: String = "https://imgs.wuliner.cn/imgs/avatar1.jpg"


    fun currentData() = Date(System.currentTimeMillis())

    fun getUser(index: Int = -1): User {
        val users = listOf("伍柠贰", "小橙子", "大橘子", "wuliner", "大孙", "阿成", "小香", "白白", "沸羊羊", "旺旺").mapIndexed { index, name ->
            User(index, name, "123", "110", 0, if (index % 2 == 0) getAvatar(2) else getAvatar(1), "")
        }
        if (index == -1) {
            return users.random()
        }
        return users[index]
    }


    // 获取头像， 默认返回随机头像，也可指定头像
    fun getAvatar(index: Int = -1): String {
        val avatar1 = "https://imgs.wuliner.cn/imgs/avatar1.jpg"
        val avatar2 = "https://imgs.wuliner.cn/imgs/avatar2.jpg"
        val avatar3 = "https://imgs.wuliner.cn/imgs/avatar3.jpg"
        val list = arrayListOf(avatar1, avatar2, avatar3)
        if (index == -1) {
            return list.random()
        }
        return list[index]
    }

    //获取图片集合，默认随机
    fun getPictures(index: Int = -1): ArrayList<String> {

        val pictures1 = arrayListOf(pictureList[0], pictureList[1], pictureList[2], pictureList[3], pictureList[4], pictureList[10])
        val pictures2 = arrayListOf(pictureList[5], pictureList[6], pictureList[7], pictureList[8], pictureList[9], pictureList[11])
        val temp = arrayListOf(pictures1, pictures2)
        if (index == -1) {
            return temp.random()
        }
        return temp[index]
    }


    //获取一张图片
    fun getOnePictureUriString(index: Int = -1): String {
        if (index == -1) {
            return pictureList.random()
        }
        return pictureList[index]
    }

    fun getComment(index: Int = -1): ArrayList<Comment> {
        val commentsContent = listOf("我简直无法用语言来形容。",
            "我真的感到非常的幸福和满足。",
            "这张图片让我想起了我曾经去过的一个地方",
            "它让我感到非常的平静和放松。",
            "这张图片真的很有艺术感",
            "这张图片让我想起了一首诗",
            "我真的很羡慕摄影师",
            "这张图片让我想起了我的家乡",
            "它让我感到非常的舒适和愉快。",
            "我可以感受到对于美景的热爱和敬仰。")

        val comments1 = arrayListOf<Comment>()
        val comments2 = arrayListOf<Comment>()
        commentsContent.mapIndexed { index, content ->
            val comment = Comment((index + 1).toString(), (index + 1).toString(), getUser(index), content, 10, currentData(), "")
            if (index < commentsContent.size / 2) {
                comments1.add(comment)
            } else {
                comments2.add(comment)
            }
        }
        val list = arrayListOf(comments1, comments2)
        if (index == -1) {
            return list.random()
        }
        return list[index]
    }

    // 获取随机动态数据
    fun getDynamic(index: Int = -1): Dynamic {
        val testDynamics = arrayListOf<Dynamic>()
        testDynamics.add(
            Dynamic(
            "0",
            "落日沉溺于橘色的海，\n晚风沦陷于赤诚的爱。",
            "我在黄昏写上一封书信，载着落日的余晖和银河的浪漫。 寄给你，寄给温柔本身。",
            getPictures(0),
            50,
            getUser(0),
            currentData(),
            getPhoneBrand(),
            getComment(0)
        )
        )

        testDynamics.add(
            Dynamic(
            "1",
            "与其幻想，不如追寻",
            "白云恋我一往情深，蓝天赐我无限盼望，选我所爱，爱我所选。",
            getPictures(1),
            34,
            getUser(1),
            currentData(),
            "iPhone15 Pro Max",
            getComment(1)))

        if (index == -1) {
            return testDynamics.random()
        }
        return testDynamics[index]
    }

    fun getPhoneBrand(): String {
        return Build.BRAND
    }


    suspend fun getRedirectedUrl(url: String, callback: () -> Unit = {}): String? {
        var redirectedUrl = ""
        withContext(Dispatchers.IO) {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.instanceFollowRedirects = false
            connection.connect()
            redirectedUrl = connection.getHeaderField("Location")
            connection.disconnect()
        }
        return redirectedUrl
    }

    suspend fun loadRandomAvatar() {
        randomAvatar = getRedirectedUrl("https://cdn.seovx.com/d/?mom=302") ?: "https://imgs.wuliner.cn/imgs/avatar1.jpg"
    }

    fun getRandomUser(): User {
        return getUser().apply {
            head = randomAvatar
        }
    }
}