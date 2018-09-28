package org.b3ds.dicom.viewer;
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;

public class DicomModule {
	private final static Logger logger = LogManager.getLogger(DicomModule.class);
	
	public void UpdateComments(String source, String destination, String[] comments, String[] impressions) throws IOException
	{
        File tempImages = new File(source);
        DicomInputStream din = new DicomInputStream(tempImages);
        Attributes attr = din.readDataset(-1, -1);
        if(comments!=null)
        {
        	attr.setString(Tag.ImageComments, VR.LT, comments);
        }
        
        if(impressions!=null)
        {
        	attr.setString(Tag.Impressions, VR.LT, impressions);
        }
        
        DicomOutputStream dos = new DicomOutputStream(new File(destination));
        dos.writeDataset(null, attr);
        dos.close();
	}


}
