package kv.key;

import kv.base.BaseDimension;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DateDimension extends BaseDimension {
    private String year;
    private String month;
    private String day;

    public DateDimension(){
        super();
    }

    public DateDimension(String year, String month, String day){
        super();
        this.year = year;
        this.month = month;
        this.day = day;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateDimension that = (DateDimension) o;

        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (month != null ? !month.equals(that.month) : that.month != null) return false;
        return day != null ? day.equals(that.day) : that.day == null;
    }

    @Override
    public int hashCode() {
        int result = year != null ? year.hashCode() : 0;
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(BaseDimension o) {
        DateDimension anotherDateDimension = (DateDimension)o;
        int result = this.year.compareTo(anotherDateDimension.year);
        if(result != 0) return result;

        result = this.month.compareTo(anotherDateDimension.month);
        if(result != 0) return result;

        result = this.day.compareTo(anotherDateDimension.day);

        return result;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.year);
        out.writeUTF(this.month);
        out.writeUTF(this.day);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.year = in.readUTF();
        this.month = in.readUTF();
        this.day = in.readUTF();
    }
}
