package analyze.fromHBToMys.entity;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MonthStat implements Writable,DBWritable {
    /**
     * callDuration: total call's duration
     */
    public String teleNumber;
    public String yearMonth;
    public int callDuration;

    public MonthStat(){
    }

    public MonthStat(
            String teleNumber,
            String yearMonth,
            int callDuration){
        this.teleNumber = teleNumber;
        this.yearMonth = yearMonth;
        this.callDuration = callDuration;
    }

    public void write(DataOutput out) throws IOException {
        Text.writeString(out,this.teleNumber);
        Text.writeString(out, yearMonth);
        out.writeInt(this.callDuration);
    }

    public void readFields(DataInput in) throws IOException {
        this.teleNumber = Text.readString(in);
        this.yearMonth = Text.readString(in);
        this.callDuration = in.readInt();
    }


    //-----------------------------------DBWritable------------------------------------------
    public void write(PreparedStatement statement) throws SQLException {
        statement.setString(1,this.teleNumber);
        statement.setString(2,this.yearMonth);
        statement.setInt(3,this.callDuration);
    }

    public void readFields(ResultSet resultSet) throws SQLException {
        this.teleNumber = resultSet.getString(1);
        this.yearMonth = resultSet.getString(2);
        this.callDuration = resultSet.getInt(3);
    }
}
