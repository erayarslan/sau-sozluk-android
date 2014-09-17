package com.guguluk.sausozluk.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.text.SpannableStringBuilder;

import com.bugsense.trace.BugSenseHandler;
import com.guguluk.sausozluk.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static void showMessage(String header, String content, Context context) {
        AlertDialog alertMessage = new AlertDialog.Builder(context).create();
        alertMessage.setTitle(header);
        alertMessage.setMessage(content);
        alertMessage.show();
    }

    public static void visitUrl(String url, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
    }

    public static void startBugSense(Activity activity) {
        BugSenseHandler.initAndStartSession(activity, activity.getString(R.string.bugSense));
    }

    public static Date currentDate() {
        return Calendar.getInstance().getTime();
    }

    public static String getTimeAgo(Date date, Context ctx) {
        if(date == null) {
            return null;
        }

        long time = date.getTime();

        Date curDate = currentDate();
        long now = curDate.getTime();
        if (time > now || time <= 0) {
            return null;
        }

        int dim = getTimeDistanceInMinutes(time);

        String timeAgo;

        if (dim == 0) {
            timeAgo = ctx.getResources().getString(R.string.date_util_term_less) + " " +  ctx.getResources().getString(R.string.date_util_term_a) + " " + ctx.getResources().getString(R.string.date_util_unit_minute);
        } else if (dim == 1) {
            return "1 " + ctx.getResources().getString(R.string.date_util_unit_minute);
        } else if (dim >= 2 && dim <= 44) {
            timeAgo = dim + " " + ctx.getResources().getString(R.string.date_util_unit_minutes);
        } else if (dim >= 45 && dim <= 89) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " "+ctx.getResources().getString(R.string.date_util_term_an)+ " " + ctx.getResources().getString(R.string.date_util_unit_hour);
        } else if (dim >= 90 && dim <= 1439) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + (Math.round(dim / 60)) + " " + ctx.getResources().getString(R.string.date_util_unit_hours);
        } else if (dim >= 1440 && dim <= 2519) {
            timeAgo = "1 " + ctx.getResources().getString(R.string.date_util_unit_day);
        } else if (dim >= 2520 && dim <= 43199) {
            timeAgo = (Math.round(dim / 1440)) + " " + ctx.getResources().getString(R.string.date_util_unit_days);
        } else if (dim >= 43200 && dim <= 86399) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " "+ctx.getResources().getString(R.string.date_util_term_a)+ " " + ctx.getResources().getString(R.string.date_util_unit_month);
        } else if (dim >= 86400 && dim <= 525599) {
            timeAgo = (Math.round(dim / 43200)) + " " + ctx.getResources().getString(R.string.date_util_unit_months);
        } else if (dim >= 525600 && dim <= 655199) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " "+ctx.getResources().getString(R.string.date_util_term_a)+ " " + ctx.getResources().getString(R.string.date_util_unit_year);
        } else if (dim >= 655200 && dim <= 914399) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_over) + " "+ctx.getResources().getString(R.string.date_util_term_a)+ " " + ctx.getResources().getString(R.string.date_util_unit_year);
        } else if (dim >= 914400 && dim <= 1051199) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_almost) + " 2 " + ctx.getResources().getString(R.string.date_util_unit_years);
        } else {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + (Math.round(dim / 525600)) + " " + ctx.getResources().getString(R.string.date_util_unit_years);
        }

        return timeAgo + " " + ctx.getResources().getString(R.string.date_util_suffix);
    }

    private static int getTimeDistanceInMinutes(long time) {
        long timeDistance = currentDate().getTime() - time;
        return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }

    public static String dirtyUrl(String url) {
        String clickedText = url;

        String[] search = new String[]{"\u00c7", "\u015e", "\u011e", "\u00dc", "\u0130", "\u00d6", "\u00e7", "\u015f", "\u011f", "\u00fc", "\u00f6", "\u0131"};
        String[] change = new String[]{"c", "s", "g", "u", "i", "o", "c", "s", "g", "u", "o", "i"};

        for(int i = 0 ; i < search.length ; i++) {
            clickedText = clickedText.replace(search[i], change[i]);
        }

        clickedText = clickedText.toLowerCase();
        clickedText = clickedText.replaceAll("^\\s+|\\s+$", "");
        clickedText = clickedText.replaceAll("\\s+", "-");
        clickedText = clickedText.replaceAll("[^a-z-]", "");

        return clickedText;
    }

    public static Typeface getFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), Constants.font_path);
    }

    public static int getVersionBasedTitleId(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return context.getResources().getIdentifier("action_bar_title", "id", "android");
        } else {
            return R.id.action_bar_title;
        }
    }

    public static void share(String content, Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType(Constants.plain_text);
        context.startActivity(intent);
    }

    public static String lowerCase(String content) {
        return content.toLowerCase(new Locale(Constants.tr_locate));
    }

    public static SpannableStringBuilder prettyBuildForContent(String input) {
        input = Utils.lowerCase(input).replace(Constants.html_break_line,"");
        SpannableStringBuilder builder = new SpannableStringBuilder(input);
        //
        Pattern bkzPattern = Pattern.compile(Constants.bkz_pattern);
        Pattern urlPattern = Pattern.compile(Constants.url_pattern);
        Pattern yildizliBkzPattern = Pattern.compile(Constants.yildizli_bkz_pattern);
        //
        Matcher bkzMatcher = bkzPattern.matcher(input);
        Matcher urlMatcher = urlPattern.matcher(input);
        Matcher yildizliBkzMatcher = yildizliBkzPattern.matcher(input);
        //
        while (bkzMatcher.find()) {
            int start = bkzMatcher.start()+5;
            int end = bkzMatcher.end()-1;
            //
            String text = input.subSequence(start, end).toString();
            ClickableBkzSpan url = new ClickableBkzSpan(text);
            builder.setSpan(url, start, end, 0);
        }
        //
        while (urlMatcher.find()) {
            int start = urlMatcher.start()+6;
            int end = urlMatcher.end()-1;
            //
            String text = input.subSequence(start, end).toString();
            ClickableLinkSpan url = new ClickableLinkSpan(text);
            builder.setSpan(url, start, end, 0);
        }
        //
        Integer deletedChar = 0;
        while (yildizliBkzMatcher.find()) {
            int start = yildizliBkzMatcher.start()+3;
            int end = yildizliBkzMatcher.end()-1;
            //
            String text = input.subSequence(start, end).toString();
            builder = builder.replace(yildizliBkzMatcher.start()-deletedChar,yildizliBkzMatcher.end()-deletedChar,"*");
            ClickableYildizliBkzSpan url = new ClickableYildizliBkzSpan(text);
            builder.setSpan(url, yildizliBkzMatcher.start()-deletedChar, yildizliBkzMatcher.start()-deletedChar+1, 0);
            deletedChar += yildizliBkzMatcher.end()-yildizliBkzMatcher.start()-1;
        }
        //
        return builder;
    }

    public static String dateToPrettyFormat(String dateString, Context context) {
        Date date;
        try {
            date = new SimpleDateFormat(Constants.time_format, Locale.getDefault()).parse(dateString);
        } catch (ParseException e) { return Constants._null; }
        if(date!=null) {
            return getTimeAgo(new Date(date.getTime() + 3 * 3600 * 1000), context);
        } else {
            return Constants._null;
        }
    }
}
