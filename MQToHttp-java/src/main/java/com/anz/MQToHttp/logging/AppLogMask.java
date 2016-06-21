package com.anz.MQToHttp.logging;

import java.io.InputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

import com.anz.common.compute.LogMaskUtils;
import com.google.gson.Gson;


@Plugin(name="mask", category="Converter")
@ConverterKeys({"msg"})
public class AppLogMask extends LogEventPatternConverter{
	
	List<String> regexArray; 
	
	/**
	 * Constructor, sets values of regexArray
	 * @param options
	 */
	public AppLogMask(String[] options) {
	
		super("msg","msg");
		
		// Get global mask regexs from local file system			
		regexArray.addAll(LogMaskUtils.getGlobalMasks("MASK_CONFIG", "regex.json"));
		
		InputStream inputStream = this.getClass().getResourceAsStream("regex.json");
		regexArray.addAll(LogMaskUtils.getPatternMasks(inputStream, "regex.json"));
			
	}
	
	/**
	 * Compulsory method
	 * @param options
	 * @return
	 */
	public static AppLogMask newInstance(final String[] options) {
	
		return new AppLogMask(options);
	
	}
	
	@Override
	/**
	 * Format the logging statements
	 * @param event
	 * @param outputMessage
	 */
	public void format(LogEvent event, StringBuilder outputMessage) {
		
		// Get message from event
		String message = event.getMessage().getFormattedMessage();	
		
		// Mask message
		message = LogMaskUtils.mask(regexArray, message, 'X');
		
		// Append masked message
		outputMessage.append(message);
		
	}

}