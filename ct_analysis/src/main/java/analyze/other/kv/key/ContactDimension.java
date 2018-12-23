package analyze.other.kv.key;

import analyze.other.kv.base.BaseDimension;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
1.因为是key，map阶段需要根据其排序，所以除了需要实现序列化(write,readFields)之外，还要实现比较器(compareTo)功能
2.因为BaseDimension 实现了 Writable 和 Comparable
3.WritableComparable: A Writable which is also Comparable.
 */

public class ContactDimension extends BaseDimension{

    //持有两个对象：telephone and name
    private String telephone;
    private String name;

    //这种无参构造器需要么？
    public ContactDimension(){
        super();
    }

    public ContactDimension(String telephone, String name){
        super();
        this.telephone = telephone;
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // 如果两个对象相同，则返回true

        //如果类型都不相同，则直接返回false
        if (o == null || getClass() != o.getClass()) return false;

        ContactDimension that = (ContactDimension) o;

        //依次比较telephone和name，若有不同，则直接返回false
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = telephone != null ? telephone.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(BaseDimension o) {
        ContactDimension anotherContactDimension = (ContactDimension) o;

        int result = this.name.compareTo(anotherContactDimension.name);
        if(result != 0) return result;

        result = this.telephone.compareTo(anotherContactDimension.telephone);
        return result;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.telephone);
        out.writeUTF(this.name);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.telephone = in.readUTF();
        this.name = in.readUTF();
    }
}
