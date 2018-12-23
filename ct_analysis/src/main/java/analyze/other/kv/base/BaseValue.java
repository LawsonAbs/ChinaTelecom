package analyze.other.kv.base;

import org.apache.hadoop.io.Writable;

//01. Writable 是一个接口。
//02. Any key or value type in the Hadoop Map-Reduce framework implements this interface.
public abstract class BaseValue implements Writable{}
