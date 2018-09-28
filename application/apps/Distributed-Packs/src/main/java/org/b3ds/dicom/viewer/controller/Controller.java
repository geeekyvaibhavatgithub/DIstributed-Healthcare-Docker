package org.b3ds.dicom.viewer.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.b3ds.dicom.viewer.DicomModule;
import org.b3ds.dicom.viewer.PatientModule;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.b3ds.dicom.hdfs.GetDicom;
import com.b3ds.dicom.postfile.UploadFile;

@RestController
public class Controller {
	
	private final Logger logger = LogManager.getLogger(Controller.class);
	
	private String url = "localhost:50070";
	private String username = "b3ds";
	private String operation = "OPEN";
	private UploadFile uploadFile;
	
	@RequestMapping("/")
	public String index(HttpServletRequest req)
	{
		logger.debug(req.getServletContext().getRealPath("/app"));
		String PATH = req.getServletContext().getRealPath("/app");
		String dirname = PATH.concat("\\dicom\\FELIX");
		logger.debug(dirname);
		File dir = new File(dirname);
		if(!dir.exists()){
			dir.mkdirs();
		}
		return "redirect:/app/index.html";
	}

	@RequestMapping(value="/getallpatients",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getAllPatients()
	{
		logger.debug("getallpatients invoked");
		PatientModule patients = new PatientModule();
		String response = patients.getAllPatients();
		
		ResponseEntity res = new ResponseEntity<>(response, HttpStatus.OK);
		return res;
	}
	
	
	@RequestMapping(value="/getsinglepatient", method=RequestMethod.POST, headers="Content-Type=application/json")
	@ResponseBody
	public ResponseEntity<String> getSinglePatientDetails(@RequestBody(required=true) Map<String, String> patientName)
	{
		logger.debug("reached");
		logger.debug(patientName);
		List<String> fields = new ArrayList<>();
		fields.add("id");		
		fields.add("PatientName");
		fields.add("PatientAge");
		fields.add("PatientBirthDate");
		fields.add("PatientID");
		fields.add("StudyID");
		fields.add("ReferringPhysicianName");
		fields.add("PerformingPhysicianName");
		fields.add("StudyDescription");
		fields.add("StudyDate");
		fields.add("InstitutionName");
		fields.add("Modality");
		fields.add("SeriesDescription");
		fields.add("FileName");
		fields.add("FileLocation");
		PatientModule module = new PatientModule();
		List<Map<?, ?>> pd = module.getSinglePatientDetails("PatientName",patientName.get("patientname").replaceAll("[\\^\\&\\.]", " "),fields);
		ResponseEntity res = new ResponseEntity<>(pd,HttpStatus.OK);
		logger.debug(res);
		return res;
	}
	
	@RequestMapping(value="/getseriesdetail", method=RequestMethod.POST, headers="Content-Type=application/json")
	@ResponseBody
	public ResponseEntity<String> getSeriesDetails(@RequestBody(required=true) Map<String, String> studyId, HttpServletRequest req) throws IOException, URISyntaxException
	{
		logger.debug("reached");
		logger.debug(studyId);
		List<String> fields = new ArrayList<>();
		fields.add("SeriesDescription");		
		fields.add("SeriesNumber");
		fields.add("SeriesInstanceUID");
		fields.add("FileName");
		fields.add("FileLocation");

		PatientModule module = new PatientModule();
		List<Map<?, ?>> pd = module.getSinglePatientDetails("StudyID",studyId.get("studyid").replaceAll("[\\^\\&\\.]", " "),fields);
		ResponseEntity res = new ResponseEntity<>(pd,HttpStatus.OK);
		logger.debug(res);
		return res;
	}
	
	
	@RequestMapping(value="/getImageUrl", method=RequestMethod.POST, headers="Content-Type=application/json")
	@ResponseBody
	public ResponseEntity<Map<String, String>> getSeriesImage(@RequestBody(required=true) Map<String, String> seriesId, HttpServletRequest req) throws IOException, URISyntaxException
	{
		logger.debug(seriesId);
		List<String> fields = Arrays.asList("FileName","FileLocation");
		PatientModule module = new PatientModule();
		List<Map<?, ?>> pd = module.getSinglePatientDetails("SeriesInstanceUID",seriesId.get("seriesId"),fields);
		logger.debug(pd);

		List<String> files = null;
		String fileLocation = null;

		Iterator<Map<?, ?>> itr = pd.iterator();
		
		while(itr.hasNext())
		{
			Map<?, ?>filelist = itr.next();
			if(filelist.containsKey("FileName"))
			{
				files = (List<String>) filelist.get("FileName");
				fileLocation = ((List<String>) filelist.get("FileLocation")).get(0);
			}
		}
		
		logger.debug(files);
		logger.debug(fileLocation);
		Iterator<String> fileItr = files.iterator();
		Map<String, String> result = null;
		while(fileItr.hasNext())
		{
			String fileName = fileItr.next();
			result = StoreImage(fileLocation, fileName, req, seriesId.get("seriesId"));
		}
		
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	@RequestMapping(value="/addCommentsAndImpressions", method=RequestMethod.POST, headers="Content-Type=application/json")
	@ResponseBody
	public ResponseEntity<?> addCommentsAndImpressions(@RequestBody(required=true) Map<String, Object> seriesId, HttpServletRequest req) throws IOException
	{
		logger.debug("Add comments and impression method invoked");
		logger.debug(seriesId);
		logger.debug(req.getContextPath());
		String PATH = req.getServletContext().getRealPath("/app");
		String file = PATH.concat("\\dicom\\"+seriesId.get("seriesId")+"\\"+seriesId.get("filename"));
		logger.debug(file);
		DicomModule module = new DicomModule();
		ArrayList<String> cList = (ArrayList<String>)seriesId.get("comments");
		ArrayList<String> iList = (ArrayList<String>)seriesId.get("impressions");
		
		String[] comments = new String[cList.size()];
		String[] impressions = new String[iList.size()];
		
		comments = cList.toArray(comments);
		impressions = iList.toArray(impressions);
		module.UpdateComments(file, file, comments, impressions);

		uploadFile = new UploadFile(new File(file), "http://192.168.1.16:7001/dicom");
		uploadFile.postFile();
		return new ResponseEntity<String>(new HashMap<String,String>().put("Status", "Saved"),HttpStatus.OK);
	}
	
	
	//delete this method
	public String testUpdate(String location) throws IOException
	{
        DicomInputStream din = new DicomInputStream(new File(location));
        Attributes attr = din.readDataset(-1, -1);
        logger.debug(attr.getString(Tag.ImageComments));
        logger.debug(attr.getString(Tag.Impressions));
		return attr.toString();
	}
	
	public Map<String, String> StoreImage (String path, String fileName, HttpServletRequest req, String seriesNumber) throws IOException, URISyntaxException
	{
		logger.debug(req.getContextPath());
		GetDicom dicom = new GetDicom();
		String PATH = req.getServletContext().getRealPath("/app");
		String dirname = PATH.concat("\\dicom\\"+seriesNumber);
		File dir = new File(dirname);
		
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		String serverLocation = dir+"\\";
		dicom.writeLocalFile(url,username,operation,path,fileName,serverLocation);
		Map<String, String> result = new HashMap<>();
		result.put("url", req.getContextPath()+"/dicom/"+seriesNumber+"/"+fileName);
		logger.debug(result);
		return result;
	}
	
}
