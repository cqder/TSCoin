package cn.ts.tscoin.entity;

public class TSRecord {

    int id;
    float money;
    int tag;
    long  time;
    String describe;

    @Override
    public String toString() {
        return "TSRecord{" +
                "id=" + id +
                ", money=" + money +
                ", tag=" + tag +
                ", time='" + time + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
