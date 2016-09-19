//package com.example.loren.minesample;
//
//import android.content.Intent;
//import android.databinding.DataBindingUtil;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.GridLayoutManager;
//import android.view.View;
//
//import com.example.loren.minesample.databinding.ActivityMainBinding;
//
//import java.util.ArrayList;
//
//import bean.User;
//
//public class MainActivity extends AppCompatActivity {
//
//    private ArrayList<User> data = new ArrayList<>();
//    private boolean isClick = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        final User user = new User("Loren", "男", "青岛");
//        user.setFlag(true);
//        binding.setUser(user);
//        binding.nameTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isClick) {
//                    user.setName("已点击");
//                    isClick = true;
//                } else {
//                    user.setName("Loren");
//                    isClick = false;
//                }
//            }
//        });
//        binding.clickMe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, MineCollectionActivity.class));
//            }
//        });
//        binding.fiveStarTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, Java2LotlinActivity.class));
//            }
//        });
//        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
//        UserAdapter mAdapter = new UserAdapter(this, data);
//        binding.recyclerView.setAdapter(mAdapter);
//        initdata();
//        mAdapter.notifyDataSetChanged();
//    }
//
//    private void initdata() {
//        data.add(new User("loren", "男", "QingDao"));
//        data.add(new User("victor", "女", "QingDao"));
//        data.add(new User("stan", "女", "QingDao"));
//        data.add(new User("adolph", "女", "QingDao"));
//        data.add(new User("tim", "女", "QingDao"));
//        data.add(new User("ace", "女", "QingDao"));
//        data.add(new User("shell", "女", "QingDao"));
//        data.add(new User("jessie", "女", "QingDao"));
//        data.add(new User("king", "女", "QingDao"));
//        data.add(new User("edward", "女", "QingDao"));
//        data.add(new User("edwin", "女", "QingDao"));
//    }
//}
