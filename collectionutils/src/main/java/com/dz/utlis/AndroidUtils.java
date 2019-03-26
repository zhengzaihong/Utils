package com.dz.utlis;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2016/5/21
 * creat_time: 11:35
 * describe
 **/
@SuppressWarnings("all")
public class AndroidUtils {

    /**
     * 禁止滑动到头以后的发光效果
     *
     * @param view
     */
    private static void disableOverScrollMode(View view) {
        if (Build.VERSION.SDK_INT < 9) {
            return;
        }
        try {
            Method m = View.class.getMethod("setOverScrollMode", int.class);
            m.setAccessible(true);
            m.invoke(view, 2); // OVER_SCROLL_NEVER = 2
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * 获取editext文本的长度
     *
     * @param editText
     * @return
     */
    public static int getEditTextLength(EditText editText) {
        if (null != editText) {
            String string = editText.getText() + "";
            if (string.trim().length() > 0) {
                return string.length();
            }
        }
        return 0;
    }


    /**
     * 用来判断服务是否运行.
     *
     * @param mContext
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }


    /**
     * 是否在后台运行
     *
     * @param context
     * @return
     */
    public static boolean isBackgroundRunning(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    /**
     * 用于点击文字监听器
     */

    public static interface OnClickTextListener {
        void onclick(View widget);
    }

    /**
     * 设置粗体文字
     *
     * @param context       上下文
     * @param textView      texview
     * @param content       文字
     * @param startPosition 文字的开始位置
     * @param endPosition   位置的结束位置
     */

    public static void setBlodTextColor(final TextView textView, String content, int startPosition, int endPosition) {
        SpannableString spanString = new SpannableString(content);
        //再构造一个改变字体颜色的Span
        spanString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startPosition, endPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //spanString.setSpan(span, startPosition, endPosition, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置给view显示出来
        textView.setText(spanString);
    }

    /**
     * @param context       上下文
     * @param textView      texview
     * @param content       文字
     * @param startPosition 文字的开始位置
     * @param endPosition   位置的结束位置
     * @param color         文字的颜色
     */

    public static void setTextColor(final TextView textView, String content, int startPosition, int endPosition, int color) {
        SpannableString spanString = new SpannableString(content);
        //再构造一个改变字体颜色的Span
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        //将这个Span应用于指定范围的字体
        spanString.setSpan(span, startPosition, endPosition, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置给view显示出来
        textView.setText(spanString);
    }


    /**
     * @param context        上下文
     * @param textView       textView
     * @param content        文字
     * @param color          文字的颜色
     * @param colorTextArray 需要被标色的文字的数组
     */
    public static void setTextColor(final TextView textView, String content, String colorTextArray[], int color) {
        SpannableString spanString = new SpannableString(content);
        //将这个Span应用于指定范围的字体
        if (colorTextArray != null && colorTextArray.length > 0) {
            for (int i = 0; i < colorTextArray.length; i++) {
                ForegroundColorSpan span = new ForegroundColorSpan(color);
                String temtext = colorTextArray[i];
                int currentposition = content.indexOf(temtext);
                spanString.setSpan(span, currentposition, currentposition + temtext.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        //设置给view显示出来
        textView.setText(spanString);
    }


    /**
     * @param context        上下文
     * @param textView       textView
     * @param content        文字
     * @param color          文字的颜色
     * @param colorTextArray 需要被标色的文字的数组
     */
    public static void setRadioButtonTextColor(final RadioButton textView, String content, String colorTextArray[], int color) {
        SpannableString spanString = new SpannableString(content);
        //将这个Span应用于指定范围的字体
        if (colorTextArray != null && colorTextArray.length > 0) {
            for (int i = 0; i < colorTextArray.length; i++) {
                ForegroundColorSpan span = new ForegroundColorSpan(color);
                String temtext = colorTextArray[i];
                int currentposition = content.indexOf(temtext);
                spanString.setSpan(span, currentposition, currentposition + temtext.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        //设置给view显示出来
        textView.setText(spanString);
    }


    /**
     * @param context        上下文
     * @param textView       textView
     * @param content        文字
     * @param color          文字的颜色
     * @param colorTextArray 需要被标色的文字的数组
     */
    public static void setCheckBoxTextColor( final CheckBox textView, String content, String colorTextArray[], int color) {
        SpannableString spanString = new SpannableString(content);
        //将这个Span应用于指定范围的字体
        if (colorTextArray != null && colorTextArray.length > 0) {
            for (int i = 0; i < colorTextArray.length; i++) {
                ForegroundColorSpan span = new ForegroundColorSpan(color);
                String temtext = colorTextArray[i];
                int currentposition = content.indexOf(temtext);
                spanString.setSpan(span, currentposition, currentposition + temtext.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        //设置给view显示出来
        textView.setText(spanString);
    }

    /**
     * @param context       上下文
     * @param edittext      edittext
     * @param content       文字
     * @param startPosition 文字的开始位置
     * @param endPosition   位置的结束位置
     * @param color         文字的颜色
     */
    public static void setEditTextColor(final EditText edittext, String content, int startPosition, int endPosition, int color) {
        SpannableString spanString = new SpannableString(content);
        //再构造一个改变字体颜色的Span
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        //将这个Span应用于指定范围的字体
        spanString.setSpan(span, startPosition, endPosition, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置给view显示出来
        edittext.setHint(spanString);
    }

    /**
     * @param context        上下文
     * @param edittext       edittext
     * @param content        文字
     * @param color          文字的颜色
     * @param colorTextArray 需要被标色的文字的数组
     */
    public static void setEditTextColor(final EditText edittext, String content, String colorTextArray[], int color) {
        SpannableString spanString = new SpannableString(content);
        //将这个Span应用于指定范围的字体
        if (colorTextArray != null && colorTextArray.length > 0) {
            for (int i = 0; i < colorTextArray.length; i++) {
                ForegroundColorSpan span = new ForegroundColorSpan(color);
                String temtext = colorTextArray[i];
                int currentposition = content.indexOf(temtext);
                spanString.setSpan(span, currentposition, currentposition + temtext.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        //设置给view显示出来
        edittext.setText(spanString);
    }

    /**
     * 可对字体响应点击事件
     *
     * @param context 上下文
     * @param content 文字
     */
    public static void setTextColorAndClick(final TextView textView, String content, int startPosition, int endPosition, final int colors, final boolean line, final OnClickTextListener listener) {
        SpannableString spanString = new SpannableString(content);
        //再构造一个改变字体颜色的Span
//        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#F06AA3"));
        //将这个Span应用于指定范围的字体
        spanString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(colors);//设置文本颜色
                ds.setUnderlineText(line);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                if (null != listener) {
                    listener.onclick(widget);
                }
            }
        }, startPosition, endPosition, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置给view显示出来
        textView.setText(spanString);
        textView.setHighlightColor(Color.TRANSPARENT);//去掉点击后出现的高亮显示
        textView.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }


    public static void setTextColorAndClick(final TextView textView, String content, String colorTextArray[], final int colors,final boolean line,final OnClickTextListener listener) {
        SpannableString spanString = new SpannableString(content);

        for (int i = 0; i < colorTextArray.length; i++) {
            //将这个Span应用于指定范围的字体
            spanString.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(colors);//设置文本颜色
                    ds.setUnderlineText(line);      //设置下划线
                }

                @Override
                public void onClick(View widget) {
                    if (null != listener) {
                        listener.onclick(widget);
                    }
                }
            }, 0, colorTextArray[i].length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        //设置给view显示出来
        textView.setText(spanString);
        textView.setHighlightColor(Color.TRANSPARENT);//去掉点击后出现的高亮显示
        textView.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }


    public static View getView(Context context, int viewid) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(viewid, null);
    }


    // 获取手机IMEI码 .平板为null，就要取设备号
    public static String getPhoneUdid(Context mContext) {
        String ss = getPhoneIMEI(mContext);// 获取手机IMEI码
        // .我平板为null，就要取设备号
        if (null == ss)
            ss = Settings.Secure.getString(mContext.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        return ss;
    }

    public static String getPhoneIMEI(Context cxt) {
        TelephonyManager tm = (TelephonyManager) cxt.getSystemService("phone");
        //使用前注意这里的系统权限
        return tm.getDeviceId();
    }

    // 调用系统播放视频
    public static void openSystemVideo(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW); // 创建一个intent,
        intent.setType("video/*"); // 設置其type，这里使用*支持全部格式
        intent.setDataAndType(Uri.parse(url), "video/*"); // 设置播放路径，可以是本地地址，也可以是网络地址
        context.startActivity(intent); // 发送intent，启动播放器
    }

    /**
     * 调用系统打电话
     *
     * @param mContext 上下文
     * @param tel      电话号码
     */
    public static void openSystemCall(Context mContext, String tel) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + tel));
        mContext.startActivity(intent);
    }


    /**
     * 获取联系人信息，并把数据转换成json数据返回
     * ContactsContract.Contacts.CONTENT_URI= content://com.android.contacts/contacts;
     * ContactsContract.Data.CONTENT_URI = content://com.android.contacts/data;
     */
    public static String getContactInfo(Context context) throws JSONException {
        List<ContactsContract.Contacts> list;
        JSONArray contactData;
        JSONObject jsonObject = null;

        list = new ArrayList<ContactsContract.Contacts>();
        contactData = new JSONArray();
        String mimetype = "";
        int oldrid = -1;
        int contactId = -1;
        // 1.查询通讯录所有联系人信息，通过id排序，我们看下android联系人的表就知道，所有的联系人的数据是由RAW_CONTACT_ID来索引开的
        // 所以，先获取所有的人的RAW_CONTACT_ID
        Uri uri = ContactsContract.Data.CONTENT_URI; // 联系人Uri；
        Cursor cursor = context.getContentResolver().query(uri,
                null, null, null, ContactsContract.Data.RAW_CONTACT_ID);
        while (cursor.moveToNext()) {
            contactId = cursor.getInt(cursor
                    .getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID));
            if (oldrid != contactId) {
                jsonObject = new JSONObject();
                contactData.put(jsonObject);
                oldrid = contactId;
            }
            mimetype = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE)); // 取得mimetype类型,扩展的数据都在这个类型里面
            // 1.1,拿到联系人的各种名字
            if (StructuredName.CONTENT_ITEM_TYPE.equals(mimetype)) {
                cursor.getString(cursor
                        .getColumnIndex(StructuredName.DISPLAY_NAME));
                String prefix = cursor.getString(cursor
                        .getColumnIndex(StructuredName.PREFIX));
                jsonObject.put("prefix", prefix);
                String firstName = cursor.getString(cursor
                        .getColumnIndex(StructuredName.FAMILY_NAME));
                jsonObject.put("firstName", firstName);
                String middleName = cursor.getString(cursor
                        .getColumnIndex(StructuredName.MIDDLE_NAME));
                jsonObject.put("middleName", middleName);
                String lastname = cursor.getString(cursor
                        .getColumnIndex(StructuredName.GIVEN_NAME));
                jsonObject.put("lastname", lastname);
                String suffix = cursor.getString(cursor
                        .getColumnIndex(StructuredName.SUFFIX));
                jsonObject.put("phonename", firstName + middleName + lastname);
                jsonObject.put("suffix", suffix);
                String phoneticFirstName = cursor.getString(cursor
                        .getColumnIndex(StructuredName.PHONETIC_FAMILY_NAME));
                jsonObject.put("phoneticFirstName", phoneticFirstName);

                String phoneticMiddleName = cursor.getString(cursor
                        .getColumnIndex(StructuredName.PHONETIC_MIDDLE_NAME));
                jsonObject.put("phoneticMiddleName", phoneticMiddleName);
                String phoneticLastName = cursor.getString(cursor
                        .getColumnIndex(StructuredName.PHONETIC_GIVEN_NAME));
                jsonObject.put("phoneticLastName", phoneticLastName);
            }
            // 1.2 获取各种电话信息
            if (Phone.CONTENT_ITEM_TYPE.equals(mimetype)) {
                int phoneType = cursor
                        .getInt(cursor.getColumnIndex(Phone.TYPE)); // 手机
                if (phoneType == Phone.TYPE_MOBILE) {
                    String mobile = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("mobile", mobile);
                }
                // 住宅电话
                if (phoneType == Phone.TYPE_HOME) {
                    String homeNum = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("homeNum", homeNum);
                }
                // 单位电话
                if (phoneType == Phone.TYPE_WORK) {
                    String jobNum = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("jobNum", jobNum);
                }
                // 单位传真
                if (phoneType == Phone.TYPE_FAX_WORK) {
                    String workFax = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("workFax", workFax);
                }
                // 住宅传真
                if (phoneType == Phone.TYPE_FAX_HOME) {
                    String homeFax = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));

                    jsonObject.put("homeFax", homeFax);
                } // 寻呼机
                if (phoneType == Phone.TYPE_PAGER) {
                    String pager = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("pager", pager);
                }
                // 回拨号码
                if (phoneType == Phone.TYPE_CALLBACK) {
                    String quickNum = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("quickNum", quickNum);
                }
                // 公司总机
                if (phoneType == Phone.TYPE_COMPANY_MAIN) {
                    String jobTel = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("jobTel", jobTel);
                }
                // 车载电话
                if (phoneType == Phone.TYPE_CAR) {
                    String carNum = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("carNum", carNum);
                } // ISDN
                if (phoneType == Phone.TYPE_ISDN) {
                    String isdn = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("isdn", isdn);
                } // 总机
                if (phoneType == Phone.TYPE_MAIN) {
                    String tel = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("tel", tel);
                }
                // 无线装置
                if (phoneType == Phone.TYPE_RADIO) {
                    String wirelessDev = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));

                    jsonObject.put("wirelessDev", wirelessDev);
                } // 电报
                if (phoneType == Phone.TYPE_TELEX) {
                    String telegram = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("telegram", telegram);
                }
                // TTY_TDD
                if (phoneType == Phone.TYPE_TTY_TDD) {
                    String tty_tdd = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("tty_tdd", tty_tdd);
                }
                // 单位手机
                if (phoneType == Phone.TYPE_WORK_MOBILE) {
                    String jobMobile = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("jobMobile", jobMobile);
                }
                // 单位寻呼机
                if (phoneType == Phone.TYPE_WORK_PAGER) {
                    String jobPager = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("jobPager", jobPager);
                } // 助理
                if (phoneType == Phone.TYPE_ASSISTANT) {
                    String assistantNum = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("assistantNum", assistantNum);
                } // 彩信
                if (phoneType == Phone.TYPE_MMS) {
                    String mms = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("mms", mms);
                }
            }
        }
        // 查找event地址
        if (Event.CONTENT_ITEM_TYPE.equals(mimetype)) { // 取出时间类型
            int eventType = cursor.getInt(cursor.getColumnIndex(Event.TYPE)); // 生日
            if (eventType == Event.TYPE_BIRTHDAY) {
                String birthday = cursor.getString(cursor
                        .getColumnIndex(Event.START_DATE));
                jsonObject.put("birthday", birthday);
            }
            // 周年纪念日
            if (eventType == Event.TYPE_ANNIVERSARY) {
                String anniversary = cursor.getString(cursor
                        .getColumnIndex(Event.START_DATE));
                jsonObject.put("anniversary", anniversary);
            }
        }
        // 获取即时通讯消息
        if (ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE.equals(mimetype)) { // 取出即时消息类型
            int protocal = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.PROTOCOL));
            if (ContactsContract.CommonDataKinds.Im.TYPE_CUSTOM == protocal) {
                String workMsg = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                jsonObject.put("workMsg", workMsg);
            } else if (ContactsContract.CommonDataKinds.Im.PROTOCOL_MSN == protocal) {
                String workMsg = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                jsonObject.put("workMsg", workMsg);
            }
            if (ContactsContract.CommonDataKinds.Im.PROTOCOL_QQ == protocal) {
                String instantsMsg = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));

                jsonObject.put("instantsMsg", instantsMsg);
            }
        }
        // 获取备注信息
        if (ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE.equals(mimetype)) {
            String remark = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
            jsonObject.put("remark", remark);
        }
        // 获取昵称信息
        if (Nickname.CONTENT_ITEM_TYPE.equals(mimetype)) {
            String nickName = cursor.getString(cursor
                    .getColumnIndex(Nickname.NAME));
            jsonObject.put("nickName", nickName);
        }
        // 获取组织信息
        if (Organization.CONTENT_ITEM_TYPE.equals(mimetype)) { // 取出组织类型
            int orgType = cursor.getInt(cursor
                    .getColumnIndex(Organization.TYPE)); // 单位
            if (orgType == Organization.TYPE_CUSTOM) { // if (orgType ==
                // Organization.TYPE_WORK)
                // {
                String company = cursor.getString(cursor
                        .getColumnIndex(Organization.COMPANY));
                jsonObject.put("company", company);
                String jobTitle = cursor.getString(cursor
                        .getColumnIndex(Organization.TITLE));
                jsonObject.put("jobTitle", jobTitle);
                String department = cursor.getString(cursor
                        .getColumnIndex(Organization.DEPARTMENT));
                jsonObject.put("department", department);
            }
        }
        // 获取网站信息
        if (Website.CONTENT_ITEM_TYPE.equals(mimetype)) { // 取出组织类型
            int webType = cursor.getInt(cursor.getColumnIndex(Website.TYPE)); // 主页
            if (webType == Website.TYPE_CUSTOM) {

                String home = cursor.getString(cursor
                        .getColumnIndex(Website.URL));
                jsonObject.put("home", home);
            } // 主页
            else if (webType == Website.TYPE_HOME) {
                String home = cursor.getString(cursor
                        .getColumnIndex(Website.URL));
                jsonObject.put("home", home);
            }
            // 个人主页
            if (webType == Website.TYPE_HOMEPAGE) {
                String homePage = cursor.getString(cursor
                        .getColumnIndex(Website.URL));
                jsonObject.put("homePage", homePage);
            }
            // 工作主页
            if (webType == Website.TYPE_WORK) {
                String workPage = cursor.getString(cursor
                        .getColumnIndex(Website.URL));
                jsonObject.put("workPage", workPage);
            }
        }
        // 查找通讯地址
        if (StructuredPostal.CONTENT_ITEM_TYPE.equals(mimetype)) { // 取出邮件类型
            int postalType = cursor.getInt(cursor
                    .getColumnIndex(StructuredPostal.TYPE)); // 单位通讯地址
            if (postalType == StructuredPostal.TYPE_WORK) {
                String street = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.STREET));
                jsonObject.put("street", street);
                String ciry = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.CITY));
                jsonObject.put("ciry", ciry);
                String box = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.POBOX));
                jsonObject.put("box", box);
                String area = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.NEIGHBORHOOD));
                jsonObject.put("area", area);

                String state = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.REGION));
                jsonObject.put("state", state);
                String zip = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.POSTCODE));
                jsonObject.put("zip", zip);
                String country = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.COUNTRY));
                jsonObject.put("country", country);
            }
            // 住宅通讯地址
            if (postalType == StructuredPostal.TYPE_HOME) {
                String homeStreet = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.STREET));
                jsonObject.put("homeStreet", homeStreet);
                String homeCity = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.CITY));
                jsonObject.put("homeCity", homeCity);
                String homeBox = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.POBOX));
                jsonObject.put("homeBox", homeBox);
                String homeArea = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.NEIGHBORHOOD));
                jsonObject.put("homeArea", homeArea);
                String homeState = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.REGION));
                jsonObject.put("homeState", homeState);
                String homeZip = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.POSTCODE));
                jsonObject.put("homeZip", homeZip);
                String homeCountry = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.COUNTRY));
                jsonObject.put("homeCountry", homeCountry);
            }
            // 其他通讯地址
            if (postalType == StructuredPostal.TYPE_OTHER) {
                String otherStreet = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.STREET));
                jsonObject.put("otherStreet", otherStreet);

                String otherCity = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.CITY));
                jsonObject.put("otherCity", otherCity);
                String otherBox = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.POBOX));
                jsonObject.put("otherBox", otherBox);
                String otherArea = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.NEIGHBORHOOD));
                jsonObject.put("otherArea", otherArea);
                String otherState = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.REGION));
                jsonObject.put("otherState", otherState);
                String otherZip = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.POSTCODE));
                jsonObject.put("otherZip", otherZip);
                String otherCountry = cursor.getString(cursor
                        .getColumnIndex(StructuredPostal.COUNTRY));
                jsonObject.put("otherCountry", otherCountry);
            }
        }
        cursor.close();
        return contactData.toString();
    }

}
