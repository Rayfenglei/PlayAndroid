package com.example.ray.playviewandroid.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.ray.playviewandroid.PlayApplication;
import com.example.ray.playviewandroid.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 通用操作类
 *
 * @use CommonUtil.xxxMethod(...);
 */
public class CommonUtil {
    private static final String TAG = "CommonUtil";

    public CommonUtil() {/* 不能实例化**/}

    //电话<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 打电话
     *
     * @param context
     * @param phone
     */
    public static void call(Activity context, String phone) {
        if (StringUtil.isNotEmpty(phone, true)) {
            Uri uri = Uri.parse("tel:" + phone.trim());
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            toActivity(context, intent);
            return;
        }
        showShortToast(context, "请先选择号码哦~");
    }

    /**
     * 跳转到拨号盘
     *
     * @param context
     * @param phoneUri
     */
    public static void dial(Activity context, String phoneUri) {
        if (StringUtil.isNotEmpty(phoneUri, true)) {
            Uri uri = Uri.parse(phoneUri);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            toActivity(context, intent);
            return;
        }
    }

    //电话>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 发送信息，多号码
     *
     * @param context
     * @param phoneList
     */
    public static void toMessageChat(Activity context, List<String> phoneList) {
        if (context == null || phoneList == null || phoneList.size() <= 0) {
            LogUtil.d(TAG, "sendMessage context == null || phoneList == null || phoneList.size() <= 0 " +
                    ">> showShortToast(context, 请先选择号码哦~); return; ");
            showShortToast(context, "请先选择号码哦~");
            return;
        }

        String phones = "";
        for (int i = 0; i < phoneList.size(); i++) {
            phones += phoneList.get(i) + ";";
        }
        toMessageChat(context, phones);
    }

    /**
     * 发送信息，单个号码
     *
     * @param context
     * @param phone
     */
    public static void toMessageChat(Activity context, String phone) {
        if (context == null || !StringUtil.isNotEmpty(phone, true)) {
            LogUtil.d(TAG, "sendMessage  context == null || StringUtil.isNotEmpty(phone, true) == false) >> return;");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("address", phone);
        intent.setType("vnd.android-dir/mms-sms");
        toActivity(context, intent);

    }

    //信息>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * 分享信息
     *
     * @param context
     * @param toShare
     */
    public static void shareInfo(Activity context, String toShare) {
        String title = context.getResources().getString(R.string.share);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, toShare);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, title));
    }

    /**
     * 发送邮件
     *
     * @param context
     * @param emailAddress
     */
    public static void sendEmail(Activity context, String emailAddress) {
        if (context == null || !StringUtil.isNotEmpty(emailAddress, true)) {
            LogUtil.d(TAG, "sendEmail  context == null || StringUtil.isNotEmpty(emailAddress, true) == false >> return;");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + emailAddress));//缺少"mailto:"前缀导致找不到应用崩溃
        intent.putExtra(Intent.EXTRA_TEXT, "内容");  //最近在MIUI7上无内容导致无法跳到编辑邮箱界面
        toActivity(context, intent, -1);
    }

    /**
     * 打开网站
     *
     * @param context
     * @param address
     */
    public static void openWebSite(Activity context, String address) {

        if (TextUtils.isEmpty(address) || address.startsWith("file://")) {
            showShortToast(context,context.getString(R.string.article_browser_error));
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(address));
        if(context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null){
            context.startActivity(intent);
        }else {
            showShortToast(context,context.getString(R.string.open_browser_unknown));
        }
    }

    /**
     * 复制文字
     *
     * @param context
     * @param value
     */
    public static void copyText(Context context, String value) {
        if (context == null || !StringUtil.isNotEmpty(value, true)) {
            LogUtil.d(TAG, "copyText  context == null || StringUtil.isNotEmpty(value, true) == false >> return;");
            return;
        }

        ClipData cD = ClipData.newPlainText("simple text", value);
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(cD);
        showShortToast(context, "已复制\n" + value);
    }

    public static boolean isHtmlTextCopy(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboardManager.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            ClipData.Item item = clipData.getItemAt(0);
            CharSequence htmlText = item.getHtmlText();
            LogUtil.d(TAG, "isHtmlTextCopy :: htmlText = " + htmlText);
            if (!TextUtils.isEmpty(htmlText)) {
                return true;
            }
        }
        return false;
    }


    //启动新Activity方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     */
    public static void toActivity(final Activity context, final Intent intent) {
        toActivity(context, intent, true);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param showAnimation
     */
    public static void toActivity(final Activity context, final Intent intent, final boolean showAnimation) {
        toActivity(context, intent, -1, showAnimation);
    }

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     * @param requestCode
     */
    public static void toActivity(final Activity context, final Intent intent, final int requestCode) {
        toActivity(context, intent, requestCode, true);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param requestCode
     * @param showAnimation
     */
    public static void toActivity(final Activity context, final Intent intent, final int requestCode, final boolean showAnimation) {
        if (context == null || intent == null) {
            return;
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (requestCode < 0) {
                    context.startActivity(intent);
                } else {
                    context.startActivityForResult(intent, requestCode);
                }
            }
        });
    }
    //启动新Activity方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //显示与关闭进度弹窗方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private static ProgressDialog progressDialog = null;

    /**
     * 展示加载进度条,无标题
     *
     * @param stringResId
     */
    public static void showProgressDialog(Activity context, int stringResId) {
        try {
            showProgressDialog(context, null, context.getResources().getString(stringResId));
        } catch (Exception e) {
            LogUtil.d(TAG, "showProgressDialog  showProgressDialog(Context context, null, context.getResources().getString(stringResId));");
        }
    }

    /**
     * 展示加载进度条,无标题
     *
     * @param dialogMessage
     */
    public void showProgressDialog(Activity context, String dialogMessage) {
        showProgressDialog(context, null, dialogMessage);
    }

    /**
     * 展示加载进度条
     *
     * @param dialogTitle   标题
     * @param dialogMessage 信息
     */
    public static void showProgressDialog(final Activity context, final String dialogTitle, final String dialogMessage) {
        if (context == null) {
            return;
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(context);
                }
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (dialogTitle != null && !"".equals(dialogTitle.trim())) {
                    progressDialog.setTitle(dialogTitle);
                }
                if (dialogMessage != null && !"".equals(dialogMessage.trim())) {
                    progressDialog.setMessage(dialogMessage);
                }
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
        });
    }


    /**
     * 隐藏加载进度
     */
    public static void dismissProgressDialog(Activity context) {
        if (context == null || progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        });
    }
    //显示与关闭进度弹窗方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //show short toast 方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 快捷显示short toast方法，需要long toast就用 Toast.makeText(string, Toast.LENGTH_LONG).show(); ---不常用所以这个类里不写
     *
     * @param context
     * @param stringResId
     */
    public static void showShortToast(final Context context, int stringResId) {
        try {
            showShortToast(context, context.getResources().getString(stringResId));
        } catch (Exception e) {
            LogUtil.d(TAG, "showShortToast  context.getResources().getString(resId) >>  catch (Exception e) {" + e.getMessage());
        }
    }

    /**
     * 快捷显示short toast方法，需要long toast就用 Toast.makeText(string, Toast.LENGTH_LONG).show(); ---不常用所以这个类里不写
     *
     * @param string
     */
    public static void showShortToast(final Context context, final String string) {
        showShortToast(context, string, false);
    }

    /**
     * 快捷显示short toast方法，需要long toast就用 Toast.makeText(string, Toast.LENGTH_LONG).show(); ---不常用所以这个类里不写
     *
     * @param string
     * @param isForceDismissProgressDialog
     */
    public static void showShortToast(final Context context, final String string, final boolean isForceDismissProgressDialog) {
        if (context == null) {
            return;
        }
        Toast toast = Toast.makeText(context, "" + string, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }
    //show short toast 方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    /**
     * 将图片改为圆角类型
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    /**
     * 保存照片到SD卡上面
     *
     * @param path
     * @param photoName
     * @param formSuffix
     * @param photoBitmap
     */
    public static String savePhotoToSDCard(String path, String photoName, String formSuffix, Bitmap photoBitmap) {
        if (photoBitmap == null || !StringUtil.isNotEmpty(path, true)
                || !StringUtil.isNotEmpty(StringUtil.getTrimedString(photoName)
                + StringUtil.getTrimedString(formSuffix), true)) {
            LogUtil.d(TAG, "savePhotoToSDCard photoBitmap == null || StringUtil.isNotEmpty(path, true) == false" +
                    "|| StringUtil.isNotEmpty(photoName, true) == false) >> return null");
            return null;
        }

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File photoFile = new File(path, photoName + "." + formSuffix); // 在指定路径下创建文件
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                        fileOutputStream)) {
                    fileOutputStream.flush();
                    LogUtil.i(TAG, "savePhotoToSDCard<<<<<<<<<<<<<<\n" + photoFile.getAbsolutePath() + "\n>>>>>>>>> succeed!");
                }
            } catch (FileNotFoundException e) {
                LogUtil.d(TAG, "savePhotoToSDCard catch (FileNotFoundException e) {\n " + e.getMessage());
                photoFile.delete();
                //				e.printStackTrace();
            } catch (IOException e) {
                LogUtil.d(TAG, "savePhotoToSDCard catch (IOException e) {\n " + e.getMessage());
                photoFile.delete();
                //				e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    LogUtil.d(TAG, "savePhotoToSDCard } catch (IOException e) {\n " + e.getMessage());
                    //					e.printStackTrace();
                }
            }

            return photoFile.getAbsolutePath();
        }

        return null;
    }

    /**
     * 获取系统属性
     *
     * @return
     */
    public static String getSystemProperty(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, key, defaultValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }


    /**
     * 检测定位服务功能是否开启
     *
     * @param context
     * @return
     */
    public static boolean isLocationEnable(Context context) {
        if (context != null) {
            LocationManager locationManager =
                    (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager == null) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= 28) {
                return locationManager.isLocationEnabled();
            }
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
        return false;
    }


    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取顶层 Activity
     *
     * @param context
     * @return
     */
    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        return runningTaskInfos == null ? "" : runningTaskInfos.get(0).topActivity.getClassName();
    }


    /**
     * 检查是否有位置权限
     *
     * @param context
     * @return
     */
    public static boolean isHaveLocationPermission(Context context) {
        return isHavePermission(context, "android.permission.ACCESS_COARSE_LOCATION") || isHavePermission(context, "android.permission.ACCESS_FINE_LOCATION");
    }

    /**
     * 检查是否有权限
     *
     * @param context
     * @param name
     * @return
     */
    public static boolean isHavePermission(Context context, String name) {
        try {
            return PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission(name, context.getPackageName());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    public static void startSharePage(Context context, String shareText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, ((Activity) context).getTitle()));
    }

    public static PackageInfo getPackageInfo(Context context, String pkgName) {
        final PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(TAG, pkgName + "  Not Existed.");
        }
        return packageInfo;
    }

    public static boolean isAppInstalled(Context context, String pkgName) {
        return getPackageInfo(context, pkgName) != null;
    }

    public static String getDefaultBrowser(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(Uri.parse("http://"));
        ResolveInfo info = pm.resolveActivity(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        android.util.Log.i("lizhiwen", "getDefaultActivity info = " + info
                + "; pkgName = " + info.activityInfo.packageName);

        return info.activityInfo.packageName;
    }

    public static void sendSetDefaultBrowserRequestIntent(Context context) {
        try {
            Intent localIntent = new Intent();
            localIntent.setAction("android.intent.action.VIEW");
            localIntent.putExtra("set_browser_congratulation",
                    "set_browser_congratulation");
            localIntent.addCategory("android.intent.category.DEFAULT");
            localIntent.setData(Uri.parse("http://www.baidu.com"));
            localIntent.setComponent(new ComponentName("android",
                    "com.android.internal.app.ResolverActivity"));
            context.startActivity(localIntent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        List<String> ps = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (ActivityCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                ps.add(permissions[i]);
            }
        }

        if (!ps.isEmpty()) {
            String[] p = new String[ps.size()];
            for (int i = 0; i < ps.size(); i++) {
                p[i] = ps.get(i);
            }
            ActivityCompat.requestPermissions(activity, p, requestCode);
        }
    }

    public static boolean isPermissionGranted(Activity activity, String[] permissions) {
        boolean granted = true;
        for (int i = 0; i < permissions.length; i++) {
            if (ActivityCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                granted = false;
                break;
            }
        }
        return granted;
    }

    public static boolean isPermissionGranted(String[] permissions, int[] grantResults, String target) {
        for (int i = 0; i < permissions.length; i++) {
            if (target.equals(permissions[i]) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Intent getAppStoreIntent(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage("com.hmdglobal.appstore");
        return intent;
    }

    public static boolean isNokiaPhone() {
        String brand = Build.BRAND;
        LogUtil.d(TAG, "isNokiaPhone :: brand - " + brand);
        if ("Nokia".equalsIgnoreCase(brand)) {
            return true;
        }
        return false;
    }

    public static boolean hasExternalStorage() {
        File sdcard_filedir = Environment.getExternalStorageDirectory();//得到sdcard的目录作为一个文件对象
        long usableSpace = sdcard_filedir.getUsableSpace();//获取文件目录对象剩余空间
        long totalSpace = sdcard_filedir.getTotalSpace();
        if(totalSpace - usableSpace <= 0){
            return  false;
        }
        return true;
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }
    /**
     * 获取随机rgb颜色值
     */
    public static int randomColor() {
        Random random = new Random();
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int red =random.nextInt(150);
        //0-190
        int green =random.nextInt(150);
        //0-190
        int blue =random.nextInt(150);
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red,green, blue);
    }

}