package record.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.zhilian.blu.R;
/**
 * 
 * ClassName:SQLite_Activity
 * 
 * @Function: 记录步数的数据库
 * @Reason: TODO ADD REASON
 *
 * @author AYI
 * @version
 * @since Ver 1.1
 * @Date 2015 2015年8月28日 下午8:27:46
 *
 * @see
 */
public class SQLite_Activity extends Activity {
	// 数据列表
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	// 简单适配器
	private SimpleAdapter adapter;
	// 初始化时执行的sql语句
	private String sql = "select * from step";
	// 侧滑删除链表
	private SwipeMenuListView sql_list;
	// 删除的id值
	String str_id = "";
	// sqlite执行类
	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite);
		viewInit();
		// 查询数据库并显示
		add();
	}

	// 初始化
	public void viewInit() {
		sql_list = (SwipeMenuListView) findViewById(R.id.swipeMenuListView1);
		String[] from = { "NUM", "TIME", "ID" };
		int[] to = { R.id.tv_num, R.id.tv_time, R.id.tv_id };
		// 初始化简单适配器
		adapter = new SimpleAdapter(SQLite_Activity.this, data, R.layout.item, from, to);
		// 加载适配器
		sql_list.setAdapter(adapter);
		// 侧滑删除方法
		cehua();
	}

	// 查询数据库并显示
	public void add() {
		// int str = 0;
		data.clear();

		SQlite sqlite = new SQlite(SQLite_Activity.this);
		db = sqlite.getReadableDatabase();
		// 光标
		Cursor cursor = db.rawQuery(sql, null);

		// 处理查询结果 取出每一行的用户名和电话号码
		while (cursor.moveToNext()) {
			int indexStep = cursor.getColumnIndex("stepnum");
			int stepnum = cursor.getInt(indexStep);

			int indexTime = cursor.getColumnIndex("time");
			String time = cursor.getString(indexTime);

			int indexId = cursor.getColumnIndex("num");
			int id = cursor.getInt(indexId);

			// map集合 一行的数据
			Map<String, Object> map = new HashMap<String, Object>();
			// str = map.size();
			map.put("NUM", stepnum);
			map.put("TIME", time);
			map.put("ID", id);
			data.add(map);
			adapter.notifyDataSetChanged();

		}
		if (cursor.getCount() != 0) {
			RelativeLayout re = (RelativeLayout) findViewById(R.id.relayout);
			re.setBackgroundResource(R.drawable.write);
		}
	}

	// 菜单键
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// 菜单键选择
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.step:// 以步数从高到低排序
			// stepnum是字符型 乘以1变成整型数值???
			sql = "select * from step order by stepnum*1 desc";
			add();
			break;
		case R.id.delete:// 删除全部数据
			sql = "delete from step";
			add();
			adapter.notifyDataSetChanged();
			RelativeLayout re = (RelativeLayout) findViewById(R.id.relayout);
			re.setBackgroundResource(R.drawable.beijing);
			break;
		case R.id.time:// 按时间的先后顺序排序
			sql = "select * from step order by time desc";
			add();
			break;
		case R.id.jilu:// 按记录查询
			sql = "select * from step order by id";
			add();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// 侧滑删除方法
	public void cehua() {
		// 创建侧滑 产生菜单
		SwipeMenuCreator creator = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				// TODO Auto-generated method stub
				// 初始化菜单项
				SwipeMenuItem item = new SwipeMenuItem(SQLite_Activity.this);
				// 只支持ColorDrawable对象
				item.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
				item.setTitle("删除");
				item.setTitleColor(Color.WHITE);
				item.setTitleSize(18);
				// 设置 菜单项的宽度 适配屏幕
				item.setWidth(dp2px(90));
				// 把菜单项 添加到 菜单上
				menu.addMenuItem(item);
			}
		};

		// 侧滑删除 列表
		sql_list.setMenuCreator(creator);
		sql_list.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				// TODO Auto-generated method stub
				// 调用删除方法
				delete(position);
				return false;
			}
		});
	}

	// 把dp值 转换成像素单位
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
	}

	// 删除方法
	public void delete(int position) {
		// 从数据源中移除数据
		data.remove(position);
		adapter.notifyDataSetChanged();

		/*
		 * listview的getchildAt方法比较特殊：这个方法只能返回当前屏幕可视范围内的view
		 * 直接写getchildAt(position)只对页面所显示的条目有效，滑动后无效，此方法返回值为null
		 * ListView对View采用回收机制，就是：如果屏幕最多可以显示n个子View，那么内存中其实只有n个View
		 * 当我们在滚动时，第(n+1)个View复用第1个View。 getChildAt(int position )
		 * 方法中position指的是当前可见区域的第几个元素。
		 */
		/*
		 * 解决方法：如果你要获得页面上显示的第n个View，那么n就是position减去第一个可见View的位置 position -
		 * sql_list.getFirstVisiblePosition()
		 */
		// 从数据库删除数据
		TextView tv_id = (TextView) sql_list.getChildAt(position - sql_list.getFirstVisiblePosition())
				.findViewById(R.id.tv_id);
		String str_id = tv_id.getText().toString();
		// System.out.println(str_id);
		// 执行删除所选中条目的数据
		db.execSQL("DELETE FROM " + "step" + " WHERE id=" + str_id);
	}
}
