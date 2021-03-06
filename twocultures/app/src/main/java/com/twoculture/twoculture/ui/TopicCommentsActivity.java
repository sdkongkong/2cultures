package com.twoculture.twoculture.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.twoculture.twoculture.R;
import com.twoculture.twoculture.adapter.TopicCommentAdapter;
import com.twoculture.twoculture.models.Comment;
import com.twoculture.twoculture.presenter.TopicCommentsPresenter;
import com.twoculture.twoculture.views.ITopicCommentsView;

import java.util.List;

import butterknife.BindView;

public class TopicCommentsActivity extends BaseActivity implements ITopicCommentsView, View.OnClickListener {

    public static final String TOPICID = "topicid";

    private TopicCommentAdapter mCommentsAdapter;
    private int mTopicId;
    private TopicCommentsPresenter mCommentsPresenter;

    private static final int PAGE_SIZE = 20;

    @BindView(R.id.rv_topic_comment)
    RecyclerView mRecyclerComments;
    @BindView(R.id.btn_post)
    Button mButtonPost;

    @BindView(R.id.et_content)
    EditText mEditContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTopicId = getIntent().getIntExtra(TOPICID, 0);
        mCommentsAdapter = new TopicCommentAdapter(this);
        initView();
        mCommentsPresenter = new TopicCommentsPresenter(this);
        mCommentsPresenter.loadComments(mTopicId, 1, PAGE_SIZE);
    }

    private void initView() {
        mRecyclerComments.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerComments.setAdapter(mCommentsAdapter);
        mButtonPost.setOnClickListener(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_topic_comments;
    }

    @Override
    public void refreshData(List<Comment> comments) {
        mCommentsAdapter.setData(comments);
        mCommentsAdapter.notifyDataSetChanged();
    }

    @Override
    public void postResult(boolean bResult, String msg) {
        if(bResult){
            mEditContent.setText("");
            mCommentsPresenter.loadComments(mTopicId, 1, PAGE_SIZE);
        }else{
            Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_post:
                String text = mEditContent.getText().toString().trim();
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(this, R.string.post_error_hint, Toast.LENGTH_SHORT).show();
                } else {
                    mCommentsPresenter.postComment(mTopicId, text);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mCommentsPresenter.stopPresenter();
        super.onDestroy();
    }
}
