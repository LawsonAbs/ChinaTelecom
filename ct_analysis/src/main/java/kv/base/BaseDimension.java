package kv.base;

import org.apache.hadoop.io.WritableComparable;

public abstract class BaseDimension implements WritableComparable<BaseDimension>{}
/*
1.无论是key,value都必须实现Writable接口
 */