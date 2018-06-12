package utils.zzh.com.utils

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dz.utlis.WatermarkUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val sourBitmap = BitmapFactory.decodeResource(resources, R.mipmap.download)
        sour_pic.setImageBitmap(sourBitmap)

        val waterBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_share_wechat_friend_circle)

        var watermarkBitmap = WatermarkUtil.createWaterMaskCenter(sourBitmap, waterBitmap)
        watermarkBitmap = WatermarkUtil.createWaterMaskLeftBottom(this, watermarkBitmap, waterBitmap, 0, 0)
        watermarkBitmap = WatermarkUtil.createWaterMaskRightBottom(this, watermarkBitmap, waterBitmap, 0, 0)
        watermarkBitmap = WatermarkUtil.createWaterMaskLeftTop(this, watermarkBitmap, waterBitmap, 0, 0)
        watermarkBitmap = WatermarkUtil.createWaterMaskRightTop(this, watermarkBitmap, waterBitmap, 0, 0)

        var textBitmap = WatermarkUtil.drawTextToLeftTop(this, watermarkBitmap, "左上角", 16, Color.RED, 0, 0)
        textBitmap = WatermarkUtil.drawTextToRightBottom(this, textBitmap, "右下角", 16, Color.RED, 0, 0)
        textBitmap = WatermarkUtil.drawTextToRightTop(this, textBitmap, "右上角", 16, Color.RED, 0, 0)
        textBitmap = WatermarkUtil.drawTextToLeftBottom(this, textBitmap, "左下角", 16, Color.RED, 0, 0)
        textBitmap = WatermarkUtil.drawTextToCenter(this, textBitmap, "中间", 16, Color.RED)

        wartermark_pic.setImageBitmap(textBitmap)
    }
}
