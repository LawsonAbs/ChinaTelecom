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

public class Statistics implements Writable,DBWritable {
    /**
     * callDuration: total call's duration
     */
    public String teleNumber;
    public int callDuration;

    public Statistics(){
    }


    public Statistics(
            String teleNumber,
            int callDuration){
        this.teleNumber = teleNumber;
        this.callDuration = callDuration;
    }

    public void write(DataOutput out) throws IOException {
        Text.writeString(out,this.teleNumber);
        out.writeInt(this.callDuration);
    }

    public void readFields(DataInput in) throws IOException {
        this.teleNumber = Text.readString(in);
        this.callDuration = in.readInt();
    }


    //-----------------------------------DBWritable------------------------------------------
    public void write(PreparedStatement statement) throws SQLException {
        statement.setString(1,this.teleNumber);
        statement.setInt(2,this.callDuration);
    }

    public void readFields(ResultSet resultSet) throws SQLException {
        this.teleNumber = resultSet.getString(1);
        this.callDuration = resultSet.getInt(2);
    }
}
