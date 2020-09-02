package com.myprojects.android.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class ToDoPagerActivity extends AppCompatActivity {

    private static final String EXTRA_TODO_ID = "com.myprojects.android.todolist.todo_id";

    private ViewPager mViewPager;
    private List<ToDo> mToDoList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_pager);

        UUID toDoId = (UUID) getIntent().getSerializableExtra(EXTRA_TODO_ID);

        mViewPager = (ViewPager) findViewById(R.id.to_do_view_pager);

        mToDoList = ToDoLab.get(this).getToDoList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                ToDo toDo = mToDoList.get(position);
                return ToDoFragment.newInstance(toDo.getId());
            }

            @Override
            public int getCount() {
                return mToDoList.size();
            }
        });

        for (int i = 0; i < mToDoList.size(); i++) {
            if (mToDoList.get(i).getId().equals(toDoId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(Context packageContext, UUID toDoId) {
        Intent intent = new Intent(packageContext, ToDoPagerActivity.class);
        intent.putExtra(EXTRA_TODO_ID, toDoId);
        return intent;
    }
}
