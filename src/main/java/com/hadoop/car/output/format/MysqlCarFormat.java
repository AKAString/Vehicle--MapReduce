package com.hadoop.car.output.format;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.hadoop.car.dimention.CarKeyDimention;
import com.hadoop.car.dimention.CarValueDimention;
import com.hadoop.qr.connection.JDBCManager;
import com.hadoop.qr.sql.collector.ICollector;




public class MysqlCarFormat extends OutputFormat<CarKeyDimention, CarValueDimention> {

	@Override
	public RecordWriter<CarKeyDimention, CarValueDimention> getRecordWriter(TaskAttemptContext context)
			throws IOException, InterruptedException {
		
		Configuration conf = context.getConfiguration();
		Connection con = JDBCManager.getConnection(conf);
		/**
		 * 1.获取连接
		 */
		
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new MySqlRecordWriter(con, conf);
	}
	class MySqlRecordWriter extends RecordWriter<CarKeyDimention, CarValueDimention> {

		Connection con;
		Configuration conf;
		HashMap<String, PreparedStatement> psMaps = new HashMap<String, PreparedStatement>();
		int count = 0;

		public MySqlRecordWriter(Connection con, Configuration conf) {
			// TODO Auto-generated constructor stub
			this.con = con;
			this.conf = conf;
		}
		@Override
		public void write(CarKeyDimention key, CarValueDimention value) throws IOException, InterruptedException {
			PreparedStatement ps = psMaps.get(key.getFlag());
			if (ps == null) {
				try {
					ps = con.prepareStatement(conf.get(key.getFlag()));
					psMaps.put(key.getFlag(), ps);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
			count++;
			try {
				ICollector collect = (ICollector) Class.forName(conf.get("collector" + key.getFlag())).newInstance();
				collect.process(key, value, ps);
			} catch (Exception e) {
				
				e.printStackTrace();
			}

			if (count % 20 == 0) {
				try {
					ps.executeBatch();
					con.commit();
				} catch (SQLException e) {
				
					e.printStackTrace();
				}
			}

			
		}

		@Override
		public void close(TaskAttemptContext context) throws IOException, InterruptedException {
			try {
				for (String key : psMaps.keySet()) {
					PreparedStatement ps = psMaps.get(key);
					ps.executeBatch();
					con.commit();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					for (String key : psMaps.keySet()) {
						PreparedStatement ps = psMaps.get(key);
						ps.close();

					}
				} catch (SQLException e) {
				
					e.printStackTrace();
				}
				finally {
					try {
						con.close();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
				}
			}
		}

	}
		

	@Override
	public void checkOutputSpecs(JobContext context) throws IOException, InterruptedException {
	}
	@Override
	public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
		
		return new FileOutputCommitter(FileOutputFormat.getOutputPath(context), context);
	}
}
