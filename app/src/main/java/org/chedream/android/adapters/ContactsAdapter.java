package org.chedream.android.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.chedream.android.R;

public class ContactsAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private String[] mNames, mValues, mValuesForAction;
    private int[] mIcons, mIconsForAction;

    public ContactsAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initResources(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        return mNames.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.contact_item, parent, false);

            TextView txtName = (TextView) convertView.findViewById(R.id.txt_contact_name);
            txtName.setText(mNames[position]);

            ImageView imgIcon = (ImageView) convertView.findViewById(R.id.img_contact_icon);
            imgIcon.setImageResource(mIcons[position]);

            TextView txtValue = (TextView) convertView.findViewById(R.id.txt_contact_value);
            txtValue.setText(mValues[position]);

            ImageButton btnAction = (ImageButton) convertView.findViewById(R.id.img_btn_contact_intent);
            btnAction.setImageResource(mIconsForAction[position]);
            btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(mValuesForAction[position]));
                    mContext.startActivity(intent);
                }
            });
        }
        return convertView;
    }

    private void initResources(Context context) {
        mNames = context.getResources().getStringArray(R.array.contact_names);
        mValues = context.getResources().getStringArray(R.array.contact_values);
        mValuesForAction = context.getResources().getStringArray(R.array.contact_values_for_action);

        mIcons = new int[]{
                R.drawable.icon_home_phone,
                R.drawable.icon_phone,
                R.drawable.icon_phone,
                R.drawable.skype,
                R.drawable.location,
        };

        mIconsForAction = new int[]{
                R.drawable.phone,
                R.drawable.phone,
                R.drawable.phone,
                R.drawable.chat,
                R.drawable.gmaps,
        };
    }
}
