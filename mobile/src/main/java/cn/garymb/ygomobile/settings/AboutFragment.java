package cn.garymb.ygomobile.settings;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import cn.garymb.ygomobile.Constants;
import cn.garymb.ygomobile.lite.R;
import cn.garymb.ygomobile.plus.DialogPlus;
import cn.garymb.ygomobile.plus.PreferenceFragmentPlus;
import cn.garymb.ygomobile.plus.VUiKit;
import cn.garymb.ygomobile.plus.WebViewPlus;

public class AboutFragment extends PreferenceFragmentPlus {
    @Override
    protected SharedPreferences getSharedPreferences() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        addPreferencesFromResource(R.xml.preference_about);
        PackageInfo packageInfo = null;
        try {
            packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        bind("pref_key_about_version", (packageInfo == null) ? "?" : packageInfo.versionName);
        bind("pref_key_change_log");
        bind("pref_key_open_alipay");
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if ("pref_key_change_log".equals(key)) {
            new DialogPlus(getActivity())
                    .setTitle(getString(R.string.settings_about_change_log))
                    .loadUrl("file:///android_asset/changelog.html")
                    .show();
        } else if ("pref_key_open_alipay".equals(key)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.ALIPAY_URL));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return false;
    }

    private void showDialog(String title, String url) {
        new DialogPlus(getActivity())
                .setTitle(title)
                .loadUrl(url)
                .show();
    }
}
