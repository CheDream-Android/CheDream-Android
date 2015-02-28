package org.chedream.android.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.chedream.android.R;

public class NavigationDrawerAdapter extends ArrayAdapter<String> {

    private int mResource;
    private Resources mResources;
    private LayoutInflater mInflater;

    public NavigationDrawerAdapter(
            Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
        mResource = resource;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResources = context.getResources();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(mResource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.img_nav_item);
            viewHolder.text = (TextView) convertView.findViewById(R.id.txt_nav_item);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.image.setImageDrawable(mResources.getDrawable(getDrawableId(position)));
        viewHolder.text.setText(getItem(position));
        return convertView;
    }

    private int getDrawableId(int position) {
        switch (position) {
            case 0:
                return R.drawable.drawer_all;
            case 1:
                return R.drawable.drawer_faq;
            case 2:
                return R.drawable.drawer_contacts;
            default:
                return 0;
        }
    }
    static class ViewHolder {
        ImageView image;
        TextView text;
    }
}
