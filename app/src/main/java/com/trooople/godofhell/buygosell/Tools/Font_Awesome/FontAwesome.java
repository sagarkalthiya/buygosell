package com.trooople.godofhell.buygosell.Tools.Font_Awesome;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FontAwesome {
    private static final String FONT_FILENAME = "fontawesome-webfont.ttf";

    private static Typeface sTypeface = null;
    private static final Pattern pattern = Pattern.compile("([^\\{]*)\\{([\\w\\-]+)\\}(.*)");

    public static void applyToAllViews(Context context, View view) {
        apply(context, view);

        if (!(view instanceof ViewGroup)) {
            return;
        }

        ViewGroup viewGroup = (ViewGroup) view;
        for (int i = 0, l = viewGroup.getChildCount(); i < l; i++) {
            final View child = viewGroup.getChildAt(i);
            applyToAllViews(context, child);
        }
    }

    public static void apply(Context context, View view) {
        if (sTypeface == null) {
            sTypeface = Typeface.createFromAsset(context.getAssets(), FONT_FILENAME);
        }

        if (!(view instanceof TextView)) {
            return;
        }

        final TextView textView = (TextView) view;
        final CharSequence text = textView.getText();
        final SpannableStringBuilder convertedText = convertText(context, text);
        textView.setText(convertedText);
        textView.setAllCaps(false);
    }

    private static SpannableStringBuilder convertText(Context context, CharSequence text) {
        final SpannableStringBuilder sb = new SpannableStringBuilder();

        while (true) {
            final Matcher matcher = pattern.matcher(text);
            if (!matcher.find()) {
                sb.append(text);
                break;
            }

            sb.append(matcher.group(1));

            final String character = getCharacterFromCode(context, matcher.group(2));
            final CustomTypefaceSpan typefaceSpan = new CustomTypefaceSpan("", sTypeface);
            sb.append(character, typefaceSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            text = matcher.group(3);
        }

        return sb;
    }

    private static String getCharacterFromCode(Context context, String code) {
        code = code.replace("-", "_");

        final Resources resources = context.getResources();
        final int id = resources.getIdentifier(code, "string", context.getPackageName());
        final String character = resources.getString(id);

        return character;
    }
}
