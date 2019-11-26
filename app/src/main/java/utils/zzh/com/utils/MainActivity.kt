package utils.zzh.com.utils

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dz.utlis.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)


        val sourBitmap = BitmapFactory.decodeResource(resources, R.mipmap.download)
        sour_pic.setImageBitmap(sourBitmap)

        val waterBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_share_wechat_friend_circle)

        var watermarkBitmap = WaterMarkUtil.createWaterMaskCenter(sourBitmap, waterBitmap)
        watermarkBitmap = WaterMarkUtil.createWaterMaskLeftBottom(this, watermarkBitmap, waterBitmap, 0, 0)
        watermarkBitmap = WaterMarkUtil.createWaterMaskRightBottom(this, watermarkBitmap, waterBitmap, 0, 0)
        watermarkBitmap = WaterMarkUtil.createWaterMaskLeftTop(this, watermarkBitmap, waterBitmap, 0, 0)
        watermarkBitmap = WaterMarkUtil.createWaterMaskRightTop(this, watermarkBitmap, waterBitmap, 0, 0)

        var textBitmap = WaterMarkUtil.drawTextToLeftTop(this, watermarkBitmap, "左上角", 16, Color.RED, 0, 0)
        textBitmap = WaterMarkUtil.drawTextToRightBottom(this, textBitmap, "右下角", 16, Color.RED, 0, 0)
        textBitmap = WaterMarkUtil.drawTextToRightTop(this, textBitmap, "右上角", 16, Color.RED, 0, 0)
        textBitmap = WaterMarkUtil.drawTextToLeftBottom(this, textBitmap, "左下角", 16, Color.RED, 0, 0)
        textBitmap = WaterMarkUtil.drawTextToCenter(this, textBitmap, "中间", 16, Color.RED)

        wartermark_pic.setImageBitmap(textBitmap)

        ToastTool.showContent("ssdnsidsidnsidnsi")

    }
}
