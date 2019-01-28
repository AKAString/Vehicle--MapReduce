package com.hadoop.qr.input.format;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReader;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;

import com.hadoop.qr.combinefile.reader.SmallFileCombineReader;
public class SmallFileCombieFormat extends CombineFileInputFormat<NullWritable, Text> {
	@Override
	public RecordReader<NullWritable, Text> createRecordReader(InputSplit arg0, TaskAttemptContext arg1)
			throws IOException {		
		//总共有几个小文件 ，当前读取的是谁
		CombineFileSplit combineFileSplit=(CombineFileSplit)arg0;
	    CombineFileRecordReader<NullWritable,Text> reader=new CombineFileRecordReader<NullWritable,Text>(combineFileSplit, arg1, SmallFileCombineReader.class);
		try {
			reader.initialize(combineFileSplit, arg1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reader;
	}

}
