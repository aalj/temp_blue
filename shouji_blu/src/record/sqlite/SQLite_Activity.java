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
	// �����б�
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	// ��������
	private SimpleAdapter adapter;
	// ��ʼ��ʱִ�е�sql���
	private String sql = "select * from step";
	// �໬ɾ������
	private SwipeMenuListView sql_list;
	// ɾ����idֵ
	String str_id = "";
	// sqliteִ����
	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite);
		viewInit();
		// ��ѯ���ݿⲢ��ʾ
		add();
	}

	// ��ʼ��
	public void viewInit() {
		sql_list = (SwipeMenuListView) findViewById(R.id.swipeMenuListView1);
		String[] from = { "NUM", "TIME", "ID" };
		int[] to = { R.id.tv_num, R.id.tv_time, R.id.tv_id };
		// ��ʼ����������
		adapter = new SimpleAdapter(SQLite_Activity.this, data, R.layout.item, from, to);
		// ����������
		sql_list.setAdapter(adapter);
		// �໬ɾ������
		cehua();
	}

	// ��ѯ���ݿⲢ��ʾ
	public void add() {
		// int str = 0;
		data.clear();

		SQlite sqlite = new SQlite(SQLite_Activity.this);
		db = sqlite.getReadableDatabase();
		// ���
		Cursor cursor = db.rawQuery(sql, null);

		// �����ѯ��� ȡ��ÿһ�е��û����͵绰����
		while (cursor.moveToNext()) {
			int indexStep = cursor.getColumnIndex("stepnum");
			int stepnum = cursor.getInt(indexStep);

			int indexTime = cursor.getColumnIndex("time");
			String time = cursor.getString(indexTime);

			int indexId = cursor.getColumnIndex("num");
			int id = cursor.getInt(indexId);

			// map���� һ�е�����
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

	// �˵���
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// �˵���ѡ��
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.step:// �Բ����Ӹߵ�������
			// stepnum���ַ��� ����1���������ֵ???
			sql = "select * from step order by stepnum*1 desc";
			add();
			break;
		case R.id.delete:// ɾ��ȫ������
			sql = "delete from step";
			add();
			adapter.notifyDataSetChanged();
			RelativeLayout re = (RelativeLayout) findViewById(R.id.relayout);
			re.setBackgroundResource(R.drawable.beijing);
			break;
		case R.id.time:// ��ʱ����Ⱥ�˳������
			sql = "select * from step order by time desc";
			add();
			break;
		case R.id.jilu:// ����¼��ѯ
			sql = "select * from step order by id";
			add();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// �໬ɾ������
	public void cehua() {
		// �����໬ �����˵�
		SwipeMenuCreator creator = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				// TODO Auto-generated method stub
				// ��ʼ���˵���
				SwipeMenuItem item = new SwipeMenuItem(SQLite_Activity.this);
				// ֻ֧��ColorDrawable����
				item.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
				item.setTitle("ɾ��");
				item.setTitleColor(Color.WHITE);
				item.setTitleSize(18);
				// ���� �˵���Ŀ�� ������Ļ
				item.setWidth(dp2px(90));
				// �Ѳ˵��� ��ӵ� �˵���
				menu.addMenuItem(item);
			}
		};

		// �໬ɾ�� �б�
		sql_list.setMenuCreator(creator);
		sql_list.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				// TODO Auto-generated method stub
				// ����ɾ������
				delete(position);
				return false;
			}
		});
	}

	// ��dpֵ ת�������ص�λ
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
	}

	// ɾ������
	public void delete(int position) {
		// ������Դ���Ƴ�����
		data.remove(position);
		adapter.notifyDataSetChanged();

		/*
		 * listview��getchildAt�����Ƚ����⣺�������ֻ�ܷ��ص�ǰ��Ļ���ӷ�Χ�ڵ�view
		 * ֱ��дgetchildAt(position)ֻ��ҳ������ʾ����Ŀ��Ч����������Ч���˷�������ֵΪnull
		 * ListView��View���û��ջ��ƣ����ǣ������Ļ��������ʾn����View����ô�ڴ�����ʵֻ��n��View
		 * �������ڹ���ʱ����(n+1)��View���õ�1��View�� getChildAt(int position )
		 * ������positionָ���ǵ�ǰ�ɼ�����ĵڼ���Ԫ�ء�
		 */
		/*
		 * ��������������Ҫ���ҳ������ʾ�ĵ�n��View����ôn����position��ȥ��һ���ɼ�View��λ�� position -
		 * sql_list.getFirstVisiblePosition()
		 */
		// �����ݿ�ɾ������
		TextView tv_id = (TextView) sql_list.getChildAt(position - sql_list.getFirstVisiblePosition())
				.findViewById(R.id.tv_id);
		String str_id = tv_id.getText().toString();
		// System.out.println(str_id);
		// ִ��ɾ����ѡ����Ŀ������
		db.execSQL("DELETE FROM " + "step" + " WHERE id=" + str_id);
	}
}
