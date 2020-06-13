package com.example.insta_test.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.BoolRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.RawRes;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.core.content.ContextCompat;
import androidx.core.os.ConfigurationCompat;
import androidx.core.text.TextUtilsCompat;
import androidx.core.view.ViewCompat;

import com.example.insta_test.BuildConfig;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;


public class Utilities
{
    // SYSTEM

    public static void log(String msg)
    {
         // todo
        Log.e("LOG", msg);
    }

    public static void catchError(Throwable e)
    {
        e.printStackTrace();
    }

    @SuppressLint("HardwareIds")
    public static String getDid(Context context)
    {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static Locale getLocale()
    {
        return ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
    }

    public static String getVersionName()
    {
        return BuildConfig.VERSION_NAME;
    }

    public static boolean isAppAvailable(Context context, String appName)
    {
        PackageManager pm = context.getPackageManager();
        try
        {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            catchError(e);
        }
        return false;
    }

    public static SharedPreferences getsSharedPreferences(Context context)
    {
        return context.getSharedPreferences("UserOptions", Context.MODE_PRIVATE);
    }

    public static boolean isLTR()
    {
        return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == ViewCompat.LAYOUT_DIRECTION_LTR;
    }

    public static void getPictureFromGallery(Activity activity, int requestCode)
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image// ");
        activity.startActivityForResult(photoPickerIntent, requestCode);
    }

    public static void getPictureFromCamera(Activity activity, int requestCode)
    {
        Intent photoPickerIntent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(photoPickerIntent2, requestCode);
    }





    // TIME FORMAT

    public static final String timeFormat_ddMyyyyHHmm = "dd.M.yyyy.HH:mm";
    public static final String timeFormat_ddMyyyy = "dd.M.yyyy";
    public static final String timeFormat_HHmmMMMMd = "HH:mm MMMM d";
    public static final String timeFormat_EdMMM = "E, d MMM";
    public static final String timeFormat_EHHmm = "E,  HH:mm";
    public static final String timeFormat_HHmm = "HH:mm";

    public static String getTimeStringFromTimeLong(String inFormat, long inTimeInMillis)
    {
//        String timeString = DateFormat.format(inFormat, inTimeInMillis).toString();


        String s = new SimpleDateFormat(inFormat, Locale.getDefault()).format(new Date(inTimeInMillis));
        return s = s.substring(0, 1).toUpperCase() + s.substring(1, s.length());


//        return  new SimpleDateFormat(inFormat, Locale.getDefault()).format(new Date(inTimeInMillis));


    }




    // KEYBOARD

    public static void hideKeyBoard(View view)
    {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean hasSoftKeys(Context context, WindowManager windowManager)
    {
        boolean hasSoftwareKeys = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            Display d = windowManager.getDefaultDisplay();

            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            d.getRealMetrics(realDisplayMetrics);

            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);

            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;

            hasSoftwareKeys = (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
        }
        else
        {
            boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            hasSoftwareKeys = !hasMenuKey && !hasBackKey;
        }
        return hasSoftwareKeys;
    }

    public static void copyToClipBoard(Context context, String textToClip, String toastStr)
    {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null)
        {
            ClipData clip = ClipData.newPlainText("IWD", textToClip);
            clipboard.setPrimaryClip(clip);
            toast(context, toastStr, Toast.LENGTH_SHORT);
        }
    }




    // PERCENT

    public static double calculatePercentRange(double FROM, double TO)
    {
        double max = Math.max(FROM, TO);
        double min = Math.min(FROM, TO);
        double res = 0;
        if (FROM < TO)
        {
            res = (max / min - 1) * 100;
        }
        if (FROM > TO)
        {
            res = (min / max - 1) * 100;
        }
        return res;
    }

    public static double calculatePercentRatio(double FROM, double part)
    {
        double TO = FROM + part;
        double max = Math.max(FROM, TO);
        double min = Math.min(FROM, TO);
        double res = 0;
        if (FROM < TO)
        {
            res = (max / min - 1) * 100;
        }
        if (FROM > TO)
        {
            res = (min / max - 1) * 100;
        }
        return res;
    }






    // RESOURCE

    public static int getResourceId(Context context, String name, String defType)
    {
//        String defPackage = "com.studio.clubok.iwego";
        String defPackage = context.getApplicationContext().getPackageName();
        Resources resources = context.getResources();
        return resources.getIdentifier(name, defType, defPackage);
    }

    public static TypedValue getTypedValue(Context context, int id)
    {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(id, outValue, true);
        return outValue;
    }

    public static String getStringRes(Context context, int id)
    {
        return context.getResources().getString(id);
    }

    public static int getColorRes(Context context, int id)
    {
        return ContextCompat.getColor(context, id);
    }

    // COLOR

    public static int getColorFromAndroidAttr(Context context, int attr)
    {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(attr, outValue, true);
        int resourceId = outValue.resourceId;
        return ContextCompat.getColor(context, resourceId);
    }

    public static int getRandomColor()
    {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static boolean getBooleanValue(Context context, @BoolRes int resId)
    {
        try
        {
            boolean isDebug = context.getResources().getBoolean(resId);
            return isDebug;

        } catch (Exception e)
        {
            catchError(e);
        }
        return false;
    }

    public static int getDimensionPixelSizeInt(Context context, int id)
    {
        return context.getResources().getDimensionPixelSize(id);
    }

    public static float getDimensionPixelSizeFloat(Context context, int id)
    {
        return context.getResources().getDimensionPixelSize(id);
    }

    // ASSETS

    public static String getJsonFromAssets(Context context, String fileName)
    {
        String jsonString;
        try
        {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }

    public static Bitmap getBitmapFromAssets(Context context, String fileName, int reqWidth, int reqHeight)
    {
        Bitmap bitmap;
        try
        {
            InputStream is = context.getAssets().open(fileName);
            bitmap = getBitmap(is, reqWidth, reqHeight);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public static Bitmap getBitmap(InputStream is, int reqWidth, int reqHeight)
    {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth)
        {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth)
            {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    // METRICS

    public static float convertDpToPx(Context context, float dp)
    {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static float convertPxToDp(Context context, float px)
    {
        return px / context.getResources().getDisplayMetrics().density;
    }




    // TOAST

    public static void toast(Context context, String text, int duration)
    {
        int BG_COLOR = 0xFFFFFFFF;
        int TEXT_COLOR = 0xFF000000;

        Toast toast = Toast.makeText(context, text, duration);
//            toast.setGravity(Gravity.CENTER, 0, 0);
        toast.getView().setAlpha(1f);
        toast.getView().getBackground().setColorFilter(BG_COLOR, PorterDuff.Mode.SRC_ATOP);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(TEXT_COLOR);
        toast.show();
    }

    public static void toastError(Context context, String text, int duration)
    {
        Toast toast = Toast.makeText(context, text, duration);
//            toast.setGravity(Gravity.CENTER, 0, 0);
        toast.getView().setAlpha(1f);
        toast.getView().getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        TextView v = toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.BLACK);
        toast.show();
    }




    // STRING

    public static ArrayList<String> convertStringToList(String str)
    {
        ArrayList<String> list;
        if (str == null || str.equals(""))
        {
            list = new ArrayList<>();
        }
        else
        {
            String strSeparator = " ";
            String[] arr = str.split(strSeparator);
            list = new ArrayList<>(Arrays.asList(arr));
        }
        return list;
    }

    public static String convertArrayListToString(List<String> list)
    {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < list.size(); i++)
        {
            str.append(list.get(i));
            // Do not append comma at the end of last element
            if (i < list.size() - 1)
            {
                str.append(" ");
            }
        }
        return str.toString();
    }


    // VIBRATE
//    public static void vibrate(Context context, long time)
//    {
//        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        if (vibrator != null && vibrator.hasVibrator()) vibrator.vibrate(time);
//    }


    // BLUR
    // Set the radius of the Blur. Supported range 0 < radius <= 25ё
    private static final float BLUR_RADIUS = 10f;
//    public static Bitmap blurBuilder(Context context, float blur_radius, int drawableRes)
//    {
//        Bitmap image = BitmapFactory.decodeResource(context.getResources(), drawableRes);
//
//        Bitmap outputBitmap = Bitmap.createBitmap(image);
//        RenderScript renderScript = RenderScript.create(context);
//        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
//        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);
//
//        //Intrinsic Gausian blur filter
//        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
//        theIntrinsic.setRadius(blur_radius);
//        theIntrinsic.setInput(tmpIn);
//        theIntrinsic.forEach(tmpOut);
//        tmpOut.copyTo(outputBitmap);
//        return outputBitmap;
//
//    }
//
//    public static void blurBackground(final Context context, final View view, final float blur_radius)
//    {
//        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
//        {
//            @Override
//            public boolean onPreDraw()
//            {
//                view.getViewTreeObserver().removeOnPreDrawListener(this);
//
//                view.buildDrawingCache();
//                Bitmap image = view.getDrawingCache();
//
//                view.setBackground(null);
//
//                Bitmap bitmapS = blur(context, image, blur_radius);
//                Drawable drawable = drawableFromBitmap(context, bitmapS);
//                view.setBackground(drawable);
//
//                return false;
//            }
//        });
//    }
//
//    public static Bitmap blur(Context context, Bitmap image, float blur_radius)
//    {
//        Bitmap outputBitmap = Bitmap.createBitmap(image);
//        RenderScript renderScript = RenderScript.create(context);
//        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
//        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);
//
//        //Intrinsic Gausian blur filter
//        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
//        theIntrinsic.setRadius(blur_radius);
//        theIntrinsic.setInput(tmpIn);
//        theIntrinsic.forEach(tmpOut);
//        tmpOut.copyTo(outputBitmap);
//        return outputBitmap;
//
//    }

    public static Drawable drawableFromResource(Context context, @DrawableRes int res)
    {
        return ContextCompat.getDrawable(context, res);
    }

    public static Drawable drawableFromBitmap(Context context, Bitmap bitmap)
    {
        return new BitmapDrawable(context.getResources(), bitmap);
    }







    // BITMAP

    public static Bitmap bitmapFromDrawable(Context context, @DrawableRes int resVector)
    {
        @SuppressLint("RestrictedApi")
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, resVector);


        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;


        Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmOut);
        drawable.setBounds(0, 0, c.getWidth(), c.getHeight());
        drawable.draw(c);
        return bmOut;
    }

    public static Bitmap bitmapFromBytes(byte[] bytes)
    {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

    }

    public static Bitmap bitmapFromByteBuffer(ByteBuffer buffer)
    {
        byte[] b = new byte[buffer.capacity()];
        buffer.get(b);
        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

    public static Bitmap bitmapFromFile(File file)
    {
        Bitmap bitmap = null;
        try
        {
            FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();
            int capacity = (int) fileChannel.size();
            ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
            fileChannel.read(byteBuffer);
            fileChannel.close();
            byteBuffer.flip();

            byte[] b = new byte[capacity];
            byteBuffer.get(b);
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

        } catch (IOException e)
        {
            catchError(e);
        }

        return bitmap;

    }

    public static Bitmap resizeBitmap(Bitmap bitmapOriginal, int width)
    {
        Bitmap bmOut = null;
        if (bitmapOriginal.getWidth() < width)
        {
            bmOut = bitmapOriginal;

        }
        else
        {
            double K = (double) bitmapOriginal.getWidth() / width;
            int w = (int) (bitmapOriginal.getWidth() / K);
            int h = (int) (bitmapOriginal.getHeight() / K);
            bmOut = Bitmap.createScaledBitmap(bitmapOriginal, w, h, true);
        }


        return bmOut;
    }

    public static Bitmap getCircularBitmap(Bitmap srcBitmap)
    {
        // init bounds
        int w = srcBitmap.getWidth();
        int h = srcBitmap.getHeight();
        int diameter = Math.min(w, h);
        int radius = diameter / 2;
        int cX = diameter / 2;
        int cY = diameter / 2;
        // calculate the left and top of copied bitmapS
        float left = (diameter - w) / 2;
        float top = (diameter - h) / 2;
        // initialize a new instance of Bitmap
        Bitmap dstBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
        // Initialize a new Paint instance
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // Initialize a new Canvas to draw circular bitmapS
        Canvas canvas = new Canvas(dstBitmap);
        // Draw a markerPoint shape on Canvas
        canvas.drawCircle(cX, cY, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // Make a rounded image by copying at the exact center position of source image
        canvas.drawBitmap(srcBitmap, left, top, paint);
        // Free the native object associated with this bitmapS.
//        srcBitmap.recycle();
        // Return the circular bitmapS
        return dstBitmap;
    }

    public static Bitmap getShapeBitmap(Bitmap srcBitmap, float radius)
    {
//        // init bounds
//        int w = srcBitmap.getWidth();
//        int h = srcBitmap.getHeight();
//        int diameter = Math.min(w, h);
//        int radius = diameter / 2;
//        int cX = diameter / 2;
//        int cY = diameter / 2;
//        // calculate the left and top of copied bitmapS
//        float left = (diameter - w) / 2;
//        float top = (diameter - h) / 2;
//        // initialize a new instance of Bitmap
//        Bitmap dstBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
//        // Initialize a new Paint instance
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        // Initialize a new Canvas to draw circular bitmapS
//        Canvas canvas = new Canvas(dstBitmap);
//        // Draw a markerPoint shape on Canvas
////        canvas.drawCircle(cX, cY, radius, paint);
//        canvas.drawRoundRect(0, 0, w, h, cornerRadius, cornerRadius, paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        // Make a rounded image by copying at the exact center position of source image
//        canvas.drawBitmap(srcBitmap, left, top, paint);
//        // Free the native object associated with this bitmapS.
////        srcBitmap.recycle();
//        // Return the circular bitmapS
//
//        return dstBitmap;


        Bitmap output = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, srcBitmap.getWidth(), srcBitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 50;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBitmap, rect, rect, paint);
        return output;


//        final Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setShader(new BitmapShader(srcBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
//
//        Bitmap output = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//        canvas.drawRoundRect(new RectF(0, 0, srcBitmap.getWidth(), srcBitmap.getHeight()), radius, radius, paint);
//
//        if (srcBitmap != output) {
//            srcBitmap.recycle();
//        }
//
//        return output;


    }

    public static Bitmap getSquareBitmap(Bitmap bitmapOriginal)
    {
        int width = bitmapOriginal.getWidth();
        int height = bitmapOriginal.getHeight();
        int size = 0;
        if (width > height) size = height;
        else size = width;


        Bitmap bmOut = Bitmap.createScaledBitmap(bitmapOriginal, size, size, false);


        return bmOut;
    }

    public static byte[] getBytesFromBitmapInWEBP(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmapS.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//        bitmapS.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, outputStream);
        return outputStream.toByteArray();

    }

    public static ByteBuffer getByteBufferFromBitmap(Bitmap bitmap)
    {
        ByteBuffer byteBuffer;
        byte[] byteArray;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, outputStream);
        byteArray = outputStream.toByteArray();
        byteBuffer = ByteBuffer.wrap(byteArray);

        return byteBuffer;
    }

    public static Bitmap getCountryFlagBitmapFromAssets(String countryCode, Context context)
    {
        String fileName = "flags/" + countryCode.toLowerCase() + ".png";
        Bitmap bitmap = null;
        try
        {
            InputStream inputStream = context.getAssets().open(fileName);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e)
        {
            catchError(e);
        }

        return bitmap;
    }

    public static Bitmap getBitmapFromUri(Uri uri)
    {
        Bitmap bitmap = null;
        try (InputStream inputStream = new URL(uri.toString()).openStream())
        {


            bitmap = BitmapFactory.decodeStream(inputStream);


        } catch (Exception e)
        {
            catchError(e);
        }

        return bitmap;

    }








    // IP, COUNTRY CODE .. .. ..

    public static String getPublicIP()
    {
        String IP = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://wtfismyip.com/text").openStream())))
        {
            StringBuilder output = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) output.append(line);
            IP = output.toString();

        } catch (Exception e)
        {
//            catchError(e);
        }

        return IP;
    }

    public static String getCurrencyCode(String publicIP)
    {
        String CC = "USD";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://ip-api.com/json").openStream())))
//        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://ipapi.co/91.197.19.200/json/").openStream())))
//        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://ipapi.co/" + publicIP + "/json/").openStream())))
        {
            StringBuilder output = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) output.append(line);

            JSONObject jsonObject = new JSONObject(output.toString());
            CC = jsonObject.getString("currency");

        } catch (Exception e)
        {
            catchError(e);
            log("CURRENCY CODE EXCEPTION " + CC);
        }

        log("CURRENCY CODE" + CC);
        return CC;
    }

    public static String getCurrencyCode()
    {
        String CC = "USD";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://ip-api.com/json").openStream())))
        {
            StringBuilder output = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) output.append(line);
            JSONObject jsonObject = new JSONObject(output.toString());


            Locale localeDefault = Locale.getDefault();
            String lc = localeDefault.getLanguage();
            String cc = jsonObject.getString("countryCode");


            Locale locale = new Locale(lc, cc);
            Currency currency = Currency.getInstance(locale);
            CC = currency.getCurrencyCode();


        } catch (Exception e)
        {
            catchError(e);

        }


        return CC;
    }








    // VIEW

    public static TransitionDrawable getTransitionDrawable(View view, int colorStart, int colorFinish)
    {
        ColorDrawable[] colorDrawables = {new ColorDrawable(colorStart), new ColorDrawable(colorFinish)};
        TransitionDrawable transitionDrawable = new TransitionDrawable(colorDrawables);
        transitionDrawable.setCrossFadeEnabled(true);
        ViewCompat.setBackground(view, transitionDrawable);
        return transitionDrawable;
    }



    // POST

    public static String sendGetRequest(String urlStr) throws Exception
    {
        StringBuffer response = new StringBuffer();


        URL url = new URL(urlStr);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(20000);
        conn.setReadTimeout(20000);

//        URL url = new URL(urlStr);
////        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setConnectTimeout(20000);
//        conn.setReadTimeout(20000);
//        conn.setRequestMethod("GET");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())))
        {
            String inputLine = "";
            while ((inputLine = br.readLine()) != null)
            {
                response.append(inputLine);
            }
            return response.toString();
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public static String sendPostRequest(String urlStr, String urlParameters) throws Exception
    {
        StringBuilder response = new StringBuilder();
        URL url = new URL(urlStr);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setConnectTimeout(20000);
        conn.setReadTimeout(20000);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream()))
        {
            wr.writeBytes(urlParameters);
            wr.flush();
        }
        int responseCode = conn.getResponseCode();
        if (responseCode == 200)
        {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())))
            {
                String inputLine;
                while ((inputLine = in.readLine()) != null) response.append(inputLine);
            }
        }
        return response.toString();
    }

}
