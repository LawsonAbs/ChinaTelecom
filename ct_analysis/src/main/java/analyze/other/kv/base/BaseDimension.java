package analyze.other.kv.base;

import org.apache.hadoop.io.WritableComparable;

/**
 * 1.无论是key,value都必须实现Writable接口
 * 2. WritableComparable 接口继承（实现）了 Writable, Comparable<T> 两个接口
 *    接口也可以使用 extends 关键字
 */

public abstract class BaseDimension implements WritableComparable<BaseDimension>{}
