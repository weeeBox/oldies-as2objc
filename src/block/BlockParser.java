package block;

import java.util.ArrayList;
import java.util.List;

import block.processors.ArrayLiteralProcessor;
import block.processors.DoubleToFloat;
import block.processors.FunctionCallProcessor;
import block.processors.LineProcessor;
import block.processors.ReplaceTokensProcessor;
import block.processors.StaticFieldProcessor;
import block.processors.StringLiteralProcessor;
import block.processors.VarLineParser;
import block.processors.VarProcessor;

public class BlockParser
{
	private List<LineProcessor> processors;
	
	public BlockParser()
	{
		processors = new ArrayList<LineProcessor>();
		processors.add(new StringLiteralProcessor());
		processors.add(new VarLineParser());
		processors.add(new ArrayLiteralProcessor());
		processors.add(new ReplaceTokensProcessor());
		processors.add(new FunctionCallProcessor());
		processors.add(new StaticFieldProcessor());
		processors.add(new DoubleToFloat());
		processors.add(new VarProcessor());
	}
	
	public List<String> parse(String body)
	{
		BlockIterator iter = new BlockIterator(body);
		
		List<String> lines = new ArrayList<String>();
		while (iter.hasNext())
		{
			String line = iter.next();
			lines.add(process(line));
		}
		
		return lines;
	}

	private String process(String line)
	{
		for (LineProcessor proc : processors)
		{
			line = proc.process(line);
		}
		
		return line;
	}
}