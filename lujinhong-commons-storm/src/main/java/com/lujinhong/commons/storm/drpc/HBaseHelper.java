package com.lujinhong.commons.storm.drpc;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 如果项目中需要用到hbase的操作，请将此类加入classpath。
 * 封装了常用的操作，包括：
 * （1）创建表，判断表是否存在，删除表
 * （2）插入数据
 * （3）get数据
 * （4）scan表
 * ...
 */
public class HBaseHelper implements Closeable {

	private final static Logger LOG = LoggerFactory.getLogger(HBaseHelper.class);
	private static Configuration configuration = null;
	private static Connection connection = null;
	private Admin admin = null;
	private static HBaseHelper helper = null;

	private HBaseHelper(Configuration conf) throws IOException {
		configuration = conf;
		connection = ConnectionFactory.createConnection(configuration);
		this.admin = connection.getAdmin();
	}

	/*
	 * 用于获取一个HBaseHelper对象的入口，需要提供一个Configuration对象，这个配置主要指定hbase-site.xml与core-
	 * site.xml。 使用单例，保证只创建一个helper，因为每创建一个connection都是高代价的，如果需要多个连接，请使用Pool。
	 */
	public static HBaseHelper getHelper(Configuration configuration) throws IOException {
		if (helper == null) {
			helper = new HBaseHelper(configuration);
		}
		return helper;
	}

	@Override
	public void close() throws IOException {
		admin.close();
		connection.close();
	}

	public Connection getConnection() {
		return connection;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void createNamespace(String namespace) {
		try {
			NamespaceDescriptor nd = NamespaceDescriptor.create(namespace).build();
			admin.createNamespace(nd);
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void dropNamespace(String namespace, boolean force) {
		try {
			if (force) {
				TableName[] tableNames = admin.listTableNamesByNamespace(namespace);
				for (TableName name : tableNames) {
					admin.disableTable(name);
					admin.deleteTable(name);
				}
			}
		} catch (Exception e) {
			// ignore
		}
		try {
			admin.deleteNamespace(namespace);
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public boolean existsTable(String table) throws IOException {
		return existsTable(TableName.valueOf(table));
	}

	public boolean existsTable(TableName table) throws IOException {
		return admin.tableExists(table);
	}

	public void createTable(String table, String... colfams) throws IOException {
		createTable(TableName.valueOf(table), 1, null, colfams);
	}

	public void createTable(TableName table, String... colfams) throws IOException {
		createTable(table, 1, null, colfams);
	}

	public void createTable(String table, int maxVersions, String... colfams) throws IOException {
		createTable(TableName.valueOf(table), maxVersions, null, colfams);
	}

	public void createTable(TableName table, int maxVersions, String... colfams) throws IOException {
		createTable(table, maxVersions, null, colfams);
	}

	public void createTable(String table, byte[][] splitKeys, String... colfams) throws IOException {
		createTable(TableName.valueOf(table), 1, splitKeys, colfams);
	}

	//使用预分区创建表，形式如byte[][] splits = new byte[][]{Bytes.toBytes("row2000id"),Bytes.toBytes("row4000id"),Bytes.toBytes("row6000id"),Bytes.toBytes("row8000id")};
	public void createTable(TableName table, int maxVersions, byte[][] splitKeys, String... colfams)
			throws IOException {
		HTableDescriptor desc = new HTableDescriptor(table);
		for (String cf : colfams) {
			HColumnDescriptor coldef = new HColumnDescriptor(cf);
			coldef.setMaxVersions(maxVersions);
			desc.addFamily(coldef);
		}
		if (splitKeys != null) {
			admin.createTable(desc, splitKeys);
		} else {
			admin.createTable(desc);
		}
		LOG.info("Create table + " + table);
	}

	public void disableTable(String table) throws IOException {
		disableTable(TableName.valueOf(table));
	}

	public void disableTable(TableName table) throws IOException {
		admin.disableTable(table);
		LOG.info("Disable table " + table.toString());
	}

	public void dropTable(String table) throws IOException {
		dropTable(TableName.valueOf(table));
	}

	// 在HBase中必须先diable再执行drop操作。
	public void dropTable(TableName table) throws IOException {
		if (existsTable(table)) {
			if (admin.isTableEnabled(table)) {
				disableTable(table);
				LOG.info("Disable table " + table.toString());
			}
			admin.deleteTable(table);
			LOG.info("Drop table " + table.toString());
		}
	}

	public void put(String table, String row, String fam, String qual, String val) throws IOException {
		put(TableName.valueOf(table), row, fam, qual, val);
	}

	public void put(TableName table, String row, String fam, String qual, String val) throws IOException {
		Table tbl = connection.getTable(table);
		Put put = new Put(Bytes.toBytes(row));
		put.addColumn(Bytes.toBytes(fam), Bytes.toBytes(qual), Bytes.toBytes(val));
		tbl.put(put);
		tbl.close();
	}

	public void put(String table, String row, String fam, String qual, long ts, String val) throws IOException {
		put(TableName.valueOf(table), row, fam, qual, ts, val);
	}

	public void put(TableName table, String row, String fam, String qual, long ts, String val) throws IOException {
		Table tbl = connection.getTable(table);
		Put put = new Put(Bytes.toBytes(row));
		put.addColumn(Bytes.toBytes(fam), Bytes.toBytes(qual), ts, Bytes.toBytes(val));
		tbl.put(put);
		tbl.close();
	}

	public void put(String table, String[] rows, String[] fams, String[] quals, long[] ts, String[] vals)
			throws IOException {
		put(TableName.valueOf(table), rows, fams, quals, ts, vals);
	}

	public void put(TableName table, String[] rows, String[] fams, String[] quals, long[] ts, String[] vals)
			throws IOException {
		Table tbl = connection.getTable(table);
		for (String row : rows) {
			Put put = new Put(Bytes.toBytes(row));
			for (String fam : fams) {
				int v = 0;
				for (String qual : quals) {
					String val = vals[v < vals.length ? v : vals.length - 1];
					long t = ts[v < ts.length ? v : ts.length - 1];
					System.out.println("Adding: " + row + " " + fam + " " + qual + " " + t + " " + val);
					put.addColumn(Bytes.toBytes(fam), Bytes.toBytes(qual), t, Bytes.toBytes(val));
					v++;
				}
			}
			tbl.put(put);
		}
		tbl.close();
	}

	public void put(String table, String[] rows, String fam, String qual, String[] vals) throws IOException {
		put(TableName.valueOf(table), rows, fam, qual, vals);
	}

	/*
	 * 将一系列的数据put进table的fam:qual中，由rows和vals来定义写入的数据，它们的长期必须相等。
	 */
	public void put(TableName table, String[] rows, String fam, String qual, String[] vals) throws IOException {
		if (rows.length != vals.length) {
			LOG.error("rows.lenght {} is not equal to val.length {}", rows.length, vals.length);
		}
		try (BufferedMutator mutator = connection.getBufferedMutator(table);) {
			for (int i = 0; i < rows.length; i++) {
				Put p = new Put(Bytes.toBytes(rows[i]));
				p.addColumn(Bytes.toBytes(fam), Bytes.toBytes(qual), Bytes.toBytes(vals[i]));
				mutator.mutate(p);
				// System.out.println(mutator.getWriteBufferSize());
			}
			mutator.flush();

		}
	}
	
	/*
	 * 获取table表中，所有rows行中的，fam:qual列的值。
	 */
	public Result get(String table, String row, String fam, String qual) throws IOException {
		return get(TableName.valueOf(table), new String[]{row}, new String[]{fam}, new String[]{qual})[0];
	}
	
	public Result get(TableName table, String row, String fam, String qual) throws IOException {
		return get(table, new String[]{row}, new String[]{fam}, new String[]{qual})[0];
	}
	
	public Result[] get(TableName table, String[] rows, String fam, String qual) throws IOException {
		return get(table, rows, new String[]{fam}, new String[]{qual});
	}
	
	public Result[] get(String table, String[] rows, String fam, String qual) throws IOException {
		return get(TableName.valueOf(table), rows, new String[]{fam}, new String[]{qual});
	}

	public Result[] get(String table, String[] rows, String[] fams, String[] quals) throws IOException {
		return get(TableName.valueOf(table), rows, fams, quals);
	}

	/*
	 * 获取table表中，所有rows行中的，fams和quals定义的所有行。
	 */
	public Result[] get(TableName table, String[] rows, String[] fams, String[] quals) throws IOException {
		Table tbl = connection.getTable(table);
		List<Get> gets = new ArrayList<Get>();
		for (String row : rows) {
			Get get = new Get(Bytes.toBytes(row));
			get.setMaxVersions();
			if (fams != null) {
				for (String fam : fams) {
					for (String qual : quals) {
						get.addColumn(Bytes.toBytes(fam), Bytes.toBytes(qual));
					}
				}
			}
			gets.add(get);
		}
		Result[] results = tbl.get(gets);

		tbl.close();
		return results;
	}

	public void dump(String table) throws IOException {
		dump(TableName.valueOf(table));
	}

	public void dump(TableName table) throws IOException {
		try (Table t = connection.getTable(table); ResultScanner scanner = t.getScanner(new Scan())) {
			for (Result result : scanner) {
				dumpResult(result);
			}
		}
	}

	public void dumpResult(Result result) {
		for (Cell cell : result.rawCells()) {
			System.out.println("Cell: " + cell + ", Value: "
					+ Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
		}
	}
}
