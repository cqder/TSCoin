package cn.ts.tscoin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ts.tscoin.MainActivity;
import cn.ts.tscoin.R;
import cn.ts.tscoin.db.DBManager;
import cn.ts.tscoin.entity.TSRecord;
import cn.ts.tscoin.entity.TSTag;

public class ChartActivity extends AppCompatActivity {

    //  2019/12/16 获得所有的记录数据
    Context chartContext;
    TextView textViewResult;
    // TODO: 2019/12/16 加载图例
    // TODO: 2019/12/16 画饼图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        chartContext = this;
        textViewResult = findViewById(R.id.tv_chart_result);

        showChart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_chat) {
            startActivity(new Intent(ChartActivity.this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    // 列出当月的收支情况
    void showChart() {

        DBManager recordManager = new DBManager(chartContext, TSRecord.class);
        DBManager tagManager = new DBManager(chartContext, TSTag.class);
        //所有的记录
//        List<TSRecord> allRecordList = recordManager.findAll(TSRecord.class);
        //当月的记录
        List<TSRecord> currentMonthRecordList = recordManager.findByArgs(TSRecord.class, "time>?", new String[]{String.valueOf(getMonthTime())});
        Toast.makeText(chartContext, "查询到的记录有:" + currentMonthRecordList.size() + "条", Toast.LENGTH_LONG).show();
        if (currentMonthRecordList.isEmpty()) return;
        // 所有数据的统计信息
        Map<String, Float> currentMap = new HashMap<>();
        for (TSRecord record : currentMonthRecordList) {
            //得到钱和TAG
            String tag = tagManager.findByArgs(TSTag.class, "id=?", new String[]{String.valueOf(record.getTag())}).get(0).getName();
            Float money = record.getMoney();
            boolean hadTag = false;
            //判断是否有这个TAG
            for (String tempTag : currentMap.keySet()) {
                if (tempTag.equals(tag)) hadTag = true;
            }
            //有TAG则取出Money再加上当前的Money
            if (hadTag) {
                Float countedMoney = currentMap.get(tag);
                money += countedMoney;
            }
            //更新Map
            currentMap.put(tag, money);
        }


        StringBuilder stringBuilder = new StringBuilder();
        for (String tempTag : currentMap.keySet()) {
            stringBuilder.append(tempTag).append("消费:");
            stringBuilder.append(currentMap.get(tempTag)).append("元\n");
        }
        stringBuilder.insert(stringBuilder.length() - 1, " ");
        String result = stringBuilder.toString();
//        Toast.makeText(chartContext,result,Toast.LENGTH_LONG).show();
        textViewResult.setText(result);

    }

    /**
     * 获得当月的时间戳
     *
     * @return 时间戳
     */
    long getMonthTime() {
        Date now = new Date();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(now);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

}