package com.example.loren.minesample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bean.ShopListBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import util.HttpUtil;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.xrv)
    XRecyclerView xrv;
    ArrayList<ShopListBean.ResultBean.StoreListBean> data = new ArrayList<>();
    XAdapter mAdapter;
    int curpage = 1;
    String url = "http://o2o.teligong.com/mobile/index.php?act=store&op=store_list";
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        mContext = this;
        init();
        initXrv();
    }

    private void initXrv() {
        xrv.setLayoutManager(new GridLayoutManager(this, 1));
        xrv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xrv.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        xrv.setArrowImageView(R.drawable.iconfont_downgrey);
        mAdapter = new XAdapter(this);
        xrv.setAdapter(mAdapter);
        xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                loadData(true);
            }

            @Override
            public void onLoadMore() {
                loadData(false);
            }
        });
        xrv.refresh();
    }

    private void loadData(final boolean isRefresh) {
        if (isRefresh) {
            curpage = 1;
        }
        JSONObject jo = new JSONObject();
        try {
            jo.put("city_id", "5");
            jo.put("commercial_id", "");
            jo.put("sc_id", "");
            jo.put("area_id", "");
            jo.put("store_type", "1");
            jo.put("lat", "36.153901");
            jo.put("lng", "120.493378");
            jo.put("sort_by", "");
            jo.put("curpage", curpage);
            jo.put("page", "20");
            jo.put("key", "eb6b40decbb06c0b86e3d82b59617438");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.post(url, jo, new HttpUtil.NetCallBack() {
            @Override
            public void onSuccess(String json) {
                if (isRefresh) {
                    data.clear();
                }
                data.addAll(new Gson().fromJson(json, ShopListBean.class).getResult().getStore_list());
                xrv.getAdapter().notifyDataSetChanged();
                loadComplete(isRefresh);
                curpage++;
            }

            @Override
            public void onFailure(String reason) {
                loadComplete(isRefresh);
                Toast.makeText(mContext, reason, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadComplete(boolean isRefresh) {
        if (isRefresh) {
            xrv.refreshComplete();
        } else {
            xrv.loadMoreComplete();
        }
    }

    private void init() {
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);
        TextView tv4 = new TextView(this);
        tv1.setTextSize(30);
        tv2.setTextSize(30);
        tv3.setTextSize(30);
        tv4.setTextSize(30);
        tv1.setText("开门呐！");
        tv2.setText("快开门呐！！");
        tv3.setText("你有本事抢男人！！！");
        tv4.setText("你有本事开门呐！！！！");
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        viewFlipper.addView(tv1);
        viewFlipper.addView(tv2);
        viewFlipper.addView(tv3);
        viewFlipper.addView(tv4);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
    }

    class XAdapter extends RecyclerView.Adapter<XAdapter.ViewHolder> {

        private Context mContext;

        XAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_xrv, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.itemTv.setText(data.get(position).getStore_name());
            ImageUtil.bind(holder.image, data.get(position).getStore_label());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.image)
            ImageView image;
            @BindView(R.id.item_tv)
            TextView itemTv;


            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }
}
