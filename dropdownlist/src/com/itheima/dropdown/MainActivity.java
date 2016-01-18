package com.itheima.dropdown;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ImageView ivArrow;
	private ListView lvList;
	private EditText etContent;

	private ArrayList<String> mList;
	private PopupWindow mPopupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ivArrow = (ImageView) findViewById(R.id.iv_arrow);
		ivArrow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDropDown();
			}
		});

		etContent = (EditText) findViewById(R.id.et_content);

		lvList = new ListView(this);
		lvList.setBackgroundResource(R.drawable.listview_background);

		// 初始化假数据
		mList = new ArrayList<String>();
		for (int i = 0; i < 200; i++) {
			mList.add("aaabbbccc" + i);
		}

		lvList.setAdapter(new MyAdapter());

		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String content = mList.get(position);
				etContent.setText(content);

				// popupwindow消失
				mPopupWindow.dismiss();
			}
		});
	}

	protected void showDropDown() {
		mPopupWindow = new PopupWindow(lvList, etContent.getWidth(), 200, true);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable());// 必须设置背景,否则点击返回键无法消失
		mPopupWindow.showAsDropDown(etContent);// 显示在控件正下方
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mList.size();
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
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.list_item, null);

				holder = new ViewHolder();
				holder.tvContent = (TextView) convertView
						.findViewById(R.id.tv_content);
				holder.ivDelete = (ImageView) convertView
						.findViewById(R.id.iv_delete);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tvContent.setText(mList.get(position));
			holder.ivDelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//删除数据
					mList.remove(position);
					//刷新界面
					notifyDataSetChanged();
				}
			});

			return convertView;
		}

	}

	static class ViewHolder {
		public TextView tvContent;
		public ImageView ivDelete;
	}

}
