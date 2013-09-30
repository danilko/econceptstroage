/**
 * 
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Kai-Ting (Danil) Ko
 * 
 * Permission is hereby granted, free of charge, 
 * to any person obtaining a copy of this software 
 * and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including 
 * without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom 
 * the Software is furnished to do so, subject to the 
 * following conditions:
 * 
 * The above copyright notice and this permission notice 
 * shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY 
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS 
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE 
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 *//*

package com.econcept.services.test;

import java.io.File;
import java.io.IOException;


import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.econcept.provider.FileProvider;

public class FileServiceTest
{
	public final static String TEST_ACCOUNT_ID="TEST_ACCOUNT_ID";
	public final static String TEST_FILENAME="TEST_FILENAME.TXT";
	
	public File mFileDir;
	public File mFile;
	

	FileProvider mFileService;
	
	@BeforeTest
	public void init()
	{
		// Remove dummy account folder if there is one
		mFileDir = new File(System.getenv(FileProvider.DATA_DIR).toString()
									.replace("\\", "/")
									+ "/" + TEST_ACCOUNT_ID);
		if(mFileDir.exists())
		{
			cleanUpDirectory();
		}  // if
		
		mFileService = new FileProvider();
	}  // void initTest
	
	private void cleanUpDirectory()
	{
		// Clean up
		try {
			FileUtils.forceDelete(mFileDir);
		}  // try
		catch (IOException pException) {
			pException.printStackTrace();
			Assert.fail("Unable to clean up test data");
		}  // catch
	}  //  void cleanUpDirectory
	
	@AfterTest
	public void tearDown()
	{
		cleanUpDirectory();
	}  // void initTest
	
	@Test
	public void testGetFileList()
	{
		// Retrieve JSON string
		mFileService.getFileList(TEST_ACCOUNT_ID);
		
		//System.out.println(lResultJSON);
		
		// Check whether return file contains the dummy test file
		// If it is, then the test fails
		//Assert.assertEquals(lResultJSON.contains(TEST_FILENAME), false);
		
		// Create dummy test file
		// Set the output file and conver to UNIX styles
		mFile = new File(System.getenv(FileProvider.DATA_DIR).toString()
							.replace("\\", "/")
							+ "/" + TEST_ACCOUNT_ID + "/" + TEST_FILENAME);
		try {
			FileUtils.write(mFile, "TEST DATA");
		}  // try 
		catch (IOException pException) {
			pException.printStackTrace();
			Assert.fail("Unable to create dummy test file");
		}  // catch
		
		// Retrieve JSON string
		mFileService.getFileList(TEST_ACCOUNT_ID);
		
		System.out.println(lResultJSON);
		
		// Check whether return file contains the dummy test file
		// If it does not, the test fails
		Assert.assertEquals(lResultJSON.contains(TEST_FILENAME), true);
	}  // void testGetFileList
	
}  // class FileServiceTest*/