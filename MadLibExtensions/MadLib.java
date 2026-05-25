import java.util.Scanner;
import java.util.ArrayList;

public class MadLib extends ConsoleProgram
{
    // Private constants
	private static final char PLACEHOLDER_START_CHARACTER = '[';
	private static final char PLACEHOLDER_END_CHARACTER = ']';
	
	// File reader to get the template from the "madlib.txt" file
	private MadLibFileReader madLibFileReader = new MadLibFileReader();
	
    public void run()
    {
        // Gets the contents of the file "madlib.txt" as a String
        // This will be the template for our Mad Lib Story
        String template = madLibFileReader.getMadLibTemplate();
        ArrayList<String> placeholders = getPlaceholders(template);
        
        if (placeholders == null)
        {
            System.out.println("Template is incorrect");
            return;
        }
        
        for(int i = 0; i < placeholders.size(); i++)
        {
            System.out.println(placeholders.get(i));
        }   
    }
    
    private ArrayList<String> getPlaceholders(String template)
    {
        ArrayList<String> placeholders = new ArrayList<String>();
        
        int count = 0;
        
        while (true)
        {
            int wordClass = template.indexOf(PLACEHOLDER_START_CHARACTER, count);
            
            if (wordClass == -1)
            {
                break;
            }
            
            int wordClassEnd = template.indexOf(PLACEHOLDER_END_CHARACTER, wordClass);
            
            if (wordClassEnd == -1)
            {
                return null;
            }
            
            String placeholder = template.substring(wordClass + 1, wordClassEnd);
            
            placeholders.add(placeholder);
            
            count = wordClassEnd + 1;
        }
        return placeholders;
    }
    
    private ArrayList<String> getReplacements(ArrayList<String> placeholders)
    {
        ArrayList<String> replacements = new ArrayList<String>();
        
        for (String placeholder: placeholders)
            {
                String userInput = readLine("Write a " + placeholder + ": ");
                replacements.add(userInput);
            }
            return replacements;
    }

    private String replacePlaceholder(String template, String placeholder, String replacement)
    {
        int index = template.indexOf(placeholder);
        
        if (index == -1)
        {
            return template;
        }
        
        String begin = template.substring(0, index);
        String after = template.substring(index + placeholder.length());
        
        return begin + replacement + after;
    }
    
    private String replaceAllPlaceholders(String template)
    {
        ArrayList<String> placeholders = getPlaceholders(template);
         
        if (placeholders == null)
        {
            return "Template is wrong";
        }
        
        if (placeholders.size() == 0)
        {
            return template;
        }
        
        ArrayList<String> replacements = getReplacements(placeholders);
    
        String result = template;
        
        for (int i = 0; i < placeholders.size(); i++)
        {
            String placeholder = "[" + placeholders.get(i) + "]";
            String replacement = replacements.get(i);
            
            int index = result.indexOf(placeholder);
            
            if (index == 0)
            {
                replacement = makeCapital(replacement);
            }
            else if (index > 1)
            {
                char previous = result.charAt(index - 2);
                
                if (previous == '.' || previous == '!' || previous == '?')
                {
                    replacement = makeCapital(replacement);
                }
            }
            result = replacePlaceholder(result, placeholder, replacement);
        }
        return result;
    }
    
    private String makeCapital(String word)
    {
        if (word.length() == 0){
            return word;
        }
        
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}