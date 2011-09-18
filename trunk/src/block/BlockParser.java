package block;

import java.util.ArrayList;
import java.util.List;

import block.processors.ConstructorLineProcessor;
import block.processors.LineProcessor;
import block.processors.StringLiteralProcessor;
import block.processors.VarLineParser;

public class BlockParser
{
	private List<LineProcessor> processors;
	
	public BlockParser()
	{
		processors = new ArrayList<LineProcessor>();
		processors.add(new StringLiteralProcessor());
		processors.add(new VarLineParser());
		processors.add(new ConstructorLineProcessor());
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