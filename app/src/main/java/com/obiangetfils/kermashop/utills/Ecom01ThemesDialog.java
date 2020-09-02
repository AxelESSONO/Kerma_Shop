package com.obiangetfils.kermashop.utills;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.obiangetfils.kermashop.Buyer.SplashActivity;
import com.obiangetfils.kermashop.R;

import java.util.ArrayList;
import java.util.List;

public class Ecom01ThemesDialog extends Dialog {

    public static int selectedTheme = 0;
    private Activity context;
    private ListView listView = null;

    public Ecom01ThemesDialog(Activity context) {
        super(context);
        this.context = context;
    }

    public Ecom01ThemesDialog(Activity context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_theme);
        listView = findViewById(R.id.listView);

        final ThemeDialogAdapter adapter = new ThemeDialogAdapter(context, getcolorList(), selectedTheme);
        adapter.listener = new ThemeDialogAdapter.ItemListener() {
            @Override
            public void onItemClicked(int position, ColorsModal item) {
                selectedTheme = position;
                Toast.makeText(context, item.getColorName() + " selected.", Toast.LENGTH_SHORT).show();
                dismiss();
                context.startActivity(new Intent(context, SplashActivity.class));
                context.finish();
            }
        };
        listView.setAdapter(adapter);
    }



    public static List<ColorsModal> getcolorList() {
        List<ColorsModal> colorsModals = new ArrayList<>();
        colorsModals.add(new ColorsModal(R.style.Ecom01, "Default", R.color.black));
        colorsModals.add(new ColorsModal(R.style.TemplateTheme1, "Red", R.color.red_500));
        colorsModals.add(new ColorsModal(R.style.TemplateTheme2, "Deep purple", R.color.deep_purple_500));
        colorsModals.add(new ColorsModal(R.style.TemplateTheme3, "Light Blue", R.color.light_blue_500));
        colorsModals.add(new ColorsModal(R.style.TemplateTheme4, "Green", R.color.green_500));
        colorsModals.add(new ColorsModal(R.style.TemplateTheme6, "Grey", R.color.grey_500));
        colorsModals.add(new ColorsModal(R.style.TemplateTheme7, "Pink", R.color.pink_500));
        colorsModals.add(new ColorsModal(R.style.TemplateTheme8, "Indigo", R.color.indigo_500));
        colorsModals.add(new ColorsModal(R.style.TemplateTheme9, "Cyan", R.color.cyan_500));
        colorsModals.add(new ColorsModal(R.style.TemplateTheme10, "Light Green", R.color.light_green_500));

        return colorsModals;
    }

    /*
    * Theme Modal Class
    * */

    public static class ColorsModal {

        int themeID;
        String colorName;
        int colorPrimary;

        public ColorsModal(int themeID, String colorName, int colorPrimary) {
            this.themeID = themeID;
            this.colorName = colorName;
            this.colorPrimary = colorPrimary;
        }

        public int getThemeID() {
            return themeID;
        }

        public void setThemeID(int themeID) {
            this.themeID = themeID;
        }

        public String getColorName() {
            return colorName;
        }

        public void setColorName(String colorName) {
            this.colorName = colorName;
        }

        public int getColorPrimary() {
            return colorPrimary;
        }

        public void setColorPrimary(int colorPrimary) {
            this.colorPrimary = colorPrimary;
        }
    }

    /*
     * Adapter for Theme List
     * */

    public static class ThemeDialogAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private List<ColorsModal> dataList;
        public ItemListener listener;

        SharedPreferences prefs;
        int checkedNumber;

        public ThemeDialogAdapter(Context context, List<ColorsModal> list, int selectedTheme) {
            this.context = context;
            this.dataList = list;
            layoutInflater = LayoutInflater.from(context);
             checkedNumber = selectedTheme;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public ColorsModal getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder holder;
            if (convertView == null) {

                convertView = layoutInflater.inflate(R.layout.dialog_theme_item, parent, false);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClicked(position, dataList.get(position));
                    }
                });
                holder = new ViewHolder();
                holder.colorName = convertView.findViewById(R.id.colorNameText);
                holder.mainLayout = convertView.findViewById(R.id.relativeLayout);
                holder.checkIcon = convertView.findViewById(R.id.checkIcon);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.colorName.setText(dataList.get(position).getColorName());
            holder.mainLayout.setBackgroundColor(context.getResources().getColor(dataList.get(position).getColorPrimary()));
            holder.checkIcon.setVisibility(checkedNumber == position ? View.VISIBLE : View.GONE);

            return convertView;
        }

        static class ViewHolder {
            RelativeLayout mainLayout;
            TextView colorName;
            ImageView checkIcon;
        }

        public interface ItemListener {
            void onItemClicked(int position, ColorsModal item);
        }

    }

}

