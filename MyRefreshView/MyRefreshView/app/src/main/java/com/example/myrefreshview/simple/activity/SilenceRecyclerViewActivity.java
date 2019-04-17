package com.example.myrefreshview.simple.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myrefreshview.R;
import com.example.myrefreshview.Volley.News;
import com.example.myrefreshview.andview.XRefreshView;
import com.example.myrefreshview.simple.recylerview.Person;
import com.example.myrefreshview.simple.recylerview.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置Recyclerview的静默加载和预加载，让滑动更顺滑。静默加载模式中footerview是不可见的
 */
public class SilenceRecyclerViewActivity extends Activity {
    RecyclerView recyclerView;
    SimpleAdapter adapter;
    List personList = new ArrayList<>();
    XRefreshView xRefreshView;
    int lastVisibleItem = 0;
    //    GridLayoutManager layoutManager;
    LinearLayoutManager layoutManager;
    private boolean isBottom = false;
    private int mLoadCount = 0;
    List<News> list=new ArrayList<>();


    private boolean isList = true;//false 为grid布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recylerview);
        xRefreshView = (XRefreshView) findViewById(R.id.xrefreshview);
        xRefreshView.setPullLoadEnable(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_test_rv);
        recyclerView.setHasFixedSize(true);

        initData();
        adapter = new SimpleAdapter(personList, this);
        // 设置静默加载模式
        xRefreshView.setSilenceLoadMore();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // 静默加载模式不能设置footerview
        recyclerView.setAdapter(adapter);
//        xRefreshView1.setAutoLoadMore(true);
        //设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
//        recyclerviewAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
//		xRefreshView1.setPullLoadEnable(false);
        //设置静默加载时提前加载的item个数
        xRefreshView.setPreLoadCount(4);

        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xRefreshView.stopRefresh();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        for (int i = 0; i < 6; i++) {
                            adapter.insert(new Person("More ", mLoadCount + "21"),
                                    adapter.getAdapterItemCount());
                        }
                        mLoadCount++;

                        if (mLoadCount >= 5) {
                            xRefreshView.setLoadComplete(true);
                        } else {
                            // 刷新完成必须调用此方法停止加载
                            xRefreshView.stopLoadMore();
                        }
                    }
                }, 300);
            }
        });
//		// 实现Recyclerview的滚动监听，在这里可以自己处理到达底部加载更多的操作，可以不实现onLoadMore方法，更加自由
//		xRefreshView1.setOnRecyclerViewScrollListener(new OnScrollListener() {
//			@Override
//			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//				super.onScrolled(recyclerView, dx, dy);
//				lastVisibleItem = layoutManager.findLastVisibleItemPosition();
//			}
//
//			public void onScrollStateChanged(RecyclerView recyclerView,
//											 int newState) {
//				if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//					isBottom = recyclerviewAdapter.getItemCount() - 1 == lastVisibleItem;
//				}
//			}
//		});
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            Person person = new Person("name" + i, "" + i);
            personList.add(person);
        }

//        StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                "http://v.juhe.cn/toutiao/index?type=yule&key=19ece5e32e6817631c966d990bb1d2cc", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
////                Log.e("TAG", "onResponse: "+getUrl() );
//                try {
//                    JSONObject mjson = new JSONObject(s);
//                    String str = mjson.getString("result");
//                    JSONObject json = new JSONObject(str);
//                    JSONArray ja = new JSONArray(json.getString("data"));
//                    for (int i = 0; i < ja.length(); i++) {
//                        JSONObject ss = ja.getJSONObject(i);
//                        String url = ss.getString("url");
//                        personList.add(new Person(ss.getString("title"), null));
//                        Log.e("TAG", "onResponse: "+personList.size() );
//                    }
////                    newAdapter.notifyDataSetChanged();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
////                Toast.makeText(getActivity(), "联网失败", Toast.LENGTH_SHORT).show();
//            }
//        });
//        VolleySingleton.getVolleySingleton(this.getApplicationContext()).addToRequestQueue(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载菜单
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch (menuId) {
            case R.id.menu_clear:
                mLoadCount = 0;
                xRefreshView.setLoadComplete(false);
                //切换布局
                isList = !isList;

                if (isList) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                }
                //当切换layoutManager时，需调用此方法
                xRefreshView.notifyLayoutManagerChanged();
                break;
            case R.id.menu_refresh:
                xRefreshView.startRefresh();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}