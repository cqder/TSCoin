package cn.ts.tscoin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.ts.tscoin.activity.ChartActivity;
import cn.ts.tscoin.db.DBManager;
import cn.ts.tscoin.entity.TSDirt;
import cn.ts.tscoin.entity.TSRecord;
import cn.ts.tscoin.entity.TSTag;
import cn.ts.tscoin.tool.LogTool;
import cn.ts.tscoin.tool.TagTool;

import static cn.ts.tscoin.Constant.KEY_INSTALL_DATE;
import static cn.ts.tscoin.Constant.KEY_IS_INSTALL;
import static cn.ts.tscoin.Constant.KEY_USE_TIMES;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // TODO: 2019/12/16 判断初次安装是用sp还是数据库
    Context context;
    //显示金额的TextView
    TextView textViewMoney, textViewTag;
    //金额
    Integer money = 0;
    String tag = "无";
    //金额的最大值
    Integer max = 1000000;
    //显示标签的TableLayout
    TableLayout tableLayoutTag;
    //日期
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    //时间
    SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        //得到是否是安装过本软件
        SharedPreferences preferences = context.getSharedPreferences("cqder", MODE_PRIVATE);
        boolean isInstall = preferences.getBoolean(KEY_IS_INSTALL, false);

        if (!isInstall) {
            //未安装过本软件
            initDatabase();
            preferences.edit().putBoolean(KEY_IS_INSTALL, true).apply();
        }

        initView();
//        Log.w("cqder", getAllRecord().toString());
    }

    /**
     * 初始化控件
     */
    void initView() {
        //通过ID赋值
        findViewById(R.id.bt_main_0).setOnClickListener(this);
        findViewById(R.id.bt_main_1).setOnClickListener(this);
        findViewById(R.id.bt_main_2).setOnClickListener(this);
        findViewById(R.id.bt_main_3).setOnClickListener(this);
        findViewById(R.id.bt_main_4).setOnClickListener(this);
        findViewById(R.id.bt_main_5).setOnClickListener(this);
        findViewById(R.id.bt_main_6).setOnClickListener(this);
        findViewById(R.id.bt_main_7).setOnClickListener(this);
        findViewById(R.id.bt_main_8).setOnClickListener(this);
        findViewById(R.id.bt_main_9).setOnClickListener(this);
        findViewById(R.id.bt_main_c).setOnClickListener(this);
        findViewById(R.id.bt_main_ok).setOnClickListener(this);

        textViewMoney = findViewById(R.id.tv_main_money);
        tableLayoutTag = findViewById(R.id.tl_main_tag);
        textViewTag = findViewById(R.id.tv_main_tag);

//        // 待动态刷新成功后删除该段代码
//        findViewById(R.id.bt_main_type).setOnClickListener(this);
//        findViewById(R.id.bt_main_type1).setOnClickListener(this);
//        findViewById(R.id.bt_main_type2).setOnClickListener(this);
//        findViewById(R.id.bt_main_type3).setOnClickListener(this);
//        findViewById(R.id.bt_main_type4).setOnClickListener(this);
//        findViewById(R.id.bt_main_type5).setOnClickListener(this);
//        findViewById(R.id.bt_main_type6).setOnClickListener(this);
//        findViewById(R.id.bt_main_type7).setOnClickListener(this);
        // TODO: 2019/12/10 标签排序


        //  2019/12/9 动态加载按钮
        List<TSTag> tagList = getAllTag();
        //tagList.size=28  tagList.size()/4=7  tagList.size()%4=0
        LogTool.w("tagList.size()/4=" + (tagList.size() / 4) + "\ntagList.size()%4=" + (tagList.size() % 4));
        int tagCount = 0;
        //要加载的行数
        int lineCount = 0;
        if (tagList.size() % 4 == 0) {
            lineCount = tagList.size() / 4;
        } else {
            lineCount += tagList.size() / 4;
        }

        for (int i = 0; i < lineCount; i++) {
            TableRow tableRow = new TableRow(context);
            for (int j = 0; j < 4; j++) {
                Button button = new Button(context);
                String tagName = tagList.get(tagCount).getName();
                button.setText(tagName);
                button.setOnClickListener(this);
                button.setBackgroundColor(getResources().getColor(R.color.bg_main_button));
                tableRow.addView(button);
                tagCount++;
            }
            tableLayoutTag.addView(tableRow);
        }

        tableLayoutTag.setStretchAllColumns(true);
        //        stretchColumns
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_main_0:
                money = money * 10;
                break;
            case R.id.bt_main_1:
                money = money * 10 + 1;
                break;
            case R.id.bt_main_2:
                money = money * 10 + 2;
                break;
            case R.id.bt_main_3:
                money = money * 10 + 3;
                break;
            case R.id.bt_main_4:
                money = money * 10 + 4;
                break;
            case R.id.bt_main_5:
                money = money * 10 + 5;
                break;
            case R.id.bt_main_6:
                money = money * 10 + 6;
                break;
            case R.id.bt_main_7:
                money = money * 10 + 7;
                break;
            case R.id.bt_main_8:
                money = money * 10 + 8;
                break;
            case R.id.bt_main_9:
                money = money * 10 + 9;
                break;
            case R.id.bt_main_c:
                money = money / 10;
                break;
            case R.id.bt_main_ok:
                if (money == 0) break;
                saveRecord();
                break;
            default:
                Button button = (Button) v;
                tag = button.getText().toString();
                textViewTag.setText(tag);
                break;
        }
//        Log.w("cqder", "money is " + money);
        showMoney();
    }

    /**
     * 保存一次记录
     */
    void saveRecord() {
        //记录的对象
        TSRecord tsRecord = new TSRecord();

        //得到Tag的ID
        int tagId = 100;
        DBManager tagManager = new DBManager(context, TSTag.class);
        tagId = tagManager.findByArgs(TSTag.class, "name=?", new String[]{tag}).get(0).getId();

        //记录的时间
        long dateTime = new Date().getTime();

        //向记录写入数据
        tsRecord.setMoney(money);
        tsRecord.setTag(tagId);
        tsRecord.setDescribe("");
        tsRecord.setTime(dateTime);


        //保存结果
        DBManager dbManager = new DBManager(context, TSRecord.class);
        long result = dbManager.insert(tsRecord);
        if (result != -1) {
            //保存成功,数字归零
            String strResult = "花费" + tsRecord.getMoney() + "元 保存成功!";
            Toast.makeText(context, strResult, Toast.LENGTH_LONG).show();
            money = 0;
        }
        //刷新数字
        showMoney();
    }

    void showMoney() {
        if (money > max) {
            money = money / 10;
        } else if (money > 99999) {
            StringBuilder stringBuilder = new StringBuilder(money.toString());
            stringBuilder.insert(2, "万");
            textViewMoney.setText(stringBuilder.toString());
        } else if (money > 9999) {
            StringBuilder stringBuilder = new StringBuilder(money.toString());
            stringBuilder.insert(1, "万");
            textViewMoney.setText(stringBuilder.toString());
        } else {
            textViewMoney.setText(String.valueOf(money));
        }
    }

    /**
     * 得到所有的TAG数据
     *
     * @return List<TSTag>  Tag的数据
     */
    private List<TSTag> getAllTag() {
        List<TSTag> TSTagList = new ArrayList<>();
        DBManager tagManager = new DBManager(context, TSTag.class);
        TSTagList = tagManager.findAll(TSTag.class);
        Log.w("cqder", TSTagList.toString());
        LogTool.w("得到的所有标签为:" + TSTagList.toString());
        return TSTagList;
    }

    private List<TSRecord> getAllRecord() {
        List<TSRecord> TSRecordList = new ArrayList<>();
        DBManager tagManager = new DBManager(context, TSRecord.class);
        TSRecordList = tagManager.findAll(TSRecord.class);
        Log.w("cqder", TSRecordList.toString());
        return TSRecordList;
    }

    // 2019/12/9 添加Tag按钮

    // 判断是否是第一次登录,弃用,初次安装没有数据库文件,运行会报错
//    private boolean isInstall() {
//        DBManager dirtManager = new DBManager(context, TSDirt.class);
//        List<TSDirt> dirtList = dirtManager.findByArgs(TSDirt.class, "key=?", new String[]{KEY_IS_INSTALL});
//        return dirtList.size() != 0;
//    }


    /**
     * 初次使用时初始化数据库: 初次使用日期,打开次数,是否已安装,Tag标签数据初次赋值
     */
    private void initDatabase() {
        //建立数据表
        List<Class> classList = new ArrayList<>();
        classList.add(TSTag.class);
        classList.add(TSDirt.class);
        classList.add(TSRecord.class);
        new DBManager(context, classList);
        //字典Manager
        DBManager dirtManager = new DBManager(context, TSDirt.class);
        //字典对象:安装日期
        TSDirt dirtInstallDate = new TSDirt();
        dirtInstallDate.setKey(KEY_INSTALL_DATE);
        dirtInstallDate.setValue(sdfDate.format(new Date()));
        //        LogTool.w("初次安装日期为:"+sdfDate.format(new Date()));
        dirtManager.insert(dirtInstallDate);

        //字典对象:使用次数
        TSDirt dirUseTimes = new TSDirt();
        dirUseTimes.setKey(KEY_USE_TIMES);
        dirUseTimes.setValue("1");
        dirtManager.insert(dirUseTimes);

        //标签Manager
        DBManager tagManager = new DBManager(context, TSTag.class);
        List<String> tags = TagTool.getTags();
        for (int i = 0; i < tags.size(); i++) {
            TSTag tsTag = new TSTag();
            tsTag.setName(tags.get(i));
            tsTag.setWeight(i);
            tagManager.insert(tsTag);
        }
        //追加一个默认标签的值
        TSTag tsTag = new TSTag();
        tsTag.setName("无");
        tsTag.setWeight(100);
        tagManager.insert(tsTag);

//        List<TSTag> tsTagList=tagManager.findAll(TSTag.class);
//        LogTool.w("所有的TAG为:"+tsTagList.toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_chat) {
            startActivity(new Intent(MainActivity.this, ChartActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}

