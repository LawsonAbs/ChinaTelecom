package analyze.other.kv.key;

import analyze.other.kv.base.BaseDimension;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
01. ComDimension 集成了两个类，分别是 ContactDimension 和 DateDimension。这里集成的意思是指这个类维护了
 ContactDimension 和 DateDimension 两个维度

 */

public class ComDimension extends BaseDimension {
    private ContactDimension contactDimension = new ContactDimension();
    private DateDimension dateDimension = new DateDimension();

    public ComDimension(){
        super();
    }
    //setter and getter
    public ContactDimension getContactDimension() {
        return contactDimension;
    }

    public void setContactDimension(ContactDimension contactDimension) {
        this.contactDimension = contactDimension;
    }

    public DateDimension getDateDimension() {
        return dateDimension;
    }

    public void setDateDimension(DateDimension dateDimension) {
        this.dateDimension = dateDimension;
    }


    //Compares this object with the specified object for order.
    //将这个对象和指定的对象进行顺序比较
    @Override
    public int compareTo(BaseDimension o) {
        ComDimension anotherComDimension = (ComDimension) o;
        int result = this.dateDimension.compareTo(anotherComDimension.dateDimension);
        if(result != 0) return result;

        result = this.contactDimension.compareTo(anotherComDimension.contactDimension);
        return result;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        contactDimension.write(out);
        dateDimension.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        contactDimension.readFields(in);
        dateDimension.readFields(in);
    }
}
