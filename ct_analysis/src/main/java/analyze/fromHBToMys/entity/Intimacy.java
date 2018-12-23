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

public class Intimacy implements Writable,DBWritable {
    /**
     * callDuration: total call's duration
     */
    public String call1;
    public String call2;
    public String year;
    public int callDuration;

    public Intimacy(){
    }


    public Intimacy(
            String call1,
            String call2,
            String year,
            int callDuration){
        this.call1 = call1;
        this.call2 = call2;
        this.year = year;
        this.callDuration = callDuration;
    }

    public void write(DataOutput out) throws IOException {
        Text.writeString(out,this.call1);
        Text.writeString(out, this.call2);
        Text.writeString(out,this.year);
        out.writeInt(this.callDuration);
    }

    public void readFields(DataInput in) throws IOException {
        this.call1 = Text.readString(in);
        this.call2 = Text.readString(in);
        this.year = Text.readString(in);
        this.callDuration = in.readInt();
    }


    //-----------------------------------DBWritable------------------------------------------
    public void write(PreparedStatement statement) throws SQLException {
        statement.setString(1,this.call1);
        statement.setString(2,this.call2);
        statement.setString(3,this.year);
        statement.setInt(4,this.callDuration);
    }

    public void readFields(ResultSet resultSet) throws SQLException {
        this.call1 = resultSet.getString(1);
        this.call2 = resultSet.getString(2);
        this.year = resultSet.getString(3);
        this.callDuration = resultSet.getInt(4);
    }
}
