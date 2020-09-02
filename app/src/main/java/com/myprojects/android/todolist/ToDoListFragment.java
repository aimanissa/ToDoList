package com.myprojects.android.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToDoListFragment extends Fragment {

    private ToDo mToDo;
    private RecyclerView mToDoRecyclerView;
    private ToDoAdapter mAdapter;
    private int adapterPosition;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmet_to_do_list, container, false);

        mToDoRecyclerView = (RecyclerView) view.findViewById(R.id.to_do_recycler_view);
        mToDoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ToDoLab toDoLab = ToDoLab.get(getActivity());
        List<ToDo> toDoList = toDoLab.getToDoList();

        if (mAdapter == null) {
            mAdapter = new ToDoAdapter(toDoList);
            mToDoRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setToDoList(toDoList);
            mAdapter.notifyItemChanged(adapterPosition);
        }

    }

    private class ToDoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mToDoTitle;
        private TextView mToDoDescription;

        public ToDoHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));
            itemView.setOnClickListener(this);

            mToDoTitle = (TextView) itemView.findViewById(R.id.to_do_title);
            mToDoDescription = (TextView) itemView.findViewById(R.id.to_do_description);

            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.solved_checkbox);
            mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mToDo.setSolved(true);
                    }
                }
            });
        }

        public void bind(ToDo toDo) {
            mToDo = toDo;
            mToDoTitle.setText(mToDo.getTitle());
            mToDoDescription.setText(mToDo.getDescription());

        }

        @Override
        public void onClick(View v) {
            adapterPosition = getBindingAdapterPosition();
            Intent intent = ToDoPagerActivity.newIntent(getActivity(), mToDo.getId());
            startActivity(intent);
        }


    }

    private class ToDoAdapter extends RecyclerView.Adapter<ToDoHolder> {

        private List<ToDo> mToDoList;

        public ToDoAdapter(List<ToDo> toDoList) {
            mToDoList = toDoList;
        }


        @Override
        public ToDoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ToDoHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ToDoHolder holder, int position) {
            ToDo toDo = mToDoList.get(position);
            holder.bind(toDo);
        }



        @Override
        public int getItemCount() {
            return mToDoList.size();
        }

        public void setToDoList(List<ToDo> toDoList) {
            mToDoList = toDoList;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_to_do_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_to_do:
                ToDo toDo = new ToDo();
                ToDoLab.get(getActivity()).addToDo(toDo);
                Intent intent = ToDoPagerActivity.newIntent(getActivity(), toDo.getId());
                startActivity(intent);
                return true;
            case R.id.delete_to_to:
                if (mToDo.isSolved()) {
                    mSolvedCheckBox.isChecked();
                    ToDoLab.get(getActivity()).deleteToDo(mToDo);
                    updateUI();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
