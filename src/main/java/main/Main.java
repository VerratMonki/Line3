package main;

public class Main {
	public static void main(String[] args)
	{


	}
}

enum OutputType
{
XML ("XML"), HTML ("HTML"), TXT ("TXT");
String type;
	OutputType(String type)
	{
		this.type=type;
	}
	String getType()
	{
		return type;
	}
}