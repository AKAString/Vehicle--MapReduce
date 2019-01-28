package com.hadoop.qr.combinefile.reader;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

public class SmallFileCombineReader extends RecordReader<NullWritable, Text> {

	private CombineFileSplit split;
	private int current;
	//每次读取一行
	private LineRecordReader lineRecordReader = new LineRecordReader();
	
	private int totalSize = 0;

	public SmallFileCombineReader(CombineFileSplit split, TaskAttemptContext context, Integer index) {
		this.split = split;
		this.current = index;
	}
	@Override
	public void close() throws IOException {
		
	}
	@Override
	public NullWritable getCurrentKey() throws IOException, InterruptedException {
		
		return NullWritable.get();
	}

	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return lineRecordReader.getCurrentValue();
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {		
		return ((float)totalSize)/current;
	}

	@Override
	public void initialize(InputSplit arg0, TaskAttemptContext arg1) throws IOException, InterruptedException {
		split = (CombineFileSplit) arg0;
		FileSplit fileSplit = new FileSplit(split.getPath(current), split.getOffset(current), split.getLength(current),
				split.getLocations());
		lineRecordReader.initialize(fileSplit, arg1);
		this.totalSize = split.getPaths().length;
	}

	/**
	 * 用来key,value赋值
	 */
	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		if (current >= 0 && current < totalSize) {			
			return lineRecordReader.nextKeyValue();
		}

		return false;
	}

}
