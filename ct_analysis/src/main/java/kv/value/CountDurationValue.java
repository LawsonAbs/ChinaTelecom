package kv.value;

import kv.base.BaseValue;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CountDurationValue extends BaseValue{
    private String callSum;
    private String callDurationSum;

    public CountDurationValue(){
        super();
    }

    public String getCallSum() {
        return callSum;
    }

    public void setCallSum(String callSum) {
        this.callSum = callSum;
    }

    public String getCallDurationSum() {
        return callDurationSum;
    }

    public void setCallDurationSum(String callDurationSum) {
        this.callDurationSum = callDurationSum;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        //需要将本类中的字段序列化，所以需要使用write方法
        out.writeUTF(callSum);
        out.writeUTF(callDurationSum);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        //如果是反序列化，则需要使用readFields()方法
        this.callSum = in.readUTF();
        this.callDurationSum = in.readUTF();
    }
}
