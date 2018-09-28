package org.b3ds.dicom.viewer.test;
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;
import org.junit.Test;


public class DicomTest {
	private final static Logger logger = LogManager.getLogger(DicomTest.class);
	public String source = "C:\\Users\\Zero.DESKTOP-UK4J6CU\\Desktop\\storage\\demo.dcm";
	public String destination = "C:\\Users\\Zero.DESKTOP-UK4J6CU\\Desktop\\storage\\updated.dcm";
	
	
	public void testPostDicomFile() throws IOException
	{
		String[] comments = new String[]{"Hello","dello"};
        File tempImages = new File(source);
        DicomInputStream din = new DicomInputStream(tempImages);
        Attributes attr = din.readDataset(-1, -1);
        attr.setString(Tag.ImageComments, VR.LT, comments);
        DicomOutputStream dos = new DicomOutputStream(new File(destination));
        dos.writeDataset(null, attr);
        dos.close();
		
	}
	
	public void testDicomFile() throws IOException
	{
		
        File tempImages = new File(destination);
        DicomInputStream din = new DicomInputStream(tempImages);
        Attributes attr = din.readDataset(-1, -1);
        logger.debug(attr.getString(Tag.ImageComments));
		
	}
}
