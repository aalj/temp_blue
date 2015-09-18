package record.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.exmaple2.bluetooth2.R;

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

/**
 * 
 * ClassName:SQLite_Activity
 * 
 * @Function: ��¼���������ݿ�
 * @Reason: TODO ADD REASON
 *
 * @author AYI
 * @version
 * @since Ver 1.1
 * @Date 2015 2015��8��28�� ����8:27:46
 *
 * @see
 */
public class SQLite_Activity extends Activity {

	List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

	SimpleAdapter adapter;

	String sql = "select * from step";

	SwipeMenuListView sql_list;

	int i = 0;

	boolean flag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite);
		// dataInit();
		viewInit();
		add();
	}

	// // ����Դ
	// public void dataInit() {
	// list.add("1");
	// list.add("1");
	// list.add("1");
	// list.add("1");
	// list.add("1");
	//
	// }

	// ��ʼ��
	public void viewInit() {
		data.clear();
		// ListView sql_list = (ListView) findViewById(R.id.listView1);
		sql_list = (SwipeMenuListView) findViewById(R.id.swipeMenuListView1);
		String[] from = { "NUM", "TIME", "ID" };
		int[] to = { R.id.tv_num, R.id.tv_time, R.id.tv_id };
		adapter = new SimpleAdapter(SQLite_Activity.this, data, R.layout.item,
				from, to);
		sql_list.setAdapter(adapter);

		cehua();
	}

	public void add() {
		data.clear();

		SQlite sqlite = new SQlite(SQLite_Activity.this);
		SQLiteDatabase db = sqlite.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);

		// ������ѯ��� ȡ��ÿһ�е��û����͵绰����
		while (cursor.moveToNext()) {
			int indexStep = cursor.getColumnIndex("stepnum");
			int stepnum = cursor.getInt(indexStep);

			int indexTime = cursor.getColumnIndex("time");
			String time = cursor.getString(indexTime);

			int indexId = cursor.getColumnIndex("id");
			int id = cursor.getInt(indexId);
			i = id;

			Map<String, Object> map = new HashMap<String,Object>();
			map.put("NUM", stepnum);
			map.put("TIME", time);
			map.put("ID", id);
			// if (flag) {
			// map.put("ID", i);
			// i++;
			// }
			data.add(map);
			adapter.notifyDataSetChanged();

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.step:
			// stepnum���ַ��� ����1���������ֵ???
			sql = "select * from step order by stepnum*1 desc";
			flag = false;
			add();
			break;
		case R.id.delete:
			sql = "delete from step";

			add();
			adapter.notifyDataSetChanged();
			RelativeLayout re = (RelativeLayout) findViewById(R.id.relayout);
			re.setBackgroundResource(R.drawable.beijing);
			break;
		case R.id.time:
			sql = "select * from step order by time desc";
			flag = false;
			add();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void cehua() {
		// �����໬ �����˵�
		SwipeMenuCreator creator = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				// TODO Auto-generated method stub
				// ��ʼ���˵���
				SwipeMenuItem item = new SwipeMenuItem(SQLite_Activity.this);
				// ֻ֧��ColorDrawable����
				item.setBackground(new ColorDrawable(Color
						.rgb(0xF9, 0x3F, 0x25)));
				// item.setIcon(R.drawable.ic_launcher);
				item.setTitle("ɾ��");
				item.setTitleColor(Color.WHITE);
				item.setTitleSize(18);
				// ���� �˵���Ŀ��� ������Ļ
				item.setWidth(dp2px(90));
				// �Ѳ˵��� ���ӵ� �˵���
				menu.addMenuItem(item);
			}
		};

		// �໬ɾ�� �б�
		sql_list.setMenuCreator(creator);
		// sw.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
		//
		// @Override
		// public void onSwipeStart(int position) {
		// // TODO Auto-generated method stub
		// }
		//
		// @Override
		// public void onSwipeEnd(int position) {
		// // TODO Auto-generated method stub
		// }
		// });
		sql_list.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu,
					int index) {
				// TODO Auto-generated method stub
				delete(position);
				return false;
			}
		});
	}

	// ��dpֵ ת�������ص�λ
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	// ɾ��
	public void delete(int position) {
		// ������Դ���Ƴ�����
		data.remove(position);
		// ���ݸı� ֪ͨҳ��ˢ��
		adapter.notifyDataSetChanged();

	}

}