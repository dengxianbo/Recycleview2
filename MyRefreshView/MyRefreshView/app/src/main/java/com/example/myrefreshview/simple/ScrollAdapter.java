package com.example.myrefreshview.simple;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myrefreshview.R;
import com.example.myrefreshview.andview.XRefreshView;
import com.example.myrefreshview.simple.ui.raindrop.CustomerFooter;


public class ScrollAdapter extends PagerAdapter {

    private int[] images;
    private Context mContext;
    private LayoutInflater mInflater;

    public ScrollAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void finishUpdate(View container) {
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        final View imageLayout = mInflater.inflate(R.layout.adapter_scrollview, view, false);
        final XRefreshView outView = (XRefreshView) imageLayout.findViewById(R.id.custom_view);
        outView.setAutoRefresh(false);
        outView.setPullLoadEnable(true);
        outView.setPinnedTime(1000);
        outView.setAutoLoadMore(false);
//		outView.setSilenceLoadMore();
        outView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                imageLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        outView.stopLoadMore();
                    }
                }, 2000);
            }

            @Override
            public void onRefresh() {
                imageLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        outView.stopRefresh();
                    }
                }, 2000);
            }
        });
        outView.setCustomFooterView(new CustomerFooter(mContext));
        LinearLayout ll = (LinearLayout) imageLayout.findViewById(R.id.ll);
        for (int i = 0; i < 50; i++) {
            TextView tv = new TextView(mContext);
            tv.setTextIsSelectable(true);
            tv.setText("数据" + i);
            ll.addView(tv);
        }
        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View container) {
    }

}
